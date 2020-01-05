package pl.coderslab.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.service.ChildService;
import pl.coderslab.login.service.ParentService;

import javax.print.DocFlavor;
import javax.validation.Valid;
import java.util.List;

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

        if (child.getId() == 0) {
            if (childService.findChildrenByEmail(child.getEmail()) != null) {
                result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
            }
            if (childService.findChildrenByName(child.getName()) != null) {
                result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
            }
        }
        if (child.getId() != 0) {
            Child childByForm = childService.findById(child.getId());
            if (!childByForm.getEmail().equals(child.getEmail())) {
                if (childService.findChildrenByEmail(child.getEmail()) != null) {
                    result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
                }
            }
            if (!childByForm.getName().equals(child.getName())) {
                if (childService.findChildrenByName(child.getName()) != null) {
                    result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
                }
            }
        }

//        Parent parentExists = childService.findParentByEmail(parent.getEmail());
//        if (parentExists
//                != null) {
//            bindingResult
//                    .rejectValue("email", "error.user",
//                            "There is already a user registered with the email provided");
//        }
//
        Child childExistsEmail = childService.findChildrenByEmail(child.getEmail());
        Child childExistsName = childService.findChildrenByName(child.getName());

//        if(childExistsEmail != null && !childExistsEmail.getEmail().equals(child.getEmail())){
//        if (!validator(child)) {
//            result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
//        }
//        if(childExistsName != null){
//            result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
//        }

        if (result.hasErrors()) {
            modelAndView.setViewName("parent/addChild");
            return modelAndView;
        }
        parentService.saveChild(child);
        modelAndView.addObject("children", parentService.findAllChildrenByParent());
        modelAndView.setViewName("parent/parent-panel");
        return modelAndView;
    }


    public boolean validator(Child child) {

        System.out.println(child.getName());
        System.out.println("IMIĘ TO " + child.getEmail());

        if (childService.getCurrentChild().getEmail() != child.getEmail()) {
            if (childService.findChildrenByEmail(child.getEmail()) != null) {
                return false;
            }
        }
        return true;
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
}
