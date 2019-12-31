package pl.coderslab.login.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

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
    private String email;
    @Column(name = "password")
    @Length(min = 5, message = "*Twoje hasło musi zawierać przynajmniej 5 znaków")
    @NotEmpty(message = "*Wpisz hasło")
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "*Wpisz imię")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "*Wpisz nazwisko")
    private String lastName;
    @NotEmpty(message = "*Wpisz wiek")
    @Column(name = "age")
    private int age;
    @Column(name = "active")
    private int active;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "child_role", joinColumns = @JoinColumn(name = "child_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
