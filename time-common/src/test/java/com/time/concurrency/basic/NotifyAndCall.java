package com.time.concurrency.basic;

public class NotifyAndCall {
	
	public static void main(String[] args){
		
		DemoN dn = new DemoN();
		
		Thread th = new Thread(dn,"DemoN");
		
		synchronized(th){
            try {
            	// 启动“线程t1”
                System.out.println(Thread.currentThread().getName()+" start th");
                th.start();
                
                // th wait,同时释放锁
				th.wait();
				
				System.out.println(Thread.currentThread().getName()+" continue");
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}


class DemoN implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(this){
			System.out.println(Thread.currentThread().getName()+" call notify()");
			this.notify();
		}
	}
	
}