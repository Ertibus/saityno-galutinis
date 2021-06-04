package lt.viko.eif.savaitgalis.gpd.controllers;

import lt.viko.eif.savaitgalis.gpd.pojo.Cases;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.pojo.Deaths;
import lt.viko.eif.savaitgalis.gpd.repos.CovidApiRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Represents a REST controller for GPD web service.
 * @author Žilvinas Mockus PI19B
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping(value = "/api/covid", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    final private CovidApiRepository repo;

    public MainController(){
        repo = new CovidApiRepository();
    }

    /**
     * GET request annotation for receiving statistics by date and country from repository.
     * @param name Describes country's name.
     * @param date Describes wanted date.
     * @return ResponseEntity.
     */
    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<EntityModel<Country>> getStatsByCountry(@PathVariable String name, @RequestParam(required = false) String date) {

        Country country = repo.getCountry(name, date);
        if (date.equals("")) {
            country = repo.getCountry(name, "today");
            if (country.equals(null))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        EntityModel<Country> model = EntityModel.of(country);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        model.add(Link.of(uriString, "self"));

        return ResponseEntity.ok(model);
    }

    /**
     * GET request annotation for receiving all favorite countries statistics from repository.
     * @return ResponseEntity.
     */
    @GetMapping("/fav")
    @ResponseBody
    public ResponseEntity<CollectionModel<EntityModel<Country>>> allFavCountry() {
        List<EntityModel<Country>> countries = repo.getFavCountries().stream()
                .map(country -> EntityModel.of(country,
                        linkTo(methodOn(MainController.class).getStatsByCountry(country.getCountry())).withSelfRel(),
                        linkTo(methodOn(MainController.class).allFavCountry()).withRel("get-fav")
                        )
                ).collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(countries, linkTo(methodOn(MainController.class).allFavCountry()).withSelfRel()));
    }

    /**
     * PUT request annotation for adding favorite country by its name to repository.
     * @param country Describes {@link Country} object.
     * @param name Describes country's name.
     * @return ResponseEntity.
     */
    @PutMapping("/fav/{name}")
    public ResponseEntity<EntityModel<Country>> putCountryToFav(@RequestBody Country country, @PathVariable String name) {

        List<Country> favCountries = repo.getFavouriteCountryStats();
        for (Country c : favCountries) {
            if (c.getCountry().equals(name))
                return ResponseEntity.ok(null);
        }

        EntityModel<Country> model = EntityModel.of(country);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        model.add(Link.of(uriString, "self"));
        repo.addFavouriteCountry(name);

        return ResponseEntity.created(URI.create(uriString)).body(model);
    }

    /**
     * DELETE request annotation for removing favorite country by its name from repository.
     * @param name Describes country's name.
     * @return ResponseEntity.
     */
    @DeleteMapping("/fav/{name}")
    public ResponseEntity<EntityModel<Country>> putCountryToFav(@PathVariable String name) {

        List<Country> favCountries = repo.getFavouriteCountryStats();
        for (Country c : favCountries) {
            if (c.getCountry().equals(name)) {
                repo.removeFavouriteCountry(name);

                EntityModel<Country> model = EntityModel.of(c);
                final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
                model.add(Link.of(uriString, "self"));

                return ResponseEntity.ok(model);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
