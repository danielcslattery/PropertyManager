package PropertyManager.Repositories;

//import classes.Apartment;
import PropertyManager.Models.Building;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends CrudRepository<Building, Long> {


    @Query(value = "SELECT * FROM Building WHERE Building.buildingaddress = :address",
            nativeQuery = true)
    public List<Building> findBuildingByAddress(@Param("address") String address);


}
