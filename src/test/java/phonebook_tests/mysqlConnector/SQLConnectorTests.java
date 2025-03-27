package phonebook_tests.mysqlConnector;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;

public class SQLConnectorTests {

    Connection connection;

    @BeforeMethod
    public void loadDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //create connection
            connection = DriverManager.getConnection(DbData.dbUrl, DbData.username, DbData.password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void closeDb() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getDataFromDataBaseTest() {
        try {
            // Создать объект оператора
            Statement statement = connection.createStatement();
            // Выполнить запрос
            ResultSet resultSet = statement.executeQuery(DbData.querySelectUsers);
            while (resultSet.next()) {
                System.out.println(
                        "username: " + resultSet.getString(DbData.index1) +
                                "\npassword: " + resultSet.getString(DbData.index2)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteUserFromDataBaseTest() {
        try {
            PreparedStatement statement = connection.prepareStatement(DbData.queryDeleteUser);
            statement.executeUpdate();
            ResultSet resultSet = statement.executeQuery(DbData.querySelectUsers);
            while (resultSet.next()) {
                System.out.println(
                        "username: " + resultSet.getString(DbData.index1) +
                                "\npassword: " + resultSet.getString(DbData.index2)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createUserTest() {
        String insertQuery = DbData.queryCreateUser;
        String selectQuery = DbData.querySelectUsers;
        boolean userFound = false;

        try (
                // Вставка пользователя
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery)
        ) {
            int rows = insertStatement.executeUpdate();
            Assert.assertEquals(rows, 1, "Пользователь не был добавлен");

            // Проверка, что пользователь появился в БД
            try (
                    PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                    ResultSet resultSet = selectStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    String userName = resultSet.getString(DbData.index1);
                    String password = resultSet.getString(DbData.index2);

                    System.out.println(
                            "username: " + userName +
                                    "\npassword: " + password
                    );

                    if ("portishead@gmail.com".equals(userName) && "Password@1".equals(password)) {
                        userFound = true;
                        break; // Как только нашли - дальше не идём
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса", e);
        }

        Assert.assertTrue(userFound, "Пользователь не найден в БД!");
    }
}

