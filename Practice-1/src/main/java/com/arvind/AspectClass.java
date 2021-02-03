package com.arvind;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AspectClass 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AspectClass.class);
	
	
	@Before(value = "execution( public * com.arvind.controller.RegistrationController.*(..) )")
	public void before(JoinPoint joinPoint)
	{
		System.out.println();
		LOGGER.info("<----------------Before Method Executing-------------------->");
		LOGGER.info("Request : " + joinPoint.getSignature());
		 Object[] signatureArgs = joinPoint.getArgs();
		   for (Object signatureArg: signatureArgs) 
		   {
		      LOGGER.info("Arg: " + signatureArg);
		   }
		System.out.println();
	}
	
	@AfterReturning(value = "execution( public * com.arvind.controller.RegistrationController.*(..))" , returning = "returnValue")
	public void after(JoinPoint joinPoint , Object returnValue)
	{
		System.out.println();
		LOGGER.info("<----------------After Returning Method Executing-------------------->");
//		LOGGER.info("After : " + joinPoint.getSignature() + " " + joinPoint.getArgs().toString());
		LOGGER.info("Response : " + joinPoint.getSignature());
		LOGGER.info("returnValue : " + returnValue);
		
		System.out.println();
	}
	
}
