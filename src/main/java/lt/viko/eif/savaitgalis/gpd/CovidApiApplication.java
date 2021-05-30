package lt.viko.eif.savaitgalis.gpd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Spring Boot launcher.
 *
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class CovidApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidApiApplication.class, args);
	}

	@PostConstruct
	private void postConstruct() {
		System.out.println("Startup configuration");
	}

	@PreDestroy
	private void preDestroy() {
		System.out.println("Shutdown configuration");
	}

}
