package pl.senla.pricer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PricerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PricerApplication.class, args);
		log.info("Welcome to PriceAnalytics");
	}
}
