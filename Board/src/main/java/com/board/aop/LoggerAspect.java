package com.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Advice설정 ( @Around ) : 타겟메서드의 실행 이전과 이후에 처리 할 코드 정의
	// execution으로 포인트컷 지정
	@Around("execution(* com.board..controller.*Controller.*(..)) or execution(* com.board..service.*Impl.*(..)) or execution(* com.board..mapper.*Mapper.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = "";
		// getSignature는 함수에 대한 정보를 가지고 있고 getDeclaringTypeName는 그 함수고 정의되어있는 타입 타입을 말한다.
		String name = joinPoint.getSignature().getDeclaringTypeName();

		if (name.contains("Controller") == true) {
			type = "Controller ===> ";

		} else if (name.contains("Service") == true) {
			type = "ServiceImpl ===> ";

		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===> ";
		}

		logger.debug("어라운드전 :"+type + name + "." + joinPoint.getSignature().getName() + "()");
		Object result =  joinPoint.proceed();
		logger.debug("어라운드후 :"+type + name + "." + joinPoint.getSignature().getName() + "()");
		return result;
	}

}