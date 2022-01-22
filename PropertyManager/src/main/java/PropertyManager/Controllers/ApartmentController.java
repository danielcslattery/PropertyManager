package PropertyManager.Controllers;

import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Model.Apartment;
import PropertyManager.ServiceInterfaces.ApartmentService;
import PropertyManager.ServiceInterfaces.BuildingService;
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
    private ApartmentRepository repository;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/all")
    @ResponseBody
    public List<Apartment> getAll(){
        return apartmentService.getAll();
    }

    @GetMapping("/byBuilding/{buildingId}")
    @ResponseBody
    public List<Apartment> getByBuilding(@PathVariable Long buildingId){
        return apartmentService.getByBuilding(buildingId);
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
    public String getLatePayments(Model model, @RequestParam int month){
        model.addAttribute("apartments", apartmentService.getLatePayments(month));
        model.addAttribute("month", month);

        return "/Apartments/LatePayments";
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

