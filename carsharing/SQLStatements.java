package carsharing;

public class SQLStatements {
    public static final String CREATE_TABLE_COMPANY = """
            CREATE TABLE IF NOT EXISTS COMPANY (
            ID INTEGER PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(255) UNIQUE NOT NULL);""";
    public static final String CREATE_COMPANY = """
            INSERT INTO COMPANY (ID, NAME)
            VALUES (?, ?);
            """;
    public static final String SELECT_COMPANY_LIST = """
            SELECT * FROM COMPANY;
            """;
    public static final String SELECT_COMPANY_NAME = """
            SELECT NAME FROM COMPANY
            WHERE ID = %d;
            """;
    public static final String CREATE_TABLE_CAR = """
            CREATE TABLE IF NOT EXISTS CAR (
            ID INTEGER PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(255) UNIQUE NOT NULL,
            COMPANY_ID INTEGER NOT NULL,
            FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(ID));""";
    public static final String DISPLAY_FULL_CAR_LIST = """
            SELECT * FROM CAR;
            """;
    public static final String LIST_CARS_BY_COMPANY = """
            SELECT * FROM CAR
            WHERE COMPANY_ID = %d;
            """;
    public static final String SELECT_CAR_BY_ID = """
            SELECT * FROM CAR
            WHERE ID = %d;
            """;
    public static final String CREATE_CAR = """
            INSERT INTO CAR (ID, NAME, COMPANY_ID)
            VALUES (?, ?, ?);
            """;
    public static final String CREATE_TABLE_CUSTOMER = """
            CREATE TABLE IF NOT EXISTS CUSTOMER(
            ID INTEGER PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(255) UNIQUE NOT NULL,
            RENTED_CAR_ID INT,
            FOREIGN KEY(RENTED_CAR_ID) REFERENCES CAR(ID));
            """;
    public static final String CREATE_CUSTOMER = """
            INSERT INTO CUSTOMER(ID, NAME, RENTED_CAR_ID)
            VALUES(?, ?, NULL);
            """;
    public static final String LIST_RENTED_CARS_IDS = """
            SELECT RENTED_CAR_ID FROM CUSTOMER
            WHERE RENTED_CAR_ID IS NOT NULL;
            """;
    public static final String SELECT_ALL_CUSTOMERS = """
            SELECT * FROM CUSTOMER;
            """;
    public static final String SELECT_CAR_BY_CUSTOMER = """
            SELECT RENTED_CAR_ID
            FROM CUSTOMER
            WHERE ID = %d;
            """;
    public static final String RENT_CAR = """
            UPDATE CUSTOMER
            SET RENTED_CAR_ID = %d
            WHERE ID = %d;
            """;
    public static final String RETURN_CAR = """
            UPDATE CUSTOMER
            SET RENTED_CAR_ID = NULL
            WHERE ID = %d;
            """;
}
