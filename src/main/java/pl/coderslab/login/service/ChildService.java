package pl.coderslab.login.service;

import org.springframework.stereotype.Service;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.repository.ChildRepository;

import java.util.List;

@Service
public class ChildService {

    private ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public Child findById(int id){
        return childRepository.findById(id);
    }
}
