package com.peters.myapp.config;

import com.peters.myapp.dtos.*;
import com.peters.myapp.service.AIDataProvider;
import com.peters.myapp.service.SystemInstructionsLoader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.converter.FormatProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;

import java.util.function.Function;

@Configuration
@Profile("openai")
public class AIFunctionConfiguration implements FormatProvider {
    private final SystemInstructionsLoader systemInstructionsLoader;


    public AIFunctionConfiguration(SystemInstructionsLoader systemInstructionsLoader) {
        this.systemInstructionsLoader = systemInstructionsLoader;
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        String systemInstructions = systemInstructionsLoader.loadSystemInstructions();
        return builder
                .defaultSystem(systemInstructions+getFormat())
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory, AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 10),
                        new SimpleLoggerAdvisor()
                )
                .defaultFunctions("listBusinesses", "addNewBusiness", "addNewFitnessClass",
                        "listBusinessClasses", "listBusinessClassesBetween", "listBusinessFacilities",
                        "listBusinessAppointments", "listBusinessPackages", "addNewUser", "listUsers", "addClassToUserBooking",
                        "getClassByName", "getUserByName", "listUserBookings")
                .build();
    }

    @Override
    public String getFormat() {
        return """
            Your response should be in JSON format.
            Strictly adhere to the following JSON structure requirements:
            - Use a valid JSON object format
            - Ensure all keys and string values are double-quoted
            - Maintain proper JSON syntax
            - Do not include any additional text or explanations
            """;
    }


 // AI function call to retrieve records
    @Bean
    @Description("List the businesses that are onboarded on Spring-fitness. " +
            "Returns a comprehensive list of all registered businesses. " +
            "Can be filtered or paginated based on specific requirements.")
    public Function<BusinessRequest, BusinessResponse> listBusinesses(AIDataProvider dataProvider) {
        return request -> {
            BusinessResponse response = dataProvider.getBusinesses();
            System.out.println("Total businesses retrieved: " + response.businesses().size());
            return response;
        };
    }
    @Bean
    @Description("List the classes that belong to a business on Spring-fitness identified by businessId. " +
            "Returns a comprehensive list of all classes that belongs businesses. " +
            "Can be filtered or paginated based on specific requirements.")
    public Function<ClassRequest, ClassResponse> listBusinessClasses(AIDataProvider dataProvider) {
        return request ->{
            ClassResponse response = dataProvider.getClasses(request.businessId());
            System.out.println("Total classes retrieved: " + response.fitnessClasses().size());
            return response;
        };
    }

    @Bean
    @Description("Fetch a fitness class by name")
    public Function<EntityRequest, AddedClassResponse> getClassByName(AIDataProvider dataProvider) {
        return request ->{
            AddedClassResponse response = dataProvider.getClassByName(request.name());
            System.out.println("Class retrieved: " + response.fitnessClass().getName());
            return response;
        };
    }

    @Bean
    @Description("Fetch a user class by name")
    public Function<EntityRequest, AddUserResponse> getUserByName(AIDataProvider dataProvider) {
        return request ->{
            AddUserResponse response = dataProvider.getUserByName(request.name());
            System.out.println("User retrieved: " + response.user().getName());
            return response;
        };
    }

    @Bean
    @Description("List the classes that matches the request parameters " +
            "Returns a comprehensive list of all classes that matches query parameters.")
    public Function<ClassFilterRequest, ClassResponse> listBusinessClassesBetween(AIDataProvider dataProvider) {
        return request ->{
            ClassResponse response = dataProvider.getClassesBetween(request);
            System.out.println("Total classes retrieved: " + response.fitnessClasses().size());
            return response;
        };
    }

    @Bean
    @Description("List the facilities that belong to a business on Spring-fitness identified by businessId. " +
            "Returns a comprehensive list of all facilities that belongs businesses.")
    public Function<FacilityRequest, FacilityResponse> listBusinessFacilities(AIDataProvider dataProvider) {
        return request ->{
            FacilityResponse response = dataProvider.getFacilities(request.businessId());
            System.out.println("Total facilities retrieved: " + response.facilities().size());
            return response;
        };
    }

    @Bean
    @Description("List the appointments that belong to a business on Spring-fitness identified by businessId. " +
            "Returns a comprehensive list of all appointments that belongs businesses.")
    public Function<AppointmentRequest, AppointmentResponse> listBusinessAppointments(AIDataProvider dataProvider) {
        return request ->{
            AppointmentResponse response = dataProvider.getAppointments(request.businessId());
            System.out.println("Total appointments retrieved: " + response.appointments().size());
            return response;
        };
    }

    @Bean
    @Description("List the packages that offered by a business on Spring-fitness identified by businessId. " +
            "Returns a comprehensive list of all packages that belongs businesses.")
    public Function<PackageRequest, PackageResponse> listBusinessPackages(AIDataProvider dataProvider) {
        return request ->{
            PackageResponse response = dataProvider.getPackages(request.businessId());
            System.out.println("Total packages retrieved: " + response.packages().size());
            return response;
        };
    }

    @Bean
    @Description("List bookings in Spring-fitness identified by userId. " +
            "Returns a comprehensive list of all booking that belongs user.")
    public Function<BookingRequest, BookingResponse> listUserBookings(AIDataProvider dataProvider) {
        return request ->{
            BookingResponse response = dataProvider.getBookings(request);
            System.out.println("Total bookings retrieved: " + response.bookings().size());
            return response;
        };
    }

    @Bean
    @Description("Book fitness class identified by classId on behalf of a user identified by userId." +
            " - Returns booking confirmation with booking details" +
            " Use this function when a user wants to book/register for a specific fitness class.")
    public Function<AddBookingRequest, AddBookingResponse> addClassToUserBooking(AIDataProvider dataProvider) {
        return request -> dataProvider.addClassToUserBooking(request.userId(), request.classId());
    }

    @Bean
    @Description("List the users on Spring-fitness" +
            "Returns a comprehensive list of all users that belongs to Spring-fitness.")
    public Function<UserRequest, UserResponse> listUsers(AIDataProvider dataProvider) {
        return request ->{
            UserResponse response = dataProvider.getUsers();
            System.out.println("Total users retrieved: " + response.user().size());
            return response;
        };
    }


    // AI function call to add record
    @Bean
    @Description("Add a fitness class to a business with strict validation:" +
            " - Allowed categories: dance, boxing, karate, weight-lifting, treadmill" +
            " - Name must be unique within the business" +
            " - Description: 10-500 characters" +
            " - Capacity: Between 5-50 participants" +
            " - Start date: Validate date format (yyy-mm-dd)" +
            " - End date: Validate date format (yyy-mm-dd)" +
            " - Start time: Validate time format (hh:mm:ss)" +
            " - End time: Validate time format (hh:mm:ss)" +
            " - Price: Minimum $10" +
            " - Class duration: Minimum 30 minutes")
    public Function<AddClassRequest, AddedClassResponse> addNewFitnessClass(AIDataProvider dataProvider){
        return dataProvider::addClass;
    }

    @Bean
    @Description("Add a user to Spring-fitness." +
            "The user must include name," +
            "valid email address," +
            "a 10-digit phone number," +
            "and gender")
    public Function<AddUserRequest, AddUserResponse> addNewUser(AIDataProvider dataProvider){
        return dataProvider::addUser;
    }

    // AI function call to add record with validation
    @Bean
    @Description("Add a new business to the Spring-fitness. "
            + "The business must include a business name, business address, city, state, country, contact person, "
            + "plus a valid email and a 10-digit phone number" +
            " if any of the mentioned fields is not provided, kindly tell the user to provide the required field " +
            " Check if the business name already exist then tell the user that the business name already exist")
    public Function<AddBusinessRequest, AddBusinessResponse> addNewBusiness(AIDataProvider dataProvider){
        return request -> {
            try {
                return dataProvider.addBusiness(request);
            } catch (IllegalArgumentException e) {
                // Return an error response for the AI to interpret
                throw new RuntimeException("Validation error: " + e.getMessage());
            }
        };
    }
}
