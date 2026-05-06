package com.study.shop.dao.mapper;

import com.study.shop.dto.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("select ifnull(max(orderid),0) from orders")
    int maxid();

    @Insert("insert into orders (orderid, userid, orderdate) values (#{orderid}, #{userid}, now())")
    void insert(Order order);

    @Select("select * from orders where userid=#{value}")
    List<Order> orderList(String userid);
}
