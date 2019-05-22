package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoImpl();

    @Override
    /**
     * 注册用户
     */
    public boolean regist(User user) {
        User u = dao.regist(user.getUsername());//直接获取用户名
        if (u != null){//和数据库进行交互
            //存在用户
            return true;
        }
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        user.setRoot("N");//用户名不存在进行设置
        dao.save(user);

        String context = "<a href='http://localhost/graduate/user/active?code="+user.getCode()+"'>点击激活【足球世界】</a>";
        MailUtils.sendMail(user.getEmail(),context,"激活账号");//给邮箱添加内容  跳转页面  激活
        return false;
    }

    @Override
    public boolean active(String code) {
        User user = dao.findByCode(code);
        if (user == null){
            //没有用户
            return false;
        }
        dao.updateStatus(user.getUid());
        return true;
    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

}
