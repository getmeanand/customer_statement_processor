# customer_statement_processor deals with the customer data's and validate it as below:
There are two validations:
  1. All transaction references should be unique
  2. The end balance needs to be validated as Euro
  
  
# Steps to Run
 1. Clone the project
 2. Import it into your Spring Tools(STS) IDE and configure Java8 in Build path, and finally maven
 3. Open application.properties file under "src/main/resources",
 4. Set your default port number
# Create Default Folder for custommer files and create another folder(processed) under the default folder as per below steps.
 5. Set file.location=/Users/454424/Desktop/customer_files (mention customer statement files location)
 6. Set file.processed=/Users/454424/Desktop/customer_files/processed (customer statement files to be moved once validated) 
 7. Build the project.
 8. Run the project.
 9. Hit the URL : http://localhost:8181/processCustomerFiles          
 10. customer_statements_processor starts validating the user files(xml,csv file from the mentioned path in properties file),and 
     process it. 
 11. Finally we will get a responce about the failured records. We can fetch it and dissplay the required fields in the UI.
  
  
  Note : while importng the project, there should not be a space in the directory.
Eg: 

D:\Rabo Bank\Workspace\ ===>instead of this, we can create as below

D:\Rabo-Bank\Workspace\

we can create dir by adding undersccore(_) or hyphen(-) for the spaces 
  
