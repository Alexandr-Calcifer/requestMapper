package ru.pupenkov.spb.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.pupenkov.spb.model.Person;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMaper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setMail(resultSet.getString("mail"));
        person.setPassword(resultSet.getString("password"));
        return person;
    }
}
