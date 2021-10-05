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
            "AND Apartment.apartment_id = :apartmentId",
            nativeQuery = true)
    public List<Apartment> findApartmentByBuildingAndApartmentId(@Param("building") long building,
                                                            @Param("apartmentId") long apartmentId);

    //Given value for month, returns list of apartment objects
    // where there is not a corresponding payment for that month
    @Query(value = "SELECT * " +
            "FROM apartment " +
            "LEFT JOIN " +
            " (SELECT * " +
            " FROM payment " +
            " WHERE payment.month = :month) " +
            "AS month_payments ON apartment.apartment_id = month_payments.apartment_id " +
            "WHERE month_payments.payment_amount IS NULL",
            nativeQuery = true)
    public List<Apartment> findDelinquentApartments(@Param("month") int month);
}
