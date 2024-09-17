package ru.stepup.dock_demo.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.stepup.dock_demo.dto.AccountDto;
import ru.stepup.dock_demo.entity.Account;
import ru.stepup.dock_demo.services.DockService;

@Tag(name = "main_methods")
@Slf4j
@RestController
@RequestMapping("/api")
public class DockController {

    @Autowired
    private DockService dockService;

    // запрос вида GET: localhost:8080/api/accountParam?id=4
    // - идентификатор переается параметром (см. https://alexkosarev.name/2016/12/29/about-pathvariable/)
    @Operation(
            summary = "получение счета",
            description = "получает по параметру id счет"
    )
    @GetMapping("/accountParam")
    public Account getAccountParam (@RequestParam Integer id) {
        return dockService.getAccount(id);
    }

    // запрос вида GET: localhost:8080/api/accountPath/4
    // - идентификатор переается не параметром, а частью запроса (см. https://alexkosarev.name/2016/12/29/about-pathvariable/)
    @Operation(
            summary = "получение счета",
            description = "получает по id счета, как часть строки"
    )
    @GetMapping("/accountPath/{id:\\d+}")
    public Account getAccountPath (@PathVariable(value = "id") Integer id) {
        return dockService.getAccount(id);
    }

    // запрос вида GET с двумя параметрами (acc_num и name): localhost:8080/api/account/47427/name5
    @Operation(
            summary = "получение id счета",
            description = "получает по id счета по двум параметрам, как часть строки"
    )
    @GetMapping("/account/{acc_num}/{name}")
    public Integer getAccountId(@PathVariable String acc_num, @PathVariable String name) {
        log.info("####### acc_num = " + acc_num + "; name = " + name);
        return dockService.getAccountId(acc_num, name);
    }

    // сохранение Account в таблицу
    @Operation(
            summary = "сохранение Account в таблицу",
            description = "получение id нового счета"
    )
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int save (@RequestBody AccountDto accountDto) {
        return dockService.save(accountDto);
    }
}
