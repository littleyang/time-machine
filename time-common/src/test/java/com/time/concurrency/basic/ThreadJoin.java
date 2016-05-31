package com.time.concurrency.basic;

public class ThreadJoin {
	
	public static void main(String[] args) throws InterruptedException{
		System.out.println("this is the main threads begin");
		Thread t3 = new Thread(new T3());
		t3.start();
		t3.join();
		System.out.println("this is the main threads end");
	}
}

class T1 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("this is T1 thread begin");
		for(int i=0; i <10000; i++)
            ;
		System.out.println("this is T1 thread end ");
	}
}

class T2 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("this is T2 thread begin");
		Thread t1 = new Thread(new T1());
		t1.setName("T1");
		try {
			t1.start();
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("this is T2 thread end");
	}
	
}

class T3 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("this is T3 thread begin");
		Thread t2 = new Thread(new T2());
		t2.setName("T2");
		try {
			t2.start();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("this is T3 thread end");
	}
	
}
