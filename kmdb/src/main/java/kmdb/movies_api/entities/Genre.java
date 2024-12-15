package kmdb.movies_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genre") // Matches the database table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Genre {
    @Id
    @SequenceGenerator(
        name = "genre_sequence",
        sequenceName = "genre_sequence",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_sequence")
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
    private Set<Movie> movies = new HashSet<>();
}
