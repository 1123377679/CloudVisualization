package cn.lanqiao.service.impl;

import cn.lanqiao.dao.AuthorityDao;
import cn.lanqiao.dao.impl.AuthorityDaoImpl;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.AuthorityService;

import java.util.List;

public class AuthorityServiceImpl implements AuthorityService {
    AuthorityDao authorityDao = new AuthorityDaoImpl();
    @Override
    public List<User> selectAll(String username) {
        return authorityDao.selectAll(username);
    }

    @Override
    public int getTotalCount() {
        return authorityDao.getTotalCount();
    }

    @Override
    public List<User> getDepatrs(String name, Integer pageStart, Integer pageSize) {
        return authorityDao.getDepatrs(name,pageStart,pageSize);
    }

    @Override
    public User getUserById(String id) {
        return authorityDao.getUserById(id);
    }

    @Override
    public int updateById(Integer id, String newType) {
        return authorityDao.updateById(id,newType);
    }

}
