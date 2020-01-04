package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.Child;

import java.util.List;


@Repository
public interface ChildRepository extends JpaRepository<Child,Integer> {
List<Child> findAllByParentId(int id);

    Child findById(int id);

    Child deleteById(int id);
}
