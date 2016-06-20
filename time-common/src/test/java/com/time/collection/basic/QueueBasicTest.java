package com.time.collection.basic;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

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
			String element = it.next();
			System.out.println(element);
		}
	}
	
	@Test
	public void testPriorityQueueEntity(){
		
		PriorityQueue<FIFOEntry<String>> queues = new PriorityQueue<FIFOEntry<String>>(2);
		
		FIFOEntry<String> entityF = new FIFOEntry<String>("F");
		
		FIFOEntry<String> entityS = new FIFOEntry<String>("S");
		
		FIFOEntry<String> entityA = new FIFOEntry<String>("A");
		
		queues.add(entityF);
		queues.add(entityS);
		queues.add(entityA);
		
		Iterator<FIFOEntry<String>> it = queues.iterator();
		
		while(it.hasNext()){
			FIFOEntry<String> element = it.next();
			System.out.println(element);
		}
	}
	
	
	@Test
	public void testPriorityBlockingQueueBasic(){
		
		PriorityBlockingQueue<FIFOEntry<String>> queues = new PriorityBlockingQueue<FIFOEntry<String>>(2);
		
		for(int i=0;i<Runtime.getRuntime().availableProcessors();i++){
			
			Thread runThread = new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					FIFOEntry<String> entityF = new FIFOEntry<String>("F" + Thread.currentThread());
					
					FIFOEntry<String> entityS = new FIFOEntry<String>("S" + Thread.currentThread());
					
					FIFOEntry<String> entityA = new FIFOEntry<String>("A" + Thread.currentThread());
					
					queues.add(entityF);
					queues.add(entityS);
					queues.add(entityA);
					
					printBlockingQueue(queues);
				}
			});
			
			runThread.start();
			runThread.run();
			
		}
	}
	
	public void printBlockingQueue(PriorityBlockingQueue<FIFOEntry<String>> queues){
		
		Iterator<FIFOEntry<String>> it = queues.iterator();
		
		while(it.hasNext()){
			FIFOEntry<String> element = it.next();
			System.out.println(element);
		}
	}

}

class EntryCompator implements Comparator<FIFOEntry<String>>{

	@Override
	public int compare(FIFOEntry<String> one, FIFOEntry<String> two) {
		// TODO Auto-generated method stub
		return one.compareTo(two);
	}
	
}

class FIFOEntry<E extends Comparable<? super E>> implements Comparable<FIFOEntry<E>> {

	static final AtomicLong seq = new AtomicLong(0);

	final long seqNum;

	final E entry;

	public FIFOEntry(E entry) {
		seqNum = seq.getAndIncrement();
		this.entry = entry;
	}

	public E getEntry() {
		return entry;
	}

	@Override
	public int compareTo(FIFOEntry<E> other) {
		// TODO Auto-generated method stub
		int res = entry.compareTo(other.entry);
		if (res == 0 && other.entry != this.entry)
			res = (seqNum < other.seqNum ? -1 : 1);
		return res;
	}
}
