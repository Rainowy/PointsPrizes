package pl.coderslab.login.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
//@Data
@Getter
@Setter
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exe_id")
    private int id;
    @NotEmpty
    @Size(min=10)
    @NotEmpty(message = "To pole nie może być puste")
    @Size(min = 10, message = "To pole musi zawierać przynajmniej 10 znaków")
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    @Min(1)
    @Max(5)
    @Column(name = "exe_points")
    private int points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }

}
