package ru.stepup.dock_demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.stepup.dock_demo.dto.AccountDto;
import ru.stepup.dock_demo.entity.Account;

import java.sql.PreparedStatement;
import java.util.Objects;

// путь к swagger: http://localhost:8080/swagger-ui/index.html#
@Slf4j
@Component
public class DockDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account getAccount(Integer id) {
        return jdbcTemplate.query("select * from account where id = ?", new Object[]{id}
                        , new BeanPropertyRowMapper<>(Account.class))
                .stream().findAny().orElse(null);
    }

    // получение id чета по номеру и имени
    public Integer getAccountId(String acc_num, String name) {
        log.info("####### acc_num = " + acc_num + "; name = " + name);

        Account account = jdbcTemplate.query("select * from account where acc_num = ? and name = ?", new Object[]{acc_num, name}
                        , new BeanPropertyRowMapper<>(Account.class))
                .stream().findAny().orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getId();
  }

/* Старый вариант (без лямбда-выражения)
    public int save (AccountDto accountDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            // определяем метод интерфейса PreparedStatementCreator.createPreparedStatement
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "insert into account(acc_num, name) values(?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"}); // Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, accountDto.acc_num());
                preparedStatement.setString(2, accountDto.name());
                return preparedStatement;
            }
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
*/

    public int save (AccountDto accountDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // определяем (@Override) метод интерфейса PreparedStatementCreator.createPreparedStatement
        jdbcTemplate.update(connection -> {
            String sql = "insert into account(acc_num, name) values(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"}); // Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, accountDto.acc_num());
            preparedStatement.setString(2, accountDto.name());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}
