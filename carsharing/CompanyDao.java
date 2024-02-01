package carsharing;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CompanyDao implements CarSharingDao<Company> {
    private final DBClient client;
    private final CarDao carDao;

    public CompanyDao(String url) {
        client = DBClient.getInstance(url);
        client.createStatement(SQLStatements.CREATE_TABLE_COMPANY);
        carDao = new CarDao(url);
    }

    @Override
    public void manageDatabase() {
        displayMenu();
        int input = Integer.parseInt(ConsoleHelper.readString());
        while (input != 0) {
            if (input == 1) {
                List<Company> companies = client.selectCompanies(SQLStatements.SELECT_COMPANY_LIST);
                displayList(companies);
            } else if (input == 2) {
                ConsoleHelper.writeMessage("\nEnter the company name:");
                String companyName = ConsoleHelper.readString();
                add(companyName, 0);
                ConsoleHelper.writeMessage("The company was created!");
            }
            displayMenu();
            input = Integer.parseInt(ConsoleHelper.readString());
        }
    }

    @Override
    public void displayList(List<Company> companies) {
        if (companies.isEmpty()) {
            ConsoleHelper.writeMessage("\nThe company list is empty!");
        } else {
            ConsoleHelper.writeMessage("\nChoose a company:");
            for (int i = 0; i < companies.size(); i++) {
                ConsoleHelper.writeMessage(i + 1 + ". " + companies.get(i).getName());
            }
            ConsoleHelper.writeMessage("0. Back");
            carDao.manageDatabase();
        }
    }

    @Override
    public void add(String companyName, int companyId) {
        companyId = client.selectCompanies(SQLStatements.SELECT_COMPANY_LIST).size();

        try(PreparedStatement statement = client.prepareStatement(SQLStatements.CREATE_COMPANY)) {
            statement.setInt(1, ++companyId);
            statement.setString(2, companyName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayMenu() {
        ConsoleHelper.writeMessage("""
                
                1. Company list
                2. Create a company
                0. Back""");
    }
}
