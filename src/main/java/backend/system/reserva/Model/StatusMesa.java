package backend.system.reserva.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusMesa {
    disponivel(1l),
    reservada(2l),
    inativa(3l);

    private long id;
}
