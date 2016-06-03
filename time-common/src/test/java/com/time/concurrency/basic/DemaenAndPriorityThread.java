package com.time.concurrency.basic;

public class DemaenAndPriorityThread {
	
	public static void main(String[] args){
		
		System.out.println("This is main thread begin");
		
		Thread priorityOne = new Thread( new Priority(),"priorityOne");
		
		Thread priorityTwo = new Thread( new Priority(),"priorityTwo");
		
		priorityOne.start();
		priorityOne.setPriority(1);
		
		priorityTwo.start();
		priorityTwo.setPriority(10);
		
		
		Thread daemon = new Thread(new Deamen(),"Deamen");
		
		Thread notdaemon = new Thread(new NotDeamen(),"notdaemon");
		
		daemon.setDaemon(true);
		
		daemon.start();
		
		notdaemon.start();
		
		System.out.println("This is main thread end");
		
	}

}

class Priority implements Runnable{
	
	public Priority(){
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("This is Priority thread");
		for (int i=0; i<5; i++) {
			 System.out.println(Thread.currentThread().getName() 
					 + "("+Thread.currentThread().getPriority()+ ")" +", loop "+i);
		}
	}
	
}

class Deamen implements Runnable{

	
	public Deamen(){
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("This is daemon thread");
		for (int i=0; i<5; i++) {
			 System.out.println(Thread.currentThread().getName()  +"(isDaemon="+Thread.currentThread().isDaemon()+ ")" +", loop "+i);
		}
	}
	
}

class NotDeamen implements Runnable{

	
	public NotDeamen(){
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("This is NotDeamen thread");
		for (int i=0; i<5; i++) {
			 System.out.println(Thread.currentThread().getName()  +"(isDaemon="+Thread.currentThread().isDaemon()+ ")" +", loop "+i);
		}
	}
	
}
