package pl.coderslab.login.service;

import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Exercise;
import pl.coderslab.login.repository.ExerciseRepository;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExerciseService {

    ExerciseRepository exerciseRepository;

    ChildService childService;

    EmailService emailService;

    public ExerciseService(ExerciseRepository exerciseRepository, ChildService childService, EmailService emailService) {
        this.exerciseRepository = exerciseRepository;
        this.childService = childService;
        this.emailService = emailService;
    }

    public void saveSpecialExercise(Exercise exercise, List<Child> children, String time) {


        LocalDateTime dateTime = getLocalDateTimeFromString(time);

        for (int i = 0; i < children.size(); i++) {
            Child child = children.get(i);
            Exercise exerciseToSave = new Exercise();
            exerciseToSave.setDescription(exercise.getDescription());
            exerciseToSave.setSpecial(1);
            exerciseToSave.setPoints(exercise.getPoints());
            exerciseToSave.setDeadLine(dateTime);
            exerciseToSave.setChild(child);
            exerciseRepository.save(exerciseToSave);
            mailCheckIfValidAndSend(child);
        }

    }

    public void mailCheckIfValidAndSend(Child child) {
        String email = child.getEmail();
        if (childService.validateMail(email)) {
            emailService.sendSimpleMessage(email, "Email test", "Dostępne nowe zadanie specjalne");
        }
    }

    private LocalDateTime getLocalDateTimeFromString(String time) {

        String str = (LocalDate.now().toString() + " " + time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(str, formatter);
    }

    public List<Exercise> getSpecialExercises() {
        return exerciseRepository.findSpecialExercises(childService.getCurrentChild().getId());
    }

    public List<Exercise> findAllByChildId(int id) {
        return exerciseRepository.findAllByChildId(id);
    }
}
