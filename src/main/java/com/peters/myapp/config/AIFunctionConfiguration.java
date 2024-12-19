package com.peters.myapp.config;

import com.peters.myapp.dtos.*;
import com.peters.myapp.service.AIDataProvider;
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

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        return builder
                .defaultSystem("""
                        You are a friendly AI assistant designed to help with the management of a wellness application call Livwell.
                        On a first response, you are to introduce yourself as 'Livwell AI' and briefly tell the users what you can help them with.
                        
                        IMPORTANT - FUNCTION CALLING RULES:
                        - For ANY questions about businesses, classes, or other data, you MUST use the provided functions
                        - NEVER make up or fabricate information about businesses, locations, or any other data
                        - ALWAYS use the listBusinesses() function when asked about business information
                        - ANY request for business information MUST trigger a function call
                        - DO NOT generate fake responses about business locations, services, or details
                        
                        Core Rules:
                        - Do not expose the business statistics to regular users. This information is reserved for business owners alone.
                        - Livwell offer a B2B services to all wellness businesses like gym, therapy clinic, physiotherapy, hospital, general health, etc.
                        - Your job is to answer questions about the businesses that are onboarded on livwell, fitness instructors, fitness classes, facilities, appointments available in the wellness application and packages offered by offered by each business using Livwell, recommend most attended classes, must booked appointments and facilities to users of a business
                        
                        Function Usage Examples:
                        - "List all businesses onboarded" → MUST use listBusinesses() function
                        - "Where is business X located?" → MUST use listBusinesses() function
                        - "Add new business" → MUST use addNewBusiness() function
                        - "Add new fitness class" → MUST use addNewFitnessClass() function
                        
                        Data Access Rules:
                        - ALL business information MUST come from function calls
                        - NEVER create fictional business details
                        - ALL responses about business data MUST be based on function results
                        
                        Response Rules:
                        - If you do not know the answer, politely tell the user you don't know the answer
                        - If function call fails, inform the user there was an error retrieving the data
                        - Do not provide answer to query that is not within Livwell system
                        
                        IMPORTANT RULES FOR ERROR HANDLING:
                        - If a function returns an error, politely explain the error to the user.
                        - Use the exact error message returned by the function, but rephrase it for clarity if necessary.
                        - Provide guidance on how the user can correct the input.
                        
                        """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory, AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 10),
                        new SimpleLoggerAdvisor()
                )
                .defaultFunctions("listBusinesses", "addNewBusiness", "addNewBusiness", "listBusinessClasses", "listBusinessClassesBetween", "listBusinessFacilities", "listBusinessAppointments", "listBusinessPackages")
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
    @Description("List the businesses that are onboarded on Livwell. " +
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
    @Description("List the classes that belong to a business on Livwell identified by businessId. " +
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
    @Description("List the facilities that belong to a business on Livwell identified by businessId. " +
            "Returns a comprehensive list of all facilities that belongs businesses.")
    public Function<FacilityRequest, FacilityResponse> listBusinessFacilities(AIDataProvider dataProvider) {
        return request ->{
            FacilityResponse response = dataProvider.getFacilities(request.businessId());
            System.out.println("Total facilities retrieved: " + response.facilities().size());
            return response;
        };
    }

    @Bean
    @Description("List the appointments that belong to a business on Livwell identified by businessId. " +
            "Returns a comprehensive list of all appointments that belongs businesses.")
    public Function<AppointmentRequest, AppointmentResponse> listBusinessAppointments(AIDataProvider dataProvider) {
        return request ->{
            AppointmentResponse response = dataProvider.getAppointments(request.businessId());
            System.out.println("Total appointments retrieved: " + response.appointments().size());
            return response;
        };
    }

    @Bean
    @Description("List the packages that offered by a business on Livwell identified by businessId. " +
            "Returns a comprehensive list of all packages that belongs businesses.")
    public Function<PackageRequest, PackageResponse> listBusinessPackages(AIDataProvider dataProvider) {
        return request ->{
            PackageResponse response = dataProvider.getPackages(request.businessId());
            System.out.println("Total packages retrieved: " + response.packages().size());
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

    // AI function call to add record with validation
    @Bean
    @Description("Add a new business to the Livwell. "
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
