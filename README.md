# file-validator deal with the customer datas and validates user input as below
There are two validations:
  1. All transaction references should be unique
  2. The end balance needs to be validated as Euro
  
  
# Steps to Run
 1. Clone the project
 2. Import it into your Spring Tools(STS) IDE
 3. Open Application.properties file under src/mail/resources 
		i. Set your default port number
	   ii. Set file.location=/Users/454424/Desktop/customer_files (customer statement files location)
	  iii. file.processed= /Users/454424/Desktop/customer_files/processed (customer statement files to be moved once validated) 
 3. Build the project.
 4. Run the project.
 5. Hit the URL : http://localhost:8181/processCustomerFiles          
 6. customer_statements_processor starts validating the user files (xml,csv file from the mentioned path in properties file), and process it. 
	finally we will get a responce that the failured records. We can fetch it and dissplay the required fields in the UI.
  
  
