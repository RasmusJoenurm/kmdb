package kmdb.movies_api.repositories;

import kmdb.movies_api.entities.Actor;
import kmdb.movies_api.entities.Genre;
import kmdb.movies_api.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Search movies by title (case-insensitive)
    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Movie> findByTitleContainingIgnoreCase(@Param("title") String title);

    // Find a movie by exact title
    @Query("SELECT m FROM Movie m WHERE m.title = ?1")
    Optional<Movie> findByTitle(String title);

    // Find all movies associated with a specific genre
    @Query("SELECT m FROM Movie m WHERE :genre MEMBER OF m.genres")
    Set<Movie> findAllByGenresContains(@Param("genre") Genre genre);

    // Find all movies associated with a specific actor
    @Query("SELECT m FROM Movie m WHERE :actor MEMBER OF m.actors")
    Set<Movie> findAllByActorsContains(@Param("actor") Actor actor);

    // Pagination: Retrieve all movies with pageable
    @NonNull
    Page<Movie> findAll(@NonNull Pageable pageable);

    // Query to filter movies by release year
    @Query("SELECT m FROM Movie m WHERE m.releaseYear = :releaseYear")
    List<Movie> findByReleaseYear(@Param("releaseYear") int releaseYear);

    // Query to filter movies by actor and genre
    @Query("SELECT DISTINCT m FROM Movie m " +
    "JOIN m.actors a " +
    "JOIN m.genres g " +
    "WHERE a.id = :actorId AND g.id = :genreId")
    List<Movie> findMoviesByActorAndGenre(@Param("actorId") Long actorId, @Param("genreId") Long genreId);
}
