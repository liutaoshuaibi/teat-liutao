package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User regist(String username);

    void save(User user);

    User findByCode(String code);

    void updateStatus(int uid);

    User findUserByUsernameAndPassword(String username, String password);
}
