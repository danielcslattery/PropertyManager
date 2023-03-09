package PropertyManager.controller;

import PropertyManager.controller.request.ApartmentRequest;
import PropertyManager.model.Apartment;
import PropertyManager.service.ApartmentService;
import PropertyManager.service.BuildingService;
import PropertyManager.service.mapper.RequestMapper;

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

    @Autowired
    private RequestMapper mapper;

    @GetMapping
    public List<Apartment> getAll(){
        return apartmentService.getAll();
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Apartment addNewApartment(@Valid @RequestBody ApartmentRequest request){
        Apartment apartment = mapper.toModel(request);

        return apartmentService.add(apartment);
    }
    
    @DeleteMapping
    public Apartment delete(@Valid @RequestBody ApartmentRequest request){
        Apartment apartment = mapper.toModel(request);

        return apartmentService.delete(apartment);
    }

    @PutMapping
    public Apartment update(@Valid @RequestBody ApartmentRequest request){
        Apartment apartment = mapper.toModel(request);

        return apartmentService.update(apartment);
    }

    @GetMapping("/byBuilding/{buildingId}")
    public List<Apartment> getByBuilding(@PathVariable Long buildingId){
        return apartmentService.getByBuilding(buildingService.getById(buildingId));
    }

    // Returns apartments where the rents have not been paid for inputted month
    @GetMapping("/latePayments")
    public List<Apartment> getLatePayments(@RequestParam int month){
        return apartmentService.getLatePayments(month);
    }

}

