package com.pandorabox.dao;

import com.pandorabox.domain.User;

public interface UserDao extends GenericDataAccessor<User, Integer> {

    public User getUserByUserName(String userName);

    public User getUserByWeiboUid(int weiboUid);

}
