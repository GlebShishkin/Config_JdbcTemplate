package ru.stepup.dock_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.dock_demo.dao.DockDao;
import ru.stepup.dock_demo.dto.AccountDto;
import ru.stepup.dock_demo.entity.Account;

@Service
public class DockService {

    @Autowired
    private DockDao dockDao;

    public Account getAccount(int id) {
        return dockDao.getAccount(id);
    }

    public Integer getAccountId(String acc_num, String name) {
        return dockDao.getAccountId(acc_num, name);
    }

    public int save (AccountDto accountDto) {
        return dockDao.save(accountDto);
    }
}