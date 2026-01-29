package com.zn.aicodemother.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import com.zn.aicodemother.model.entity.App;

/**
 *  映射层。
 *
 * @author Zn
 * @since 2026-01-17
 */
@Mapper
public interface AppMapper extends BaseMapper<App> {

}
