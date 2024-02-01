Project assignment from JetBrains Academy (www.hyperskill.org), Java Backend Developer track.

This application uses the H2 database. It simulates a car rental service. Car company
managers can create new car sharing companies and add cars for rental, and customers can rent
a car from a company of their own choice with this application.

The application begins with a menu screen. The user can choose the manager or the customer
view, or add a new customer. 0 is used to close the application.

Display of the main menu screen:

1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit

1 puts the user in the manager view, 2 in the customer view. The user can create new customer
with 3. 

--Manager view

The user enters the manager view by selecting 1 from the main menu. They are presented with 
a second menu screen where they can view the current list of companies or create a new
company. 0 is used if the user desires to return to the main menu:

1. Company list
2. Create a company
0. Back

When the user selsets 1, they will be presented with a list of existing companies. If the 
company list is empty, the user is shown an error message, "The company list is empty!".
Otherwise, each of the existing companies are presented in a separate list entry. Example
menu with a list of companies:

Choose the company:
1. Car To Go
2. Drive Now
0. Back

The user can select individual companies to add new cars or check out existing list of cars
currently belonging to the company. Say if the user chooses 1, the next menu will be shown:

'Car To Go' company
1. Car list
2. Create a car
0. Back

The user can press 1 to see the current list of car belonging to this company. The last menu
is also shown after the car list. If no car exists in the company, the error message, "The
car list is empty!" will be shown.

Example car list view:

Car list
1. Hyundai Venue
2. Maruti Suzuki Dzire

1. Car list
2. Create a car
0. Back

As shown, the user remains in the 'Car To Go' company view. The user can therefore view the
existing cars again by pressing 1, or press 2 to add a new car. If the user presses 2, the
user is prompted to enter a car name: 

Enter the car name:

The user can then add a car to the 'Car to Go' company. The message "The car was added!" will
be displayed after the user presses enter.

If the user desires to work on another company, they can press 0 (the "Back" command) to
return to the main manager view. 

--Customer view

The user enters customer view by selecting 2 from the main menu. The first screen shows a 
list of customer name ordered by id, or the error message "the customers list is empty!" if
there is currently no customer in the database. The user can also select 0 to return to the
main menu. The user logs in by selecting the id, and is presented with the customer menu screen, as shown below:

1. Rent a car
2. Return a rented car
3. My rented car
0. Back

A. Renting a car

If the user selects 1 and is currently the owner of a rented car, an error message, "You've
already rented a car!" was shown. Otherwise, the user is presented with a list of companies,
or the error message "The company list is empty!" if no company exists in the database. An
example company list view is seen below:

Choose a company: 
1. Car to Go
2. Drive now
0. Back

The user chooses a company by entering a number, or 0 to return to the previous menu. If the
selected company currently does not have any car available for rent, an error message "No
available car in the '{Company name}' company" is displayed, where {Company name} is the
user's selection. Otherwise, the user is presented with a list of car belonging to the chosen
company. An example car list view:

Choose a car:
1. Hyundai Venue
2. Maruti Suzuki Dzire
0. Back

The user presses a number to rent a car or 0 to return to the customer menu screen. If the
user selects a car, they become the owner of the car, and the car will not be shown to any
other customers in company listings. The user will be informed of their rental: "You rented {car name}", where {car name} is the brand of the car getting rented, then returned to the
customer menu screen. Note that only one car can be rented per customer.

B. Returning a rented car

A user can return a rented car by selecting 2 from the customer menu screen. If the user
currently does not own a rented car, the error message, "you didn't rent a car!" is shown.
Otherwise, the car currently belonging to the user is transferred back to the company's
ownership and available for rental by other customers. The user receives a confirmation
message, "You've returned a rented car!" and is returned to the customer menu screen.

C. My rented car

A user can view the details of the car they own by selecting 3 from the customer menu screen.
If the user currently does not own a rented car, the error message "You didn't rent a car!" 
is shown. Otherwise, the user is presented with the the following info:

Your rented car:
{Car name}
Company:
{Company name}

The {Car name} and {Company name} depends on the user's selection during car rental. The user
is then immediately returned to the customer menu screen.

D. Back

If the user selects 0 from the customer menu screen, they will be returned to the main menu.

-- Create a new customer

Selecting 3 from the main menu allows new customers to be created. The user is prompted to
enter the name of the customer, and after the name is entered, the customer database is
updated, and the user receives a confirmation message, "The customer was added!"

-- Relational databases in this program

The user can specify the name of the database via command line arguments "-databaseFileName
{filename}", where {filename} is the database name. The database file is specified to the
path relative to working directory, "./src/carsharing/db/" plus the database name. A default
database name "carsharing" is used if no file name is specified. The program uses the H2 
database to store all data, and the database retains all information after the application
closes.

Three tables are created (if the tables currently do not exist) in the database: COMPANY,
CAR, and CUSTOMER. The COMPANY table has the columns ID (integer and primary key, with auto-
increment) and NAME (string, must be unique and not null). The CAR table has the columns
ID (integer and primary key, with auto-increment), NAME (string, must be unique and not
null), and COMPANY_ID (integer and not null, and a foreign key referencing ID from the 
COMPANY table). The CUSTOMER table has the columns ID (integer and primary key, with auto-
increment), NAME (string, must be unique and not null), and RENTED_CAR_ID (integer, can
be null or a foreign key referencing ID from the CAR table).

Whenever the manager adds a company, the COMPANY table is updated with a new entry containing
information of the new company. The new company has the ID one higher than the ID of the last
compnay of the table.

Whenever the manager adds a car, the CAR table is updated with a new entry containing
information of the new car. The new car has the ID one higher than the ID of the last car of
the table and the company that the manager previously selected to add the new car in.

Whenever a new customer is created, the CUSTOMER table is updated with a new entry containing
information of the new customer. The new customer has the ID one higher than the ID of the
last customer, and the RENTED_CAR_ID is set to the default value (null).

If a customer rents a car, the RENTED_CAR_ID is updated to the ID of the CAR table,
indicating that the customer is currently in possession of the car and that the car of this
ID is unavailable for rental. Once the customer returns the car, the RENTED_CAR_ID is reset
to null and the car will show up again among the company's list of available car.

Sample databases are included in this repository.

January 31th, 2024--description by E. Hsu (nahandove@gmail.com)