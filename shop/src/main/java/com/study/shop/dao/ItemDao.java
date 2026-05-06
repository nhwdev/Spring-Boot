package com.study.shop.dao;

import com.study.shop.dao.mapper.ItemMapper;
import com.study.shop.dto.Item;
import jakarta.validation.Valid;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Repository 영속성 객체. DB와 연결된 객체
@Repository // @Component + Repository 기능
public class ItemDao {
    private final Class<ItemMapper> cls = ItemMapper.class;
    @Autowired
    private SqlSessionTemplate template;
    private Map<String, Object> param = new HashMap<>();

    public List<Item> list() {
        return template.getMapper(cls).selectList();
    }

    public Item selectOne(Integer id) {
        return template.getMapper(cls).selectOne(id);
    }

    public void delete(Integer id) {
        template.getMapper(cls).delete(id);
    }

    public void insert(Item item) {
        template.getMapper(cls).insert(item);
    }

    public void update(@Valid Item item) {
        template.getMapper(cls).update(item);
    }

    public int maxId() {
        return template.getMapper(cls).maxId();
    }

}
