package br.com.gabriels.clickbuschallenge.place;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @PastOrPresent
    private LocalDate createdAt;

    private LocalDate updatedAt;

    @Deprecated
    public Place() {
    }

    public Place(@NotBlank String name, @NotBlank String slug, @NotBlank String city, @NotBlank String state, @PastOrPresent LocalDate createdAt) {
        this.name = name;
        this.slug = slug;
        this.city = city;
        this.state = state;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return this.id;
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

    public void update(FieldsToUpdatePlace updatedPlace) {
        this.name = updatedPlace.getName();
        this.slug = updatedPlace.getSlug();
        this.city = updatedPlace.getCity();
        this.state = updatedPlace.getState();
        this.updatedAt = LocalDate.now();
    }
}
