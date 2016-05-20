package com.time.collection.basic;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class MapTest {
	
	/**
	 * Map可以看成是小型的Key-Value内存数据库
	 * Map是与Collection并行的一个接口，其具体的实现主要有:Hashtable,linkedHashMap,HashMap,TreeMap
	 * HashMap使用hash table的方式存储元素，不允许重复的Key值，且是无序的。可以与HashSet进行比较其差异。
	 * linkedhashmap使用链表的方式存储元素，与linkedhashset可以比较不同。在元素的insert方面性能较好
	 * treemap使用red－black tree的方式存储元素，不允许null值。且里面的元素按照key值有序的。性能比其他map差，因为要维持其元素的顺序。
	 * Hashtable与其他相比，主要是线程安全的，使用synchronized修饰的。
	 */
	
	@Test
	public void testHashMapBasic(){
		
		HashMap<Dog,Integer> dogMaps = new HashMap<Dog,Integer>();
		
		Dog d1 = new Dog("d1");
		Dog d2 = new Dog("d2");
		Dog d3 = new Dog("d3");
		Dog d4 = new Dog("d4");
		Dog d5 = new Dog("d5");
		
		dogMaps.put(d1, 1);
		dogMaps.put(d2, 2);
		dogMaps.put(d3, 3);
		dogMaps.put(d4, 4);
		dogMaps.put(d5, 5);
		
		Assert.assertEquals("map size should be equal 5", 5, dogMaps.size());
		
		//调用iterator接口的方式直接读取出每个具体的元素
		for(Entry<Dog,Integer> entity : dogMaps.entrySet()){
			System.out.println(entity.getKey().toString() +" value: "+ entity.getValue());
		}
	}
	
	@Test
	public void testTreeMapBasic(){
		//需要实现排序接口
		
		TreeMap<Cat,Integer> treeMaps = new TreeMap<Cat,Integer>();
		
		Cat c1 = new Cat("c1");
		Cat c2 = new Cat("c2");
		Cat c3 = new Cat("c3");
		Cat c4 = new Cat("c4");
		Cat c5 = new Cat("c5");
		
		treeMaps.put(c2, 2);
		treeMaps.put(c4, 4);
		treeMaps.put(c5, 5);
		treeMaps.put(c1, 1);
		treeMaps.put(c3, 3);
		
		Assert.assertEquals("map size should be equal 5", 5, treeMaps.size());
		
		//有序的输出map里面的元素
		
		for(Entry<Cat,Integer> entity : treeMaps.entrySet()){
			System.out.println(entity.getKey().toString() +" value: "+ entity.getValue());
		}
	}
	
	@Test
	public void testLinkedHashMapBasic(){
		
	}
}

class Cat implements Comparable<Cat>{
	String name;
	
	public Cat(String name){
		this.name = name;
	}
	
	public String toString(){
		return name + "_dog";
	}

	@Override
	public int compareTo(Cat o) {
		// TODO Auto-generated method stub
		return this.name.hashCode() - o.name.hashCode();
	}
}

class Dog{
	String name;
	
	public Dog(String name){
		this.name = name;
	}
	
	public String toString(){
		return name + "_dog";
	}
}
