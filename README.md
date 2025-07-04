# Job Seeker Company Management System

A RESTful API built with Java Spring Boot and MongoDB for managing candidates and job vacancies.

## Features

### Candidate Management
- Create, read, update, and delete candidate profiles
- Unique email validation
- Comprehensive field validation

### Vacancy Management
- Create, read, update, and delete job vacancies
- Support for multiple criteria types (Age, Gender, Salary Range)
- Weighted criteria system

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB 4.4+

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/daenuli/jobseeker_company.git
cd jobseeker_company
```

### 2. Start MongoDB
Make sure MongoDB is running on `localhost:27017`

### 3. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Candidate Endpoints

#### Create Candidate
```http
POST /api/candidates
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "birthDate": "1990-05-15",
  "gender": "male",
  "currentSalary": 750000
}
```

#### Get All Candidates
```http
GET /api/candidates
```

#### Get Candidate by ID
```http
GET /api/candidates/{id}
```

#### Update Candidate
```http
PUT /api/candidates/{id}
Content-Type: application/json

{
  "name": "John Smith",
  "email": "john.smith@example.com",
  "birthDate": "1990-05-15",
  "gender": "male",
  "currentSalary": 800000
}
```

#### Delete Candidate
```http
DELETE /api/candidates/{id}
```

### Vacancy Endpoints

#### Create Vacancy
```http
POST /api/vacancies
Content-Type: application/json

{
  "name": "Senior Software Developer",
  "criteria": [
    {
      "type": "AGE",
      "minimumAge": 25,
      "maximumAge": 45,
      "weight": 2
    },
    {
      "type": "GENDER",
      "gender": "any",
      "weight": 1
    },
    {
      "type": "SALARY_RANGE",
      "minimumSalary": 600000,
      "maximumSalary": 1200000,
      "weight": 3
    }
  ]
}
```

#### Get All Vacancies
```http
GET /api/vacancies
```

#### Get Vacancy by ID
```http
GET /api/vacancies/{id}
```

#### Update Vacancy
```http
PUT /api/vacancies/{id}
Content-Type: application/json

{
  "name": "Updated Vacancy Name",
  "criteria": [
    {
      "type": "AGE",
      "minimumAge": 30,
      "maximumAge": 50,
      "weight": 2
    }
  ]
}
```

#### Delete Vacancy
```http
DELETE /api/vacancies/{id}
```

#### Search Vacancies by Name
```http
GET /api/vacancies/search?name=developer
```

### Ranking Endpoints

#### Rank Candidates for Vacancy
```http
GET /api/ranking/vacancy/{vacancyId}/candidates
```

Ranks all candidates based on how well they match the specified vacancy's criteria. The ranking algorithm considers:
- Age criteria matching
- Gender criteria matching
- Salary range criteria matching
- Weighted scoring based on criteria importance

**Response:**
```json
[
  {
    "rank": 1,
    "candidateId": "507f1f77bcf86cd799439011",
    "candidateName": "John Doe",
    "candidateEmail": "john.doe@example.com",
    "score": 85
  },
  {
    "rank": 2,
    "candidateId": "507f1f77bcf86cd799439012",
    "candidateName": "Jane Smith",
    "candidateEmail": "jane.smith@example.com",
    "score": 72
  }
]
```

**Scoring System:**
- Each criterion contributes to the total score based on its weight
- Perfect matches receive full points for that criterion
- Partial matches (e.g., salary within range) receive proportional points
- Final score is calculated as: `(total_weighted_score / max_possible_score) * 100`

## Data Models

### Candidate
- `id`: String (auto-generated)
- `name`: String (required, 2-100 characters)
- `email`: String (required, unique, valid email format)
- `birthDate`: Date (required, format: yyyy-MM-dd, must be in the past)
- `gender`: String (required, "male" or "female")
- `currentSalary`: Double (required, > 0)

### Vacancy
- `id`: String (auto-generated)
- `name`: String (required, 2-200 characters)
- `criteria`: List<Criteria> (required, at least one criterion)

### Criteria Types

#### Age Criteria
```json
{
  "type": "AGE",
  "minimumAge": 25,
  "maximumAge": 45,
  "weight": 2
}
```

#### Gender Criteria
```json
{
  "type": "GENDER",
  "gender": "any",
  "weight": 1
}
```
- `gender`: "male", "female", or "any"

#### Salary Range Criteria
```json
{
  "type": "SALARY_RANGE",
  "minimumSalary": 600000,
  "maximumSalary": 1200000,
  "weight": 3
}
```

## Error Handling

The API returns appropriate HTTP status codes and error messages:

- `200 OK`: Successful GET/PUT requests
- `201 Created`: Successful POST requests
- `204 No Content`: Successful DELETE requests
- `400 Bad Request`: Validation errors
- `404 Not Found`: Resource not found
- `409 Conflict`: Email already exists
- `500 Internal Server Error`: Unexpected errors

### Error Response Format
```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2023-12-01T10:30:00",
  "fieldErrors": {
    "email": "Email should be valid",
    "name": "Name is required"
  }
}
```

## Configuration

### Database Configuration
Update `src/main/resources/application.yml` to configure MongoDB connection:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/jobseeker_company
```

### Server Configuration
```yaml
server:
  port: 8080
```

## Testing

### Unit Tests
Run tests with:
```bash
mvn test
```

### API Testing with Postman
A comprehensive Postman collection is available for testing all API endpoints:

📁 **Collection File**: `jobseeker_api_documentation.postman_collection`

**To use the collection:**
1. Import the collection file into Postman
2. Set up environment variables if needed:
   - `baseUrl`: `http://localhost:8080`
3. Start the application locally
4. Execute requests to test all endpoints

**Collection includes:**
- All CRUD operations for candidates and vacancies
- Ranking API endpoints
- Sample request bodies and expected responses
- Pre-configured test scenarios

## Built With

- Spring Boot 3.2.0
- Spring Data MongoDB
- Spring Boot Validation
- Maven
- Java 17

## License

This project is licensed under the MIT License.