# Spring Fitness Assistant

A smart fitness assistant that leverages OpenAI's capabilities through Spring AI to provide personalized fitness guidance and business information to users.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Project Configuration](#project-configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)

## Prerequisites

Before you begin, ensure you have the following installed:
- Java 17 or higher
- Maven 3.6+
- Git
- Your favorite IDE (IntelliJ IDEA recommended)

## Setup

### 1. OpenAI Account Creation
1. Visit [OpenAI's website](https://platform.openai.com/signup)
2. Create an account or sign in
3. Navigate to API section
4. Generate an API key
5. Save your API key securely - you'll need it for configuration

### 2. Project Dependencies

Add these dependencies to your `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring AI -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
    </dependency>
    
    <!-- Other common dependencies -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### 3. Configuration

Create `application.yml` in `src/main/resources`:

```properties
spring.profiles.active=openai

spring.datasource.url=jdbc:mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.jpa.generate-ddl=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update

spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-3.5-turbo
spring.ai.openai.project-id=${OPENAI_PROJECT_ID}
```

Create `.env` file in project root:
```
OPENAI_API_KEY=your_api_key_here
```

## Project Configuration

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── peters/
│   │           └── fitnessbot/
│   │               ├── FitnessBotApplication.java
│   │               ├── config/
│   │               ├── controller/
│   │               ├── service/
│   │               └── model/
│   └── resources/
│       ├── application.yml
│       └── static/
└── test/
    └── java/
```

### Basic Configuration Class

```java
import org.springframework.stereotype.Service;

@Configuration
public class OpenAIConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        return builder
                .defaultSystem("""
                        <WELL FORMAT INSTRUCTIONS FOR THE AI TO FOLLOW>
                        """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory, AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 10),
                        new SimpleLoggerAdvisor()
                )
                // list out your functions for the ai model to register
                .defaultFunctions("listBusinesses", "addNewBusiness", "addNewBusiness", "listBusinessClasses", "listBusinessClassesBetween", "listBusinessFacilities", "listBusinessAppointments", "listBusinessPackages")
                .build();
    }
    
    // Function declarations
    // Replace <YOUR BUSINESS NAME> with your system or application name 
    @Bean
    @Description("List the businesses that are onboarded on <YOUR BUSINESS NAME>. " +
            "Returns a comprehensive list of all registered businesses. " +
            "Can be filtered or paginated based on specific requirements.")
    public Function<BusinessRequest, BusinessResponse> listBusinesses(AIDataProvider dataProvider) {
        return request -> {
            return dataProvider.getBusinesses();
        };
    }
}

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    public String chatWithAI(String query){
        return
                this.chatClient
                        .prompt()
                        .user(u->{
                            u.text(query);
                        })
                        .call()
                        .content();
    }
}
@RestController
@RequestMapping("/api/v1/ai")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String chatWithAI(@RequestBody String message) {
        return chatService.chatWithAI(message);
    }
}
```

### Environment Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/fitness-bot.git
```

2. Navigate to project directory:
```bash
cd fitness-bot
```

3. Install dependencies:
```bash
mvn clean install
```

4. Set up environment variables:
```bash
export OPENAI_API_KEY=your_api_key_here
```

## Running the Application

1. Start the application:
```bash
mvn spring-boot:run
```

2. The application will be available at `http://localhost:8080`

## API Documentation

### Available Endpoints

#### Spring Fitness Assistant
```http
POST /api/chat
Content-Type: application/json

{
    "message": "Hi?"
}
```

Response:
```text
 Hello! I'm Spring-fitness AI, here to assist you with managing your wellness application. How can I help you today?
```
Followup question:
```json
{
  "message": "can you give me a list of businesses available on Spring-fitness?"
}
```
Response:
```text
 Here is a list of businesses available on Spring-fitness:

1. **Vitality Wellness Center**
   - Address: 123 Elm Street, New York, NY, USA
   - Phone: +1234567890
   - Email: info@vitality.com
   - Contact Person: John Doe

2. **Zen Fitness Studio**
   - Address: 456 Oak Avenue, San Francisco, CA, USA
   - Phone: +1234567891
   - Email: contact@zenfitness.com
   - Contact Person: Jane Smith

3. **Harmony Health Solutions**
   - Address: 789 Pine Road, Seattle, WA, USA
   - Phone: +1234567892
   - Email: support@harmonyhealth.com
   - Contact Person: Mike Johnson

These are some of the businesses available on Spring-fitness. Let me know if you need more information or assistance with any specific business.
```
## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Security

- Never commit your OpenAI API key
- Use environment variables for sensitive information


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Spring Boot team for the amazing framework
- Spring AI team for the AI integration capabilities
- OpenAI for their powerful AI models