package pl.coderslab.login.service;

import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Exercise;
import pl.coderslab.login.repository.ExerciseRepository;

@Service
public class ExerciseService {

    ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise saveSpecialExercise(Exercise exercise){
        exercise.setSpecial(1);
        return exerciseRepository.save(exercise);
    }
}
