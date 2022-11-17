# Fuel Pass App Clone <img src="asset/img/fuel-pump.png" alt="fuel-pump" width="27px"/>

A Java FX application clone of the [fuelpass.gov.lk](https://fuelpass.gov.lk/)

The Information and Communication Technology Agency (ICTA) and the Ministry of Power and Energy together created the national 
fuel pass app to provide Sri Lanka with the best possible response to the country's fuel shortages.
This clone application was created exclusively for educational purposes, 
in order to understand and improve the software development skills.
The whole design layout and images are copyright to their respective owners.

#### Images of the user interfaces
1. Splash screen<br>
   <img src="asset/img/splash-screen.png" alt="splash-screen" width="500px"/>

2. Welcome-form<br>
   The user-login and registration forms are linked via the welcome form's navigation buttons.
   Using the padlock icon in the upper right corner, admin can access the admin login form.
   By clicking the logo in the upper left corner of the welcome form, a user or an admin can always be sent back to the welcome form.<br>
   <img src="asset/img/welcome-form.png" alt="welcome-form" width="500px"/>

3. Registration-form<br>
   <img src="asset/img/registration-form.png" alt="registration-form" width="500px"/>

4. Login-form<br>
   <img src="asset/img/user-login-form.png" alt="user-login-form" width="500px"/>

5. User dashboard-form<br>
   <img src="asset/img/user-dashboard-form.png" alt="user dashboard-form" width="500px"/>

6. Admin login-form<br>
   <img src="asset/img/admin-login-form.png" alt="admin-login-form" width="500px"/>

7. Control center-form<br>
   <img src="asset/img/control-center-form.png" alt="control-center-form" width="500px"/>

## Used Technologies

- Java SE 11
- MySQL Community Server 8.0.31
- Apache Maven 3.8.6
- Added dependencies to pom.xml
    - javafx-fxml 18.0.1
    - javafx-web 18.0.1
    - javafx-swing 18.0.1
    - ZXing Core 3.5.0
    - mysql-connector-java 8.0.30

#### Used Integrated Development Environment
- IntelliJ IDEA

## How to use ?
This project can be used by cloning the
project to your local computer. 

Make sure to create a "fuel_app_db" database in the MySQL community server 
and add the table from the database-script.sql file to it. 

To establish a singleton connection with the MySQL server, modify the DBConnection class mySQL user name and password.

#### Clone the repository
1. Clone the project using `git clone https://github.com/PubuduJ/fuel-pass-app-clone.git` terminal command.
2. Open the `pom.xml` file from **IntelliJ IDEA**, make sure to open this as a project.
3. To run this application execute `mvn javafx:run` goal or create separate maven configuration in **IntelliJ IDEA**.

## Credits
This project was carried out under the guidance of the [IJSE](https://www.ijse.lk/) Direct Entry Program 9.

## Version
v1.0.0

## License
Copyright &copy; 2022 [PubuduJ](https://www.linkedin.com/in/pubudujanith94/). All Rights Reserved.<br>
This project is licensed under the [MIT license](LICENSE.txt).