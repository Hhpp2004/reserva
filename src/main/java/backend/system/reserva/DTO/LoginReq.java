package backend.system.reserva.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginReq(@NotBlank String senha, @NotBlank String email) {
    
}
