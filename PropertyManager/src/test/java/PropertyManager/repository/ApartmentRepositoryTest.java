package PropertyManager.repository;

import java.util.Arrays;
import java.util.List;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import PropertyManager.model.Apartment;
import PropertyManager.model.Building;

@SpringBootTest
@Transactional
public class ApartmentRepositoryTest {

    @Autowired
    private ApartmentRepository apartmentRepository;

    
    @Autowired
    private BuildingRepository buildingRepository;
    
    @Test
    public void ApartmentRepository_Save_AddsToRepository() {
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);
        Apartment apartment = new Apartment(building, "1W");
        apartmentRepository.save(apartment);

        Assertions.assertThat(apartmentRepository.findById(apartment.getId())).isPresent();
    }

    @Test
    public void ApartmentRepository_FindAll_ReturnsList() {
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);

        Assertions.assertThat(apartmentRepository.findAll().size()).isEqualTo(8);

        Apartment apartment1 = new Apartment(building, "1W");
        Apartment apartment2 = new Apartment(building, "1E");
        apartmentRepository.save(apartment1);
        apartmentRepository.save(apartment2);

        List<Apartment> apartments = apartmentRepository.findAll();

        Assertions.assertThat(apartments).isNotNull();
        Assertions.assertThat(apartments.size()).isEqualTo(10);
    }

    @Test
    public void ApartmentRepository_Delete_RemovesFromDatabase(){
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);
        Apartment apartment = new Apartment(building, "1W");
        apartmentRepository.save(apartment);

        apartmentRepository.delete(apartment);

        Assertions.assertThat(apartmentRepository.findById(apartment.getId())).isEmpty();
    }

    @Test
    public void ApartmentRepository_Save_UpdatesApartments(){
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);
        Apartment apartment = new Apartment(building, "1Q");
        apartmentRepository.save(apartment);

        apartment.setNumber("1W");
        apartmentRepository.save(apartment);

        Assertions.assertThat(apartmentRepository.findById(apartment.getId())).isNotEmpty();
        Assertions.assertThat(apartmentRepository.findById(apartment.getId()).get().getNumber()).isNotEqualTo("1Q");
        Assertions.assertThat(apartmentRepository.findById(apartment.getId()).get().getNumber()).isEqualTo("1W");
    }

    @Test
    public void ApartmentRepository_FindByBulding_ReturnsCorrectList(){
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);

        Assertions.assertThat(apartmentRepository.findAll().size()).isEqualTo(8);

        Apartment apartment2 = new Apartment(building, "1E");
        Apartment apartment1 = new Apartment(building, "1W");
        apartmentRepository.save(apartment2);
        apartmentRepository.save(apartment1);

        List<Apartment> apartments = Arrays.asList(apartment1, apartment2);
        Assertions.assertThat(apartmentRepository.findByBuilding(building)).containsAll(apartments);
    }

}
