package com.time.reflection.basic;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionBasicTest {
	
	@Test
	public void testReflectionBasic() throws NoSuchMethodException, SecurityException, IllegalAccessException, 
		IllegalArgumentException, InvocationTargetException, NoSuchFieldException{
		
		// according reflection get class name
		Foo foo = new Foo();
		
		// class name
		Assert.assertEquals("class name should be com.time.reflection.basic.Foo", 
				"com.time.reflection.basic.Foo", foo.getClass().getName());
		
		// get reflection method 
		Method method;
		Assert.assertEquals("Foo class methods size ", 3 , foo.getClass().getDeclaredMethods().length);
		
		// get say hello method
		method = foo.getClass().getMethod("sayHello", String.class);
		method.invoke(foo,"zy");
		
		// get reflection proper
		Field field;
		Assert.assertEquals("Foo class methods size ", 1 , foo.getClass().getDeclaredFields().length);
		
		field = foo.getClass().getDeclaredField("name");
		Assert.assertEquals("field name should be name ", "name", field.getName());
		
		// reflection create instance
		Class<?> ins = null;
		try {
			ins = Class.forName("com.time.reflection.basic.Foo");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//创建一个实例
		Foo fTemp = null;
		try {
			fTemp = (Foo) ins.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// call say hello method 
		Assert.assertNotNull("should not be null when call method", fTemp.sayHello("zy"));
		
		// get class constructor method to create an instance
		Foo f1 = null;
		Foo f2 = null;
		
		Constructor<?>[] constructors = ins.getConstructors();
		
		//创建 Foo 对象
		try {
			f1 = (Foo) constructors[0].newInstance();
			f2 = (Foo) constructors[1].newInstance("littleyang");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 调用
		f1.sayHello("zy");
		f2.sayHello("littleyang");
	}
}

class Foo{
	
	private String name ;
	
	public Foo(){
		
	}
	
	public Foo(String name){
		this.name = name;
	}
	
	public String sayHello(String name){
		return name + " hello";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
