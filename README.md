# University Library Management - Backend

This project is developed for a university library management system, focusing on two main microservices:

- **User Management Service**
- **Document Management Service**

This repository currently contains the backend structure for the **User Management Service**.

## 📚 Project Structure

- **Model Layer**
  - `Usager`: User entity (login, password, name, address).
  - `Groupe`: Group entity (e.g., Student, Teacher, etc.).
  - `Membre`: Association between Users and Groups.

- **Repository Layer**
  - `UsagerRepository`
  - `GroupeRepository`
  - `MembreRepository`

- **Controller Layer**
  - `UsagerController`: Basic CRUD operations for Users.

## 🚀 How to Run

1. Clone the repository:
```bash
git clone https://github.com/your-username/library-microservices.git
cd library-microservices
```

2. Open with IntelliJ IDEA (or your favorite IDE).

3. Ensure you have:
- Java 17+
- Maven or Gradle
- Spring Boot

4. Run the application:
```bash
./mvnw spring-boot:run
```

5. Test the APIs using Postman:
- `GET /api/usagers` - Retrieve all users.
- `POST /api/usagers` - Create a new user.

## 📦 Planned Features

- Add authentication (JWT login system)
- Add Group and Member Controllers
- Connect User and Document Services

## 📄 License

This project is for educational purposes at ISGA.

---

_Developed with ❤️ using Spring Boot and Java 17._
