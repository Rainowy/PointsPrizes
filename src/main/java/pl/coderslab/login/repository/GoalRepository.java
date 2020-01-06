package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.Goal;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Integer> {

    List<Goal> findAllByChildId(int id);
}
