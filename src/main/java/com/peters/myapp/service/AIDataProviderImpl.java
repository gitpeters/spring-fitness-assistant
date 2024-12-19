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
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public AIDataProviderImpl(BusinessRepository businessRepository, ClassRepository classRepository, FacilityRepository facilityRepository, AppointmentRepository appointmentRepository, PackageRepository packageRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.businessRepository = businessRepository;
        this.classRepository = classRepository;
        this.facilityRepository = facilityRepository;
        this.appointmentRepository = appointmentRepository;
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
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
    public UserResponse getUsers() {
        List<User> users = userRepository.findAll();
        return new UserResponse(users);
    }

    @Override
    public AddUserResponse addUser(AddUserRequest request) {
        User user = request.user();
        userRepository.save(user);
        return new AddUserResponse(user);
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
    public AddBookingResponse addClassToUserBooking(long userId, long classId) {
        User user = userRepository.findById(userId).orElse(null);
        FitnessClass fitnessClass = classRepository.findById(classId).orElse(null);
        if(user!=null && fitnessClass!=null) {
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setFitnessClass(fitnessClass);
            bookingRepository.save(booking);
            return new AddBookingResponse(booking);
        }
        return null;
    }

    @Override
    public BookingResponse getBookings(BookingRequest request) {
        List<Booking> bookings = bookingRepository.findAllByUserId(request.userId());
        return new BookingResponse(bookings);
    }

    @Override
    public AddUserResponse getUserByName(String name) {
        User user = userRepository.findFirstByName(name.trim()).orElse(null);
        return new AddUserResponse(user);
    }

    @Override
    public AddedClassResponse getClassByName(String name) {
        FitnessClass fitnessClass = classRepository.findFirstByName(name.trim()).orElse(null);
        return new AddedClassResponse(fitnessClass, fitnessClass!=null ? fitnessClass.getBusiness() : null);
    }

    @Override
    public AddBusinessResponse addBusiness(AddBusinessRequest request) {
        Business business = mapTo(request);
        businessRepository.save(business);
        return new AddBusinessResponse(business);
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
