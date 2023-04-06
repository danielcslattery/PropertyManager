package PropertyManager.repository;

import PropertyManager.model.Building;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    public List<Building> findByAddress(String address);

}
