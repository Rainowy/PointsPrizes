package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findByEmail(String email);
}
