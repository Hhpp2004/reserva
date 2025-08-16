package backend.system.reserva.DTO;

import backend.system.reserva.Model.Mesas;
import backend.system.reserva.Model.StatusMesa;
import jakarta.validation.constraints.NotBlank;

public record MesaDTO(@NotBlank String nome,@NotBlank int capacidade,@NotBlank StatusMesa status) {
    public Mesas create()
    {
        return new Mesas(nome, capacidade, status);
    }
}
