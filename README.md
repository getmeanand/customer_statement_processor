# customer_statement_processor deals with the customer data's and validate it as below:
There are two validations:
  1. All transaction references should be unique
  2. The end balance needs to be validated as Euro
  
  
# Steps to Run
 1. Clone the project
 2. Import it into your Spring Tools(STS) IDE and configure Java8 in Build path, and finally maven
 3. Open application.properties file under "src/main/resources",
 4. Set your default port number
 5. Build the project.
 6. Run the project.
 7. Hit the URL : POST :  http://localhost:8181/uploadCustomerFiles         
 8. customer_statements_processor starts validating the user files(xml,csv file from the user input),and 
     process it. 
 9. Finally we will get a responce about the failured records. We can fetch it and dissplay the required fields in the UI.
  
 # Note : while importng the project, there should not be a space in the directory.
Eg: 

D:\Rabo Bank\Workspace\ ===>instead of this, we can create as below
D:\Rabo-Bank\Workspace\

we can create dir by adding undersccore(_) or hyphen(-) for the spaces 
  
# PostMan Configuration for select Customer input file(s)
1. Remove "Content-Type" from header tab.
2. Navigate to Body tab and selet option "form-data"
3. Add key as "customerFile" and value as "File".
4. Chooose the customer input files and hit the below url
	POST :  http://localhost:8181/uploadCustomerFiles    

