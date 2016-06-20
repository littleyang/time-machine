package com.time.collection.basic;

import java.util.Iterator;
import java.util.PriorityQueue;

import org.junit.Test;

public class QueueBasicTest {
	
	@Test
	public void testPriorityQueueTests(){
		
		PriorityQueue<String> queues = new PriorityQueue<String>(2);
		
		queues.add("S");
		
		queues.add("F");
		
		queues.add("A");
		
		Iterator<String> it = queues.iterator();
		
		while(it.hasNext()){
			String element = (String) it.next();
			System.out.println(element);
		}
		
		
	}

}
