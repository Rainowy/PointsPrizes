package pl.coderslab.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.entity.Role;
import pl.coderslab.login.repository.ChildRepository;
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

    private ChildRepository childRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ChildService(RoleRepository roleRepository, ChildRepository childRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.childRepository = childRepository;
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
        return childRepository.save(child);
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
