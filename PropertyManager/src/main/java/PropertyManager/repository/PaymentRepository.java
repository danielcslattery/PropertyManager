package PropertyManager.repository;

import PropertyManager.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

      @Query(value = "SELECT * FROM Payment WHERE Payment.apartment_id = :apartment_id", nativeQuery = true)
      public List<Payment> findPaymentsByApartmentId(@Param("apartment_id") Long apartmentId);
}
