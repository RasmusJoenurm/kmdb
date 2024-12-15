package kmdb.movies_api.services;

import kmdb.movies_api.entities.Movie;
import kmdb.movies_api.dto.MovieDTO;
import kmdb.movies_api.entities.Actor;
import kmdb.movies_api.entities.Genre;
import kmdb.movies_api.exceptions.ResourceNotFoundException;
import kmdb.movies_api.repositories.MovieRepository;
import kmdb.movies_api.repositories.ActorRepository;
import kmdb.movies_api.repositories.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
        public Page<Movie> getAllMovies(Pageable pageable) {
            return movieRepository.findAll(pageable);
        }
    
        public Movie addMovie(MovieDTO movieDTO) {
            Movie movie = new Movie();
            movie.setTitle(movieDTO.getTitle());
            movie.setReleaseYear(movieDTO.getReleaseYear());
            movie.setDuration(movieDTO.getDuration());
    
            // Map actor IDs to Actor entities
            Set<Actor> actors = movieDTO.getActors().stream()
                    .map(actorId -> actorRepository.findById(actorId)
                            .orElseThrow(() -> new ResourceNotFoundException("Actor with ID " + actorId + " not found")))
                    .collect(Collectors.toSet());
            movie.setActors(actors);
    
            // Map genre IDs to Genre entities
            Set<Genre> genres = movieDTO.getGenres().stream()
                    .map(genreId -> genreRepository.findById(genreId)
                            .orElseThrow(() -> new ResourceNotFoundException("Genre with ID " + genreId + " not found")))
                    .collect(Collectors.toSet());
            movie.setGenres(genres);
    
            return movieRepository.save(movie);
        }
    
        public Movie assignActorToMovie(Long movieId, Long actorId) {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found"));
    
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Actor with ID " + actorId + " not found"));
    
            movie.getActors().add(actor);
            return movieRepository.save(movie);
        }
    
        public Movie assignGenreToMovie(Long movieId, Long genreId) {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found"));
    
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre with ID " + genreId + " not found"));
    
            movie.getGenres().add(genre);
            return movieRepository.save(movie);
        }
    
        public Movie removeActorFromMovie(Long movieId, Long actorId) {
            // Fetch the movie by ID
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found"));
        
            // Fetch the actor by ID
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Actor with ID " + actorId + " not found"));
        
            // Debugging: Check if the actor is in the set
            if (!movie.getActors().contains(actor)) {
                System.out.println("Actors in the movie before removal: " + movie.getActors());
                throw new ResourceNotFoundException("Actor with ID " + actorId + " is not associated with Movie ID " + movieId);
            }
        
            // Remove the actor
            movie.getActors().remove(actor);
        
            // Save and return the updated movie
            return movieRepository.save(movie);
        }
    
        public Movie removeGenreFromMovie(Long movieId, Long genreId) {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found"));
    
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre with ID " + genreId + " not found"));
    
            movie.getGenres().remove(genre);
            return movieRepository.save(movie);
        }
    
        public List<Movie> getMoviesByReleaseYear(int releaseYear) {
            return movieRepository.findByReleaseYear(releaseYear);
        }
    
        public List<Movie> getMoviesByActor(Long actorId) {
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Actor not found"));
    
            return new ArrayList<>(movieRepository.findAllByActorsContains(actor));
        }
    
        public List<Movie> getMoviesByGenre(Long genreId) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
    
            return new ArrayList<>(movieRepository.findAllByGenresContains(genre));
        }
    
        public Optional<Movie> getMovieById(Long id) {
            return Optional.of(movieRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + id + " not found")));
        }
        
    
        public Optional<Set<Actor>> getActorsInMovie(Long movieId) {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    
            return Optional.of(movie.getActors());
        }
    
        public List<Movie> searchMoviesByTitle(String title) {
            return movieRepository.findByTitleContainingIgnoreCase(title);
        }
    
        public void setStarRating(int starRating) {
    }

    public Movie rateMovie(Long id, int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars");
        }
    
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + id + " not found"));
    
        // Set the star rating
        movie.setStarRating(stars);
    
        return movieRepository.save(movie);
    }
    

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movieRepository.delete(movie);
    }
}
