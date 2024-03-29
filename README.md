Task:
Develop a Restful WEB project that fully implements one of the sets of requirements described below.
A web interface is not required; it is sufficient to implement an API for access through Postman/Insomnia.

<<< Project. Food Price Monitoring Service >>>
Application must have next functiofunctionality:
o	User registration (administrators, regular users).
o	Profile editing.
o	Directory of product categories, directory of trading points.
o	View a list of products by categories. Search and filtering.
o	Ability to add/edit/delete a product.
o	Ability to link the price to a specific product in a store at the current moment. ?
o	Ability to track price dynamics for a specific product in a given period.
o	Price comparison for items in different stores (at least two).
o	Ability to graphically display price change dynamics. (returning an array of points in the response body of the REST method will be sufficient for graph construction)
o	Ability to batch add information about prices and products (e.g., loading in CSV, xlsx format).


<<< General Requirements for the Assignment >>>
Mandatory:
•	The assignment must adhere to all canonical principles of writing Java programs and Java naming conventions.
•	Adherence to MVC principles.
•	Adherence to the principles of "High Cohesion" and "Loose Coupling."
•	Well-thought-out modular structure (package structure).
•	Reasonable use of multiple design patterns.
•	Have a database diagram.
•	The database must be brought to at least the 3rd normal form.
•	Have a database initialization script (SQL).
•	Have JUnit tests covering the main functionality.
•	Have an exception handling system.
•	Have a multi-level logging system.
•	Have scripts for automatically building the application into a ready-to-use WAR or JAR file.
•	Have detailed, step-by-step documentation on installing and deploying the application.
•	Have a detailed user and role system (e.g., regular user and administrator).

Preferred:
•	Compliance with the planned deadlines (4 weeks).
•	Sending emails about lost passwords (emails generated by Velocity).
•	Presence of project documentation (Javadoc, good UML diagrams).
•	Presence of data validation.

Technical Requirements for the Assignment:
Mandatory:
•	Use Hibernate or MyBatis for working with the database.
•	Use Maven or Gradle.
•	Use MySQL, Postgres, or Oracle (free limited version).
•	Use Spring for configuring and implementing "inversion of control."
•	Use JUnit for writing Unit tests.
•	Use Spring Security for user authorization.
Defense of the Assignment:
For admission to defense, you need to:
•	Pass theory exams.
•	Complete all practical assignments.

For defense, you need to prepare:
•	Archive with the application.
•	Scripts for building the application.
•	Scripts for initializing the database.
•	Documentation on installing the project.
•	The required version of Tomcat (if used).

During defense, you need to:
•	Build the project into a WAR/JAR file using Maven/Gradle.
•	Install the database using prepared scripts.
•	Run the application in Tomcat. (if used???)
•	Demonstrate the functionality of all assignment points. (should I need only requests in Postman?)
•	Demonstrate the completion of all project requirements.
•	Explain the structure and principles of the application.
•	Demonstrate project documentation.
•	Discuss the technologies used and their peculiarities.
•	Answer all questions about the code of the application.
