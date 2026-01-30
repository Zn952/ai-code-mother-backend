package com.zn.aicodemother.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zn.aicodemother.model.entity.ChatHistory;
import com.zn.aicodemother.mapper.ChatHistoryMapper;
import com.zn.aicodemother.service.ChatHistoryService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Zn
 * @since 2026-01-17
 */
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory>  implements ChatHistoryService{

}
