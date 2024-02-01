package carsharing;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CarDao implements CarSharingDao<Car> {
    private final DBClient client;


    public CarDao(String url) {
        client = DBClient.getInstance(url);
        client.createStatement(SQLStatements.CREATE_TABLE_CAR);
    }

    @Override
    public void manageDatabase() {
        int companyId = Integer.parseInt(ConsoleHelper.readString());
        if(companyId != 0) {
            String companyName = client.selectCompanyName(String.format(SQLStatements.SELECT_COMPANY_NAME, companyId));
            ConsoleHelper.writeMessage(String.format("\n'%s' company", companyName));
            displayMenu();
            int input = Integer.parseInt(ConsoleHelper.readString());
            while (input != 0) {
                if (input == 1) {
                    List<Car> cars = client.selectCars(String.format(SQLStatements.LIST_CARS_BY_COMPANY, companyId));
                    displayList(cars);
                } else if (input == 2) {
                    ConsoleHelper.writeMessage("\nEnter the car name:");
                    String carName = ConsoleHelper.readString();
                    add(carName, companyId);
                }
                displayMenu();
                input = Integer.parseInt(ConsoleHelper.readString());
            }
        }
    }

    @Override
    public void add(String carName, int companyId) {
        int count = client.selectCars(SQLStatements.DISPLAY_FULL_CAR_LIST).size();
        try(PreparedStatement statement = client.prepareStatement(SQLStatements.CREATE_CAR)) {
            statement.setInt(1, ++count);
            statement.setString(2, carName);
            statement.setInt(3, companyId);
            statement.executeUpdate();
            ConsoleHelper.writeMessage("The car was added!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayList(List<Car> cars) {
        if (cars.isEmpty()) {
            ConsoleHelper.writeMessage("\nThe car list is empty!\n");
        } else {
            ConsoleHelper.writeMessage("\nCar list:");
            for (int i = 0; i < cars.size(); i++) {
                ConsoleHelper.writeMessage(i + 1 + ". " + cars.get(i).getName());
            }
            ConsoleHelper.writeMessage("");
        }
    }

    @Override
    public void displayMenu() {
        ConsoleHelper.writeMessage("""
                1. Car list
                2. Create a car
                0. Back""");
    }
}
