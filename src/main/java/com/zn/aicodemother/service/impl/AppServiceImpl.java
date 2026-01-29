package com.zn.aicodemother.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zn.aicodemother.constant.AppConstant;
import com.zn.aicodemother.constant.UserConstant;
import com.zn.aicodemother.exception.ErrorCode;
import com.zn.aicodemother.exception.ThrowUtils;
import com.zn.aicodemother.mapper.AppMapper;
import com.zn.aicodemother.model.dto.app.AppAddRequest;
import com.zn.aicodemother.model.dto.app.AppQueryRequest;
import com.zn.aicodemother.model.dto.app.AppUpdateRequest;
import com.zn.aicodemother.model.entity.App;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.model.enums.CodeGenTypeEnum;
import com.zn.aicodemother.model.vo.AppVO;
import com.zn.aicodemother.model.vo.UserVO;
import com.zn.aicodemother.service.AppService;
import com.zn.aicodemother.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 服务层实现。
 *
 * @author Zn
 * @since 2026-01-17
 */
@Service
@Slf4j
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Resource
    private UserService userService;

    /**
     * 创建应用
     *
     * @param appAddRequest 应用创建请求
     * @param loginUser     登录用户
     * @return 应用ID
     */
    @Override
    public Long createApp(AppAddRequest appAddRequest, User loginUser) {
        ThrowUtils.throwIf(appAddRequest == null, ErrorCode.PARAMS_ERROR);
        String initPrompt = appAddRequest.getInitPrompt();
        // 校验参数
        ThrowUtils.throwIf(CharSequenceUtil.isBlank(initPrompt), ErrorCode.PARAMS_ERROR, "初始化提示词不能为空");
        // 构造入库对象
        App app = new App();
        BeanUtil.copyProperties(appAddRequest, app);
        app.setUserId(loginUser.getId());
        // 应用名称暂时为 initPrompt 前 12 位
        app.setAppName(initPrompt.substring(0, Math.min(initPrompt.length(), 12)));
        // 暂时设置为多文件生成
        app.setCodeGenType(CodeGenTypeEnum.MULTI_FILE.getValue());
        // 插入数据库
        boolean result = this.save(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        log.info("用户ID：{}，创建应用成功，ID: {}, 类型: {}", app.getUserId(), app.getId(), CodeGenTypeEnum.MULTI_FILE.getValue());
        return app.getId();
    }

    /**
     * 用户更新自己的应用（只支持修改应用名称）
     *
     * @param appUpdateRequest 应用更新请求
     * @param loginUser        登录用户
     * @return 更新结果
     */
    @Override
    public Boolean updateApp(AppUpdateRequest appUpdateRequest, User loginUser) {
        ThrowUtils.throwIf(appUpdateRequest == null, ErrorCode.PARAMS_ERROR);
        Long id = appUpdateRequest.getId();
        String appName = appUpdateRequest.getAppName();
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(CharSequenceUtil.isBlank(appName), ErrorCode.PARAMS_ERROR, "应用名称不能为空");
        // 查询应用
        App app = this.getById(id);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        // 校验权限：只能修改自己的应用
        ThrowUtils.throwIf(!app.getUserId().equals(loginUser.getId()), ErrorCode.NO_AUTH_ERROR, "只能修改自己的应用");
        // 更新应用名称
        app.setAppName(appName);
        app.setEditTime(LocalDateTime.now());
        boolean result = this.updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return true;
    }

    /**
     * 用户删除自己的应用
     *
     * @param id        应用ID
     * @param loginUser 登录用户
     * @return 删除结果
     */
    @Override
    public Boolean deleteApp(Long id, User loginUser) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询应用
        App app = this.getById(id);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        // 校验权限：仅本人或管理员可删除
        ThrowUtils.throwIf(!app.getUserId().equals(loginUser.getId()) && !UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole()),
                ErrorCode.NO_AUTH_ERROR,
                "只能删除自己的应用");
        boolean result = this.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return true;
    }

    /**
     * 根据ID获取应用详情
     *
     * @param id 应用ID
     * @return 应用详情
     */
    @Override
    public AppVO getAppVOById(Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        App app = this.getById(id);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        return this.getAppVO(app);
    }

    /**
     * 分页查询用户自己的应用列表（支持根据名称查询，每页最多 20 个）
     *
     * @param appQueryRequest 查询请求
     * @param loginUser        登录用户
     * @return 分页结果
     */
    @Override
    public Page<AppVO> listMyAppVOByPage(AppQueryRequest appQueryRequest, User loginUser) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 只查询当前用户的应用
        appQueryRequest.setUserId(loginUser.getId());
        return this.listAppVOByPage(appQueryRequest);
    }

    /**
     * 分页查询精选的应用列表（支持根据名称查询，每页最多 20 个）
     *
     * @param appQueryRequest 查询请求
     * @return 分页结果
     */
    @Override
    public Page<AppVO> listFeaturedAppVOByPage(AppQueryRequest appQueryRequest) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        appQueryRequest.setPriority(AppConstant.GOOD_APP_PRIORITY);
        return this.listAppVOByPage(appQueryRequest);
    }

    /**
     * 分页查询应用列表
     *
     * @param appQueryRequest 查询请求
     * @return 分页结果
     */
    @Override
    public Page<AppVO> listAppVOByPage(AppQueryRequest appQueryRequest) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 设置分页参数，每页最多 20 个
        long pageNum = appQueryRequest.getPageNum();
        long pageSize = appQueryRequest.getPageSize();
        pageNum = Math.max(pageNum, 1);
        pageSize = pageSize < 1 ? 10 : Math.min(pageSize, 20);
        QueryWrapper queryWrapper = this.getQueryWrapper(appQueryRequest);
        // 查询
        Page<App> appPage = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        // 转换为VO
        Page<AppVO> appVOPage = new Page<>(pageNum, pageSize, appPage.getTotalRow());
        List<AppVO> appVOList = this.getAppVOList(appPage.getRecords());
        appVOPage.setRecords(appVOList);
        return appVOPage;
    }

    /**
     * 根据应用实体对象获取对应的AppVO对象
     *
     * @param app 应用实体对象
     * @return AppVO对象
     */
    @Override
    public AppVO getAppVO(App app) {
        if (app == null) {
            return null;
        }
        AppVO appVO = new AppVO();
        BeanUtils.copyProperties(app, appVO);
        // 关联查询用户信息
        Long userId = app.getUserId();
        if (userId != null) {
            User user = userService.getById(userId);
            UserVO userVO = userService.getUserVO(user);
            appVO.setUser(userVO);
        }
        return appVO;
    }

    /**
     * 根据应用列表获取应用视图对象列表
     *
     * @param appList 应用实体列表
     * @return AppVO视图对象列表
     */
    @Override
    public List<AppVO> getAppVOList(List<App> appList) {
        ThrowUtils.throwIf(CollUtil.isEmpty(appList), ErrorCode.PARAMS_ERROR, "应用列表为空");
        // 批量获取用户信息，避免 N+1 查询问题
        Set<Long> userIds = appList.stream()
                .map(App::getUserId)
                .collect(Collectors.toSet());
        Map<Long, UserVO> userVOMap = userService.listByIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, userService::getUserVO));
        return appList.stream().map(app -> {
            AppVO appVO = getAppVO(app);
            UserVO userVO = userVOMap.get(app.getUserId());
            appVO.setUser(userVO);
            return appVO;
        }).collect(Collectors.toList());
    }

    /**
     * 根据应用查询请求条件构建查询包装器（用户查询）
     *
     * @param appQueryRequest 应用查询请求对象
     * @return QueryWrapper
     */
    @Override
    public QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Long id = appQueryRequest.getId();
        String appName = appQueryRequest.getAppName();
        String cover = appQueryRequest.getCover();
        String initPrompt = appQueryRequest.getInitPrompt();
        String codeGenType = appQueryRequest.getCodeGenType();
        String deployKey = appQueryRequest.getDeployKey();
        Integer priority = appQueryRequest.getPriority();
        Long userId = appQueryRequest.getUserId();
        String sortField = appQueryRequest.getSortField();
        String sortOrder = appQueryRequest.getSortOrder();
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.eq("id", id)
                .like("appName", appName)
                .like("cover", cover)
                .like("initPrompt", initPrompt)
                .eq("codeGenType", codeGenType)
                .like("deployKey", deployKey)
                .eq("priority", priority)
                .eq("userId", userId);
        if (CharSequenceUtil.isNotBlank(sortField)) {
            queryWrapper.orderBy(sortField, "ascend".equals(sortOrder));
        } else {
            // 默认按创建时间降序
            queryWrapper.orderBy("createTime", false);
        }
        return queryWrapper;
    }
}
