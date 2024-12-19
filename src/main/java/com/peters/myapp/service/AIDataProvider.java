package com.peters.myapp.service;

import com.peters.myapp.dtos.*;

public interface AIDataProvider {
    BusinessResponse getBusinesses();
    ClassResponse getClasses(long businessId);
    FacilityResponse getFacilities(long businessId);
    AppointmentResponse getAppointments(long businessId);
    PackageResponse getPackages(long businessId);
    UserResponse getUsers();
    AddUserResponse addUser(AddUserRequest request);
    AddedClassResponse addClass(AddClassRequest request);
    AddBusinessResponse addBusiness(AddBusinessRequest request);
    AddFacilityResponse addFacility(FacilityRequest request);
    AddAppointmentResponse addAppointment(AppointmentRequest request);
    AddPackageResponse addPackage(PackageRequest request);
    ClassResponse getClassesBetween(ClassFilterRequest request);
    AddBookingResponse addClassToUserBooking(long userId, long classId);
    AddUserResponse getUserByName(String name);
    AddedClassResponse getClassByName(String name);
    BookingResponse getBookings(BookingRequest request);
}
