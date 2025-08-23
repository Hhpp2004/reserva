package backend.system.reserva.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthProvider {
    LOCAL(1l,"LOCAL"),
    GOOGLE(2l,"GOOGLE");

    private long id;
    private String nome;
}
