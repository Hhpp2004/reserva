package backend.system.reserva.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusReserva {
    ativo(1l),
    cancelado(2l);

    private long id;
}
