package PropertyManager.Repositories;

import PropertyManager.Model.Apartment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {


    @Query(value = "SELECT * FROM Apartment WHERE Apartment.apartment_number = :number",
    nativeQuery = true)
    public List<Apartment> findApartmentByNumber(@Param("number") String number);

    // Return list of apartments within a specific building
    @Query(value = "SELECT * FROM Apartment WHERE Apartment.building_id = :building",
            nativeQuery = true)
    public List<Apartment> findApartmentByBuilding(@Param("building") Long building);

    // Used to find apartment object to add payment to
    @Query(value = "SELECT * FROM Apartment " +
            "WHERE Apartment.building_id = :building " +
            "AND Apartment.apartment_id = :apartment",
            nativeQuery = true)
    public List<Apartment> findApartmentByBuildingAndApartmentId(@Param("building") long building,
                                                            @Param("apartment") long apartment);

    // Given value for month, returns list of apartment objects
    // where there is not a corresponding payment for that month
    @Query(value = "SELECT * " +
            "FROM apartment " +
            "LEFT JOIN " +
            " (SELECT * " +
            " FROM payment " +
            " WHERE payment.month = :month) " +
            "AS month_payments ON apartment.id = month_payments.apartment_id " +
            "WHERE month_payments.amount IS NULL",
            nativeQuery = true)
    public List<Apartment> findLatePayments(@Param("month") int month);
}
