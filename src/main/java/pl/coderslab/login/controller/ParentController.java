package pl.coderslab.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.service.ParentService;

import javax.validation.Valid;

@Controller
@RequestMapping("/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @GetMapping("/addChild")
    public ModelAndView addChild() {
        ModelAndView modelAndView = new ModelAndView();
        Child child = new Child();
        modelAndView.addObject("child", child);
        modelAndView.setViewName("user/addChild");
        return modelAndView;
    }

    @PostMapping("/addChild")
    public ModelAndView addChild(@Valid Child child, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("user/addChild");
            return modelAndView;
        }
        parentService.saveChild(child);
        modelAndView.setViewName("user/user-panel");
        return modelAndView;
    }
}
