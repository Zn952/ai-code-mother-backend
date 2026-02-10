package com.zn.aicodemother.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

/**
 * 对话历史消息类型枚举。
 *
 * @author Zn
 * @since 2026-01-30
 */
@Getter
public enum ChatHistoryMessageTypeEnum {

    USER("用户消息", "user"),
    AI("AI 消息", "ai");

    private final String text;
    private final String value;

    ChatHistoryMessageTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 枚举值的 value
     * @return 枚举值，未找到返回 null
     */
    public static ChatHistoryMessageTypeEnum getEnumByValue(String value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (ChatHistoryMessageTypeEnum anEnum : ChatHistoryMessageTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
