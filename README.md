# Hibernate-Based Login, Registration and Employee Management Web Application

A database-driven Java web application that combines user authentication with employee record management. It is built on Java Servlets and JSP for the web layer, Hibernate ORM for persistence, and MySQL for storage.

Developed as an academic project for the MCA program at Galgotias University.

---

## Overview

The application lets a user register an account, log in, and manage employee records through a browser. Access to internal pages is controlled through HTTP sessions, and all database operations are handled by Hibernate rather than hand-written JDBC code.

The project is intended to demonstrate object-relational mapping, session-based access control, transaction handling, and CRUD operations in a layered Java web architecture.

## Features

**Authentication**
- User registration with server-side validation
- Login verified against the database, with distinct messages for a missing field, an unknown username, and an incorrect password
- Session-based access control; opening a protected page without logging in redirects to the login page

**Employee management**
- Add a new employee record (ID is generated automatically)
- Search an employee by ID
- View all employees in a table
- Delete an employee, with a confirmation step showing the record before removal
- Modify the logged-in user's own details (name, city, email, mobile number)

## Validation Rules

| Field | Rule |
|---|---|
| All registration fields | Mandatory |
| Employee number | One alphabet followed by exactly four digits (for example, `A1234`) |
| Password | Exactly six characters |

The six-character password rule is kept to match the assignment specification. See [Security Notes](#security-notes) for what a production deployment would require.

## Technology Stack

| Component | Role |
|---|---|
| Java | Application and business logic |
| Java Servlets | Request handling and navigation control |
| JSP | Dynamic pages and forms |
| Hibernate ORM | Mapping of entity classes to tables; persistence operations |
| MySQL | Storage of user and employee data |
| Apache Tomcat | Servlet container |
| Maven | Dependency management and build (if configured) |

## Architecture

The application follows a layered design.

| Layer | Implementation | Responsibility |
|---|---|---|
| Presentation | JSP pages | Forms, menus, employee details, status messages |
| Controller | Servlets | Receive requests, validate input, control navigation |
| Model | Entity / JavaBean classes | Represent users and employees |
| Data access | Hibernate sessions and transactions | Save, retrieve, update, delete |
| Database | MySQL | Persistent storage |

Request flow:

```
Browser -> JSP form -> Servlet -> Hibernate ORM -> MySQL
```

The servlet processes the result and forwards or redirects the user to the appropriate JSP page.

## Database Design

Table and column names depend on the Hibernate configuration used in the project. The logical structure is as follows.

**User**

| Field | Description |
|---|---|
| ID | Unique user identifier |
| Username | Login name |
| Password | Stored password |
| Employee number | One alphabet followed by four digits |
| Email | User's email address |
| Name, City, Mobile number | Personal details editable after login |

**Employee**

| Field | Description |
|---|---|
| ID | Auto-generated identifier |
| Name | Employee name |
| Gender | Employee gender |
| Designation | Job designation |
| Salary | Employee salary |
| City | Employee city |
| Email ID | Employee email address |
| Mobile number | Employee mobile number |

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Apache Tomcat
- MySQL Server
- A Java IDE (Eclipse, IntelliJ IDEA, or similar)
- Maven, if the project is built with it

### Setup

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```

2. **Create the database**

   Start MySQL and create an empty schema for the application.

   ```sql
   CREATE DATABASE <database_name>;
   ```

3. **Configure Hibernate**

   In the Hibernate configuration file (`hibernate.cfg.xml`), set the JDBC URL, username and password for your MySQL instance. Tables are created or updated according to the `hibernate.hbm2ddl.auto` setting in that file.

4. **Build the project**

   ```bash
   mvn clean package
   ```

   If you are not using Maven, import the project into your IDE and ensure the Hibernate, MySQL connector and Servlet/JSP libraries are on the build path.

5. **Deploy to Tomcat**

   Deploy the generated WAR file to Tomcat, or run the project directly from the IDE with Tomcat configured as the server.

6. **Open the application**

   ```
   http://localhost:8080/<context-path>/
   ```

## Usage

1. Open the home page and choose **Register**.
2. Enter a username, a six-character password, an employee number (such as `A1234`) and an email address.
3. Log in with the new credentials to reach the welcome page.
4. Use the employee menu to add, search, view or delete employee records, or to update your own details.

## Testing

The application was verified manually against the following scenarios.

| Scenario | Expected result |
|---|---|
| Registration with valid details | Account is created |
| Registration with a missing field | Validation message is shown |
| Invalid employee number | Input is rejected |
| Password not exactly six characters | Input is rejected |
| Login with valid credentials | Welcome page is displayed |
| Login with unknown username | Error message is displayed |
| Login with wrong password | Error message is displayed |
| Direct access to the welcome page without login | Redirect to login |
| Access to CRUD pages while logged out | Login is required |
| Add employee | Record is saved |
| Delete employee | Details are shown; deletion requires confirmation |
| Modify personal details | Updated information is saved |
| Search employee by ID | Matching record is displayed |
| Select all employees | All stored records are displayed |

## Security Notes

This project is built for academic demonstration. Before any production use, the following should be addressed:

- Hash passwords with a strong algorithm (such as bcrypt) and relax the fixed six-character length rule
- Serve the application over HTTPS
- Add CSRF protection to state-changing requests
- Keep database credentials out of source control and in secure external storage
- Add role-based access-control checks beyond the current session check

## Limitations

- Intended for demonstration rather than production deployment
- Behavior depends on correct database and server configuration
- Testing is manual; there are no automated unit or integration tests yet

## Future Enhancements

- Administrator and employee roles
- Advanced search, filtering, pagination and sorting
- Password reset
- Automated unit and integration tests
- Responsive user interface
- Export of employee records to PDF or Excel
- Logging and audit trails

## Screenshots

Add screenshots of the running application under a `docs/screenshots/` folder and reference them here, for example:

```markdown
![Home page](docs/screenshots/home.png)
```

Suggested captures: home page, registration form, login page, welcome page, employee menu, add employee form, employee list, search result, modify details page, delete confirmation, login error, and the redirect shown when a protected page is opened without logging in.

## References

- [Oracle Java Documentation](https://docs.oracle.com/en/java/)
- [Hibernate ORM Documentation](https://hibernate.org/orm/documentation/)
- [Apache Tomcat Documentation](https://tomcat.apache.org/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Jakarta Servlet Specification](https://jakarta.ee/specifications/servlet/)
- [Jakarta Server Pages Specification](https://jakarta.ee/specifications/pages/)

## Author

**Shrishty Ranjan**
MCA, Galgotias University
