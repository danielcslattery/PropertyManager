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
		Building building1 = buildingService.add(new Building("6114 Washington"));
		Building building2 = buildingService.add(new Building("5943 Kingsbury"));

		apartmentService.add(new Apartment(building1, "1E"));
		apartmentService.add(new Apartment(building1, "1W"));
		apartmentService.add(new Apartment(building1, "2E"));
		apartmentService.add(new Apartment(building1, "2W"));

		apartmentService.add(new Apartment(building2, "1E"));
		apartmentService.add(new Apartment(building2, "1W"));
		apartmentService.add(new Apartment(building2, "2E"));
		apartmentService.add(new Apartment(building2, "2W"));


		for (Apartment apartment : apartmentService.getByBuilding(building1)) {
			paymentService.add(new Payment(apartment, 1200, currentMonth));
		}

		for (Apartment apartment : apartmentService.getByBuilding(building2)) {
			paymentService.add(new Payment(apartment, 1200, currentMonth));
		}

		for (Apartment apartment : apartmentService.getByBuilding(building1)) {
			paymentService.add(new Payment(apartment, 1200, (currentMonth + 1) % 12));
		}
	}
}
