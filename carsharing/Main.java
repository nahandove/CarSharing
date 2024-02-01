package carsharing;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";

    public static void main(String[] args) {
        String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
        try {
            Class.forName(JDBC_DRIVER);
            if (args.length == 2 && args[0].equals("-databaseFileName")) {
                DB_URL = "jdbc:h2:./src/carsharing/db/" + args[1];
            }
            CompanyDao companyDao = new CompanyDao(DB_URL);
            CustomerDao customerDao = new CustomerDao(DB_URL);
            ConsoleHelper.writeMessage("""
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    0. Exit""");
            int input = Integer.parseInt(ConsoleHelper.readString());
            while (input != 0) {
                if (input == 1) {
                    companyDao.manageDatabase();
                } else if (input == 2) {
                    customerDao.manageDatabase();
                } else if (input == 3) {
                    ConsoleHelper.writeMessage("\nEnter the customer name:");
                    String name = ConsoleHelper.readString();
                    customerDao.add(name, 0);
                    ConsoleHelper.writeMessage("The customer was added!");
                }
                ConsoleHelper.writeMessage("""
                    
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    0. Exit""");
                input = Integer.parseInt(ConsoleHelper.readString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}