package com.study.shop.service;

import com.study.shop.dao.UserDao;
import com.study.shop.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao dao;

    public void insUser(User user) {
        dao.insert(user);
    }

    public User getUser(String userid) {
        return dao.selectOne(userid);
    }

    public void updUser(User user) {
        dao.update(user);
    }

    public void delUser(String userid) {
        dao.delete(userid);
    }

    public void pwUser(String userid, String pass) {
        dao.update(userid, pass);
    }

    public String searchUser(User user, String url) {
        return dao.search(user, url);
    }

    public List<User> listUser() { // 모든 사용자 데이터 리턴
        return dao.list();
    }

    // 오버로딩 메서드
    public List<User> listUser(String[] idchks) { // idchks 값에 저장된 userid 값만 목록으로 리턴
        return dao.list(idchks);
    }
}
