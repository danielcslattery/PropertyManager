package PropertyManager.repository;

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
public class BuildingRepositoryTest {
    
    @Autowired
    private BuildingRepository buildingRepository;
    
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    public void BuildingRepository_Save_AddsToRepository() {
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);

        Assertions.assertThat(buildingRepository.findById(building.getId())).isPresent();
    }

    @Test
    public void BuildingRepository_FindAll_ReturnsList() {

        Assertions.assertThat(buildingRepository.findAll().size()).isEqualTo(2);

        buildingRepository.save(new Building("1234 Sesame Street"));
        buildingRepository.save(new Building("9876 Washington Avenue"));

        List<Building> buildings = buildingRepository.findAll();

        Assertions.assertThat(buildings).isNotNull();
        Assertions.assertThat(buildings.size()).isEqualTo(4);
    }

    @Test
    public void BuildingRepository_Delete_RemovesFromDatabase(){
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);

        buildingRepository.delete(building);

        Assertions.assertThat(buildingRepository.findById(building.getId())).isEmpty();
    }

    @Test
    public void BuildingRepository_Save_UpdatesBuildings(){
        Building building = new Building("1234 Sesme Street");

        buildingRepository.save(building);

        building.setAddress("1234 Sesame Street");
        buildingRepository.save(building);

        Assertions.assertThat(buildingRepository.findById(building.getId())).isNotEmpty();
        Assertions.assertThat(buildingRepository.findById(building.getId()).get().getAddress()).isNotEqualTo("1234 Sesme Street");
        Assertions.assertThat(buildingRepository.findById(building.getId()).get().getAddress()).isEqualTo("1234 Sesame Street");
    }

    @Test
    public void ApartmentRepository_Delete_DeletesRelatedApartments(){
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);
        Apartment apartment = new Apartment(building, "1W");
        apartmentRepository.save(apartment);

        Assertions.assertThat(apartmentRepository.findById(apartment.getId())).isNotEmpty();

        buildingRepository.delete(building);

        Assertions.assertThat(apartmentRepository.findById(apartment.getId())).isEmpty();
    }
}
