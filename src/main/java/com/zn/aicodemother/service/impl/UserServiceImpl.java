package com.zn.aicodemother.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.mapper.UserMapper;
import com.zn.aicodemother.service.UserService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Zn
 * @since 2026-01-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService{

}
