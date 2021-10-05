package PropertyManager;

//import classes.Apartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//TODO: Learn how to organize controllers, repositories, and classes into separate packages.
//@ComponentScan({"classes.Apartment", "repositories.ApartmentRepository"})
public class PropertyManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(PropertyManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PropertyManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BuildingRepository buildingRepository,
								  ApartmentRepository apartmentRepository,
								  PaymentRepository paymentRepository){
		return args -> {
			//save a few apartments
			buildingRepository.save(new Building("6114 Washington"));
			apartmentRepository.save(new Apartment(1L, "1E"));
			apartmentRepository.save(new Apartment(1L,"1W"));
			apartmentRepository.save(new Apartment(1L,"2E"));
			apartmentRepository.save(new Apartment(1L,"2W"));
			for (Apartment apartment : apartmentRepository.findAll()) {
				apartment.addPayment(paymentRepository, 1200, 9);
			}
			apartmentRepository.findById(2).addPayment(paymentRepository, 1200, 10);
			apartmentRepository.findById(3).addPayment(paymentRepository, 1200, 10);

			// fetch all apartments
			log.info("Apartments found with findAll():");
			log.info("-------------------------------");
			for (Apartment apartment : apartmentRepository.findAll()) {
				log.info(apartment.toString());
			}
			log.info("");

			// fetch an individual Apartment by ID
			Apartment apartment = apartmentRepository.findById(2L);
			log.info("Apartment found with findById(1L):");
			log.info("--------------------------------");
			log.info(apartment.toString());
			log.info("");

		};
	}
}
