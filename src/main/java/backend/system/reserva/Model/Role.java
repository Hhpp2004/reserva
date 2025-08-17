package backend.system.reserva.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Role {
    public enum Valores {
        ADMIN(1l,"ADMIN"),
        CLIENTE(2l,"CLIENTE");

        String nome;
        long id;

        private Valores(long id,String nome) {
            this.id = id;
            this.nome = nome.toUpperCase();
        }

        public long getId() {
            return id;
        }

    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_role")
    private long id;
    private String nome;
    
    public Role(long id, String nome) {
        this.id = id;
        this.nome = nome.toUpperCase();
    }
}