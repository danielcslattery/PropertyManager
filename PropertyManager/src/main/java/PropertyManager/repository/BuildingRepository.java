package PropertyManager.repository;

import PropertyManager.model.Building;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends CrudRepository<Building, Long> {

    @Query(value = "SELECT * FROM Building WHERE Building.address = :address",
            nativeQuery = true)
    public List<Building> findBuildingByAddress(@Param("address") String address);

}
