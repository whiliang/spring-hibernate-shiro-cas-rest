package com.ums.umsAdmin.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SystemLogAspect {

	@AfterReturning("@annotation(sysLog)")
	public void insertLog(JoinPoint joinPoint, SystemLog sysLog) {
		System.out.println("System log: " + sysLog.module() + "-"
				+ sysLog.action());
	}
}
