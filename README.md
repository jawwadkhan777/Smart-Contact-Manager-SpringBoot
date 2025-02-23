# Smart Contact Manager

Smart Contact Manager is a full-stack web application built with **Spring Boot** and **Spring Security** that allows users to securely manage their contacts in the cloud. The application supports user authentication via **Google and GitHub OAuth**, as well as email-based registration with verification.

---

## Features

- **User Authentication**
    - Spring Security-based authentication
    - OAuth login with Google and GitHub
    - Email-based registration with verification

- **Contact Management**
    - Add, view, update, and delete contacts
    - Store contact details such as name, email, phone number, profile picture, website, and LinkedIn link
    - Mark contacts as favorites

- **Cloud Storage**
    - Contact information is securely stored in the cloud

---

## Tech Stack

- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf, HTML, CSS, JavaScript
- **Database:** MySQL
- **Authentication:** Spring Security OAuth (Google, GitHub), Email Verification
- **Other:** Lombok, Hibernate, JPA, Cloud Storage

---

## Setup and Installation

### Prerequisites
- Java 17+
- MySQL Database
- Maven
- SMTP Email Service (for email verification)
- Google and GitHub Developer Credentials

### Steps to Run the Project

1. **Clone the Repository**
   ```sh
   git clone https://github.com/your-username/smart-contact-manager.git
   cd smart-contact-manager
   ```

2. **Configure Database**
    - Update `application.properties` file with your MySQL credentials:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/contact_manager
      spring.datasource.username=root
      spring.datasource.password=yourpassword
      ```

3. **Configure OAuth Credentials**
    - Add Google and GitHub client credentials to `application.properties`:
      ```properties
      spring.security.oauth2.client.registration.google.client-id=your-google-client-id
      spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret
      
      spring.security.oauth2.client.registration.github.client-id=your-github-client-id
      spring.security.oauth2.client.registration.github.client-secret=your-github-client-secret
      ```

4. **Run the Application**
   ```sh
   mvn spring-boot:run
   ```

5. **Access the Application**
    - Open your browser and go to: `http://localhost:8080`

---

## API Endpoints

| Method | Endpoint                           | Description        |
|--------|------------------------------------|--------------------|
| GET    | `/`                                | Home Page          |
| GET    | `/login`                           | Login Page         |
| POST   | `/do-register`                     | User Registration  |
| GET    | `/auth/verify-email`               | Email Verification |
| GET    | `user/contacts`                    | View Contacts      |
| POST   | `user/contacts/add`                | Add a Contact      |
| PUT    | `user/contacts/update/{contactId}` | Update a Contact   |
| DELETE | `user/contacts/delete/{contactId}` | Delete a Contact   |
| GET    | `user/contacts/search`             | Search a Contact   |

---

## 📜 License
This project is **open-source** and available under the [MIT License](LICENSE).

---

## 🤝 Contributing
Contributions are welcome! Feel free to **fork** this repository and submit a **pull request**. 🙌

---

## 📞 Contact
For any issues or suggestions, reach out at:
📧 **m.jawwadkhan777@gmail.com**  
🔗 [LinkedIn Profile](https://www.linkedin.com/in/jawwadkhan777/)

