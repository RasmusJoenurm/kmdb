package kmdb.movies_api.services;

import kmdb.movies_api.entities.Genre;
import kmdb.movies_api.entities.Movie;
import kmdb.movies_api.exceptions.ResourceAlreadyExistsException;
import kmdb.movies_api.exceptions.ResourceNotFoundException;
import kmdb.movies_api.repositories.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional
    public void deleteGenre(Long genreId, boolean force) {
    Genre genre = genreRepository.findById(genreId)
            .orElseThrow(() -> new ResourceNotFoundException("Genre with ID " + genreId + " not found"));

    if (force) {
        // Remove associations with movies
        for (Movie movie : genre.getMovies()) {
            movie.getGenres().remove(genre);
        }
        genreRepository.delete(genre);
    } else {
        if (!genre.getMovies().isEmpty()) {
            throw new IllegalStateException("Cannot delete genre with associated movies. Use force=true to override.");
        }
        genreRepository.delete(genre);
    }
}

    public List<Genre> searchGenresByName(String name) {
        List<Genre> genres = genreRepository.findByNameContainingIgnoreCase(name);
        if (genres.isEmpty()) {
            throw new ResourceNotFoundException("No genres found with name containing: " + name);
        }
        return genres;
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with ID " + id + " not found"));
    }

    public ResponseEntity<String> addGenre(Genre genre) {
        Optional<Genre> genreOptional = genreRepository.findByName(genre.getName());
        if (genreOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("Genre '" + genre.getName() + "' already exists");
        }
        genreRepository.save(genre);
        return new ResponseEntity<>("Genre '" + genre.getName() + "' added successfully", HttpStatus.CREATED);
    }

    public Genre assignGenreToMovie(Long genreId, Long movieId) {
        throw new UnsupportedOperationException("This functionality is handled by MovieService.");
    }

    public Genre removeGenreFromMovie(Long genreId, Long movieId) {
        throw new UnsupportedOperationException("This functionality is handled by MovieService.");
    }
}
