package pl.coderslab.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.login.service.ChildService;

@Controller
@RequestMapping("/child")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping("/edit")
    public ModelAndView editChild(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName",childService.getCurrentChild().getName());

//        System.out.println(childService.getCurrentChild());

modelAndView.setViewName("/child/child-panel");
return modelAndView;


    }

//    @GetMapping("/edit")
//    @ResponseBody(){
//
//        return
//    }



}
