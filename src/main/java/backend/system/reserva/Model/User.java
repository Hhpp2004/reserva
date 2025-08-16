package backend.system.reserva.Model;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import backend.system.reserva.DTO.LoginReq;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user")
    private Long id;    
    @Column(unique = false)
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name= "id_role"))
    private Set<Role> role;
    
    public User(String nome, String email, String senha,Set<Role> role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public Boolean isLoginCorrect(LoginReq password, PasswordEncoder senha)
    {
        return senha.matches(password.senha(), this.senha);
    }
}
