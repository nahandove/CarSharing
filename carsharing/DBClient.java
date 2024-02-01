package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBClient {
    private static String url;
    private static DBClient client;

    private DBClient(String url) {
        this.url = url;
    }

    public static DBClient getInstance(String url) {
        if (client == null) {
            return new DBClient(url);
        }
        return client;
    }

    public void createStatement(String sqlString) {
        Statement statement;
        try (Connection connection = DriverManager.getConnection(url)) {
            connection.setAutoCommit(true);
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareStatement(String sqlString) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        connection.setAutoCommit(true);
        return connection.prepareStatement(sqlString);
    }

    public List<Company> selectCompanies(String query) {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Company company = new Company(id, name);
                companies.add(company);
            }
            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    public String selectCompanyName(String query) {
        String companyName = "";
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while (resultSet.next()) {
                companyName = resultSet.getString("NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyName;
    }

    public List<Car> selectCars(String query) {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int companyId = resultSet.getInt("COMPANY_ID");
                Car car = new Car(id, name, companyId);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Customer> selectCustomers(String query) {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int rentedCarId = resultSet.getInt("RENTED_CAR_ID");
                Customer customer = new Customer(id, name, rentedCarId);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<Integer> selectCarIds(String query) {
        List<Integer> carIds = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while (resultSet.next()) {
                int carId = resultSet.getInt("RENTED_CAR_ID");
                carIds.add(carId);
            }
            return carIds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carIds;
    }

    public int selectRentedCar(String query) {
        int carId = 0;
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while (resultSet.next()) {
                carId = resultSet.getInt("RENTED_CAR_ID");
            }
            return carId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carId;
    }

    public Car selectCarById(String query) {
        Car car = null;
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while (resultSet.next()) {
                int carId = resultSet.getInt("ID");
                String carName = resultSet.getString("NAME");
                int companyId = resultSet.getInt("COMPANY_ID");
                car = new Car(carId, carName, companyId);
            }
            return car;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }
}
