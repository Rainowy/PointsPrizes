//package pl.coderslab.login.validation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import pl.coderslab.login.entity.Child;
//import pl.coderslab.login.repository.ChildRepository;
//import pl.coderslab.login.service.ChildService;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import java.lang.annotation.Annotation;
//
//public class EmailExistsConstraintValidator implements ConstraintValidator<EmailExistsConstraint, String> {
//
//    private ChildRepository childRepository;
//
//    private ChildService childService;
//
//    public EmailExistsConstraintValidator(ChildRepository childRepository, ChildService childService) {
//        this.childRepository = childRepository;
//        this.childService = childService;
//    }
//
//    @Override
//    public void initialize(EmailExistsConstraint constraint) {
//
//    }
//
//    @Override
//    public boolean isValid(String email, ConstraintValidatorContext context) {
//        int childFormId = childService.findChildrenByEmail(email).getId();
//
//        boolean answer = false;
//        if (childFormId != 0) {
//            if (email != null && !childRepository.findChildByEmail(email).isPresent()) { //zwroci true// jest pusty i nie ma go w bazie
////                return true;
//                answer = true;
//            }
//        }
//        if (childFormId == 0){
//            if(email != null && !childRepository.findChildByEmail(email).isPresent()) {
//                answer = true;
//            }
//        }
//        return answer;
//
////        return false;
////        if (child.getId() == 0) {
////            if (childService.findChildrenByEmail(child.getEmail()) != null) {
////                result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
////            }
////            if (childService.findChildrenByName(child.getName()) != null) {
////                result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
////            }
////        }
////        if (child.getId() != 0) {
////            Child childById = childService.findById(child.getId());
////            if (!childById.getEmail().equals(child.getEmail())) {
////                if (childService.findChildrenByEmail(child.getEmail()) != null) {
////                    result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
////                }
////            }
////            if (!childById.getName().equals(child.getName())) {
////                if (childService.findChildrenByName(child.getName()) != null) {
////                    result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
////                }
////            }
////        }
//
//
////    @Autowired
////    ChildService childService;
////
////    @Override
////    public void initialize(EmailExistsConstraint emailExists) {
////
////    }
////
////    @Override
////    public boolean isValid(Child child, ConstraintValidatorContext constraintValidatorContext) {
////
////        if (child.getId() == 0) {
//////            if (childService.findChildrenByEmail(child.getEmail()) != null) {
////////                result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
//////            }
////
////            return (childService.findChildrenByName(child.getName()) != null);
////
//////                result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
////
//////
////        }
////        if (child.getId() != 0) {
////            Child childById = childService.findById(child.getId());
//////            if (!childById.getEmail().equals(child.getEmail())) {
//////                if (childService.findChildrenByEmail(child.getEmail()) != null) {
//////                    result.rejectValue("email", "error.user", "Istnieje już osoba o podanym emailu");
//////                }
//////            }
////            if (!childById.getName().equals(child.getName())) {
////              return  (childService.findChildrenByName(child.getName()) != null) ;
//////                    result.rejectValue("name", "error.user", "Istnieje już osoba o podanym imieniu");
////
////            }
////        }
////        return true;
////    }
////
////    @Override
////    public void initialize(Annotation constraintAnnotation) {
////
////    }
////
////    @Override
////    public boolean isValid(Child child, ConstraintValidatorContext constraintValidatorContext) {
////
////        if (child.getId() != 0) {
////            Child childById = childService.findById(child.getId());
////        }
////        return false;
////    }
//    }
//}