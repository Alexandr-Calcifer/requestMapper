package ru.pupenkov.spb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.pupenkov.spb.model.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPerson() {
        return jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPersonById(int id) {
        return jdbcTemplate.query("select * from users where id=?"
                , new BeanPropertyRowMapper<>(Person.class), new Object[]{id}).stream().findAny().
                orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into users values (1,?,?,?)"
                , person.getName(), person.getMail(), person.getPassword());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update users set name=?, mail=?, password=?)"
                , updatedPerson.getName(), updatedPerson.getMail(), updatedPerson.getPassword());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete users where id=?", id);
    }
}
