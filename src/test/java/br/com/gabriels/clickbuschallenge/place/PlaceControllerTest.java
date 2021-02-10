package br.com.gabriels.clickbuschallenge.place;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@JqwikSpringSupport
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlaceRepository placeRepository;

    @Property(tries = 100)
    void save__should_return_error_if_the_request_has_valid_conditions(@ForAll @AlphaChars @StringLength(min = 1, max = 200) String name,
                                                                       @ForAll @AlphaChars @StringLength(min = 1, max = 200) String slug,
                                                                       @ForAll @AlphaChars @StringLength(min = 1, max = 200) String city,
                                                                       @ForAll @AlphaChars @StringLength(min = 1, max = 200) String state,
                                                                       @ForAll("datesBetween1900and2099") @StringLength(value = 10) String creationDate) throws Exception {

        String string = new ObjectMapper().writeValueAsString(Map.of("name", name, "slug", slug, "city", city,
                "state", state, "createdAt", creationDate));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .content(string)).andExpect(status().isCreated());

    }

    @Property(tries = 100)
    void save_should_return_bad_request_for_null_and_empty(@ForAll("stringsWithNullAndBlank") String name, @ForAll("stringsWithNullAndBlank") String slug,
                                                           @ForAll("stringsWithNullAndBlank") String city, @ForAll("stringsWithNullAndBlank") String state,
                                                           @ForAll("stringsWithNullAndBlank") String creationDate) throws Exception {

        String jsonPlace = PlaceBuilder.onePlace().called(name).withSlug(slug)
                .inTheStateOf(state)
                .inTheCity(city)
                .createdAt(creationDate)
                .asJson();

        System.out.println(jsonPlace);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlace)).andExpect(status().isBadRequest());
    }

    @Test
    void findBy_should_return_ok_when_there_is_a_place_with_id_1() throws Exception {
        Place place = placeRepository.save(new Place("Calabouço do android", "CDA", "Illinois", "Springfield",
                LocalDate.now().minusYears(2)));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/places/" + place.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void findBy_should_return_not_found_when_there_is_not_a_place_with_any_id() throws Exception {
        placeRepository.save(new Place("Calabouço do android", "CDA", "Illinois", "Springfield",
                LocalDate.now().minusYears(2)));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/places/" + 1237))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_should_return_ok_when_you_call() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/places"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_should_delete_one_existent_place() throws Exception {
        Place place = placeRepository.save(new Place("Calabouço do android", "CDA", "Illinois", "Springfield",
                LocalDate.now().minusYears(2)));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/places/" + place.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void delete_should_return_not_found_for_inexistent_place() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/places/" + 28139))
                .andExpect(status().isNotFound());
    }

    @Property(tries = 100)
    void edit_should_edit_one_place(@ForAll @AlphaChars @StringLength(min = 1, max = 200) String name,
                                                           @ForAll @AlphaChars @StringLength(min = 1, max = 200) String slug,
                                                           @ForAll @AlphaChars @StringLength(min = 1, max = 200) String city,
                                                           @ForAll @AlphaChars @StringLength(min = 1, max = 200) String state,
                                                           @ForAll("datesBetween1900and2099") @StringLength(value = 10) String creationDate) throws Exception {
        Place place = placeRepository.save(new Place("Calabouço do android", "CDA", "Illinois", "Springfield",
                LocalDate.now().minusYears(2)));

        String placeAsJson = new ObjectMapper().writeValueAsString(Map.of("name", name, "slug", slug, "city", city,
                "state", state, "createdAt", creationDate));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/places/" + place.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(placeAsJson))
                .andExpect(status().isOk());
    }

    @Test
    void edit_should_edit_one_place() throws Exception {
        String placeAsJson = new ObjectMapper().writeValueAsString(Map.of("name", "name", "slug", "slug", "city", "city",
                "state", "state", "createdAt", "creationDate"));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/places/" + 210921)
                .contentType(MediaType.APPLICATION_JSON)
                .content(placeAsJson))
                .andExpect(status().isNotFound());
    }

    @Provide
    Arbitrary<String> datesBetween1900and2099() {
        LocalDate now = LocalDate.now();
        Arbitrary<Integer> years = Arbitraries.integers().between(2000, now.getYear() - 1);
        Arbitrary<Integer> months = Arbitraries.integers().between(1, 12);
        Arbitrary<Integer> days = Arbitraries.integers().between(1, 28);


        return Combinators.combine(years, months, days)
                .as((year, month, day) -> LocalDate.of(year, month, day).toString());
    }

    @Provide
    Arbitrary<String> stringsWithNullAndBlank() {
        return Arbitraries.strings().whitespace().injectNull(0.4);
    }
}