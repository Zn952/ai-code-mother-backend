package com.zn.aicodemother.service.impl;

import com.zn.aicodemother.innerservice.InnerUserService;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.model.vo.UserVO;
import com.zn.aicodemother.service.UserService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @program: ai-code-mother-backend-microservice
 * @description: 内部服务实现类
 * @author: Zn
 * @create: 2026-02-19 20:36
 **/
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserService userService;

    @Override
    public List<User> listByIds(Collection<? extends Serializable> ids) {
        return userService.listByIds(ids);
    }

    @Override
    public User getById(Serializable id) {
        return userService.getById(id);
    }

    @Override
    public UserVO getUserVO(User user) {
        return userService.getUserVO(user);
    }
}

