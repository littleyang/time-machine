package com.time.concurrency.basic;

public class SynchronizedLock {
	
	public static void main(String[] args){
		Demo demo = new Demo();
		
		Thread t1 = new Thread(demo,"t1");
		Thread t2 = new Thread(demo,"t2");
		
		t1.start();
		t2.start();
	
	}

}

class Demo implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(this){
			for(int i=0;i<5;i++){
				try {
					Thread.sleep(100);
					System.out.println(Thread.currentThread().getName() + " loop " + i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 休眠100ms
				 
			}
		}
	}
	
}
