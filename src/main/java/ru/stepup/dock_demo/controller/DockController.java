package ru.stepup.dock_demo.controller;
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

    @GetMapping("/register")
    public String getNewRegisters (@RequestParam Integer id) {

        log.info("1) ############## getNewRegisters: conferenceID = " + id);
        return id.toString();
    }

    @GetMapping("/account")
    public Account getAccount (@RequestParam Integer id) {

        log.info("1) ############## getAccount: id = " + id + "; dockService.getAccount(id) = " + dockService.getAccount(id));
        return dockService.getAccount(id);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int save (@RequestBody AccountDto accountDto) {
        log.info("1) ############## save: acc_num = " + accountDto.acc_num() + "; name = " + accountDto.name());

        return dockService.save(accountDto);
    }
}
