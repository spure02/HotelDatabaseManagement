# Hotel Database Management System

### Table of Contents
- [Introduction](#introduction)
- [Implementation](#implementation)
- [Functions and Queries](#functions-and-queries)
- [Input and Error Validation](#input-and-error-validation)

## Introduction
The hotel database managament system is a DBMS which tracks different information about different hotels, such as the rooms they own, the maintenance of the rooms, the managers they employ, the bookings their customers make, and information about customers that the hotel services. The system features a simple and user-friendly text-based user interface. When run, the user is greeted to a menu which includes a variety of options. The user is able to select an option with a numeric value (1-17) with option 17 exiting the system. If options 1-16 are selected, the user is able to follow the on screen instructions based on whichever option they chose. Once the user has followed all instructions, they are greeted back to the main menu. This repeats until the user exits out of the system.

## Implementation
The client application was created using Java Database Connector (JDBC), and the specific queries were coded in PostgreSQL. We stored our queries into a String datatype which we passed to the JDBC functions in order to execute our queries.

## Functions and Queries
- addCustomer
  - This function adds a new customer to the database. It will ask for the customer ID, the customer’s first and last name, the customer’s address, the customer’s phone number, the customer’s date of birth, and the customer’s gender (male, female, or other). We then use a simple INSERT query to insert these user values into the database.

- addRoom
  - This function adds a room to the database. It will ask for an existing hotel ID, new room number, and new room type (suite, economy, deluxe). We then use a simple INSERT query to insert these user values into the database.

- addMaintenanceCompany
  - This function adds a new maintenance company into the database. It will ask for a new company ID, new company name, new company address, and its certification (TRUE or FALSE). We then use a simple INSERT query to insert these user values into the database.
  
- addRepair
  - This function adds a new repair into the database. It will ask for a new repair ID, an existing hotel ID, an existing room number, an existing company ID, and a new repair date. We then use a simple INSERT query to insert these user values into the database.
 
- bookRoom 
  - This function adds a new booked room into the database. It will generate a new booking ID automatically since it is a new entry. It will then ask for an existing hotel ID, existing room number, existing name of the customer (first and last name), the new date of the booking, how many people were in that customer’s party, and the price of the booking. Based on the customer name, it will fetch the customer ID from the Customer table. These values are inserted into the database using the INSERT query.
  
- assignHouseCleaningToRoom
  - This function adds a new assigned cleaning staff to a room into the database. It will generate a new assigned ID automatically since it is a new entry. It will then ask for an existing staff ID, existing hotel ID, and existing room number. These values are inserted into the database using the INSERT query.

- repairRequest
  - This function adds a new repair request into the database. However, only a staff with manager status can add a new repair request into the database. We first ask for its manager ID and check if that ID is in the database using a SELECT query to cross check if the manager ID exists in the Staff table and if they have manager status. If this is false, the system will throw an error. If this is true, the system will be able to ask the user for an existing repair ID and the new date of the request. Since we are making a new request, we generate a new repair ID automatically. These values (including the manager ID) are inserted into the database using the INSERT query.

- numberOfAvailableRooms
  - This function lists the available rooms in the database based on a hotel ID. The system will prompt the user for an existing hotel ID. Then we use a SELECT query to count the number of rooms that are not in the Booking table based on the hotel ID the user inputted.

- numberOfBookedRooms
  - This function lists the number of booked rooms in the database based on a hotel ID. The system will prompt the user for an existing hotel ID. Then we use a SELECT query to count the number of rooms in the Booking table based on the hotel ID the user inputted.
listHotelRoomBookingsForAWeek
This function lists all the booked rooms in the database within a week based on a booking date and hotel ID. The system will ask the user for a booking date and a hotel ID. Then, we use a SELECT query to find the booked rooms between the booking date and the date 7 days after the booking date.

- topKHighestRoomPriceForADateRange
  - This function lists the k most expensive rooms and their prices for a specified date range based on two dates and a k value (any number). The system will ask the user for a date range by asking for two dates separately, then it will ask how many entries of these rooms the user wants to see. Then, we use a SELECT query to find the most expensive rooms and their prices between those user inputted dates and list them in decreasing order based on the k value the user gave.

- topKHighestPriceBookingsForACustomer
  - This function lists the most expensive rooms a customer booked based on the customer information and a k value (any number). The system will ask the user for their first and last name separately, then it will ask how many entries of these rooms the user wants to see. Then, we use a SELECT query to find the most expensive rooms using the customer information and the customer ID and list them in decreasing order based on the k value the user gave. 

- totalCostForCustomer
  - This function lists the total cost incurred by a customer based on the hotel ID, customer information, and a date range. The system will ask the user for a hotel ID, the customer’s first and last name separately, and two seperate dates for a range. Then, we use a SELECT query to get the sum of all prices of a single customer using the hotel ID, the customer’s first and last name, and the date range using the user inputted dates.

- listRepairsMade
  - This function lists all repairs a maintenance company made based on the company ID. The system will ask the user for the company ID. Then we use a SELECT query to get all repairs the maintenance company made and its relevant information including the type of repair, hotel ID, and room numbers.

- topKMaintenanceCompany
  - This function lists the top k maintenance company names based on the total repair count. The system will only ask the user how many entries he or she would like to see. We used a SELECT query to find a list of the top maintenance companies based on the repair ID and the k value the user inputted.

- numberOfRepairsForEachRoomPerYear
  - This function lists the number of repairs for each room per year based on the hotel ID and room number. The system will ask the user for an existing hotel ID and existing room number. Then, we use a SELECT query to get the number of repair for each room using the  year portion of the repair date, the hotel ID, and the room number.

## Input & Error Validation
Since we prompt the user to ask for multiple inputs, we used a variety of checks to validate these inputs and possible errors that we came across. For each String datatype, we checked the length of the input if it exceeds the maximum length as stated in the create.sql file. For first and last names, and company names,  we made sure the user input cannot be less than or equal to 0 (to check for empty inputs) or greater than 30 (the maximum string length). For roomType in the Room table and repairType in the Repair table, we made sure the user input cannot be less than or equal to 0 (to check for empty inputs) or greater than 10 (the maximum string length). Numeric datatype values we checked for negative number input and also for empty number input. For the Date datatype values, since there is no maximum limit, we treated them as String datatypes and made sure the input cannot be less than or equal to 0 (to check for empty inputs).

We also added a unique validation into the repairRequest function. Since only a manager can make a repair request, we have to validate that the user is a manager. Once the user chooses the repairRequest option, the system immediately prompts the user for the staff ID. We validate this input with a new query to check if this staff ID exists in the Staff table, and if this staff ID corresponds to a Manager staffRole. If this staff ID does exist and if it also corresponds to a Manager staffRole, then the user will be able to add a repair request. If it does not exist, then the system will throw an error asking for a valid staff ID and the user will stay at that prompt window.
