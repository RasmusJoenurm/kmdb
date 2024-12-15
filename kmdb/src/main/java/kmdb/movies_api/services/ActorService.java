package kmdb.movies_api.services;

import kmdb.movies_api.entities.Actor;
import kmdb.movies_api.entities.Movie;
import kmdb.movies_api.exceptions.ResourceAlreadyExistsException;
import kmdb.movies_api.exceptions.ResourceNotFoundException;
import kmdb.movies_api.repositories.ActorRepository;
import kmdb.movies_api.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public Optional<List<Actor>> getAllActors() {
        List<Actor> actorsList = actorRepository.findAll();
        if (actorsList.isEmpty()) {
            throw new ResourceNotFoundException("No actors found in the database");
        }
        return Optional.of(actorsList);
    }

    public String getActorCount() {
        return "Actors in database: " + actorRepository.count();
    }

    public Optional<List<Actor>> getActorsByPage(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Actor> actorsPage = actorRepository.findAll(pageable);
        List<Actor> actorsList = actorsPage.getContent();
        if (actorsList.isEmpty()) {
            throw new ResourceNotFoundException("No actors found on page " + page);
        }
        return Optional.of(actorsList);
    }

    public Optional<Actor> getActorById(Long actorId) {
        Optional<Actor> actor = actorRepository.findById(actorId);
        if (actor.isPresent()) {
            return actor;
        } else {
            throw new ResourceNotFoundException("Actor with ID " + actorId + " does not exist");
        }
    }

    public Optional<List<Actor>> findActorsByName(String name) {
        if (name == null || name.isEmpty()) {
            return getAllActors(); // Return all actors if no name provided
        }

        List<Actor> actorsList = actorRepository.findByNameContainingIgnoreCase(name);
        if (actorsList.isEmpty()) {
            throw new ResourceNotFoundException("Actor with name containing '" + name + "' does not exist");
        }
        return Optional.of(actorsList);
    }

    public Optional<List<Movie>> getMoviesByActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor with ID " + actorId + " does not exist"));

        List<Movie> moviesList = new ArrayList<>(movieRepository.findAllByActorsContains(actor));

        if (moviesList.isEmpty()) {
            throw new ResourceNotFoundException("No movies found starring actor '" + actor.getName() + "'");
        }
        return Optional.of(moviesList);
    }

    public ResponseEntity<String> addActor(Actor actor) {
        Optional<Actor> actorOptional = actorRepository.findByName(actor.getName());
        if (actorOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("Actor '" + actor.getName() + "' already exists");
        }
        actorRepository.save(actor);
        return new ResponseEntity<>("Actor '" + actor.getName() + "' added successfully", HttpStatus.CREATED);
    }

    @Transactional
    public void deleteActor(Long actorId, boolean force) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor with ID " + actorId + " does not exist"));

        int numOfMovies = actor.getMovies().size();

        if (force) {
            actorRepository.deleteById(actorId); // Delete regardless of relationships
            return;
        }

        if (numOfMovies > 0) {
            throw new IllegalStateException(
                    "Cannot delete actor '" + actor.getName() + "' because they are associated with " + numOfMovies + " movie(s)");
        }
        actorRepository.deleteById(actorId);
    }

    @Transactional
    public void updateActor(Long actorId, String name, String birthDate) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor with ID " + actorId + " does not exist"));

        if (name != null && !name.isEmpty()) {
            actor.setName(name);
        }

        if (birthDate != null) {
            actor.setBirthDate(birthDate);
        }
    }
}
