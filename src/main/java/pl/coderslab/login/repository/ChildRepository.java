package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.Child;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChildRepository extends JpaRepository<Child,Integer> {
List<Child> findAllByParentId(int id);

    Child findById(int id);

    Child deleteById(int id);

    Optional<Child> findChildByEmail(String email);

    Child findByEmail(String email);

    Child findByName(String name);
}
