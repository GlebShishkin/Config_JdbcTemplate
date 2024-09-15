package ru.stepup.dock_demo.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.stepup.dock_demo.dao.DockDao;
import ru.stepup.dock_demo.entity.Account;
import ru.stepup.dock_demo.services.DockService;

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

        log.info("1) ############## getAccount: id = " + id);
        log.info("2) ############## dockService.getAccount(id) = " + dockService.getAccount(id));
        return dockService.getAccount(id);
    }
}
