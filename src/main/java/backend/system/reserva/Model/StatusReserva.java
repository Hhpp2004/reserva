package backend.system.reserva.Model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusReserva {
    ativo(1l),
    cancelado(2l);

    private long id;
}
