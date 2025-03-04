## *Employee Performance Appraisal System*

### *Overview*

This project is a *Spring Boot* application designed to manage employee performance appraisals. It allows HR managers to:
- Add employees with their performance ratings.
- View a list of all employees.
- Analyze employee performance using a *bell curve* distribution.
- Suggest revisions to employee ratings to align with the standard bell curve.

The application uses *MySQL* as the database for persistent storage and *Thymeleaf* for server-side rendering of the frontend.

---

### *Features*

1. *Add Employees*:
   - Add new employees with their ID, name, and performance rating (A, B, C, D, or E).
   - Form validation ensures all fields are filled.

2. *View Employee List*:
   - Display a list of all employees with their details (ID, name, and rating).

3. *Bell Curve Analysis*:
   - Compare the actual distribution of employee ratings with the standard bell curve distribution.
   - Calculate the deviation between actual and standard distributions.

4. *Suggested Revisions*:
   - Suggest revisions to employee ratings to align with the standard bell curve.

5. *Persistent Storage*:
   - Uses *MySQL* to store employee data, ensuring data persistence across application restarts.

---

### *Technologies Used*

- *Backend*:
  - *Spring Boot*: Core framework for building the application.
  - *Spring Data JPA*: For database interactions.
  - *MySQL*: Relational database for persistent storage.
  - *Thymeleaf*: Server-side templating engine for rendering HTML.

- *Frontend*:
  - *HTML*: Structure of the web pages.
  - *CSS*: Basic styling for the web pages.
  - *Thymeleaf*: Dynamic rendering of data in HTML.

- *Tools*:
  - *Maven*: Dependency management and build tool.
  - *H2 Database*: Used during development for testing (optional).
  - *MySQL Workbench*: For managing the MySQL database.

---

### *Setup Instructions*

#### *Prerequisites*

1. *Java Development Kit (JDK)*: Version 17 or higher.
2. *MySQL*: Installed and running on your machine.
3. *Maven*: Installed for dependency management.
4. *IDE*: SPRING TOOL SUITE.

#### *Steps to Run the Project*

1. *Import the project*:
   
   Import the project to STS- https://github.com/Vinodr29/employee-performance-appraisal.git
  
   

2. *Create the MySQL Database*:
   - Open MySQL and create a database named employee_performance_db:
     sql
     CREATE DATABASE employee_performance_db;
     

3. *Update Database Configuration*:
   - Open src/main/resources/application.properties.
   - Update the MySQL credentials (username and password) if necessary:
     properties
     spring.datasource.url=jdbc:mysql://localhost:3306/employee_performance
     spring.datasource.username=your_username
     spring.datasource.password=your_mysql_password
     

4. *Run the Application*:
   - Run as Spring Boot App
     

5. *Access the Application*:
   - Open your browser and navigate to:
     - *Add Employee Form*: http://localhost:8080/employees/add-form
     - *Employee List*: http://localhost:8080/employees
     - *Bell Curve Analysis*: http://localhost:8080/employees/bell-curve
     - *Suggested Revisions*: http://localhost:8080/employees/suggest-revisions
