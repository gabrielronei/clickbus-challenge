package br.com.gabriels.clickbuschallenge.place;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class NewPlaceRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotNull
    @PastOrPresent
    private LocalDate createdAt;

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Place toModel() {
        return new Place(this.name, this.slug, this.city, this.state, this.createdAt);
    }
}
