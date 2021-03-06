package pl.coderslab.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.entity.User;
import pl.coderslab.login.service.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {


    private ParentService parentService;

    private ExerciseService exerciseService;

    private ChildService childService;

    public LoginController(ParentService parentService, ExerciseService exerciseService, ChildService childService) {
        this.parentService = parentService;
        this.exerciseService = exerciseService;
        this.childService = childService;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * register parent
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Parent parent = new Parent();
        modelAndView.addObject("parent", parent);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewParent(@Valid Parent parent, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Parent parentExists = parentService.findParentByEmail(parent.getEmail());
        if (parentExists
                != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            parentService.saveParent(parent);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("parent", new Parent());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping("/parent/panel")
    public ModelAndView parent() {
        ModelAndView modelAndView = new ModelAndView();
        List<Integer> special = new ArrayList<>();
        modelAndView.addObject("special", special);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Parent parent = parentService.findParentByEmail(auth.getName());
        List<Child> allChildrenByParent = parentService.findAllChildrenByParent(parent.getId());
        modelAndView.addObject("children", allChildrenByParent);

//        modelAndView.addObject("userName", "Welcome " + parent.getName());
//        + " " + parent.getName() + " (" + parent.getEmail() + ")");
//        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("userName", "Witaj " + parent.getName() + " " + parent.getLastName() + " (" + parent.getEmail() + ")");
        modelAndView.setViewName("parent/parent-panel");
        return modelAndView;
    }

    @GetMapping("/child/panel")
    public ModelAndView child(@AuthenticationPrincipal Principal child) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName", "Witaj " + child.getName());
        modelAndView.addObject("userPoints", "Twoja aktualna liczba punktów: " + childService.getCurrentChild().getPoints());
        modelAndView.addObject("special", exerciseService.getSpecialExercises());
        modelAndView.addObject("goals", childService.findGoalsByChildId());

        modelAndView.addObject("adminMessage", "Panel Dzieci");
        modelAndView.setViewName("child/child-panel");
        return modelAndView;
    }
}