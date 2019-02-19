package com.ssm.web.thread;

import com.ssm.common.po.User;

public class UserThreadLocal {
	
	//如果保存数据有多个,则使用Map集合
	private static ThreadLocal<User> userThread = new ThreadLocal<User>();
	
	public static void set(User user){
		
		userThread.set(user);
	}
	
	public static User get(){
		
		return userThread.get();
	}
	
	//线程销毁方法
	public static void remove(){
		
		userThread.remove();
	}
}
