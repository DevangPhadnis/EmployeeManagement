# Employee Management System

An **Employee Management System (EMS)** built using **Angular** for the frontend and **Spring Boot** for the backend. This project streamlines employee data handling with **role-based access**, **authentication**, and efficient management workflows. It also demonstrates deployment of both frontend and backend on the same **AWS EC2 server** (different ports) with the database hosted on **AWS RDS**.

---

## 🚀 Features

* Employee Management: Add, update, delete, and view employee records.
* Role-Based Access: Secure authentication and authorization using JWT and roles stored in the database.
* Database Integration: AWS RDS for scalable and reliable database hosting.
* Deployment: Angular frontend and Spring Boot backend deployed on the same EC2 instance.
* UI Framework: PrimeNG for modern and responsive UI.
* Notification Services: Email notifications via SMTP and SMS integration using Twilio.
* Backend REST APIs built with Spring Boot
* Database integration with MySQL
* Deployment ready for AWS services (EC2, RDS, S3)

---

## 🛠️ Tech Stack

* **Frontend:** Angular (CLI v14)
* **Backend:** Java Spring Boot v3.4.4
* **Database:** MySQL
* **Deployment:** AWS EC2, AWS RDS, AWS S3
* **Integrations:** Twilio (SMS), SMTP (Email)
* **Runtime:** Node.js v18, Java v17.0.7

---

## ⚙️ Getting Started

Follow the steps below to set up the project locally:

### 1. Clone the Repository

```bash
git clone https://github.com/DevangPhadnis/EmployeeManagement.git
cd EmployeeManagement
```

### 2. Backend Setup (Spring Boot)

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Create a MySQL database:

   ```sql
   CREATE DATABASE employeemanagement;
   ```

3. Update your `application.properties` with the following values:

   ```properties
   # MySQL Configuration
   spring.datasource.name = employeemanagement
   spring.datasource.url = jdbc:mysql://localhost:3306/employeemanagement
   spring.datasource.username = root
   spring.datasource.password = root
   spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver

   # Hibernate Config
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql = true
   spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

   # JWT Config
   jwt.TokenExpiry = 30
   jwt.SecretKey = TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V

   # Twilio Config
   twilio.account.sid = <your_twilio_sid>
   twilio.auth.token = <your_twilio_auth_token>
   twilio.phone.number = <your_twilio_phone_number>

   # Email Config
   spring.mail.host = <your_smtp_host>
   spring.mail.port = <your_smtp_port>
   spring.mail.username = <your_email>
   spring.mail.password = <your_email_password>
   spring.mail.properties.mail.smtp.auth = true
   spring.mail.properties.mail.smtp.starttls.enable = true

   ```

4. Build and run the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

   The backend server will start on `http://localhost:8080`.

---

### 3. Frontend Setup (Angular)

1. Navigate to the frontend directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the Angular development server:

   ```bash
   ng serve
   ```

   The frontend will be available at `http://localhost:4200`.

---

### 4. Environment Setup

Make sure you have the following installed/configured:

* **Java 17.0.7**
* **Spring Boot 3.4.4**
* **Angular CLI v14**
* **Node.js v18**
* **MySQL** running locally
* **AWS credentials** (for S3 + RDS deployment)
* **Twilio account** (for SMS service)
* **SMTP email service** configured
  
---

## 🎥 Demo / Screenshots

* **Login Page**
  <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/31d7066e-2a9d-4f70-872f-3ba258d2b02b" />

* **List of Active Employee**
  <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/df174393-f009-43f6-87a7-98b9b18aa1c5" />

* **Add New Employee**
  <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/d2e35eb7-4ff7-48f8-90e1-4e3b59cf7271" />

* **List of Departments**
  <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/2e75c9d3-9dd1-4881-9f69-e627dd4510bc" />

* **Admin Dashboard**
  <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/fae1535c-7b7c-48fb-acaa-81db4f794f0c" />

---

## 📩 Contact

For any queries, feel free to reach out
- Email: **[devangphadnis2001@gmail.com](mailto:devangphadnis2001@gmail.com)**
- GitHub Profile: [**Devang Phadnis**](https://github.com/DevangPhadnis)
- Project LIVE URL: [**Employee Management System**](http://ec2-52-7-49-188.compute-1.amazonaws.com:4200/login)

---
