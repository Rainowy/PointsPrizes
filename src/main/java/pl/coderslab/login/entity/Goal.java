package pl.coderslab.login.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
@Getter
@Setter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "goal_id")
    private int id;
    @NotEmpty(message = "{field.notempty}")
    private String name;
    @NotEmpty(message ="{field.notempty}")
    @Size(message = "{field.size}" ,min=10)
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

    /**Synchro methods*/
    public void addExercise(Exercise exercise){
        exercises.add(exercise);
        exercise.setGoal(this);
    }
    public void removeExercise(Exercise exercise){
        exercises.remove(exercise);
        exercise.setGoal(null);
    }
}
