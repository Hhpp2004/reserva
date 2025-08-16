package backend.system.reserva.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import backend.system.reserva.DTO.ReservaDTO;
import backend.system.reserva.Exception.Cancelamento;
import backend.system.reserva.Exception.Capacidade;
import backend.system.reserva.Exception.Inativo;
import backend.system.reserva.Exception.NotFoundTable;
import backend.system.reserva.Exception.Reservado;
import backend.system.reserva.Model.Mesas;
import backend.system.reserva.Model.Reserva;
import backend.system.reserva.Model.StatusMesa;
import backend.system.reserva.Model.StatusReserva;
import backend.system.reserva.Model.User;
import backend.system.reserva.Repository.MesaRepository;
import backend.system.reserva.Repository.ReservaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservasService {
    private final ReservaRepository rr;
    private final MesaRepository mr;

    // ok
    public List<Reserva> lista(Optional<User> aux) {
        List<Reserva> lista = rr.findByUser(aux.get());
        if (!lista.isEmpty()) {
            return lista;
        } else {
            return null;
        }
    }

    // ok
    public long createReserva(ReservaDTO reserva, User user) throws Exception {
        Mesas mesa = mr.findByNome(reserva.mesa().getNome())
                .orElseThrow(() -> new NotFoundTable());
        if (!mesa.getStatus().equals(StatusMesa.inativa)) {
            if (rr.existsByMesaAndHorario(mesa, reserva.horario())) {
                throw new Reservado();
            }

            if (mesa.getCapacidade() <= reserva.capacidade()) {
                throw new Capacidade();
            }
            Reserva reservado = reserva.createReserva();
            reservado.setUser(user);
            mesa.setStatus(StatusMesa.reservada);
            reservado.setMesa(mesa);
            reservado.setStatus(StatusReserva.ativo);
            return rr.save(reservado).getId();
        }
        else
        {
            throw new Inativo();
        }

    }

    // ok
    public boolean cancelar(long id, User user) throws Exception {
        Reserva reserva = rr.findById(id).get();
        Mesas mesa = mr.findById(reserva.getMesa().getId()).get();
        if (reserva.getUser().getId() == user.getId()) {
            reserva.setStatus(StatusReserva.cancelado);
            mesa.setStatus(StatusMesa.disponivel);
            rr.save(reserva);
            return true;
        } else {
            throw new Cancelamento();
        }
    }
}
