package PropertyManager.Controllers;

import PropertyManager.Model.Apartment;
import PropertyManager.Services.ApartmentService;
import PropertyManager.Services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/apartments")
@CrossOrigin(origins = "http://localhost:4200")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/all")
    public ResponseEntity<List<Apartment>> getAll(){
        return new ResponseEntity<>(apartmentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/byBuilding/{buildingId}")
    public ResponseEntity<List<Apartment>> getByBuilding(@PathVariable Long buildingId){
        return new ResponseEntity<>(apartmentService.getByBuilding(buildingId), HttpStatus.OK);
    }

    // .add() method for service could maybe take a single Apartment argument
    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    public ResponseEntity<Apartment> addNewApartment(@Valid @RequestBody Apartment apartment){
        Apartment apartmentAdded = apartmentService.add(apartment);
        return new ResponseEntity<>(apartmentAdded, HttpStatus.CREATED);
    }


    // Returns apartments where the rents have not been paid for inputted month
    @GetMapping("/latePayments")
    public ResponseEntity<List<Apartment>> getLatePayments(@RequestParam int month){
        List<Apartment> latePaymentApartments = apartmentService.getLatePayments(month);
        System.out.println(latePaymentApartments);
        return new ResponseEntity<>(latePaymentApartments, HttpStatus.OK);
    }

    @DeleteMapping("/{apartmentId}")
    public ResponseEntity<Apartment> delete(@PathVariable Long apartmentId){
        Apartment apartmentDeleted = apartmentService.delete(apartmentId);
        return new ResponseEntity<>(apartmentDeleted, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Apartment> update(@Valid @RequestBody Apartment apartment){
        Apartment apartmentUpdated = apartmentService.update(apartment);
        return new ResponseEntity<>(apartmentUpdated, HttpStatus.OK);
    }
}

