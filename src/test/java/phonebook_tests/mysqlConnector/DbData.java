package phonebook_tests.mysqlConnector;

public class DbData {
    //connection URL "jdbc:mysql://localhost:3306/phonebook"
    public static final String dbUrl = "jdbc:mysql://localhost:3306/phonebook";

    //database username
    public static final String username = "root";

    //database password
    public static final String password = "2318";

    //query
    public static final int index1 = 1;
    public static final int index2 = 2;

    public static final String querySelectUsers = "SELECT * FROM users;";
    public static final String queryDeleteUser = "DELETE FROM users WHERE userName='portishead@gmail.com';";
    public static final String queryCreateUser = "INSERT INTO users (userName, password) VALUES ('portishead@gmail.com','Password@1');";


}
