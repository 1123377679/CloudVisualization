package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.MemberDao;
import cn.lanqiao.pojo.User;
import cn.lanqiao.utils.DButils;

import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao {

    @Override
    public int getTotalCount() {
        return DButils.commonQueryCount("select count(*) from tb_user where isdelete = 0");
    }

    @Override
    public List<User> getDepatrs(String name, Integer pageStart, Integer pageSize) {
        //模糊查询预以及分页查询
        if (name != null && !"".equals(name)) {
            //模糊查询,查询出与此name有关的所有数据
            return DButils.commonQuery(User.class, "select * from tb_user where isdelete = 0 and username like ? limit ?,?", "%" + name + "%", pageStart, pageSize);
        } else {
            //查询出所有数据,进行分页
            return DButils.commonQuery(User.class, "select * from tb_user where isdelete = 0 limit ?,?", pageStart, pageSize);
        }
    }

    @Override
    public int addUser(User user) {
        return DButils.commonInsert("insert into tb_user values(null,?,?,?,?,?,?,?,0)",
                user.getUsername(),
                user.getPassword(),
                user.getSex(),
                user.getBirthday(),
                user.getPhone(),
                user.getAddress(),
                user.getType());
    }

    @Override
    public User getUserById(String id) {
        ArrayList<User> users = DButils.commonQuery(User.class, "select * from tb_user where isdelete = 0 and id =?", id);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int updateById(User user) {
        return DButils.commonUpdate("update tb_user set username= ? ,password = ?,sex = ?,birthday = ?,phone = ?,address = ?,type = ? where id=?",
                user.getUsername(),
                user.getPassword(),
                user.getSex(),
                user.getBirthday(),
                user.getPhone(),
                user.getAddress(),
                user.getType(),
                user.getId());
    }

    @Override
    public int updatePwd(Integer userId, String newPwd) {
        return DButils.commonUpdate("update tb_user set password =? where id =?", newPwd, userId);
    }

    @Override
    public int deleteById(String id) {
        return DButils.commonUpdate("update tb_user set isdelete = 1 where id =?", id);
    }

    @Override
    public int checkName(String name) {
        return DButils.commonQueryCount( "select count(*) from tb_user where username =?",name);
    }

    @Override
    public int checkPhone(String phone) {
        return DButils.commonQueryCount( "select count(*) from tb_user where phone =?",phone);
    }
}
