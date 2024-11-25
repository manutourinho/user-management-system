# User Management System

A web application built using the Spring framework, designed to perform CRUD (Create, Read, Update, Delete) operations on user data. The application includes user authentication, role-based access control, and integration with a MySQL database for persistent data storage.

---

## ⚙️ Features
- **User Authentication**: Secure login functionality implemented with Spring Security.
- **CRUD Operations**: Full control over user data with the ability to create, read, update, and delete records through a user-friendly interface.
- **Role-Based Access Control**: Dynamic access and functionality based on user roles (e.g., Admin, User).
- **Database Connectivity**: MySQL database integration using Spring Data for reliable data storage.
- **Dynamic User Interface**: Developed with Thymeleaf templates and JavaScript to provide an interactive and responsive UI.

---

## 🍵 Technologies and Concepts Utilized
- **Java**: Backend logic development using object-oriented principles.
- **Spring Framework**:
    - **Spring Security** for user authentication and authorization.
    - **Spring MVC** for managing application flow and web requests.
    - **Spring Data** for seamless interaction with the MySQL database.
- **MySQL**: Configured for persistent storage and efficient database operations.
- **Thymeleaf**: Used for creating dynamic HTML templates integrated with Java.
- **JavaScript**: Enhanced interactivity and responsiveness in the user interface.

---

## 🚀 Getting Started
### Prerequisites
- **Java 17** or higher
- **Apache Maven**
- **MySQL Server**

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/user-management-system.git
   cd user-management-system
2. Configure the database:
- Create a MySQL database named user_management.
- Update the database configuration in the application.properties file:
  ```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/user_management
  spring.datasource.username=your-username
  spring.datasource.password=your-password
3. Build the application:
   ```bash
   mvn clean install
4. Run the application:
   ```bash
   mvn spring-boot:run

---

## 🖥️ Usage
1. Access the application in your web browser at http://localhost:8080.
2. Register or log in using the provided authentication system.
3. Perform CRUD operations on user data based on your role's access.

---

## 🤝 Contributing
Contributions are welcome! If you'd like to contribute:
1. Fork the repository. 
2. Create a feature branch (git checkout -b feature-name). 
3. Commit your changes (git commit -m "Add feature-name"). 
4. Push to the branch (git push origin feature-name). 
5. Open a Pull Request.

---

## 📧 Contact
You can reach me through the following channels:
- **Email**: [tourinhomanuelacontact@gmail.com](mailto:tourinhomanuelacontact@gmail.com)
- **LinkedIn**: [linkedin.com/in/manuelatourinho/](https://www.linkedin.com/in/manuelatourinho/)
- **GitHub**: [github.com/manutourinho/](https://github.com/manutourinho/)
