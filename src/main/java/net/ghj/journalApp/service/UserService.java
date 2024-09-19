package net.ghj.journalApp.service;

import net.ghj.journalApp.entity.User;
import java.util.List;

public interface UserService {

    boolean saveNewUser(User user);

    void saveUser(User user);

    void saveAdmin(User user);

    List<User> getAllUser();

    User findByUserName(String userName);

    void deleteByUserName(String userName);
}
