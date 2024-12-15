package kmdb.movies_api.repositories;

import kmdb.movies_api.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    // Query to find genres by name (case-insensitive, partial match)
    @Query("SELECT g FROM Genre g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Genre> findByNameContainingIgnoreCase(@Param("name") String name);

    // Query to find genres by exact name (case-insensitive)
    @Query("SELECT g FROM Genre g WHERE LOWER(g.name) = LOWER(:name)")
    Optional<Genre> findByName(@Param("name") String name);
}
