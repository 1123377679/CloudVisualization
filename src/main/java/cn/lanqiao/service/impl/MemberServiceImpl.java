package cn.lanqiao.service.impl;

import cn.lanqiao.dao.MemberDao;
import cn.lanqiao.dao.impl.MemberDaoImpl;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.MemberService;

import java.util.List;

public class MemberServiceImpl implements MemberService {
    MemberDao memberDao = new MemberDaoImpl();
    @Override
    public int getTotalCount() {
        return memberDao.getTotalCount();
    }

    @Override
    public List<User> getDepatrs(String name, Integer pageStart, Integer pageSize) {
        return memberDao.getDepatrs(name,pageStart,pageSize);
    }

    @Override
    public int addUser(User user) {
        return memberDao.addUser(user);
    }

    @Override
    public User getUserById(String id) {
        return memberDao.getUserById(id);
    }

    @Override
    public int updateById(User user) {
        return memberDao.updateById(user);
    }

    @Override
    public int updatePwd(Integer userId, String newPwd) {
        return memberDao.updatePwd(userId,newPwd);
    }

    @Override
    public int deleteById(String id) {
        return memberDao.deleteById(id);
    }

    @Override
    public int checkName(String name) {
        return memberDao.checkName(name);
    }

    @Override
    public List<User> selectAllUser() {
        return memberDao.selectAllUser();
    }
}
