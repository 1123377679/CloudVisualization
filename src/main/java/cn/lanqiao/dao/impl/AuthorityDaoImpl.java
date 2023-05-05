package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.AuthorityDao;
import cn.lanqiao.pojo.User;
import cn.lanqiao.utils.DButils;

import java.util.ArrayList;
import java.util.List;

public class AuthorityDaoImpl implements AuthorityDao {
    @Override
    public List<User> selectAll(String username) {
        if (username != null && !"".equals(username)){
            return DButils.commonQuery(User.class, "select * from tb_user where isdelete=0 and username like ?", "%" + username + "%");
        }else {
            return DButils.commonQuery(User.class, "select * from tb_user where isdelete=0");
        }
    }

    @Override
    public int getTotalCount() {
        return DButils.commonQueryCount("select count(*) from tb_user where isdelete = 0");
    }

    @Override
    public List<User> getDepatrs(String name, Integer pageStart, Integer pageSize) {
        //首先要做模糊查询以及分页查询
        if (name != null &&!"".equals(name)){
            //前端有条件需要查询
            return DButils.commonQuery(User.class,"select * from tb_user where isdelete = 0 and username like ? limit ?,?","%"+name+"%",pageStart,pageSize);
        }else {
            //如果没有输入条件，则按照所有条件进行分页
            return DButils.commonQuery(User.class,"select * from tb_user where isdelete = 0 limit ?,?",pageStart,pageSize);
        }
    }

    @Override
    public User getUserById(String id) {
        ArrayList<User> users = DButils.commonQuery(User.class, "select * from tb_user where isdelete = 0 and id = ?",id);
        if (users.size() > 0){
            return users.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int updateById(Integer id, String newType) {
        return DButils.commonUpdate("update tb_user set type = ? where id = ?",newType,id);
    }
}
