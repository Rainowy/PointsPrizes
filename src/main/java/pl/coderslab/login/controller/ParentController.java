package pl.coderslab.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.login.Wrapper.SpecialExercise;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Exercise;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.service.ChildService;
import pl.coderslab.login.service.ParentService;

import javax.print.DocFlavor;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private ChildService childService;

    @GetMapping("/addChild")
    public ModelAndView addChild() {
        ModelAndView modelAndView = new ModelAndView();
        Child child = new Child();
        modelAndView.addObject("child", child);
        modelAndView.setViewName("parent/addChild");
        return modelAndView;
    }

    @PostMapping("/addChild")
    public ModelAndView addChild(@Valid Child child, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        /**Check if email and name changed and exists in DB */
        childService.existenceValidator(child, result);

        if (result.hasErrors()) {
            modelAndView.setViewName("parent/addChild");
            return modelAndView;
        }
        parentService.saveChild(child);
        modelAndView.addObject("children", parentService.findAllChildrenByParent());
        modelAndView.setViewName("parent/parent-panel");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editChild(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("child", childService.findById(id));
        modelAndView.setViewName("parent/addChild");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteChild(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        parentService.deleteChild(id);
        modelAndView.addObject("children", parentService.findAllChildrenByParent());
        modelAndView.addObject("adminMessage", "Usunięto");
        modelAndView.setViewName("/parent/parent-panel");
        return modelAndView;
    }

    @GetMapping ("/special")
//    @ResponseBody
    public ModelAndView special(@RequestParam  int childId[]) {
        Exercise exercise = new Exercise();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exercise",exercise);

        List<Child> children = new ArrayList<>();

        IntStream stream = Arrays.stream(childId);

        stream.forEach(id -> children.add(childService.findById(id)));

        modelAndView.addObject("children",children);




//        dzieci.stream()
//                .forEach(System.out::println);
//
//
//
//
//
//        for (int l = 0; l <childId.length ; l++) {
//            System.out.println(childId[l])
        modelAndView.setViewName("parent/specialExercise");
        return modelAndView;
    }

    @PostMapping("/special")
    public ModelAndView special(@Valid Exercise exercise, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("parent/parent-panel");
return modelAndView;
    }
}

