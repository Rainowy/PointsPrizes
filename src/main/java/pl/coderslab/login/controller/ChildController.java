package pl.coderslab.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.service.ChildService;

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
        if (result.hasErrors()) {
            modelAndView.setViewName("child/editProfile");
            return modelAndView;
        }
        childService.saveChild(child);
        modelAndView.setViewName("child/child-panel");
        return modelAndView;
    }
}
