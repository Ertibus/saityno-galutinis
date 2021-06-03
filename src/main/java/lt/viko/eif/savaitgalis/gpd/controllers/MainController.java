package lt.viko.eif.savaitgalis.gpd.controllers;

import lt.viko.eif.savaitgalis.gpd.pojo.Cases;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.pojo.Deaths;
import lt.viko.eif.savaitgalis.gpd.repos.CovidApiRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

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

    // ~TEMP~
    /*private Cases tCase = new Cases(77777, 77777, 77777, 77777, 77777);
    private Deaths tDeath = new Deaths(77777, 77777);
    private Country tCountry = new Country("USA", 1234, 1234, tCase, tDeath);*/

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

        Country country = new repo.getCountry(name, date);
        if (date.equals("")) {
            country = new repo.getCountry(name, "today");
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
    public ResponseEntity<EntityModel<List<Country>>> getFavoriteStats() {

        List<Country> favCountries = new repo.getFavouriteCountryStats();
        if (favCountries.equals(null)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // TEMP:
        Cases tCase = new Cases(87777, 77777, 77777, 77777, 77777);
        Deaths tDeath = new Deaths(77777, 77777);
        Country tCountry = new Country("USA", 1234, 1234, tCase, tDeath);

        EntityModel<List<Country>> model = EntityModel.of(favCountries);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        model.add(Link.of(uriString, "self"));

        return ResponseEntity.ok(model);
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
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        EntityModel<Country> model = EntityModel.of(country);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        model.add(Link.of(uriString, "self"));
        favCountries.addFavouriteCountry(name);

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
