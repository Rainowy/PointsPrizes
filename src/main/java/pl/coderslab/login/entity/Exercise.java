package pl.coderslab.login.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotEmpty(message = "{field.notempty}")
    @Size(min = 10, message = "{field.size}")
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    @Min(1)
    @Max(5)
    @Column(name = "exe_points")
    private int points;

//    @NotNull(message = "Wybierz cel lub dodaj nowy")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    private int special;

    private LocalDateTime deadLine;

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }

}
