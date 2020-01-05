package pl.coderslab.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.entity.Role;
import pl.coderslab.login.repository.ChildRepository;
import pl.coderslab.login.repository.RoleRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
}
