package backend.system.reserva.DTO;



import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import backend.system.reserva.Model.Role;
import backend.system.reserva.Model.User;
import jakarta.validation.constraints.NotBlank;

public record CreateUser(@NotBlank String nome, @NotBlank String senha, @NotBlank String email,@NotBlank Role role) {
    public User createUserClient(PasswordEncoder passwordEncoder,Role role)
    {
        return new User(nome, email, passwordEncoder.encode(senha()),Set.of(role));
    }
}