package carsharing;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements CarSharingDao<Customer> {
    private final DBClient client;

    public CustomerDao(String url) {
        client = DBClient.getInstance(url);
        client.createStatement(SQLStatements.CREATE_TABLE_CUSTOMER);
    }

    @Override
    public void manageDatabase() {
        if (!hasCustomers()) {
            ConsoleHelper.writeMessage("\nThe customer list is empty!");
        } else {
            List<Customer> customers = client.selectCustomers(SQLStatements.SELECT_ALL_CUSTOMERS);
            displayList(customers);
            int customerId = Integer.parseInt(ConsoleHelper.readString());
            if (customerId != 0) {
                displayMenu();
                int choice = Integer.parseInt(ConsoleHelper.readString());
                while (choice != 0) {
                    processChoice(customerId, choice);
                    displayMenu();
                    choice = Integer.parseInt(ConsoleHelper.readString());
                }
            }
        }
    }

    @Override
    public void add(String customerName, int customerId) {
        customerId = client.selectCustomers(SQLStatements.SELECT_ALL_CUSTOMERS).size();

        try(PreparedStatement statement = client.prepareStatement(SQLStatements.CREATE_CUSTOMER)) {
            statement.setInt(1, ++customerId);
            statement.setString(2, customerName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayList(List<Customer> customers) {
        ConsoleHelper.writeMessage("\nChoose a customer:");
        for (int i = 0; i < customers.size(); i++) {
            ConsoleHelper.writeMessage(i + 1 + ". " + customers.get(i).getName());
        }
        ConsoleHelper.writeMessage("0. Back");
    }

    @Override
    public void displayMenu() {
        ConsoleHelper.writeMessage("""
                
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back
                """);
    }

    private boolean hasCustomers() {
        return !client.selectCustomers(SQLStatements.SELECT_ALL_CUSTOMERS).isEmpty();
    }

    private void processChoice(int customerId, int choice) {
        switch(choice) {
            case 1 -> rentACar(customerId);
            case 2 -> returnCar(customerId);
            case 3 -> showRentedCar(customerId);
        }
    }

    private void rentACar(int customerId) {
        List<Company> companies = client.selectCompanies(SQLStatements.SELECT_COMPANY_LIST);
        if (hasCar(customerId)) {
            ConsoleHelper.writeMessage("\nYou've already rented a car!");
        } else if (companies.isEmpty()) {
            ConsoleHelper.writeMessage("\nThe company list is empty!");
        } else {
            ConsoleHelper.writeMessage("\nChoose a company:");
            for (int i = 0; i < companies.size(); i++) {
                ConsoleHelper.writeMessage(i + 1 + ". " + companies.get(i).getName());
            }
            ConsoleHelper.writeMessage("0. Back");
            int selection = Integer.parseInt(ConsoleHelper.readString());
            selectCar(customerId, selection, companies);
        }
    }

    private void selectCar(int customerId, int selection, List<Company> companies) {
        if (selection != 0) {
            int companyId = companies.get(selection - 1).getId();
            String companyName = client.selectCompanyName(String.format(SQLStatements.SELECT_COMPANY_NAME, companyId));
            List<Car> cars = client.selectCars(String.format(SQLStatements.LIST_CARS_BY_COMPANY, companyId));
            List<Integer> rentedCarIds = client.selectCarIds(SQLStatements.LIST_RENTED_CARS_IDS);
            List<Car> carsCopy = new ArrayList<>(cars);
            for (Car car : carsCopy) {
                if (rentedCarIds.contains(car.getId())) {
                    cars.remove(car);
                }
            }
            if (cars.isEmpty()) {
                ConsoleHelper.writeMessage(String.format("\nNo available car in the '%s' company", companyName));
            } else {
                ConsoleHelper.writeMessage("\nChoose a car:");
                for (int i = 0; i < cars.size(); i++) {
                    ConsoleHelper.writeMessage(i + 1 + ". " + cars.get(i).getName());
                }
                ConsoleHelper.writeMessage("0. Back");
                int carChoice = Integer.parseInt(ConsoleHelper.readString());
                if (carChoice != 0) {
                    client.createStatement(String.format(SQLStatements.RENT_CAR, cars.get(carChoice - 1).getId(), customerId));
                    ConsoleHelper.writeMessage(String.format("\nYou rented '%s'", cars.get(carChoice - 1).getName()));
                }
            }
        }
    }

    private void returnCar(int customerId) {
        if (!hasCar(customerId)) {
            ConsoleHelper.writeMessage("\nYou didn't rent a car!");
        } else {
            client.createStatement(String.format(SQLStatements.RETURN_CAR, customerId));
            ConsoleHelper.writeMessage("\nYou've returned a rented car!");
        }
    }

    private void showRentedCar(int customerId) {
        int rentedCarId = client.selectRentedCar(String.format(SQLStatements.SELECT_CAR_BY_CUSTOMER, customerId));
        if (rentedCarId == 0) {
            ConsoleHelper.writeMessage("\nYou didn't rent a car!");
        } else {
            ConsoleHelper.writeMessage("\nYour rented car:");
            Car car = client.selectCarById(String.format(SQLStatements.SELECT_CAR_BY_ID, rentedCarId));
            ConsoleHelper.writeMessage(car.getName());
            ConsoleHelper.writeMessage("Company:");
            int companyId = car.getCompanyId();
            String company = client.selectCompanyName(String.format(SQLStatements.SELECT_COMPANY_NAME, companyId));
            ConsoleHelper.writeMessage(company);
        }
    }

    private boolean hasCar(int customerId) {
        return client.selectRentedCar(String.format(SQLStatements.SELECT_CAR_BY_CUSTOMER, customerId)) != 0;
    }
}
