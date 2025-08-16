package backend.system.reserva.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_reserva")
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Mesas mesa;
    private LocalDateTime horario;
    @Column(name = "status_reserva")
    @Enumerated(EnumType.ORDINAL)
    private StatusReserva status;
    
    public Reserva(Mesas mesa, LocalDateTime horario, StatusReserva status) {
        this.mesa = mesa;
        this.horario = horario;
        this.status = status;
    }
}
