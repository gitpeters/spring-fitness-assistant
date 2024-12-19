package com.peters.myapp.service;

import com.peters.myapp.dtos.*;
import com.peters.myapp.models.*;
import com.peters.myapp.models.Package;
import com.peters.myapp.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIDataProviderImpl implements AIDataProvider {
    private final BusinessRepository businessRepository;
    private final ClassRepository classRepository;
    private final FacilityRepository facilityRepository;
    private final AppointmentRepository appointmentRepository;
    private final PackageRepository packageRepository;

    public AIDataProviderImpl(BusinessRepository businessRepository, ClassRepository classRepository, FacilityRepository facilityRepository, AppointmentRepository appointmentRepository, PackageRepository packageRepository) {
        this.businessRepository = businessRepository;
        this.classRepository = classRepository;
        this.facilityRepository = facilityRepository;
        this.appointmentRepository = appointmentRepository;
        this.packageRepository = packageRepository;
    }

    @Override
    public BusinessResponse getBusinesses() {
        List<Business> businesses = businessRepository.findAll();
        return new BusinessResponse(businesses);
    }

    @Override
    public ClassResponse getClasses(long businessId) {
        List<FitnessClass> classes = classRepository.findAllByBusinessId(businessId);
        return new ClassResponse(classes);
    }

    @Override
    public AddedClassResponse addClass(AddClassRequest request) {
        Business business = businessRepository.findById(request.businessId()).orElse(null);
        if(business != null) {
            FitnessClass fitnessClass = request.fitnessClass();
            fitnessClass.setBusiness(business);
            fitnessClass.setCategory(Category.valueOf(request.category().toUpperCase()));
            classRepository.save(fitnessClass);
            return new AddedClassResponse(fitnessClass, business);
        }
        return null;
    }

    @Override
    public FacilityResponse getFacilities(long businessId) {
        List<Facility> facilities = facilityRepository.findAllByBusinessId(businessId);
        return new FacilityResponse(facilities);
    }

    @Override
    public AppointmentResponse getAppointments(long businessId) {
        List<Appointment> appointments = appointmentRepository.findAllByBusinessId(businessId);
        return new AppointmentResponse(appointments);
    }

    @Override
    public PackageResponse getPackages(long businessId) {
        List<Package> packages = packageRepository.findAllByBusinessId(businessId);
        return new PackageResponse(packages);
    }

    @Override
    public AddFacilityResponse addFacility(FacilityRequest request) {
        Business business = businessRepository.findById(request.businessId()).orElse(null);
        if(business != null) {
            Facility facility = request.facility();
            facility.setBusiness(business);
            facilityRepository.save(facility);
            return new AddFacilityResponse(facility, business);
        }
        return null;
    }

    @Override
    public AddAppointmentResponse addAppointment(AppointmentRequest request) {
        Business business = businessRepository.findById(request.businessId()).orElse(null);
        if(business != null) {
            Appointment appointment = request.appointment();
            appointment.setBusiness(business);
            appointmentRepository.save(appointment);
            return new AddAppointmentResponse(appointment, business);
        }
        return null;
    }

    @Override
    public AddPackageResponse addPackage(PackageRequest request) {
        Business business = businessRepository.findById(request.businessId()).orElse(null);
        if(business != null) {
            Package _package = request._package();
            _package.setBusiness(business);
            packageRepository.save(_package);
            return new AddPackageResponse(_package, business);
        }
        return null;
    }

    @Override
    public ClassResponse getClassesBetween(ClassFilterRequest request) {
        List<FitnessClass> classes = classRepository.findAllByStartDateBetween(request.startDate(), request.endDate());
        return new ClassResponse(classes);
    }

    @Override
    public AddBusinessResponse addBusiness(AddBusinessRequest request) {
//        validateRequest(request);
        Business business = mapTo(request);
        businessRepository.save(business);
        return new AddBusinessResponse(business);
    }

    private void validateRequest(AddBusinessRequest request) {
        if (request.name() == null || request.name().length() < 2 || request.name().length() > 50) {
            throw new IllegalArgumentException("Business name must be unique and 2-50 characters long.");
        }

        // Validate address
        if (request.address() == null || request.address().isBlank()) {
            throw new IllegalArgumentException("Address is required.");
        }

        // Validate contact person
        if (request.contactPerson() == null || !request.contactPerson().trim().contains(" ")) {
            throw new IllegalArgumentException("Contact Person must include a first and last name.");
        }

        // Validate email
        if (request.email() == null || !request.email().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Validate phone
        if (request.phone() == null || !request.phone().matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }

        // Perform duplicate name check
        if (businessRepository.existsByName(request.name().trim())) {
            throw new IllegalArgumentException("Business name already exists.");
        }

    }

    private Business mapTo(AddBusinessRequest request) {
        Business business = new Business();
                business.setName(request.name());
                business.setAddress(request.address());
                business.setCity(request.city());
                business.setState(request.state());
                business.setCountry(request.country());
                business.setEmail(request.email());
                business.setPhone(request.phone());
                business.setContactPerson(request.contactPerson());
                return business;
    }
}
