package com.example.Recipe.log;

import com.example.Recipe.log.annotation.LogAnnotation;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class LogAnnotationBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Annotation logAnnotation = bean
				.getClass()
				.getAnnotation(LogAnnotation.class);
		if (logAnnotation != null) {
			return proxiedBean(bean);
		}
		return bean;
	}

	private Object proxiedBean(Object bean) {
		ProxyFactory proxyFactory = new ProxyFactory(bean);
		proxyFactory.addAdvice(new Interceptor());
		return proxyFactory.getProxy();
	}

	private static class Interceptor implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation methodInvocation) throws Throwable {
			final StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			try {
				return methodInvocation.proceed();
			} finally {
				stopWatch.stop();
				System.out.println("Execution of " + resolveLogMethod(methodInvocation)
						                   + "took " + stopWatch.getTotalTimeMillis()
						                   + " ms");
			}
		}
		private String resolveLogMethod(MethodInvocation invocation) {
			return invocation.getMethod().getDeclaringClass().getCanonicalName() + "#" + invocation.getMethod().getName();
		}
	}

	public LogAnnotationBeanPostProcessor() {
		System.out.println("Constructor");
	}
	@PostConstruct
	private void postConstruct() {
		System.out.println("postConstruct");
	}
}
