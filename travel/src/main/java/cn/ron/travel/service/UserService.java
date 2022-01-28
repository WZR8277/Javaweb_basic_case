package cn.ron.travel.service;

import cn.ron.travel.domain.User;

public interface UserService {
    boolean regist(User user);

    boolean active(String code);

    User login(User user);
}
