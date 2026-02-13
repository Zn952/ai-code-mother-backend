package com.zn.aicodemother.aop;

import com.zn.aicodemother.annotation.AuthCheck;
import com.zn.aicodemother.exception.ErrorCode;
import com.zn.aicodemother.exception.ThrowUtils;
import com.zn.aicodemother.innerservice.InnerUserService;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.model.enums.UserRoleEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @program: ai-code-mother-backend
 * @description: 权限校验AOP
 * @author: Zn
 * @create: 2026-01-19 10:12
 **/
@Aspect
@Component
@Slf4j
public class AuthInterceptor {

    /**
     * 执行拦截器方法
     * 该方法用于处理带有AuthCheck注解的方法拦截逻辑
     *
     * @param proceedingJoinPoint 连接点对象，用于获取被拦截方法的信息和执行被拦截方法
     * @param authCheck           AuthCheck注解对象，包含注解中定义的属性值
     * @return 返回被拦截方法的执行结果
     * @throws Throwable 可能抛出的异常
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint proceedingJoinPoint, AuthCheck authCheck) throws Throwable {
        // 获取当前请求对象
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取当前用户信息
        User loginUser = InnerUserService.getLoginUser(request);
        // 无需权限的
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        if (mustRoleEnum == null) {
            return proceedingJoinPoint.proceed();
        }
        // 需要权限的
        // 获取当前用户权限
        UserRoleEnum currentUserRole = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        ThrowUtils.throwIf(currentUserRole == null, ErrorCode.NO_AUTH_ERROR);
        // 判断当前用户权限是否满足要求
        ThrowUtils.throwIf((UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(currentUserRole)), ErrorCode.NO_AUTH_ERROR);
        return proceedingJoinPoint.proceed();
    }
}
