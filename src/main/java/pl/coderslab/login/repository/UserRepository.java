package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.User;

import java.util.Optional;

//@Repository("userRepository")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User findByEmail(String email);

User findUserByEmail(String email);
}
