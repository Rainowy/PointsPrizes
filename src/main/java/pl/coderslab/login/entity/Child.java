package pl.coderslab.login.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
//import pl.coderslab.login.validation.EmailExistsConstraintValidator;
//import pl.coderslab.login.validation.EmailExistsConstraint;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//@Table(name = "child")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private int id;
    @Column(name = "email")
    @Email(message = "*Jeżeli posiadasz, wpisz email")
//    @NotEmpty(message = "*Wpisz email")
//    @EmailExistsConstraint
    private String email;
    @Column(name = "password")
    @Length(min = 5, message = "*Twoje hasło musi zawierać przynajmniej 5 znaków")
    @NotEmpty(message = "*Wpisz hasło")
    private String password;
    @Column(name = "username")
    @NotEmpty(message = "*Wpisz imię")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "*Wpisz nazwisko")
    private String lastName;
    @NotNull(message = "*Wpisz wiek")
    @Column(name = "age")
    private int age;
    @Column(name = "active")
    private int active;
//    @ManyToMany(cascade = CascadeType.ALL)
    @ManyToMany
    @JoinTable(name = "child_role", joinColumns = @JoinColumn(name = "child_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
