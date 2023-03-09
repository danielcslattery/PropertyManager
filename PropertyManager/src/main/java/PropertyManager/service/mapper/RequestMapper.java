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

        apartment.setNumber(request.getNumber());
        apartment.setBuildingId(request.getBuildingId());

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

        payment.setApartmentId(request.getApartmentId());
        payment.setAmount(request.getAmount());
        payment.setMonth(request.getMonth());

        return payment;
    }
}
