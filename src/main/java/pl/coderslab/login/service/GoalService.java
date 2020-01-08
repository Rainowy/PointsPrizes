package pl.coderslab.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Exercise;
import pl.coderslab.login.entity.Goal;
import pl.coderslab.login.repository.GoalRepository;

@Service
public class GoalService {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    ChildService childService;

    public Goal saveGoal(Goal goal, Exercise exercise) {
        goal.setChild(childService.getCurrentChild());
        exercise.setChild(childService.getCurrentChild());
        goal.addExercise(exercise);
        return goalRepository.save(goal);
    }
}
