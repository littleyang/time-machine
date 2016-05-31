package com.time.concurrency.basic;

public class ConcurrenceBasic {
	
	public static void main(String[] args) throws InterruptedException{
		
		Runnable run = new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true){
	                if (Thread.currentThread().isInterrupted()){
	                    System.out.println("线程被中断了");
	                    return ;
	                }else{
	                    System.out.println("线程没有被中断");
	                }
	            }
			}
			
		};
		
		
		Thread thread = new Thread(run);
		
		thread.start();
		
		Thread.sleep(3000);
		
		thread.interrupt();
		
		System.out.println("线程中断了，程序到这里了");
		
	}

}
