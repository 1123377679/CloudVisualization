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
}
