package pl.coderslab.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.entity.Role;
import pl.coderslab.login.repository.ParentRepository;
import pl.coderslab.login.repository.RoleRepository;
import pl.coderslab.login.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

//public class ParentService {
//}


@Service("parentService")
public class ParentService {

    private ParentRepository parentRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ParentService(ParentRepository parentRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.parentRepository = parentRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Parent findParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    public void saveParent(Parent parent) {
        parent.setPassword(bCryptPasswordEncoder.encode(parent.getPassword()));
        parent.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        parent.setRoles(new HashSet<>(Arrays.asList(userRole)));
        parentRepository.save(parent);
    }
}