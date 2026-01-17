package com.zn.aicodemother.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import com.zn.aicodemother.model.entity.User;

/**
 *  映射层。
 *
 * @author Zn
 * @since 2026-01-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
