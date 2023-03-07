package PropertyManager.controller;

import PropertyManager.model.Apartment;
import PropertyManager.service.ApartmentService;
import PropertyManager.service.BuildingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public ResponseEntity<List<Apartment>> getAll(){
        return new ResponseEntity<>(apartmentService.getAll(), HttpStatus.OK);
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    public ResponseEntity<Apartment> addNewApartment(@Valid @RequestBody Apartment apartment){
        return new ResponseEntity<>(apartmentService.add(apartment), HttpStatus.CREATED);
    }
    
    @DeleteMapping
    public ResponseEntity<Apartment> delete(@Valid @RequestBody Apartment apartment){
        return new ResponseEntity<>(apartmentService.delete(apartment), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Apartment> update(@Valid @RequestBody Apartment apartment){
        return new ResponseEntity<>(apartmentService.update(apartment), HttpStatus.OK);
    }

    @GetMapping("/byBuilding/{buildingId}")
    public ResponseEntity<List<Apartment>> getByBuilding(@PathVariable Long buildingId){
        return new ResponseEntity<>(apartmentService.getByBuilding(buildingService.getById(buildingId)), HttpStatus.OK);
    }

    // Returns apartments where the rents have not been paid for inputted month
    @GetMapping("/latePayments")
    public ResponseEntity<List<Apartment>> getLatePayments(@RequestParam int month){
        return new ResponseEntity<>(apartmentService.getLatePayments(month), HttpStatus.OK);
    }

}
