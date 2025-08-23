package backend.system.reserva.Repository;

import java.security.AuthProvider;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.system.reserva.Model.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNome(String nome);
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderId(String providerId);
    Optional<User> findByEmailAndProvider(String email,AuthProvider provider);
    boolean existsByEmail(String email);
    boolean existsByEmailAndProvider(String email, AuthProvider provider);
}
