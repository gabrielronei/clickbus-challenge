package br.com.gabriels.clickbuschallenge.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlaceBuilder {
    @JsonProperty
    private String name;
    @JsonProperty
    private String slug;
    @JsonProperty
    private String city;
    @JsonProperty
    private String state;
    @JsonProperty
    private String createdAt;

    public static PlaceBuilder onePlace() {
        return new PlaceBuilder();
    }

    public PlaceBuilder called(String name) {
        this.name = name;
        return this;
    }

    public PlaceBuilder withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public PlaceBuilder inTheStateOf(String state) {
        this.state = state;
        return this;
    }

    public PlaceBuilder inTheCity(String city) {
        this.city = city;
        return this;
    }

    public PlaceBuilder createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String asJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


