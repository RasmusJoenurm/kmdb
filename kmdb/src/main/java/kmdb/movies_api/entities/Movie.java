package kmdb.movies_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "movie") // Ensure the table name matches your database
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Min(value = 0, message = "Movie release year must be between 0 and 2300")
    @Max(value = 2300, message = "Movie release year must be between 0 and 2300")
    private int releaseYear;

    @Min(value = 0, message = "Movie duration must be between 0 and 1000 minutes")
    @Max(value = 1000, message = "Movie duration must be between 0 and 1000 minutes")
    private int duration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "actors", 
        joinColumns = @JoinColumn(name = "movie_id"), 
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "genres", 
        joinColumns = @JoinColumn(name = "movie_id"), 
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    // Add the starRating field
    @Column(name = "star_rating", nullable = false, columnDefinition = "INTEGER default 0")
    @JsonIgnore
    private int starRating;

    // Method to represent the star rating
    @JsonProperty("Rating")
    public String getStarRatingRepresentation() {
        return "★".repeat(starRating) + "☆".repeat(5 - starRating); // Full and empty stars
    }
    
    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }
    
}
