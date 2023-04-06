package PropertyManager.repository;

import PropertyManager.model.Apartment;
import PropertyManager.model.Building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    public List<Apartment> findByNumber(String number);

    // Return list of apartments within a specific building
    public List<Apartment> findByBuilding(Building building);

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
