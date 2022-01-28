package cn.ron.travel.service.impl;

import cn.ron.travel.dao.UserDao;
import cn.ron.travel.dao.impl.UserDaoImpl;
import cn.ron.travel.domain.User;
import cn.ron.travel.service.UserService;
import cn.ron.travel.util.MailUtils;
import cn.ron.travel.util.UuidUtil;

public class UserSeviceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        User u = userDao.findByUserName(user.getUsername());
        if(u!=null){
            return false;
        }

        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");

        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'> " +
                "点击激活【Ron的旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        userDao.save(user);

        return true;
    }

    @Override
    public boolean active(String code) {

        User user = userDao.findByCode(code);
        if(user != null){
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public User login(User user) {
        return userDao.findByUserNameAndPassword(user.getUsername(),user.getPassword());
    }
}
