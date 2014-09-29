package com.pandorabox.service;

import com.pandorabox.domain.User;

/**
 * @author hywang UserService 用来提供用户服务API
 */
public interface UserService {

    public int addUser(User user);

    public void removeUser(int userId);

    public void updateUser(User user);

    public User getUserByUserName(String userName);

    public User getUserByWeiboUid(int weiboUid);

    /**
     * @param weiboUid 微博用户的唯一id
     * @param screenName 微博用户的昵称
     * @param screenName 用户的微博统一URL地址
     * @return User 已经和微博用户绑定好的本站账户
     * */
    public User bindWeiboUser(int weiboUid, String screenName, String profileUrl);

    public User getUserById(int uid);

}
