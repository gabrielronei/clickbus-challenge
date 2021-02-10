package br.com.gabriels.clickbuschallenge.place;

import java.time.LocalDate;

public class SpecificPlaceOutputDTO {

    private final Long id;
    private final String name;
    private final String slug;
    private final String city;
    private final String state;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public SpecificPlaceOutputDTO(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.slug = place.getSlug();
        this.city = place.getCity();
        this.state = place.getState();
        this.createdAt = place.getCreatedAt();
        this.updatedAt = place.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

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

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}
