//package pl.coderslab.login.validation;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.*;
//
//@Documented
//@Constraint(validatedBy = EmailExistsConstraintValidator.class)
//@Target( { ElementType.METHOD, ElementType.FIELD })
//@Retention(RetentionPolicy.RUNTIME)
//
//public @interface EmailExistsConstraint {
//
//    String message() default "Osoba o podanym dupa istnieje";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//
//}
