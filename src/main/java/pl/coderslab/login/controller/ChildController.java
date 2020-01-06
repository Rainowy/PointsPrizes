package pl.coderslab.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Goal;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.service.ChildService;

import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/child")
//@SessionAttributes("childParent")
public class ChildController {

    @Autowired
    private ChildService childService;

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
    public ModelAndView exercises(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exercises",childService.findExercisesByChildId(childService.getCurrentChild().getId()));
        modelAndView.setViewName("child/exercises");
        return modelAndView;
    }

    @GetMapping("/goals")
    public ModelAndView goals(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goals",childService.findGoalsByChildId(childService.getCurrentChild().getId()));
        modelAndView.setViewName("child/goals");
        return modelAndView;
    }

    @GetMapping("/addGoal")
    public ModelAndView addGoal(){
        ModelAndView modelAndView = new ModelAndView();
        Goal goal = new Goal();
        modelAndView.addObject("goal",goal);
        modelAndView.setViewName("child/addGoal");
        return modelAndView;
    }

    @PostMapping("/addGoal")
    public ModelAndView addGoal(@Valid Goal goal, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("child/addGoal");
            return modelAndView;
        }
        childService.saveChild(goal);
//        Child currentChild = childService.getCurrentChild();
//
//        goal.setChild(currentChild);
//        childService.saveChild(currentChild);
//        System.out.println(goal);


        modelAndView.setViewName("child/addGoal");
        return modelAndView;
    }
}
