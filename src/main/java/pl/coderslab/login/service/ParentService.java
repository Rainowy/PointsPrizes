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
import pl.coderslab.login.repository.ChildRepository;
import pl.coderslab.login.repository.ParentRepository;
import pl.coderslab.login.repository.RoleRepository;
import pl.coderslab.login.repository.UserRepository;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

//public class ParentService {
//}


@Service("parentService")
public class ParentService {

    private ChildRepository childRepository;
    private ParentRepository parentRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ParentService(ChildRepository childRepository, ParentRepository parentRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.childRepository = childRepository;
        this.parentRepository = parentRepository;
        this.roleRepository = roleRepository;
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

    public void saveParent(Parent parent) {
        parent.setPassword(bCryptPasswordEncoder.encode(parent.getPassword()));
        parent.setActive(1);
        Role userRole = roleRepository.findByRole("PARENT");
        parent.setRoles(new HashSet<>(Arrays.asList(userRole)));
        parentRepository.save(parent);
    }

    public void saveChild(Child child) {
        child.setPassword(bCryptPasswordEncoder.encode(child.getPassword()));
        child.setActive(1);
        Role userRole = roleRepository.findByRole("CHILD");
        child.setRoles(new HashSet<>(Arrays.asList(userRole)));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Parent parent = findParentByEmail(auth.getName());
        parent.addChild(child);
        child.setParent(parent);
        parentRepository.save(parent);
    }

    public List<Child> findAllChildrenByParent(int id){
        return childRepository.findAllByParentId(id);
    }
}