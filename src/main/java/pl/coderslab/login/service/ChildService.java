package pl.coderslab.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.login.entity.*;
import pl.coderslab.login.repository.ChildRepository;
import pl.coderslab.login.repository.ExerciseRepository;
import pl.coderslab.login.repository.GoalRepository;
import pl.coderslab.login.repository.RoleRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChildService {

    private RoleRepository roleRepository;

    private GoalRepository goalRepository;

    private ChildRepository childRepository;

    private ExerciseRepository exerciseRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ChildService(RoleRepository roleRepository, GoalRepository goalRepository, ChildRepository childRepository, ExerciseRepository exerciseRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.goalRepository = goalRepository;
        this.childRepository = childRepository;
        this.exerciseRepository = exerciseRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Child findById(int id) {
        return childRepository.findById(id);
    }

    public Child findChildrenByEmail(String email) {
        return childRepository.findByEmail(email);
    }

    public Child findChildrenByName(String name) {
        return childRepository.findByName(name);
    }

    public Child saveChild(Child child) {
        child.setPassword(bCryptPasswordEncoder.encode(child.getPassword()));
        child.setActive(1);
        Role userRole = roleRepository.findByRole("CHILD");
        child.setRoles(new HashSet<>(Arrays.asList(userRole)));
        child.setParent(getCurrentChild().getParent());
        child.setGoals(getCurrentChild().getGoals());
        return childRepository.save(child);
    }

    public Child saveChild(Goal goal){
        Child currentChild = getCurrentChild();
        currentChild.addGoal(goal);
        return childRepository.save(currentChild);

    }

    public Child getCurrentChild() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String credential = auth.getName();
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher mat = pattern.matcher(credential);
        if (mat.matches()) {
            return childRepository.findByEmail(credential);
        } else return childRepository.findByName(credential);
    }
    public List<Exercise> findExercisesByChildId(int id){
        return exerciseRepository.findAllByChildId(id);
    }

    public List<Goal> findGoalsByChildId(int id){
        return goalRepository.findAllByChildId(id);
    }



    //TODO change this into smth. nicer
    public void existenceValidator(@Valid Child child, BindingResult result) {
        if (child.getId() == 0) {
            if (findChildrenByEmail(child.getEmail()) != null) {
                result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
            }
            if (findChildrenByName(child.getName()) != null) {
                result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
            }
        }
        if (child.getId() != 0) {
            Child childById = findById(child.getId());
            if (!childById.getEmail().equals(child.getEmail())) {
                if (findChildrenByEmail(child.getEmail()) != null) {
                    result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
                }
            }
            if (!childById.getName().equals(child.getName())) {
                if (findChildrenByName(child.getName()) != null) {
                    result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
                }
            }
        }
    }

}
