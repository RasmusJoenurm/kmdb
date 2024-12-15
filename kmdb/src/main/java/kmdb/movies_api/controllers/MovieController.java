package kmdb.movies_api.controllers;

import kmdb.movies_api.dto.MovieDTO;
import kmdb.movies_api.entities.Movie;
import kmdb.movies_api.entities.Actor;
import kmdb.movies_api.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        Movie createdMovie = movieService.addMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    // Get all movies with pagination
    @GetMapping
    public Page<Movie> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return movieService.getAllMovies(PageRequest.of(page, size));
    }

    // Assign actor to movie
    @PatchMapping("/{movieId}/assign-actor/{actorId}")
    public ResponseEntity<Movie> assignActorToMovie(@PathVariable Long movieId, @PathVariable Long actorId) {
        Movie updatedMovie = movieService.assignActorToMovie(movieId, actorId);
        return ResponseEntity.ok(updatedMovie);
    }

    // Assign genre to movie
    @PatchMapping("/{movieId}/assign-genre/{genreId}")
    public ResponseEntity<Movie> assignGenreToMovie(@PathVariable Long movieId, @PathVariable Long genreId) {
        Movie updatedMovie = movieService.assignGenreToMovie(movieId, genreId);
        return ResponseEntity.ok(updatedMovie);
    }

    // Remove actor from movie
    @PatchMapping("/{movieId}/remove-actor/{actorId}")
    public ResponseEntity<Movie> removeActorFromMovie(@PathVariable Long movieId, @PathVariable Long actorId) {
        Movie updatedMovie = movieService.removeActorFromMovie(movieId, actorId);
        return ResponseEntity.ok(updatedMovie);
    }

    // Remove genre from movie
    @PatchMapping("/{movieId}/remove-genre/{genreId}")
    public ResponseEntity<Movie> removeGenreFromMovie(@PathVariable Long movieId, @PathVariable Long genreId) {
        Movie updatedMovie = movieService.removeGenreFromMovie(movieId, genreId);
        return ResponseEntity.ok(updatedMovie);
    }

    // Get movies by release year
    @GetMapping(params = "releaseYear")
    public ResponseEntity<List<Movie>> getMoviesByReleaseYear(@RequestParam int releaseYear) {
        List<Movie> movies = movieService.getMoviesByReleaseYear(releaseYear);
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }

    // Get movies by actor
    @GetMapping(params = "actor")
    public ResponseEntity<List<Movie>> getMoviesByActor(@RequestParam Long actor) {
        List<Movie> movies = movieService.getMoviesByActor(actor);
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }

    // Get movies by genre
    @GetMapping(params = "genre")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@RequestParam Long genre) {
        List<Movie> movies = movieService.getMoviesByGenre(genre);
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }

    // Get a specific movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return ResponseEntity.ok(movie);
    }

    // Get actors in a specific movie
    @GetMapping("/{id}/actors")
    public ResponseEntity<Set<Actor>> getActorsInMovie(@PathVariable Long id) {
        Set<Actor> actors = movieService.getActorsInMovie(id)
                .orElseThrow(() -> new RuntimeException("Actors not found for this movie"));
        return ResponseEntity.ok(actors);
    }

    // Search movies by title
    @GetMapping("/search/title")
    public ResponseEntity<List<Movie>> searchMoviesByTitle(@RequestParam String title) {
        List<Movie> movies = movieService.searchMoviesByTitle(title);
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }

    @PatchMapping("/{id}/rate")
    public ResponseEntity<Movie> rateMovie(@PathVariable Long id, @RequestParam int stars) {
    Movie updatedMovie = movieService.rateMovie(id, stars);
    return ResponseEntity.ok(updatedMovie);
}

    // Delete a movie by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
