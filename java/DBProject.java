/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
   if(outputHeader){
      for(int i = 1; i <= numCol; i++){
    System.out.print(rsmd.getColumnName(i) + "\t");
      }
      System.out.println();
      outputHeader = false;
   }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
        System.out.println("MAIN MENU");
        System.out.println("---------");
        System.out.println("1. Add new customer");
        System.out.println("2. Add new room");
        System.out.println("3. Add new maintenance company");
        System.out.println("4. Add new repair");
        System.out.println("5. Add new Booking"); 
        System.out.println("6. Assign house cleaning staff to a room");
        System.out.println("7. Raise a repair request");
        System.out.println("8. Get number of available rooms");
        System.out.println("9. Get number of booked rooms");
        System.out.println("10. Get hotel bookings for a week");
        System.out.println("11. Get top k rooms with highest price for a date range");
        System.out.println("12. Get top k highest booking price for a customer");
        System.out.println("13. Get customer total cost occurred for a give date range"); 
        System.out.println("14. List the repairs made by maintenance company");
        System.out.println("15. Get top k maintenance companies based on repair count");
        System.out.println("16. Get number of repairs occurred per year for a given hotel room");
        System.out.println("17. < EXIT");

            switch (readChoice()){
           case 1: addCustomer(esql); break;
           case 2: addRoom(esql); break;
           case 3: addMaintenanceCompany(esql); break;
           case 4: addRepair(esql); break;
           case 5: bookRoom(esql); break;
           case 6: assignHouseCleaningToRoom(esql); break;
           case 7: repairRequest(esql); break;
           case 8: numberOfAvailableRooms(esql); break;
           case 9: numberOfBookedRooms(esql); break;
           case 10: listHotelRoomBookingsForAWeek(esql); break;
           case 11: topKHighestRoomPriceForADateRange(esql); break;
           case 12: topKHighestPriceBookingsForACustomer(esql); break;
           case 13: totalCostForCustomer(esql); break;
           case 14: listRepairsMade(esql); break;
           case 15: topKMaintenanceCompany(esql); break;
           case 16: numberOfRepairsForEachRoomPerYear(esql); break;
           case 17: keepon = false; break;
           default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface                       \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   //CHOICE 1 - DONE
   public static void addCustomer(DBProject esql){
    // Given customer details add the customer in the DB 
      
    try {
      int customerid;
      String firstname;
      String lastname;
      String address;
      int phonenum;
      String dateofbirth;
      String gender;

      System.out.println(
         "\n\n*******************************************************\n" +
         "              ADD A CUSTOMER                     \n" +
         "*******************************************************\n");
      
      //customerid validation
      do {
        System.out.print("Enter the customerID of the customer: ");
        try {
          customerid = Integer.parseInt(in.readLine());
          break;
        } catch(Exception e){
         System.err.println (e.getMessage());
         continue;
        }
      } while(true);

      //first name validation
      do {
        System.out.print("Enter the first name of the customer: ");
        try {
          firstname = in.readLine();
          if(firstname.length() <= 0 || firstname.length() > 30) {
            throw new RuntimeException("Invalid input");
          }
          break;
        } catch(Exception e){
          System.err.println (e.getMessage());
          continue;
        }
      } while(true);

      //last name validation
      do {
        System.out.print("Enter the last name of the customer: ");
        try {
          lastname = in.readLine();
          if(lastname.length() <= 0 || lastname.length() > 30) {
            throw new RuntimeException("Invalid input");
          }
          break;
        } catch(Exception e){
          System.err.println (e.getMessage());
          continue;
        }
      } while(true);

      //address validation
      do {
        System.out.print("Enter the address of the customer: ");
        try {
          address = in.readLine();
          break;
        } catch(Exception e){
          System.err.println (e.getMessage());
          continue;
        }
      } while(true);

      //phone number validation
       do {
        System.out.print("Enter the phone number of the customer, at most 10 digits, no spaces or hyphens: ");
        try {
          phonenum = Integer.parseInt(in.readLine());
          break;
        } catch(Exception e){
          System.err.println (e.getMessage());
          continue;
        }
      } while(true);

      //date of birth validation
      do {
        System.out.print("Enter the date of birth of the customer in the format MM/DD/YY: ");
        try {
          dateofbirth = in.readLine();
          if(lastname.length() <= 0) {
            throw new RuntimeException("Invalid input");
          }
          break;
        } catch(Exception e){
          System.err.println (e.getMessage());
          continue;
        }
      } while(true);

      //gender validation
      do {
        System.out.print("Enter the customer's gender, either Male, Female, or Other: ");
        try {
          gender = in.readLine();
          break;
        } catch(Exception e){
          System.err.println (e.getMessage());
          continue;
        }
      } while(true);

      String newcustomer = "INSERT INTO Customer VALUES (" + customerid + ", " + "'" + firstname + "', '"  + lastname + "', '" + address + "', " + phonenum + ", '" + dateofbirth + "', '" + gender + "')";
      //System.out.println(newcustomer);

      esql.executeUpdate(newcustomer);

      System.out.print("\nSuccessfully added the following customer to the database:\n");
      System.out.print("\tCustomer ID: " + customerid + "\n");
      System.out.print("\tName: " + firstname + " " + lastname + "\n");
      System.out.print("\tAddress: " + address +  "\n");
      System.out.print("\tPhone Number: " + phonenum + "\n");
      System.out.print("\tDate of Birth: " + dateofbirth +  "\n");
      System.out.print("\tGender: " + gender + "\n\n");
      

    } catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addCustomer

   //CHOICE 2 - DONE
   public static void addRoom(DBProject esql){
    // Given room details add the room in the DB
      // Your code goes here.
      // ...
      // ...
      try {
        System.out.println("\tADD ROOM");
        System.out.print("Enter the hotelID: ");
        String hotelid = in.readLine();

        System.out.print("Enter the room number: ");
        String roomnum = in.readLine();

        System.out.print("Enter the room type: ");
        String roomtype = in.readLine();

        String query = "INSERT INTO Room VALUES (" + hotelid + ", " + roomnum + ", '" + roomtype + "')";
        System.out.println(query);
         

        int rowCount = esql.executeQuery(query);
        System.out.println ("total row(s): " + rowCount);
      } catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end addRoom

   //CHOICE 3 - DONE
   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB
      // ...
      // ...

    try {
      System.out.println("\tADD MAINTENANCE COMPANY");

      System.out.print("Enter the companyID: ");
      String companyid = in.readLine();

      System.out.print("Enter the company name: ");
      String companyname = in.readLine();

      System.out.print("Enter the company address: ");
      String companyaddy = in.readLine();

      System.out.print("Is this company certified? TRUE OR FALSE: ");
      String companycert= in.readLine();

      String query = "INSERT INTO MaintenanceCompany VALUES (" + companyid + ", '" + companyname + "', '" + companyaddy + "', '" + companycert + "')";
  
      esql.executeUpdate(query);
      //System.out.println ("total row(s): " + rowCount);

      System.out.print("\nSuccessfully added the following company:\n");
      System.out.print("\tCompany name: " + companyname + "\n");
      System.out.print("\tCompany ID: " + companyid + "\n");
      System.out.print("\tAddress: " + companyaddy + "\n");
      System.out.print("\tCERTIFIED\n\n");


      } catch(Exception e) {
        System.err.println(e.getMessage());
      }

   }//end addMaintenanceCompany

   //CHOICE 4 - DONE
   public static void addRepair(DBProject esql){
    // Given repair details add repair in the DB
      // Your code goes here.
      // ...
      // ...
      try {
        System.out.println("\tADD REPAIR");

        System.out.print("Enter the repairID: ");
        String repairid = in.readLine();

        System.out.print("Enter the hotelID: ");
        String hotelid = in.readLine();

        System.out.print("Enter the room number: ");
        String roomnum = in.readLine();

        System.out.print("Enter the maintenance companyID: ");
        String companyid = in.readLine();

        System.out.print("Enter the repair date in the format MM/DD/YY: ");
        String repairdate = in.readLine();

        String query = "INSERT INTO Repair VALUES (" + repairid + ", " + hotelid + ", " + roomnum + ", " + companyid + ", '" + repairdate + "')";
     
        System.out.println(query);
        
        int rowCount = esql.executeQuery(query);
        System.out.println ("total row(s): " + rowCount);
      } catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end addRepair

  //CHOICE 5 - DONE
   public static void bookRoom(DBProject esql){
    // Given hotelID, roomNo and customer Name create a booking in the DB 
      // Your code goes here.
      // ...
      // ...

    try {
      System.out.println("\tBOOK A ROOM");

      System.out.print("Enter the hotelID: ");
      String hotelid = in.readLine();

      System.out.print("Enter the room number: ");
      String roomnum = in.readLine();

      System.out.print("Enter the customer's first name: ");
      String firstname = in.readLine();

      System.out.print("Enter the customer's last name: ");
      String lastname = in.readLine();

      System.out.print("Enter the date of the booking in the format MM/DD/YY: ");
      String bookingdate = in.readLine();

      String getbookingid = "(SELECT MAX(bID) FROM Booking)";
      Statement stmt = esql._connection.createStatement();
      ResultSet rs = stmt.executeQuery(getbookingid);
      rs.next();
      int bookingid = rs.getInt(1) + 1;

      String getcustomerid = "SELECT C.customerID FROM Customer C WHERE C.fname = '" + firstname + "' AND C.lname = '" + lastname + "'";
      System.out.println(getcustomerid);
      Statement stmt1 = esql._connection.createStatement();
      ResultSet rs1 = stmt1.executeQuery(getcustomerid);
      //rs1.next();
      //int customerid = rs1.getInt(1);
      int customerid = 0;
      if(rs1.next()){
        customerid = rs1.getInt(1);

      }else{
        // throw error saying user don't exist
        System.out.println("No USER!!!");

      }

      int partypeople = 1;

      System.out.print("What was the price of your stay?: $");
      String price = in.readLine();

      String query = "INSERT INTO Booking VALUES (" + bookingid + ", " + customerid + ", " + hotelid + ", " + roomnum + ", \'" + bookingdate + "\', " + partypeople + ", " + price + ")";
      System.out.println(query);
      int rowCount = esql.executeQuery(query);
      System.out.println ("total row(s): " + rowCount);
      
      System.out.print("Successfully added the following booking:\n");
      } catch(Exception e) {
        System.err.println(e.getMessage());
      }
   }//end bookRoom

    //CHOICE 6 - DONE
   public static void assignHouseCleaningToRoom(DBProject esql){
    // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      // Your code goes here.
      // ...
      // ...
      try {
          System.out.println("\tASSIGN HOUSE CLEANING TO ROOM");

          System.out.print("Enter the staffID: ");
          String staffid = in.readLine();

          System.out.print("Enter the hotelid: ");
          String hotelid = in.readLine();

          System.out.print("Enter the room number: ");
          String roomnum = in.readLine();

          String getassignedid = "(SELECT MAX(asgID) FROM Assigned)";
          Statement stmt = esql._connection.createStatement();
          ResultSet rs = stmt.executeQuery(getassignedid);
          rs.next();
          int assignedid = rs.getInt(1) + 1;

          String query = "INSERT INTO Assigned VALUES (" + assignedid + ", "  + staffid + ", " + hotelid + ", " + roomnum + ")";
          System.out.println(query);

          int rowCount = esql.executeQuery(query);
          System.out.println ("total row(s): " + rowCount);
      } catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end assignHouseCleaningToRoom
   
   
   //CHOICE 7 - DONE
   public static void repairRequest(DBProject esql){
    // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      // Your code goes here.
      // ...
      // ...

    try {
      System.out.println("\tREPAIR REQUESTS");

      System.out.print("Please enter your managerID: ");
      String managerid = in.readLine();

      System.out.print("Please enter the repairID for the request: ");
      String repairid = in.readLine();

      System.out.print("Please enter the date of the request in the format MM/DD/YY: ");
      String requestdate = in.readLine();

      String getrequestid = "(SELECT MAX(reqID) FROM REQUEST)";
      Statement stmt = esql._connection.createStatement();
      ResultSet rs = stmt.executeQuery(getrequestid);
      rs.next();
      int requestid = rs.getInt(1) + 1;

      String requestinsert = "INSERT INTO Request VALUES (" + requestid + ", " + managerid + ", " + repairid + ", '" + requestdate + "')";
      
      System.out.println(requestinsert);

      int rowCount = esql.executeQuery(requestinsert);
      System.out.println ("total row(s): " + rowCount);
    } catch(Exception e) {
        System.err.println(e.getMessage());
      }
   }//end repairRequest
   
   //CHOICE 8 - DONE 
   public static void numberOfAvailableRooms(DBProject esql){
    // Given a hotelID, get the count of rooms available 
      // Your code goes here.
      // ...
      // ...
      try {
        System.out.println("\tGET NUMBER OF AVAILABLE ROOMS");

         String query = "SELECT COUNT(*) FROM Room R, Booking B WHERE R.hotelID = B.hotelID AND R.roomNo NOT IN (SELECT R.roomNo FROM Booking B WHERE R.roomNo = B.roomNo ) AND R.hotelID = ";
         System.out.println("Enter the hotelID:");
         String input = in.readLine();
         query += input;

         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      } catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end numberOfAvailableRooms
   
   //CHOICE 9 - DONE
   public static void numberOfBookedRooms(DBProject esql){
    // Given a hotelID, get the count of rooms booked
      // Your code goes here.
      // ...
      // ...
    try {
      System.out.println("\tGET NUMBER OF BOOKED ROOMS");
      String query = "SELECT COUNT(*) FROM Room R, Booking B WHERE R.hotelID = B.hotelID AND R.roomNo IN (SELECT R.roomNo FROM Booking B WHERE R.roomNo = B.roomNo ) AND R.hotelID = ";
      System.out.print("Enter the hotelID: ");
      String hotelid = in.readLine();
      query += hotelid;

      int rowCount = esql.executeQuery(query);
      System.out.println ("total row(s): " + rowCount);

    } catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end numberOfBookedRooms
   
   //CHOICE 10 - DONE
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
    // Given a hotelID, date - list all the booked rooms for a week(including the input date) 
      // Your code goes here.
      // ...
      // ...
    try {
      System.out.print("Enter the booking date: ");
      String bookingdate = in.readLine();

      System.out.print("Enter the hotelid: ");
      String hotelid = in.readLine();

      String query = "SELECT B.roomNo FROM Booking B WHERE (B.bookingDate BETWEEN \'" + bookingdate + "\' AND DATE \'" + bookingdate + "\' + INTERVAL \'1 week\') AND B.hotelID = " + hotelid + " GROUP BY B.roomNo";
      
      System.out.println(query);

      int rowCount = esql.executeQuery(query);
      System.out.println("total row(s): " + rowCount);  

    } catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end listHotelRoomBookingsForAWeek
   
   //CHOICE 11 - DONE
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
    // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      // ...
      // ...

    try {
      System.out.println("\tTOP HIGHEST ROOM PRICE FOR A DATE RANGE");

      System.out.print("Enter the first date range in the format MM/DD/YY: ");
      String daterange1 = in.readLine();

      System.out.print("Enter the second date range in the format MM/DD/YY: ");
      String daterange2 = in.readLine();

      System.out.print("Enter how many rooms you want to see: ");
      String k = in.readLine();

      String query = "SELECT B.price, B.bookingDate FROM Room R, Booking B WHERE R.roomNo = B.roomNo AND B.bookingDate BETWEEN '" + daterange1 + "' AND '" + daterange2 + "' ORDER BY B.price DESC LIMIT " + k;
      System.out.println(query);

      int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);

    } catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end topKHighestRoomPriceForADateRange
   
   //CHOICE 12 - DONE 
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
    // Given a customer Name, List Top K highest booking price for a customer (enter number)
      // Your code goes here. 

      try {
         System.out.println("\tTOP K HIGHEST PRICE BOOKINGS FOR A CUSTOMER");

         System.out.print("Enter the customer's first name: ");
         String firstname = in.readLine();

         System.out.print("Enter the customer's last name: ");
         String lastname = in.readLine();

         System.out.print("How many entries would you like to see?: ");
         String k = in.readLine();

         String query = "SELECT B.price, B.bID FROM Customer C, Booking B WHERE C.fName = '" + firstname + "' AND C.lName = '" + lastname + "' AND C.customerID = B.customer ORDER BY B.price DESC LIMIT " + k;
         System.out.println(query);

         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      } catch(Exception e){
         System.err.println (e.getMessage());
      }
      
   }//end topKHighestPriceBookingsForACustomer
   
   //CHOICE 13 - DONE
   public static void totalCostForCustomer(DBProject esql){
    // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      // ...
      // ...

    try {
      System.out.println("\tGET TOTAL COST FOR A CUSTOMER");

      System.out.print("Enter the hotelID: ");
      String hotelid = in.readLine();

      System.out.print("Enter the first name of the customer: ");
      String firstname = in.readLine();

      System.out.print("Enter the last name of the customer: ");
      String lastname = in.readLine();

      System.out.print("Enter the first date range in the format MM/DD/YY: ");
      String daterange1 = in.readLine();

      System.out.print("Enter the second date range in the format MM/DD/YY: ");
      String daterange2 = in.readLine();

      String query = "SELECT SUM(price) FROM Booking B WHERE B.hotelID = " + hotelid + " AND B.customer = (SELECT C.customerID FROM Customer C WHERE C.fName = \'" + firstname + "\' AND C.lName = \'" + lastname + "\') AND B.bookingDate BETWEEN \'" + daterange1 + "\' AND \'" + daterange2 + "\'";
      System.out.println(query);

      int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);

    } catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end totalCostForCustomer
   
   //CHOICE 14 - DONE
   public static void listRepairsMade(DBProject esql){
    // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
      try {
         System.out.println("\tLIST REPAIRS MADE");

         System.out.println("Enter the Maintenance company name: ");
         String input = in.readLine();

         String query = "SELECT DISTINCT Rep.rID, Rep.repairType, Rep.hotelID, Rep.roomNo FROM Repair Rep, MaintenanceCompany M WHERE M.cmpID = Rep.mCompany AND M.name = '" + input + "'";

         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      } catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end listRepairsMade
   
   //CHOICE 15 - DONE
   public static void topKMaintenanceCompany(DBProject esql){
    // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      // ...
      // ...
    try {
      System.out.println("\tLIST TOP K MAINTENANCE COMPANIES");

      System.out.print("How many top companies do you want to see?: ");
      String k = in.readLine();

      String query = "SELECT COUNT(rID), M.name FROM MaintenanceCompany M, Repair R WHERE M.cmpID = R.mCompany GROUP BY M.name ORDER BY COUNT(rID) DESC LIMIT " + k;
      System.out.println(query);

      int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);

    } catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end topKMaintenanceCompany
   
   //CHOICE 16 - DONE
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
    // Given a hotelID, roomNo, get the count of repairs per year
      // Your code goes here.
    
      try{
        System.out.println("\tLIST NUMBER OF REPAIRS FOR EACH ROOM PER YEAR");
         
        System.out.print("Enter the hotelID: ");
        String hotelid = in.readLine();

        System.out.print("Enter the room number: ");
        String roomnum = in.readLine();
         
        String query = "SELECT DATE_PART('year', Rep.repairDate), COUNT(*) FROM Repair Rep WHERE Rep.hotelID = " + hotelid + " AND Rep.roomNo = " + roomnum + " GROUP BY DATE_PART('year', Rep.repairDate)";
        
        System.out.println(query);

        int rowCount = esql.executeQuery(query);
        System.out.println ("total row(s): " + rowCount);
         
      } catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end listRepairsMade
}//end DBProject
