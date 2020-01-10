package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.Exercise;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    List<Exercise> findAllByChildId(int id);

    @Query("SELECT e from Exercise e where e.deadLine > current_timestamp and e.child.id=?1 and e.special=1 ")
    List<Exercise> findSpecialExercises(int id);

    Exercise findById(int id);
}
