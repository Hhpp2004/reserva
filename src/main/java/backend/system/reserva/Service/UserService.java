package backend.system.reserva.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import backend.system.reserva.DTO.CreateUser;
import backend.system.reserva.DTO.LoginReq;
import backend.system.reserva.DTO.LoginRes;
import backend.system.reserva.Exception.BadCrendential;
import backend.system.reserva.Exception.EmailJaEmUso;
import backend.system.reserva.Exception.NotFound;
import backend.system.reserva.Exception.OAuth2Authentication;
import backend.system.reserva.Exception.RunTimeError;
import backend.system.reserva.Model.AuthProvider;
import backend.system.reserva.Model.Role;
import backend.system.reserva.Model.User;
import backend.system.reserva.Repository.RoleRepository;
import backend.system.reserva.Repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder encoder;
    private final UserRepository ur;
    private final RoleRepository rr;
    private final JwtEncoder jwtEncoder;

    // ok
    public Long createUser(CreateUser newUser) throws Exception {
        Role userRole = rr.findByNome(Role.Valores.CLIENTE.name())
                .orElseThrow(() -> new RunTimeError());
        long id = 0l;
        if (!ur.existsByEmail(newUser.email())) {
            User user = newUser.createUserClient(encoder, userRole);
            user.setRole(Set.of(userRole));
            id = ur.save(user).getId();
            return id;
        } else {
            throw new EmailJaEmUso();
        }
    }

    // ok
    public LoginRes login(LoginReq user) throws Exception {
        if (ur.existsByEmail(user.email())) {
            Optional<User> lista = ur.findByEmail(user.email());
            if (lista.isPresent() && lista.get().isLoginCorrect(user, encoder)) {
                Instant now = Instant.now();
                Long tempo = 3600L;
                var scope = lista.get().getRole().stream().map(Role::getNome).collect(Collectors.joining(""));
                var claims = JwtClaimsSet.builder()
                        .issuer("Security")
                        .subject(lista.get().getId().toString())
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(tempo))
                        .claim("scope", scope)
                        .build();
                var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
                return new LoginRes(jwtValue, tempo);
            } else {
                throw new BadCrendential();
            }
        } else {
            throw new BadCrendential();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = ur.findByEmail(username).orElseThrow(() -> new NotFound());
        if (user.isOAuth2User()) {
            throw new BadCrendential();
        } else {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getSenha())
                    .authorities(user.getRole().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getNome()))
                            .toArray(SimpleGrantedAuthority[]::new))
                    .build();
        }
    }

     private OAuth2User processOAuth2User(OAuth2AuthorizationRequest userRequest, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String picture = (String) attributes.get("picture");
        String providerId = (String) attributes.get("sub"); 

        if (email == null || email.isEmpty()) {
            throw new OAuth2Authentication();
        }

        Optional<User> userOptional = ur.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            
            if (user.getProvider() != AuthProvider.GOOGLE) {
                throw new OAuth2AuthenticationException(
                    "Email já registrado com autenticação local. Use login com email/senha.");
            }
            
            user.setNome(name);
            user.setPictures(picture);
        } else {
            user = new User();
            user.setNome(name);
            user.setEmail(email);
            user.setProviderId(providerId);
            user.setPictures(picture);
            user.setProvider(AuthProvider.GOOGLE);
            user.setSenha(null); 
            user.setRole(Set.of(new Role(2l,"CLIENTE")));
        }

        user = ur.save(user);
        
        return oAuth2User;
    }
}