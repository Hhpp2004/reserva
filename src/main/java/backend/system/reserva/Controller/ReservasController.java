package backend.system.reserva.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import backend.system.reserva.DTO.ReservaDTO;
import backend.system.reserva.Model.Reserva;
import backend.system.reserva.Model.User;
import backend.system.reserva.Repository.UserRepository;
import backend.system.reserva.Service.ReservasService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ReservasController {
    private final ReservasService rs;
    private final UserRepository ur;

    @GetMapping("/reservas")
    @PreAuthorize("hasAuthority('SCOPE_CLIENTE')")
    public ResponseEntity<List<Reserva>> lista(JwtAuthenticationToken user) 
    {   
        Optional<User> aux = ur.findById(Long.valueOf(user.getName()));
        if(aux.isPresent())
        {
            List<Reserva> lista = rs.lista(aux);
            return ResponseEntity.ok(lista);
        }
        else
        {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/reserva")
    @PreAuthorize("hasAuthority('SCOPE_CLIENTE')")
    public ResponseEntity<String> create(@RequestBody ReservaDTO novaReserva, JwtAuthenticationToken userToken) throws Exception
    {
        Optional<User> userOptional = ur.findByNome(userToken.getName());
        Optional<User> email = ur.findByEmail(userToken.getName());
        long flag = 0l;
        if (userOptional.isEmpty() || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }
        if(userOptional.isPresent())
        {
            flag = rs.createReserva(novaReserva, userOptional.get());
        }
        else if(!email.isPresent())
        {
            flag = rs.createReserva(novaReserva, email.get());
        }
        
        return ResponseEntity.ok("Reserva criada " + flag);
    }

    @PatchMapping("/reserva/{id}/cancelar")
    @PreAuthorize("hasAuthority('SCOPE_CLIENTE')")
    public ResponseEntity<String> cancelar(@PathVariable long id,JwtAuthenticationToken user) throws Exception
    {
        Optional<User> aux = ur.findById(Long.valueOf(user.getName()));
        boolean flag = rs.cancelar(id, aux.get());
        return ResponseEntity.ok("Reserva cancelado: "+flag);
    }
}
