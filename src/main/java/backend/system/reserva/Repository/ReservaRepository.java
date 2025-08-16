package backend.system.reserva.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.system.reserva.Model.Mesas;
import backend.system.reserva.Model.Reserva;
import backend.system.reserva.Model.User;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUser(User user);
    Optional<Reserva> findByMesa(Mesas mesa);
    boolean existsByMesaAndHorario(Mesas mesa, LocalDateTime horario);
}
