package com.zn.aicodemother.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zn.aicodemother.model.entity.App;
import com.zn.aicodemother.mapper.AppMapper;
import com.zn.aicodemother.service.AppService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Zn
 * @since 2026-01-17
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{

}
