package br.com.gabriels.clickbuschallenge.place;

import br.com.gabriels.clickbuschallenge.shared.exception.PlaceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> save(@RequestBody @Valid NewPlaceRequest newPlaceRequest, UriComponentsBuilder uriBuilder) {
        Place newPlace = placeRepository.save(newPlaceRequest.toModel());

        URI uri = uriBuilder.path("/places/{id}")
                .buildAndExpand(newPlace.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecificPlaceOutputDTO> findBy(@PathVariable("id") Long id) {
        Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        return ResponseEntity.ok(new SpecificPlaceOutputDTO(place));
    }

    @GetMapping
    public ResponseEntity<List<SpecificPlaceOutputDTO>> findAll() {
        List<SpecificPlaceOutputDTO> places = placeRepository.findAllByOrderByName()
                .stream().map(SpecificPlaceOutputDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(places);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity edit(@PathVariable("id") Long id, @RequestBody @Valid EditPlaceRequest editPlaceRequest) {
        Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        place.update(editPlaceRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        placeRepository.delete(place);

        return ResponseEntity.ok().build();
    }
}
