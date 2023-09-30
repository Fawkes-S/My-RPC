package sjc.rpc.cousumer.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import sjc.rpc.cousumer.annotation.RemoteInvoke;
import sjc.rpc.cousumer.core.NettyClient;
import sjc.rpc.cousumer.param.ClientRequest;
import sjc.rpc.cousumer.param.Response;

@Component
public class InvokeProxy implements BeanPostProcessor {
	// 在CGLIB中，Enhancer是用于创建代理类的类
	public static Enhancer enhancer = new Enhancer();

	public Object postProcessAfterInitialization(Object bean, String arg1) throws BeansException {
		return bean;
	}
	
	
	//对属性的所有方法和属性类型放入到HashMap中
	private void putMethodClass(HashMap<Method, Class> methodmap, Field field) {
		Method[] methods = field.getType().getDeclaredMethods();
		for(Method method : methods){
			methodmap.put(method, field.getType());
		}
		
	}

	public Object postProcessBeforeInitialization(Object bean, String arg1) throws BeansException {
//		System.out.println(bean.getClass().getName());
		Field[] fields = bean.getClass().getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(RemoteInvoke.class)){
				field.setAccessible(true);
				
//				final HashMap<Method, Class> methodmap = new HashMap<Method, Class>();
//				putMethodClass(methodmap,field);
//				Enhancer enhancer = new Enhancer();
				// 对那些接口进行动态代理
				enhancer.setInterfaces(new Class[]{field.getType()});
				// MethodInterceptor是CGLIB提供的接口，用于拦截代理对象上的方法调用
				enhancer.setCallback(new MethodInterceptor() {
					
					public Object intercept(Object instance, Method method, Object[] args, MethodProxy proxy) throws Throwable {
						// 采用netty客户端去调用服务器
						ClientRequest clientRequest = new ClientRequest();
						clientRequest.setContent(args[0]);
//						String command= methodmap.get(method).getName()+"."+method.getName();
						String command = method.getName();//修改
//						System.out.println("InvokeProxy中的Command是:"+command);
						clientRequest.setCommand(command);
						
						Response response = NettyClient.send(clientRequest);
						return response;
					}
				});
				try {
					// 使用enhancer.create()来创建代理对象，并将代理对象设置到bean的相应字段中。
					// 这样，原始的bean对象中的被@RemoteInvoke注解标记的字段将被替换为代理对象。
					field.set(bean, enhancer.create());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return bean;
	}

}
