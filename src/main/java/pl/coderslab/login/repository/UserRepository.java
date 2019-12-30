package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.User;

//@Repository("userRepository")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
