package pl.coderslab.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.entity.Role;
import pl.coderslab.login.repository.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

//public class ParentService {
//}

@Transactional
@Service("parentService")
public class ParentService {

    private ChildRepository childRepository;
    private ParentRepository parentRepository;
    private RoleRepository roleRepository;
    private ExerciseRepository exerciseRepository;
    private GoalRepository goalRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ParentService(ChildRepository childRepository, ParentRepository parentRepository, RoleRepository roleRepository, ExerciseRepository exerciseRepository, GoalRepository goalRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.childRepository = childRepository;
        this.parentRepository = parentRepository;
        this.roleRepository = roleRepository;
        this.exerciseRepository = exerciseRepository;
        this.goalRepository = goalRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Parent getCurrentParent(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findParentByEmail(auth.getName());
    }

    public Parent findParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    public Parent findParentByName(String name) {
        return parentRepository.findParentByName(name);
    }

    public List<Child> findAllChildrenByParent(){
     return findAllChildrenByParent(getCurrentParent().getId());
    }

    public Parent saveParent(Parent parent) {
        parent.setPassword(bCryptPasswordEncoder.encode(parent.getPassword()));
        parent.setActive(1);
        Role userRole = roleRepository.findByRole("PARENT");
        parent.setRoles(new HashSet<>(Arrays.asList(userRole)));
        return parentRepository.save(parent);
    }

    public Parent saveChild(Child child) {
        if(child.getId() != 0){
            child.setExercises(exerciseRepository.findAllByChildId(child.getId()));
            child.setGoals(goalRepository.findAllByChildId(child.getId()));
        }
        child.setPassword(bCryptPasswordEncoder.encode(child.getPassword()));
        child.setActive(1);
        Role userRole = roleRepository.findByRole("CHILD");
        child.setRoles(new HashSet<>(Arrays.asList(userRole)));
        Parent parent = getCurrentParent();
        parent.addChild(child);
        child.setParent(parent);
        return parentRepository.save(parent);
    }

    public List<Child> findAllChildrenByParent(int id){
        return childRepository.findAllByParentId(id);
    }

    public void deleteChild(int id){
        Child childToDelete = childRepository.findById(id);
        Parent currentParent = getCurrentParent();
        currentParent.removeChild(childToDelete);
        childRepository.deleteById(id);
    }
}