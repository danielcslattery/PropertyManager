package PropertyManager;

import PropertyManager.Entities.*;
import PropertyManager.Repositories.*;
import PropertyManager.Services.ApartmentServiceImpl;
import PropertyManager.Services.BuildingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PropertyManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(PropertyManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PropertyManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BuildingServiceImpl buildingRepository,
								  ApartmentServiceImpl apartmentRepository,
								  PaymentRepository paymentRepository){
		return args -> {
			//save a few apartments
			buildingRepository.addNew("6114 Washington");
			buildingRepository.addNew("5943 Kingsbury");

			apartmentRepository.addNewApartment(1L, "1E");
			apartmentRepository.addNewApartment(1L,"1W");
			apartmentRepository.addNewApartment(1L,"2E");
			apartmentRepository.addNewApartment(1L,"2W");

			apartmentRepository.addNewApartment(2L, "1E");
			apartmentRepository.addNewApartment(2L,"1W");
			apartmentRepository.addNewApartment(2L,"2E");
			apartmentRepository.addNewApartment(2L,"2W");

			for (Apartment apartment : apartmentRepository.getByBuildingId(1L)) {
				apartment.addPayment(paymentRepository, 1200, 9);
			}

			for (Apartment apartment : apartmentRepository.getByBuildingId(1L)) {
				apartment.addPayment(paymentRepository, 1200, 9);
			}

			// Add payment to apartments.  findById returns an Apartment Optional, so get() retrieves the apartment instance.
			apartmentRepository.getById(3L).get().addPayment(paymentRepository, 1200, 10);
			apartmentRepository.getById(4L).get().addPayment(paymentRepository, 1200, 10);

			// fetch all apartments
//			log.info("Apartments found with findAll():");
//			log.info("-------------------------------");
//			for (Apartment apartment : apartmentRepository.findAll()) {
//				log.info(apartment.toString());
//			}
//			log.info("");
//
//			// fetch an individual Apartment by ID
//			Apartment apartment = apartmentRepository.findById(3L).get();
//			log.info("Apartment found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(apartment.toString());
//			log.info("");

		};
	}
}
