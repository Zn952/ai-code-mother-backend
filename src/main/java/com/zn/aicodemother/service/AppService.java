package com.zn.aicodemother.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.zn.aicodemother.model.dto.app.AppAddRequest;
import com.zn.aicodemother.model.dto.app.AppQueryRequest;
import com.zn.aicodemother.model.dto.app.AppUpdateRequest;
import com.zn.aicodemother.model.entity.App;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.model.vo.AppVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author Zn
 * @since 2026-01-17
 */
public interface AppService extends IService<App> {

    /**
     * 创建应用
     *
     * @param appAddRequest 应用创建请求
     * @param loginUser     登录用户
     * @return 应用ID
     */
    Long createApp(AppAddRequest appAddRequest, User loginUser);

    /**
     * 用户更新自己的应用（只支持修改应用名称）
     *
     * @param appUpdateRequest 应用更新请求
     * @param loginUser        登录用户
     * @return 更新结果
     */
    Boolean updateApp(AppUpdateRequest appUpdateRequest, User loginUser);

    /**
     * 用户删除自己的应用
     *
     * @param id        应用ID
     * @param loginUser 登录用户
     * @return 删除结果
     */
    Boolean deleteApp(Long id, User loginUser);

    /**
     * 根据ID获取应用详情
     *
     * @param id 应用ID
     * @return 应用详情
     */
    AppVO getAppVOById(Long id);

    /**
     * 分页查询用户自己的应用列表（支持根据名称查询，每页最多 20 个）
     *
     * @param appQueryRequest 查询请求
     * @param loginUser       登录用户
     * @return 分页结果
     */
    Page<AppVO> listMyAppVOByPage(AppQueryRequest appQueryRequest, User loginUser);

    /**
     * 分页查询精选的应用列表（支持根据名称查询，每页最多 20 个）
     *
     * @param appQueryRequest 查询请求
     * @return 分页结果
     */
    Page<AppVO> listFeaturedAppVOByPage(AppQueryRequest appQueryRequest);

    /**
     * 管理员分页查询应用列表（支持根据除时间外的任何字段查询，每页数量不限）
     *
     * @param appQueryRequest 查询请求
     * @return 分页结果
     */
    Page<AppVO> listAppVOByPage(AppQueryRequest appQueryRequest);

    /**
     * 根据应用实体对象获取对应的AppVO对象
     *
     * @param app 应用实体对象
     * @return AppVO对象
     */
    AppVO getAppVO(App app);

    /**
     * 根据应用列表获取应用视图对象列表
     *
     * @param appList 应用实体列表
     * @return AppVO视图对象列表
     */
    List<AppVO> getAppVOList(List<App> appList);

    /**
     * 根据应用查询请求条件构建查询包装器（用户查询）
     *
     * @param appQueryRequest 应用查询请求对象
     * @return QueryWrapper
     */
    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

}
