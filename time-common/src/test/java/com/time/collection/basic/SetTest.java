package com.time.collection.basic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class SetTest {
	
	/**
	 * set也是collection接口下面的一个子接口，里面的元素是不允许重复的，在非并发的情况下主要有下面三个:HashSet/LinkedHashSet/TreeSet
	 * 通常情况下性能HashSet>LinkedHashSet>TreeSet,主要原因是元素的存储方式以及排序问题
	 * hashset允许null元素
	 * linkedhashset允许null元素
	 * tree set 不允许空元素出现，因为要排序
	 */
	
	@Test
	public void testhashSetBasic(){
		//HastSet主要使用hash table存储元素，里面会计算元素的hash值，会做元素重复的计算，里面的元素是无序的，可以允许null值
		HashSet<String> sets = new HashSet<String>();
		Assert.assertNotEquals("should not be null", null,sets);
		
		//允许存放null元素
		sets.add(null);
		Assert.assertEquals("should be 1", 1, sets.size());
		
	}
	
	@Test
	public void testTreeSetsBasic(){
		// treeset元素是有序的，使用red-black tree的方式存储元素，如果是对象，需要实现比较接口
		TreeSet<Integer> sets = new TreeSet<Integer>();
		//sets.add(null);
		sets.add(45);
		sets.add(34);
		sets.add(199);
		
		Assert.assertEquals("should be 3", 3, sets.size());
		
		Iterator<Integer> it = sets.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	@Test
	public void testLinkedHashSetBasic(){
		//linked hash set 使用的是linkedList(双线链表)的方式存储,在元素的add/remove方面使用较多
		//元素也是无序的
		//允许空元素存在
		LinkedHashSet<Integer> sets = new LinkedHashSet<Integer>();
		Assert.assertNotEquals("should not be null", null,sets);
		
		sets.add(null);
		Assert.assertEquals("should be 1", 1, sets.size());
		
		// 元素是无序的
		sets.add(3);
		sets.add(4);
		sets.add(2);
		
		Assert.assertEquals("should be 4", 4, sets.size());
		
		Iterator<Integer> it = sets.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		System.out.println("====================");
		
		// add性能比较好
		sets.add(6);
		Assert.assertEquals("should be 5", 5, sets.size());
		
		// remove性能比较好
		sets.remove(4);
		Assert.assertEquals("should be 4", 4, sets.size());
		
		Iterator<Integer> ito = sets.iterator();
		while(ito.hasNext()){
			System.out.println(ito.next());
		}
	}

}
