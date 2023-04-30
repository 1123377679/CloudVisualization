package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.MemberDao;
import cn.lanqiao.pojo.User;
import cn.lanqiao.utils.DButils;

import java.util.List;

public class MemberDaoImpl implements MemberDao {

    @Override
    public int getTotalCount() {
        return DButils.commonQueryCount("select count(*) from tb_user where isdelete = 0");
    }

    @Override
    public List<User> getDepatrs(String name, Integer pageStart, Integer pageSize) {
        //模糊查询预以及分页查询
        if (name != null &&!"".equals(name)){
            //模糊查询,查询出与此name有关的所有数据
            return DButils.commonQuery(User.class,"select * from tb_user where isdelete = 0 and username like ? limit ?,?","%"+name+"%",pageStart,pageSize);
        } else {
            //查询出所有数据,进行分页
            return DButils.commonQuery(User.class,"select * from tb_user where isdelete = 0 limit ?,?",pageStart,pageSize);
        }
    }
}
