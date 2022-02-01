package PropertyManager;

import PropertyManager.Model.*;
import PropertyManager.Services.ApartmentService;
import PropertyManager.Services.BuildingService;
import PropertyManager.Services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;


@SpringBootApplication
public class PropertyManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(PropertyManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PropertyManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BuildingService buildingService,
								  ApartmentService apartmentService,
								  PaymentService paymentService){
		return args -> {

			AddDefaultData(buildingService, apartmentService, paymentService);
			// fetch all apartments
//			log.info("Apartments found with findAll():");
//			log.info("-------------------------------");
//			for (Apartment apartment : apartmentService.findAll()) {
//				log.info(apartment.toString());
//			}
//			log.info("");
//
//			// fetch an individual Apartment by ID
//			Apartment apartment = apartmentService.findById(3L).get();
//			log.info("Apartment found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(apartment.toString());
//			log.info("");

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


		for (Apartment apartment : apartmentService.getByBuilding(1L)) {
			paymentService.add(new Payment(apartment.getId(), 1200, currentMonth));
		}

		for (Apartment apartment : apartmentService.getByBuilding(2L)) {
			paymentService.add(new Payment(apartment.getId(), 1200, currentMonth));
		}

		for (Apartment apartment : apartmentService.getByBuilding(1L)) {
			paymentService.add(new Payment(apartment.getId(), 1200, (currentMonth + 1) % 12));
		}


	}
}
