package cn.ron.travel.dao.impl;

import cn.ron.travel.dao.SellerDao;
import cn.ron.travel.domain.RouteImg;
import cn.ron.travel.domain.Seller;
import cn.ron.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Seller findById(int id) {
        String sql = "select * from tab_seller where sid = ? ";

        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),id);
    }
}
