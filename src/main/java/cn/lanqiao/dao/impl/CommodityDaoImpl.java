package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.CommodityDao;
import cn.lanqiao.pojo.Commodity;
import cn.lanqiao.utils.DButils;

import java.util.ArrayList;
import java.util.List;

public class CommodityDaoImpl implements CommodityDao {
    @Override
    public int getTotalCount() {
        return DButils.commonQueryCount("select count(*) from tb_commodity");
    }

    @Override
    public List<Commodity> getDepatrs(String name,Integer pageStart, Integer pageSize) {
        StringBuilder stringBuilder = new StringBuilder("select * from tb_commodity");
        List<Object> parms = new ArrayList<Object>();
        if (name != null && !"".equals(name)){
            stringBuilder.append(" where name like ?");
            parms.add("%"+name+"%");
        }
        stringBuilder.append(" limit ?,?");
        parms.add(pageStart);
        parms.add(pageSize);
        return DButils.commonQuery(Commodity.class,stringBuilder.toString(),parms.toArray());
    }

    @Override
    public int changeStatus(String id) {
        return DButils.commonUpdate("UPDATE tb_commodity set status = case status when 0 then 1 else 0 end where id = ?",id);
    }

    @Override
    public int add(Commodity commodity) {
        return DButils.commonInsert("insert into tb_commodity values(null,?,?,?,0)",
                commodity.getName(),
                commodity.getBarcode(),
                commodity.getPrice());
    }

    @Override
    public List<Commodity> queryCommodity() {
        return DButils.commonQuery(Commodity.class,"select * from tb_commodity");
    }

    @Override
    public Commodity queryCommodityByCode(String barcode) {
        return DButils.commonQuery(Commodity.class,"select name, price from tb_commodity where barcode =?",barcode).get(0);
    }

    @Override
    public int queryBarcodeExist(String barcode) {
        return DButils.commonQueryCount("select  count(*) from tb_commodity where barcode=?",barcode);
    }


}
