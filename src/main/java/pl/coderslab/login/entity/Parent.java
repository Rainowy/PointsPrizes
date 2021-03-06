package pl.coderslab.login.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter
@Entity
//@Table(name = "parent")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id")
    private int id;
    @Column(name = "email")
    @Email(message = "{email.regular}")
    @NotEmpty(message = "{email.notempty}")
    private String email;
    @Column(name = "password")
    @Length(min = 5, message = "{password.length}")
    @NotEmpty(message = "{password.notempty}")
    private String password;
    @Column(name = "username")
    @NotEmpty(message = "{username.notempty}")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "{lastname.notempty}")
    private String lastName;
    @Column(name = "active")
    private int active;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "parent_role", joinColumns = @JoinColumn(name = "parent_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Child> children = new ArrayList<>();

    /**Synchro methods*/
    public void addChild(Child child){
        children.add(child);
        child.setParent(this);
    }
    public void removeChild(Child child){
        children.remove(child);
        child.setParent(null);
    }
    /***/

}
