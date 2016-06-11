package com.time.concurrency.basic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerLock {
	
	public static void main(String[] args){
		
		DepotLock depot = new DepotLock(100);
		
		ProducerLock producer = new ProducerLock(depot);
		ConsumerLock consumer = new ConsumerLock(depot);
		
		producer.produce(10);
		producer.produce(100);
		
		consumer.consume(20);
		consumer.consume(100);
		
		producer.produce(10);
		
		
	}
}

class ProducerLock{
	
	private DepotLock depot;
	
	public ProducerLock(DepotLock depot){
		this.depot = depot;
	}
	
	public void produce(int value){
		new Thread(){
			public void run(){
				depot.produce(value);
			}
		}.start();		
	}
	
}

class ConsumerLock{
	
	private DepotLock depot;
	
	public ConsumerLock(DepotLock depot){
		this.depot = depot;
	}
	
	public void consume(int value){
		new Thread(){
			public void run(){
				depot.consume(value);
			}
		}.start();	
	}
	
}


class DepotLock{
	
	 private int capacity;
	 private int size;
	 
	 private Lock lock;
	 
	 private Condition emptyCondition;
	 private Condition fullCondition;
	 
	 
	 public DepotLock(int capacity){
		 this.capacity = capacity;
		 this.size = 0;
		 this.lock = new ReentrantLock();
		 this.emptyCondition = lock.newCondition();
		 this.fullCondition = lock.newCondition();
	 }
	
	 /**
	  * 同步生产方法
	  * @param value
	  */
	 public void produce(int value){
		 // get lock
		 lock.lock();
		 try {
			 // 要生产的数量，剩余多少数量没有生产
			 int left = value;
			 
			 if(left>0){
				 // 库存已满时,生产者等待，等待“消费者”消费产品。
				 while(size>=capacity)
					//wait();
					 // depot has full, the threads on fullCondition should wait
					fullCondition.await();
				 // 获取实际生产的数量
				 int increatment = capacity - size<left?capacity - size:left;
				 //size <= capacity
				 size = size + increatment;
				 //0<=left<=capacity
				 left = left - increatment;
				 System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n",
						 Thread.currentThread().getName(), value, left, increatment, size);
				 
				 // notify consumer
				 //notifyAll();
				 // notify all threads on emptyCondition
				 emptyCondition.signal();
				 
			 }
		 } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}finally{
			lock.unlock();
		}
		 
	 }
	 
	 /**
	  * 消费方法
	  * @param value
	  */
	 public void consume(int value){
		 // get lock
		 lock.lock();
		 try{
			 // 要消费的数量，剩余多少未消费的
			 int left = value;
			 while(left>0){
				 while(size<=0)
					 // wait for producer to produce，表示不可消费
					 //wait();
					 // all threads on emptyCondition should waits
					 emptyCondition.await();
				 // 实际可消费的数量
				 int decrease = left<size?left:size;
				 size = size - decrease;
				 left = left - decrease;
				 System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d\n", 
						 Thread.currentThread().getName(), value, left, decrease, size);
				 //notifyAll();
				 // all threads on fullCondition should go on
				 fullCondition.signal();
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
				lock.unlock();
		 } 
	 }
}
