package PropertyManager.Repositories;

import PropertyManager.Model.Building;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface BuildingRepository extends CrudRepository<Building, Long> {

    @Query(value = "SELECT * FROM Building WHERE Building.address = :address",
            nativeQuery = true)
    public List<Building> findBuildingByAddress(@Param("address") String address);

    // Directly adding or deleting from a field when the number of apartments changes may be
    // easier but this uses the concept of GROUP BY and UPDATE, which is good to understand
    @Modifying
    @Transactional
    @Query(value = "UPDATE Building AS d " +
            "INNER JOIN" +
            "   (SELECT a.building_id, COUNT(a.id) AS count FROM Apartment AS a " +
            "    GROUP BY a.building_id) AS c " +
            "ON d.id = c.building_id " +
            "SET d.number_apartments = c.count;", nativeQuery = true)
    public void updateNumberOfApartmentsInBuilding();
}
