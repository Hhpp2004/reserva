package backend.system.reserva.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Mesas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_mesa")
    private Long id;
    private String nome;
    private int capacidade;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_mesa")
    private StatusMesa status;
    
    public Mesas(String nome, int capacidade, StatusMesa status) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.status = status;
    }
}
