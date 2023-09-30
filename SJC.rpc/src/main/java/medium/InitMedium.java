package medium;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import annotation.Remote;
import annotation.RemoteInvoke;
import controller.UserController;

@Component
public class InitMedium implements BeanPostProcessor{
	//中介者
	//你可以使用BeanPostProcessor来检查类上是否有Controller注解，并将带有该注解的类的所有方法保存起
	//来以供后续请求处理。这不涉及动态代理，而是通过反射和元数据（注解）来实现的。
	@Override
	public Object postProcessAfterInitialization(Object bean, String arg1) throws BeansException {
		if(bean.getClass().isAnnotationPresent(Remote.class)){
			Method[] methods = bean.getClass().getDeclaredMethods();//客户端那里用的是接口，所以getSuperClass
			for(Method m : methods){
//				String key = bean.getClass().getInterfaces()[0].getName()+"."+m.getName();
				String key = m.getName();//修改
				HashMap<String, BeanMethod> map = Medium.mediamap;
				BeanMethod beanMethod = new BeanMethod();
				beanMethod.setBean(bean);
				beanMethod.setMethod(m);
				map.put(key,beanMethod);
				System.out.println(key);
			}
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String arg1) throws BeansException {
		
		
		return bean;
	}

}
