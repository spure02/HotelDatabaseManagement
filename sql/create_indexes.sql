DROP INDEX IF EXISTS hotel_index;
DROP INDEX IF EXISTS staff_index;
DROP INDEX IF EXISTS room_index;
DROP INDEX IF EXISTS customer_index;
DROP INDEX IF EXISTS company_index;
DROP INDEX IF EXISTS booking_index;
DROP INDEX IF EXISTS repair_index;
DROP INDEX IF EXISTS request_index;
DROP INDEX IF EXISTS assigned_index;

CREATE INDEX hotel_index
ON Hotel
(hotelID);

CREATE INDEX staff_index
ON Staff
(staffID);

CREATE INDEX room_index
ON Room
(roomID);

CREATE INDEX customer_index
ON Customer
(customerID);

CREATE INDEX company_index
ON MaintenanceCompany
(cmpID);

CREATE INDEX booking_index
ON Booking
(bookingDate);

CREATE INDEX repair_index
ON Repair
(mCompany);

CREATE INDEX request_index
ON Request
(managerID);

CREATE INDEX assigned_index
ON Assigned
(staffID);