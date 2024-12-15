package kmdb.movies_api.dto;

import kmdb.movies_api.entities.Movie;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class MovieDTO {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Min(value = 0, message = "Release year must be a positive number")
    private int releaseYear;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @NotNull(message = "Actors list cannot be null")
    private List<Long> actors; // IDs of actors

    @NotNull(message = "Genres list cannot be null")
    private List<Long> genres; // IDs of genres

    private String starRating; // Star-based rating

    // Constructor for DTO from Movie
    public MovieDTO(Movie movie) {
        this.title = movie.getTitle();
        this.releaseYear = movie.getReleaseYear();
        this.duration = movie.getDuration();
        this.starRating = movie.getStarRatingRepresentation();
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Long> getActors() {
        return actors;
    }

    public void setActors(List<Long> actors) {
        this.actors = actors;
    }

    public List<Long> getGenres() {
        return genres;
    }

    public void setGenres(List<Long> genres) {
        this.genres = genres;
    }

    public String getStarRating() {
        return starRating;
    }
}
