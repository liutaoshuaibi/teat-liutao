package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    /**
     * 查询是否有用户
     */
    public User regist(String username) {
        String sql = "select * from user where username = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    /**
     * 注册用户
     */
    public void save(User user) {
        String sql = "insert into user(username,password,name,birthday,sex,telephone,email,status,code,root) values(?,?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode(), user.getRoot());
    }

    @Override
    //激活码
    public User findByCode(String code) {
        String sql = "select * from user where code = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    /**
     * 激活用户
     */
    public void updateStatus(int uid) {
        String sql = "update user set status = 'Y',code = ? where uid = ?";
        template.update(sql, "null", uid);
    }

    @Override
    /**
     * 查找用户
     */
    public User findUserByUsernameAndPassword(String username, String password) {
        String sql = "select * from user where username = ? and password = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {
        }
        return user;
    }
}
