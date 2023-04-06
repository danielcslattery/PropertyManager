package PropertyManager.service.mapper;

import PropertyManager.model.Apartment;
import PropertyManager.model.Building;
import PropertyManager.model.Payment;
import PropertyManager.repository.ApartmentRepository;
import PropertyManager.repository.BuildingRepository;
import PropertyManager.repository.PaymentRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import PropertyManager.controller.dto.ApartmentDTO;
import PropertyManager.controller.dto.BuildingDTO;
import PropertyManager.controller.dto.PaymentDTO;
import PropertyManager.controller.request.ApartmentRequest;
import PropertyManager.controller.request.BuildingRequest;
import PropertyManager.controller.request.PaymentRequest;

@Component
public class RequestMapper {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    
    public Building toModel(BuildingRequest request){
        Optional<Building> buildingOpt = buildingRepository.findById(request.getId());
        Building building;

        if (buildingOpt.isPresent()) {
            building = buildingOpt.get();
        } else {
            building = new Building();
        }

        building.setAddress(request.getAddress());
        return building;
    } 

    public Apartment toModel(ApartmentRequest request){
        Optional<Apartment> apartmentOpt = apartmentRepository.findById(request.getId());
        Apartment apartment;

        if (apartmentOpt.isPresent()){
            apartment = apartmentOpt.get();
        } else {
            apartment = new Apartment();
        }

        Building building = buildingRepository.findById(request.getBuildingId()).get();
        apartment.setNumber(request.getNumber());
        apartment.setBuilding(building);

        return apartment;
    }

    public Payment toModel(PaymentRequest request){
        Optional<Payment> paymentOpt = paymentRepository.findById(request.getId());
        Payment payment;

        if (paymentOpt.isPresent()){
            payment = paymentOpt.get();
        } else {
            payment = new Payment();
        }

        Apartment apartment = apartmentRepository.findById(request.getApartmentId()).get();
        payment.setApartment(apartment);
        payment.setAmount(request.getAmount());
        payment.setMonth(request.getMonth());

        return payment;
    }


    public PaymentDTO toDTO(Payment payment){
        PaymentDTO dto = new PaymentDTO();

        dto.setId(payment.getId());
        dto.setApartmentId(payment.getApartment().getId());
        dto.setAmount(payment.getAmount());
        dto.setMonth(payment.getMonth());

        return dto;
    }

    public ApartmentDTO toDTO(Apartment apartment){
        ApartmentDTO dto = new ApartmentDTO();

        dto.setId(apartment.getId());
        dto.setBuildingId(apartment.getBuilding().getId());
        dto.setNumber(apartment.getNumber());

        return dto;
    }

    public BuildingDTO toDTO(Building building){
        BuildingDTO dto = new BuildingDTO();

        dto.setAddress(building.getAddress());
        dto.setId(building.getId());
        dto.setNumberApartments(building.getNumberApartments());

        return dto;
    }
}
