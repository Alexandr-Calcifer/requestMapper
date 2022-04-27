package ru.pupenkov.spb.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.pupenkov.spb.model.Person;

import java.sql.*;
import java.util.ArrayList;

//@Component
@PropertySource(value = "classpath:app.properties", encoding="UTF-8")
public class DbConnection {

    @Value("${study.db.url}")
    private String dbUrl;
    @Value("${study.db.user}")
    private String dbUser;
    @Value("${study.db.pass}")
    private String dbPass;
    @Value("${study.db.driver}")
    private String dbDriver;

    private Connection connection = null;
    private Statement statement = null;


    public void setPropForDBConnection() throws SQLException, ClassNotFoundException {
        Class.forName(dbDriver);
        if(connection == null)
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }

    private void setStatement() throws SQLException {
        if(statement == null)
            statement = connection.createStatement();
    }

    public ArrayList<Person> selectAndCloseConn(String query){
        ArrayList<Person> values = new ArrayList<>();
        try {
            if(connection == null)setPropForDBConnection();
            try {
                if(statement == null)setStatement();
                ResultSet resultSet = null;
                try {
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        Person person = new Person();
                        person.setId(resultSet.getInt("id"));
                        person.setName(resultSet.getString("name"));
                        person.setMail(resultSet.getString("mail"));
                        person.setPassword(resultSet.getString("password"));
                        values.add(person);
                    }
                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    } else {
                        System.err.println("ошибка во время чтения из БД");
                    }
                }
            } catch (SQLException e) {
                System.err.println("DB connection error: " + e);
            }
//            finally {
//                if (statement != null) {
//                    statement.close();
//                } else {
//                    System.err.println("Statement не создан");
//                }
//            }
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    System.err.println("Сonnection close error: " + e);
//                }
//            }
//        }
        if (values.size() > 0) {
            return values;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Person> select(String query){
        ArrayList<Person> values = new ArrayList<>();
        try {
            if (connection == null) {
                try {
                    setPropForDBConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (statement == null) setStatement();
                ResultSet resultSet = null;
                try {
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        Person person = new Person();
                        person.setId(resultSet.getInt("id"));
                        person.setName(resultSet.getString("name"));
                        person.setMail(resultSet.getString("mail"));
                        person.setPassword(resultSet.getString("password"));
                        values.add(person);
                    }
                } catch (SQLException e) {
                    System.err.println("DB connection error: " + e);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (values.size() > 0) {
                return values;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    public boolean preparedStatement(String...values){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES(1,?,?,?)");
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i+1, values[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DbConnection closeStatement(){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.err.println("Statement не создан");
        }
        return this;
    }

    public DbConnection closeConnection(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.err.println("Connection не создан");
        }
        return this;
    }
}
