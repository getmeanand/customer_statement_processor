# file-validator deal with the customer datas and validates user input as below
There are two validations:
  1. All transaction references should be unique
  2. The end balance needs to be validated as Euro
  
  
# Steps to Run
 1. Clone the project
 2. Import it into your favourite IDE
 3. Build the project.
 4. Run the project.
 5. Hit the URL : http://localhost:8181/csvFileValidator
                : http://localhost:8181/xmlFileValidator
 6. file-validator starts processing user data(xml,csv file from class path), and validates it. finally will display both the transaction     reference and description of each of the failed records as response
  
  
