package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.Goal;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findAllByChildId(int id);

    @Query("select g.points from Goal g where g.id =?1")
    Optional<Integer> goalPoints(int id);

}
