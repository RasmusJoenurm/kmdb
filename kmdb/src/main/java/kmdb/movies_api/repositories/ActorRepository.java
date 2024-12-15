package kmdb.movies_api.repositories;

import kmdb.movies_api.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository
        extends JpaRepository<Actor, Long>, JpaSpecificationExecutor<Actor> {

    // Query to find actors by exact name
    @Query("SELECT actor FROM Actor actor WHERE actor.name = ?1")
    Optional<Actor> findByName(String name);

    // Query to search actors by name (case-insensitive, partial match)
    @Query("SELECT actor FROM Actor actor WHERE LOWER(actor.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Actor> findByNameContainingIgnoreCase(@Param("name") String name);
}
