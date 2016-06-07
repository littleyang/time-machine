package com.time.concurrency.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolBasic {
	
	public static void main(String[] args){
		
		// 创建一个可重用固定线程数的线程池
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		ThreadPoolDemo ta =  new ThreadPoolDemo();
		
		ThreadPoolDemo tb =  new ThreadPoolDemo();
		
		ThreadPoolDemo tc =  new ThreadPoolDemo();
		
		ThreadPoolDemo td =  new ThreadPoolDemo();
		
		ThreadPoolDemo te =  new ThreadPoolDemo();
		
		ThreadPoolDemo tf =  new ThreadPoolDemo();
		
		pool.execute(ta);
		
		pool.execute(tb);
		
		pool.execute(tc);
		
		pool.execute(td);
		
		pool.execute(te);
		
		pool.execute(tf);
		
		// close poll
		pool.shutdown();
		
	}

}


class ThreadPoolDemo implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("this is thread " + Thread.currentThread().getName());
	}
	
}