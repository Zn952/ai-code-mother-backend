package com.zn.aicodemother.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.zn.aicodemother.model.dto.chatHistory.ChatHistoryQueryRequest;
import com.zn.aicodemother.model.entity.ChatHistory;
import com.zn.aicodemother.model.entity.User;

import java.time.LocalDateTime;

/**
 * 对话历史服务层。
 *
 * @author Zn
 * @since 2026-01-17
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    /**
     * 保存对话信息
     *
     * @param appId       应用 id
     * @param userId      用户 id
     * @param messageType 消息类型
     * @param message     消息内容
     * @return 是否保存成功
     */
    Boolean addChatMessage(Long appId, Long userId, String messageType, String message);

    /**
     * 按应用删除该应用下所有对话历史（应用删除时关联删除）
     *
     * @param appId 应用 id
     * @return 是否删除成功
     */
    Boolean removeByAppId(Long appId);

    /**
     * 分页查询应用聊天记录
     *
     * @param appId          应用ID，用于指定要查询哪个应用的聊天记录
     * @param pageSize       每页大小，控制返回结果的数量
     * @param lastCreateTime 最后一次创建时间，用于分页查询，通常作为游标使用
     * @param loginUser      登录用户信息，用于权限验证
     * @return 返回一个包含聊天记录的分页对象，其中包含当前页的聊天记录列表
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize, LocalDateTime lastCreateTime, User loginUser);


    /**
     * 根据聊天历史查询请求参数构造查询条件包装器
     *
     * @param chatHistoryQueryRequest 聊天历史查询请求对象，包含查询条件参数
     * @return QueryWrapper 返回一个包含查询条件的QueryWrapper对象，用于数据库查询操作
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);
}
