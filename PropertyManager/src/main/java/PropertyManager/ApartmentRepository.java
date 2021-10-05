package PropertyManager;

//import classes.Apartment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {

    Apartment findById(long id);

    @Query(value = "SELECT * FROM Apartment WHERE Apartment.apartment_number = :number",
    nativeQuery = true)
    public List<Apartment> findApartmentByNumber(@Param("number") String number);

    @Query(value = "SELECT * FROM Apartment WHERE Apartment.building_id = :building",
            nativeQuery = true)
    public List<Apartment> findApartmentByBuilding(@Param("building") Long building);

    @Query(value = "SELECT * FROM Apartment " +
            "WHERE Apartment.building_id = :building " +
            "AND Apartment.apartment_number = :number",
            nativeQuery = true)
    public List<Apartment> findApartmentByBuildingAndNumber(@Param("building") long building,
                                                            @Param("number") String number);
}
