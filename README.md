# AI-Powered Fitness Bot

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
        <version>0.8.0</version>
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

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      model: gpt-4
      temperature: 0.7

server:
  port: 8080
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
│   │       └── yourcompany/
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
@Configuration
public class OpenAIConfig {
    
    @Bean
    public OpenAIClient openAIClient(OpenAIProperties properties) {
        return new OpenAIClient(properties);
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
    "message": "What's a good workout routine for beginners?"
}
```

Response:
```json
{
    "response": "Here's a beginner-friendly workout routine...",
    "timestamp": "2024-03-19T10:30:00Z"
}
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