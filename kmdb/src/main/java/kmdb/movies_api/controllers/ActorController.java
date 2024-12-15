package kmdb.movies_api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import kmdb.movies_api.entities.Actor;
import kmdb.movies_api.services.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping(path = "api/actors")
public class ActorController {

    private final ActorService actorService;

    // Get all actors
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Optional<List<Actor>> getAllActors() {
        return actorService.getAllActors();
    }

    // Get number of actors
    @GetMapping(params = "count")
    @ResponseStatus(HttpStatus.OK)
    public String getActorCount() {
        return actorService.getActorCount();
    }

    // Get actors by page and page size
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<List<Actor>> getActorsByPage(
            @Min(value = 0, message = "Page index must not be less than zero")
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,

            @Min(value = 1, message = "Page size must not be less than one")
            @Max(value = 100, message = "Page size limit is 100")
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return actorService.getActorsByPage(page, size);
    }

    // Get actors by ID
    @GetMapping(path = "{actorId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Actor> getActorsById(
            @PathVariable @Positive(message = "Actor ID must be greater than 0") Long actorId) {
        return actorService.getActorById(actorId);
    }

    // Retrieve actors by name or all if no parameter
    @GetMapping(path = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Optional<List<Actor>> findActorsByName(@RequestParam(required = false) String name) {
        return actorService.findActorsByName(name);
    }

    // Add actor
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addActor(@Valid @RequestBody Actor actor) {
        return actorService.addActor(actor);
    }

    // Delete actor by ID
    @DeleteMapping(path = "{actorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActor(
            @PathVariable("actorId") @Positive(message = "Actor ID must be greater than 0") Long actorId,
            @RequestParam(value = "force", defaultValue = "false", required = false) boolean force) {
        actorService.deleteActor(actorId, force);
    }

    // Update actor
    @PatchMapping(path = "{actorId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateActor(
            @PathVariable("actorId") @Positive(message = "Actor ID must be greater than 0") Long actorId,
            @RequestBody Actor request) {
        actorService.updateActor(actorId, request.getName(), request.getBirthDate());
    }
}
