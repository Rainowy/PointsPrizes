package pl.coderslab.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Exercise;
import pl.coderslab.login.entity.Goal;
import pl.coderslab.login.repository.GoalRepository;

@Service
public class GoalService {

    private GoalRepository goalRepository;

    private ChildService childService;

    public GoalService(GoalRepository goalRepository, ChildService childService) {
        this.goalRepository = goalRepository;
        this.childService = childService;
    }

    public Goal saveGoal(Goal goal, Exercise exercise) {
        goal.setChild(childService.getCurrentChild());
        exercise.setChild(childService.getCurrentChild());
        goal.addExercise(exercise);
        return goalRepository.save(goal);
    }
}
