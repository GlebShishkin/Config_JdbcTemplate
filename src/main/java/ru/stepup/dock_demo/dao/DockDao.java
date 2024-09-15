package ru.stepup.dock_demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.stepup.dock_demo.entity.Account;

import java.sql.SQLException;

@Component
public class DockDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DockDao(JdbcTemplate jdbcTemplate) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account getAccount(Integer id) {
        return jdbcTemplate.query("select * from account where id = ?", new Object[]{id}
                        , new BeanPropertyRowMapper<>(Account.class))
                .stream().findAny().orElse(null);

    }
}
