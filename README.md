SidClasses – Keycloak Authentication
SidClasses-Keycloak is a Spring Boot project that demonstrates OAuth2 authentication and authorization using Keycloak. This project extends the SidClasses learning platform by integrating enterprise-grade identity and access management.

🚀 Features
✅ Spring Boot application with RESTful APIs

🔐 Authentication & Authorization via Keycloak

👥 Role-based access control (Admin, User)

📂 CRUD operations for:

Categories

Courses

Videos

⚡ Pagination and Sorting support

🛡️ Centralized Exception Handling

🧪 Unit Tests with JUnit

🛠️ Tech Stack
Java 17

Spring Boot 3 (Spring Data JPA, Security)

Keycloak (OAuth2, OIDC)

Maven for build automation

MySQL / H2 Database

JUnit & Mockito for testing

🔑 Keycloak Integration
Uses OpenID Connect (OIDC) flow for user authentication

Configured Resource Server with JWT token validation

Custom roles and permissions managed via Keycloak

Includes CustomConverter for mapping Keycloak roles to Spring Security authorities

⚙️ Setup Instructions
1️⃣ Clone Repository
bash
Copy
Edit
git clone https://github.com/Sidharth-ORG/SidClasses-Keycloak.git
cd SidClasses-Keycloak
2️⃣ Start Keycloak
Install Keycloak or use a Docker container

Create a Realm and a Client for SidClasses

Add Roles: ADMIN, USER

Create sample users and assign roles

3️⃣ Configure Application
Update application.properties with:

properties
Copy
Edit
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/YourRealm
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8080/realms/YourRealm/protocol/openid-connect/certs
4️⃣ Run the Project
bash
Copy
Edit
./mvnw spring-boot:run
5️⃣ Access Endpoints
Public API: GET /courses

Protected API: POST /courses (Admin only)

📌 API Highlights
GET /api/categories → Fetch all categories

POST /api/categories → Add category (Admin only)

GET /api/courses → List courses with pagination

POST /api/videos/upload → Upload video

🧪 Testing
Run tests with:

bash
Copy
Edit
./mvnw test
📸 Screenshots / Architecture
(Optional – add Keycloak login page screenshot and simple architecture diagram here)

👨‍💻 Author
Sidharth Bhuyan

🌐 GitHub

💼 Java Developer | IAM Specialist | Spring Boot | Keycloak

🏆 Why this Project?
This project demonstrates:

✅ Real-world Spring Boot + Keycloak integration

✅ Industry-standard OAuth2 security patterns

✅ Strong understanding of Identity and Access Management (IAM)

✅ Skills in designing secure, scalable REST APIs