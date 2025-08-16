package backend.system.reserva.DTO;

import java.time.LocalDateTime;

import backend.system.reserva.Model.Mesas;
import backend.system.reserva.Model.Reserva;
import backend.system.reserva.Model.StatusReserva;
import jakarta.validation.constraints.NotBlank;

public record ReservaDTO(@NotBlank Mesas mesa, @NotBlank LocalDateTime horario, @NotBlank int capacidade) {
    public Reserva createReserva()
    {
        return new Reserva(mesa, horario, StatusReserva.ativo);
    }
}
