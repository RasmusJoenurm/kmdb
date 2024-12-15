package kmdb.movies_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Actor {
    @Id
    @SequenceGenerator(
        name = "actor_sequence",
        sequenceName = "actor_sequence",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actor_sequence")
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    @Column(columnDefinition = "VARCHAR(10)")
    @Pattern(
        regexp = "(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])",
        message = "Please enter a valid date in yyyy-MM-dd format"
    )
    private String birthDate;

    public Actor(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}