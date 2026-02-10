package com.zn.aicodemother.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

/**
 * @program: ai-code-mother-backend
 * @description: 用户角色枚举值
 * @author: Zn
 * @create: 2026-01-17 15:56
 **/
@Getter
public enum UserRoleEnum {

    ADMIN("管理员", "admin"),
    USER("普通用户", "user");

    private final String text;
    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据给定的值获取对应的枚举实例
     *
     * @param value 枚举值的字符串表示
     * @return 如果找到匹配的枚举则返回对应的枚举实例，否则返回null
     */
    public static UserRoleEnum getEnumByValue(String value) {
        // 检查输入值是否为空
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        // 遍历所有枚举实例
        for (UserRoleEnum roleEnum : UserRoleEnum.values()) {
            // 比较当前枚举实例的值与输入值是否相等
            if (roleEnum.getValue().equals(value))
                return roleEnum;
        }
        // 未找到匹配的枚举实例，返回null
        return null;
    }
}
