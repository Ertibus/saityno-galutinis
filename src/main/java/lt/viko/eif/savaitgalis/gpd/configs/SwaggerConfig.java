package lt.viko.eif.savaitgalis.gpd.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration class
 * Configures SwaggersUI, sets it's info
 *
 * @author Emil
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class SwaggerConfig {
    /**
     * A Spring Bean that sets the header/info section of SwaggerUI
     * @return {@link OpenAPI} object with the header information
     */
    @Bean
    public OpenAPI covidApiHeaders() {
        return new OpenAPI()
                .info(new Info().title("Covid-19 API")
                        .description("Covid-19 API created for the final project. It can make calls to get statistics about COVID-19 for all countries")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Savaitgalis").url("https://github.com/Ertibus/saityno-galutinis"))
                );
    }
}

