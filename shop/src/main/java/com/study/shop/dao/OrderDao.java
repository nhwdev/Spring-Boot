package com.study.shop.dao;

import com.study.shop.dao.mapper.OrderMapper;
import com.study.shop.dto.Order;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {
    @Autowired
    private SqlSessionTemplate template;
    private Class<OrderMapper> cls = OrderMapper.class;

    public int getMaxOrderId() {

        return template.getMapper(cls).maxid();
    }

    public void insert(Order order) {
        template.getMapper(cls).insert(order);
    }

    public List<Order> orderList(String userid) {
        return template.getMapper(cls).orderList(userid);
    }
}
