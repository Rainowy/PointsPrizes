package pl.coderslab.login.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.repository.ChildRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChildService {

    private ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public Child findById(int id) {
        return childRepository.findById(id);
    }

    public Child getCurrentChild(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();
        email.getClass();
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher mat = pattern.matcher(email);

        if(mat.matches()){
            return childRepository.findByEmail(email);
        }
        else return childRepository.findByName(email);

//        return findChildByEmail(auth.getName());
    }
}
