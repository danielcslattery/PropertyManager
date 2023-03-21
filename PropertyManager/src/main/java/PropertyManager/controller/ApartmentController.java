package PropertyManager.controller;

import PropertyManager.controller.dto.ApartmentDTO;
import PropertyManager.controller.request.ApartmentRequest;
import PropertyManager.model.Apartment;
import PropertyManager.model.Building;
import PropertyManager.service.ApartmentService;
import PropertyManager.service.BuildingService;
import PropertyManager.service.mapper.RequestMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ApartmentDTO> getAll(){

        List<Apartment> apartments = apartmentService.getAll();
        List<ApartmentDTO> dtos = apartments.stream()
            .map(apartment -> mapper.toDTO(apartment))
            .collect(Collectors.toList());
        System.out.println(dtos);
        return dtos;
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDTO addNewApartment(@Valid @RequestBody ApartmentRequest request){
        Apartment apartment = mapper.toModel(request);

        apartment = apartmentService.add(apartment);

        return mapper.toDTO(apartment);
    }
    
    @DeleteMapping
    public ApartmentDTO delete(@Valid @RequestBody ApartmentRequest request){
        Apartment apartment = mapper.toModel(request);

        apartmentService.delete(apartment);

        return mapper.toDTO(apartment);
    }

    @PutMapping
    public ApartmentDTO update(@Valid @RequestBody ApartmentRequest request){
        Apartment apartment = mapper.toModel(request);

        apartmentService.update(apartment);

        return mapper.toDTO(apartment);
    }

    @GetMapping("/byBuilding/{buildingId}")
    public List<ApartmentDTO> getByBuilding(@PathVariable Long buildingId){
        Building building = buildingService.getById(buildingId);
        List<Apartment> apartments = apartmentService.getByBuilding(building);

        List<ApartmentDTO> dtos = apartments.stream()
            .map(apartment -> mapper.toDTO(apartment))
            .collect(Collectors.toList());

        return dtos;
    }

    // Returns apartments where the rents have not been paid for inputted month
    @GetMapping("/latePayments")
    public List<ApartmentDTO> getLatePayments(@RequestParam int month){

        List<Apartment> apartments = apartmentService.getLatePayments(month);
        List<ApartmentDTO> dtos = apartments.stream()
            .map(apartment -> mapper.toDTO(apartment))
            .collect(Collectors.toList());

        return dtos;
    }

}

