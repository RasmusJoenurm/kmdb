package kmdb.movies_api.controllers;

import jakarta.validation.Valid;
import kmdb.movies_api.entities.Genre;
import kmdb.movies_api.exceptions.ResourceNotFoundException;
import kmdb.movies_api.services.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/search")
    public ResponseEntity<List<Genre>> searchGenresByName(@RequestParam String name) {
    List<Genre> genres = genreService.searchGenresByName(name);
    if (genres.isEmpty()) {
        throw new ResourceNotFoundException("No genres found with name containing: " + name);
    }
    return ResponseEntity.ok(genres);
}

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        Genre genre = genreService.getGenreById(id);
        return ResponseEntity.ok(genre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addGenre(@Valid @RequestBody Genre genre) {
        return genreService.addGenre(genre);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean force) {
    genreService.deleteGenre(id, force);
    }

}
