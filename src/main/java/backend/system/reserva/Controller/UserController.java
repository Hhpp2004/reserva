package backend.system.reserva.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.system.reserva.DTO.LoginReq;
import backend.system.reserva.DTO.LoginRes;
import backend.system.reserva.Service.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UserController {
    private final UserService us;

    @PostMapping("/cadastro")
    public ResponseEntity<String> createUser(@RequestBody backend.system.reserva.DTO.createUser newUser) throws Exception
    {
        Long id = us.createUser(newUser);
        if(id != 0l)
        {
            return ResponseEntity.ok("User "+id+" create");
        }
        else
        {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq login) throws Exception
    {
        LoginRes user = us.login(login);
        if(user != null)
        {
            return ResponseEntity.ok(user);
        }
        else
        {
            return ResponseEntity.badRequest().build();
        }
    }
}
