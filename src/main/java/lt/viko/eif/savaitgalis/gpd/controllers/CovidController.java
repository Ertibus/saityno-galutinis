package lt.viko.eif.savaitgalis.gpd.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.repos.CovidApiRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Represents a REST controller for GPD web service.
 *
 * @author Žilvinas Mockus PI19B
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping(value = "/api/covid", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Covid API", description = "Represents a REST controller for GPD web service")
public class CovidController {

    final private CovidApiRepository repo;

    public CovidController(){
        repo = new CovidApiRepository();
    }

    /**
     * GET request annotation for receiving statistics by date and country from repository.
     *
     * @param name Describes country's name.
     * @param date Describes wanted date.
     * @return ResponseEntity
     * @see Country
     */
    @GetMapping("/{name}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK!"),
                    @ApiResponse(responseCode = "400", description = "Bad Request!")
            }
    )
    @Operation(summary="Get Statistics by Country", description = "Returns a countries COVID statistics, within the given date. For global statistics input 'all'")
    @ResponseBody
    public ResponseEntity<EntityModel<Country>> getStatsByCountry(@PathVariable String name, @RequestParam(required = false) String date) {

        Country country;

        if (date == null || date.isEmpty()) {
            country = repo.getCountry(name, "today");
        }
        else
            country = repo.getCountry(name, date);

        if (country == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        EntityModel<Country> model = EntityModel.of(country);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        model.add(Link.of(uriString, "self"));

        return ResponseEntity.ok(model);
    }

    /**
     * GET request annotation for receiving all favorite countries statistics from repository.
     *
     * @return ResponseEntity.
     * @see Country
     */
    @GetMapping("/fav")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK!"),
                    @ApiResponse(responseCode = "204", description = "No content!")
            }
    )
    @Operation(summary="Get favorite countries statistics", description = "Returns a list of favorite countries COVID statistics today")
    @ResponseBody
    public ResponseEntity<CollectionModel<EntityModel<Country>>> allFavCountry() {

        List<Country> tempList = repo.getFavouriteCountryStats();
        if (tempList == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        List<EntityModel<Country>> countries = tempList.stream()
                .map(country -> EntityModel.of(country,
                        linkTo(methodOn(CovidController.class).getStatsByCountry(country.getCountry(), String.valueOf(country.getDate()))).withSelfRel(),
                        linkTo(methodOn(CovidController.class).allFavCountry()).withRel("get-fav")
                        )
                ).collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(countries, linkTo(methodOn(CovidController.class).allFavCountry()).withSelfRel()));
    }

    /**
     * PUT request annotation for adding favorite country by its name to repository.
     *
     * @param name Describes country's name.
     * @return ResponseEntity.
     * @see Country
     */
    @PutMapping("/fav/{name}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK!"),
                    @ApiResponse(responseCode = "201", description = "CREATED!"),
            }
    )
    @Operation(summary="Add a country to favorites", description = "Adds a country to favorites list.")
    public ResponseEntity<EntityModel<Country>> putCountryToFav(@PathVariable String name) {

        List<Country> favCountries = repo.getFavouriteCountryStats();
        if (favCountries != null) {
            for (Country c : favCountries) {
                if (c.getCountry().equals(name))
                    return ResponseEntity.ok(EntityModel.of(c));
            }
        }

        EntityModel<Country> model = EntityModel.of(repo.getCountry(name, "today"));
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        model.add(Link.of(uriString, "self"));
        repo.addFavouriteCountry(name);

        return ResponseEntity.created(URI.create(uriString)).body(model);
    }

    /**
     * DELETE request annotation for removing favorite country by its name from repository.
     *
     * @param name Describes country's name.
     * @return ResponseEntity.
     * @see Country
     */
    @DeleteMapping("/fav/{name}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK!"),
                    @ApiResponse(responseCode = "404", description = "Not found!")
            }
    )
    @Operation(summary="Delete a country from favorites", description = "Removes a country from the favorites list.")
    public ResponseEntity<EntityModel<Country>> deleteCountryFromFav(@PathVariable String name) {

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
