package backend.system.reserva.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import backend.system.reserva.DTO.MesaDTO;
import backend.system.reserva.Model.Mesas;
import backend.system.reserva.Service.MesaService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MesaController {
    private final MesaService ms;

    @GetMapping("/mesas")
    @PreAuthorize("hasAuthority('SCOPE_CLIENTE')")
    public ResponseEntity<List<Mesas>> lista() {
        List<Mesas> lista = ms.lista();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @PostMapping("/mesas")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> createMesa(@RequestBody MesaDTO novaMesa) {
        long id = ms.createMesa(novaMesa);
        return ResponseEntity.ok("Mesa " + id + " criada");
    }

    @PatchMapping("/mesas/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> reservaMesa(@RequestBody MesaDTO novoDado,@PathVariable Long id) {
        Mesas mesa = ms.atualizacao(novoDado,id);
        if (mesa != null) {
            return ResponseEntity.ok("Mesa " + mesa.getNome() + " atualizado");
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/mesas/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> apagarMesa(@PathVariable long id)
    {
        Boolean flag = ms.delete(id);
        if(flag == true)
        {
            return ResponseEntity.ok("Mesa "+id+" apagada");
        }
        else
        {
            return ResponseEntity.noContent().build();
        }
    }
}
