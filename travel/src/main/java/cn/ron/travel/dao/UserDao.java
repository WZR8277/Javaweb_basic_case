package cn.ron.travel.dao;

import cn.ron.travel.domain.User;

public interface UserDao {


    public User findByUserName(String username);

    public void save(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUserNameAndPassword(String username, String password);
}
