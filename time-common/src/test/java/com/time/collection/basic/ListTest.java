package com.time.collection.basic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

public class ListTest {
	
	/**
	 * List: 集合的一个子接口，同时下面还有 ArrayList/LinkedList/Vector,List的元素是允许重复的
	 */
	
	@Test
	public void listBasicTest(){
		//ArrayList: 以数组的方式存储，元素是无序的，空间可以自动增长，初始化是10，每次自动增长50%,在get性能上优于LinkedList/Vector
		//可以在初始化的时候指定其大小，可以使用iterator方式访问数组的元素
		//ArrayList通过移位来实现扩容。在旧的容量之上左移一位来>>1实现。也就是约等于原来的50%
		ArrayList<Integer> intLists = new ArrayList<Integer>(10);
		intLists.add(1);
		intLists.add(2);
		Assert.assertEquals("intLists size should be 2", 2 ,intLists.size());
		intLists.remove(0);
		Assert.assertEquals("intLists size should be 1", 1 ,intLists.size());
		intLists.remove(0);
		Assert.assertEquals("intLists size should be 0", 0 ,intLists.size());
	}
	
	@Test
	public void linkedListBasicTest(){
		//linkedList: 以双向链表的方式存储，元素是无序的，在remove，add方面性能优于ArrayList/Vector
		//同时linkedList实现了也实现了queue的接口，具有queue相关的方法
		LinkedList<String> linkedList = new LinkedList<String>();
		linkedList.add("one");
		linkedList.add("two");
		linkedList.add("three");
		Assert.assertEquals("linkedList size should be 3", 3 ,linkedList.size());
		
		//使用队列的方法取出第三个元素
		Assert.assertEquals("linkedList 1th should be three", "one" ,linkedList.pop());
		Assert.assertEquals("linkedList size should be 2", 2 ,linkedList.size());
		
		//同时具有list相关的方法，remove等
		Assert.assertEquals("linkedList 2th should be two", "two" ,linkedList.get(0));
		linkedList.remove(0);
		Assert.assertEquals("linkedList size should be 1", 1 ,linkedList.size());
	}
	
	@Test
	public void testVectorBasicTest(){
		//vector与ArrayList相似，只是里面的元素使用synchronized关键字，是线程安全的
		Vector<String> vectors = new Vector<String>();
		vectors.add("one");
		Assert.assertEquals("linkedList size should be 1", 1 ,vectors.size());
	}

}
