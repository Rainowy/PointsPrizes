package pl.coderslab.login.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "goal_id")
    private int id;
    private String name;
    @NotEmpty
    @Size(min=10)
    private String description;

    private LocalDateTime created;
    private LocalDateTime updated;

    @Column(name = "goals_points")
    private int points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @OneToMany(mappedBy = "goal",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }


}
