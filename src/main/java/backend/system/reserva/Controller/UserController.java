package backend.system.reserva.Controller;

import java.net.URI;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.system.reserva.DTO.CreateUser;
import backend.system.reserva.DTO.LoginReq;
import backend.system.reserva.DTO.LoginRes;
import backend.system.reserva.Model.User;
import backend.system.reserva.Repository.UserRepository;
import backend.system.reserva.Service.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UserController {
    private final UserService us;
    private final UserRepository ur;

    @PostMapping("/cadastro")
    public ResponseEntity<String> createUser(@RequestBody CreateUser newUser) throws Exception
    {
        Long id = us.createUser(newUser);
        return ResponseEntity.ok("User "+id+" create");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
       if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Não autenticado");
        }

        String email = authentication.getName();
        User user = ur.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("nome", user.getNome());
        response.put("email", user.getEmail());
        response.put("picture", user.getPictures());
        response.put("provider", user.getProvider());
        response.put("roles", user.getRole());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/google")
    public ResponseEntity<?> googleAuth() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Redirecionando para Google OAuth");
        response.put("redirectUrl", "/oauth2/authorization/google");
        
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("redirect:/oauth2/authorization/google"))
            .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok().body("Logout realizado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq login) throws Exception
    {
        LoginRes user = us.login(login);
        return ResponseEntity.ok(user);
    }
}
