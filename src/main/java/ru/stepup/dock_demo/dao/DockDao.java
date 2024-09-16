package ru.stepup.dock_demo.dao;

import io.swagger.v3.oas.annotations.Operation;
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

// пуст к swagger: http://localhost:8080/swagger-ui/index.html#

@Component
public class DockDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Operation(
            summary = "получение счета",
            description = "получает по id счет"
    )
    public Account getAccount(Integer id) {
        return jdbcTemplate.query("select * from account where id = ?", new Object[]{id}
                        , new BeanPropertyRowMapper<>(Account.class))
                .stream().findAny().orElse(null);
    }

/* Старый вариант (без лямбда-выражения)
    // сохранение Account в таблицу
    @Operation(
            summary = "сохранение Account в таблицу",
            description = "получение id нового счета"
    )
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

    // сохранение Account в таблицу
    @Operation(
            summary = "сохранение Account в таблицу",
            description = "получение id нового счета"
    )
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
