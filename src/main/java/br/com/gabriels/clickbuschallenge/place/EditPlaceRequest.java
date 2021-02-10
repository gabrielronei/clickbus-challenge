package br.com.gabriels.clickbuschallenge.place;

import javax.validation.constraints.NotBlank;

public class EditPlaceRequest implements FieldsToUpdatePlace {
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    @NotBlank
    private String city;
    @NotBlank
    private String state;

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
}
