package pl.coderslab.login.service;

import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Exercise;
import pl.coderslab.login.repository.ExerciseRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExerciseService {

    ExerciseRepository exerciseRepository;

    ChildService childService;

    public ExerciseService(ExerciseRepository exerciseRepository, ChildService childService) {
        this.exerciseRepository = exerciseRepository;
        this.childService = childService;
    }

    public void saveSpecialExercise(Exercise exercise, List<Child> children, String time) {

        LocalDateTime dateTime = getLocalDateTimeFromString(time);

        for (int i = 0; i < children.size(); i++) {
            Exercise exerciseToSave = new Exercise();
            exerciseToSave.setDescription(exercise.getDescription());
            exerciseToSave.setSpecial(1);
            exerciseToSave.setPoints(exercise.getPoints());
            exerciseToSave.setDeadLine(dateTime);
            exerciseToSave.setChild(children.get(i));
            exerciseRepository.save(exerciseToSave);
        }
    }

    private LocalDateTime getLocalDateTimeFromString(String time) {

        String str = (LocalDate.now().toString() + " " + time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(str, formatter);
    }

    public List<Exercise> getSpecialExercises(){
        return exerciseRepository.findSpecialExercises(childService.getCurrentChild().getId());
    }
}
