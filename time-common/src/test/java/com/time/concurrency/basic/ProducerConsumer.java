package com.time.concurrency.basic;

public class ProducerConsumer {
	
	public static void main(String[] args){
		
		Depot depot = new Depot(100);
		
		Producer producer = new Producer(depot);
		Consumer consumer = new Consumer(depot);
		
		producer.produce(10);
		producer.produce(100);
		
		consumer.consume(20);
		consumer.consume(100);
		
		producer.produce(10);
		
		
	}
}

class Producer{
	
	private Depot depot;
	
	public Producer(Depot depot){
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

class Consumer{
	
	private Depot depot;
	
	public Consumer(Depot depot){
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


class Depot{
	
	 private int capacity;
	 
	 private int size;
	 
	 public Depot(int capacity){
		 this.capacity = capacity;
		 this.size = 0;
	 }
	
	 /**
	  * 同步生产方法
	  * @param value
	  */
	 public synchronized void produce(int value){
		 
		 try {
			 // 要生产的数量，剩余多少数量没有生产
			 int left = value;
			 
			 if(left>0){
				 // 库存已满时,生产者等待，等待“消费者”消费产品。
				 while(size>=capacity)
					wait();
				 // 获取实际生产的数量
				 int increatment = capacity - size<left?capacity - size:left;
				 //size <= capacity
				 size = size + increatment;
				 //0<=left<=capacity
				 left = left - increatment;
				 System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n",
						 Thread.currentThread().getName(), value, left, increatment, size);
				 
				 // notify consumer
				 notifyAll();
			 }
		 } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
		 
	 }
	 
	 /**
	  * 消费方法
	  * @param value
	  */
	 public synchronized void consume(int value){
		
		 try{
			 // 要消费的数量，剩余多少未消费的
			 int left = value;
			 while(left>0){
				 while(size<=0)
					 // wait for producer to produce，表示不可消费
					 wait();
				 // 实际可消费的数量
				 int decrease = left<size?left:size;
				 size = size - decrease;
				 left = left - decrease;
				 System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d\n", 
						 Thread.currentThread().getName(), value, left, decrease, size);
				 notifyAll();
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
	 }
}
