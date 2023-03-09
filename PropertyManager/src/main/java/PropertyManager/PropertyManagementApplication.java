package PropertyManager;

import PropertyManager.model.*;
import PropertyManager.service.ApartmentService;
import PropertyManager.service.BuildingService;
import PropertyManager.service.PaymentService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Calendar;


@SpringBootApplication
public class PropertyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BuildingService buildingService,
								  ApartmentService apartmentService,
								  PaymentService paymentService){
		return args -> {

			AddDefaultData(buildingService, apartmentService, paymentService);

		};
	}

	// Enable CORS globally on port 4200 for Angular client.
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
		};
	}

	private static void AddDefaultData(BuildingService buildingService,
									   ApartmentService apartmentService,
									   PaymentService paymentService){
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

		//save a few apartments
		buildingService.add(new Building("6114 Washington"));
		buildingService.add(new Building("5943 Kingsbury"));

		apartmentService.add(new Apartment(1L, "1E"));
		apartmentService.add(new Apartment(1L, "1W"));
		apartmentService.add(new Apartment(1L, "2E"));
		apartmentService.add(new Apartment(1L, "2W"));

		apartmentService.add(new Apartment(2L, "1E"));
		apartmentService.add(new Apartment(2L, "1W"));
		apartmentService.add(new Apartment(2L, "2E"));
		apartmentService.add(new Apartment(2L, "2W"));


		for (Apartment apartment : apartmentService.getByBuilding(buildingService.getById(1L))) {
			paymentService.add(new Payment(apartment.getId(), 1200, currentMonth));
		}

		for (Apartment apartment : apartmentService.getByBuilding(buildingService.getById(2L))) {
			paymentService.add(new Payment(apartment.getId(), 1200, currentMonth));
		}

		for (Apartment apartment : apartmentService.getByBuilding(buildingService.getById(1L))) {
			paymentService.add(new Payment(apartment.getId(), 1200, (currentMonth + 1) % 12));
		}
	}
}
