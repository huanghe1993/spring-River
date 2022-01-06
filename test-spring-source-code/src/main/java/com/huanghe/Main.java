package com.huanghe;

import com.huanghe.bean.User;
import com.huanghe.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * https://blog.csdn.net/yy_diego/article/details/116381958
 */
public class Main {
	public static void main(String[] args){
		// 创建 context ，并向其注册一个 User bean。
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		// 将 User bean 拿出来。
		User user =  context.getBean(User.class);
		// 查看内容。
		System.out.println("name: "+ user.getName());
		System.out.println("age: "+ user.getAge());
	}
}
