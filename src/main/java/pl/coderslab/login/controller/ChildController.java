package pl.coderslab.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Exercise;
import pl.coderslab.login.entity.Goal;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.service.ChildService;
import pl.coderslab.login.service.GoalService;

import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/child")
//@SessionAttributes("childParent")
public class ChildController {

    private Exercise exercise;

    @Autowired
    private ChildService childService;

    @Autowired
    private GoalService goalService;

    @GetMapping("/edit")
    public ModelAndView editChild() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("child", childService.getCurrentChild());
        modelAndView.setViewName("child/editProfile");
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editChild(@Valid Child child, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        childService.existenceValidator(child, result);

        if (result.hasErrors()) {
            modelAndView.setViewName("child/editProfile");
            return modelAndView;
        }
        childService.saveChild(child);
        modelAndView.setViewName("child/child-panel");
        return modelAndView;
    }

    @GetMapping("/exercises")
    public ModelAndView exercises() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exercises", childService.findExercisesByChildId(childService.getCurrentChild().getId()));
        modelAndView.setViewName("child/exercises");
        return modelAndView;
    }

    @GetMapping("/goals")
    public ModelAndView goals() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goals", childService.findGoalsByChildId(childService.getCurrentChild().getId()));
        modelAndView.setViewName("child/goals");
        return modelAndView;
    }

    @GetMapping("/addGoal")
    public ModelAndView addGoal(@ModelAttribute("newGoal") String newGoal) {
        ModelAndView modelAndView = new ModelAndView();
//        if (!newGoal.isEmpty()) {
//            modelAndView.addObject("newGoal", "true");
//        }
//        System.out.println("aaaaa" + newGoal); //jest pusty
        //tu go ustawia
        modelAndView.addObject("newGoal", newGoal);
        Goal goal = new Goal();
        modelAndView.addObject("goal", goal);
        modelAndView.setViewName("child/addGoal");
        return modelAndView;
    }

    @PostMapping("/addGoal")
    public ModelAndView addGoal(@Valid Goal goal, BindingResult result,
                                @RequestParam(required = false) String newGoal) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("child/addGoal");
            return modelAndView;
        }


//        if (goal == null && newGoal.isEmpty()) {
//            result.rejectValue("goal", "error.user", "Wybierz cel lub dodaj nowy");
//        }

        if (newGoal != null && !newGoal.isEmpty()) {
            this.exercise.setChild(childService.getCurrentChild());
            goal.addExercise(exercise);
            childService.saveChild(goal);
        } else {
            childService.saveChild(goal);
        }
//        modelAndView.addObject("successMessage", "Cel został dodany");
        modelAndView.setViewName("child/exercises");
        return modelAndView;
    }

    @GetMapping("/addExercise")
    public ModelAndView addExercise() {
        ModelAndView modelAndView = new ModelAndView();
        Exercise exercise = new Exercise();
        modelAndView.addObject("exercise", exercise);
        modelAndView.addObject("goals", childService.findGoalsByChildId(childService.getCurrentChild().getId()));
        modelAndView.setViewName("child/addExercise");
        return modelAndView;
    }

    @PostMapping("/addExercise")
    public ModelAndView addExercise(@Valid Exercise exercise, BindingResult result,
                                    @RequestParam(required = false) String newGoal,
                                    RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (exercise.getGoal() == null && newGoal == null) {
            result.rejectValue("goal", "messageCode", "Wybierz cel lub dodaj nowy");
        }
        if (result.hasErrors()) {
            modelAndView.setViewName("child/addExercise");
            modelAndView.addObject("goals", childService.findGoalsByChildId(childService.getCurrentChild().getId()));
            return modelAndView;
        }
        System.out.println("cel " + exercise.getGoal() );

        System.out.println("NEW GOAL " + newGoal);





        if (newGoal == null) {
            childService.saveChild(exercise);
        }
        //TODO atrybut w addGoal nie jest ustawiany bo newGoaL nie jest przesłany do addGoal controlera
        else {
            this.exercise = exercise;
            redirectAttributes.addFlashAttribute("newGoal", "true");
//            modelAndView.addObject("newGoal","true");
            modelAndView.setViewName("redirect:/child/addGoal");
            return modelAndView;
        }


        System.out.println("EXERCISE " + exercise);
        System.out.println("NEW GOAL " + newGoal);
//        modelAndView.setViewName("child/addExercise");
        modelAndView.setViewName("child/exercises");
        return modelAndView;
    }
}
