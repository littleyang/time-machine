package com.time.concurrency.basic;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueBasic {
	
	private static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(20);
	
	public static void main(String[] args){
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(1,1,1000,TimeUnit.MICROSECONDS, new ArrayBlockingQueue<Runnable>(1));
		
		// 设置拒绝策略
		pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		
		QueueThread ta = new QueueThread();
		
		QueueThread tb = new QueueThread();
		
		QueueThread tc = new QueueThread();
		
		pool.execute(ta);
		
		pool.execute(tb);
		
		pool.execute(tc);
		
		pool.shutdown();
		
		
	}
	
	private static void printAll() {
        String value;
        Iterator<String> iter = queue.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
        }
        System.out.println();
    }
	
	private static class QueueThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int i = 0;
            while (i++ < 6) {
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName()+i;
                queue.add(val);
                // 通过“Iterator”遍历queue。
                printAll();
            }
		}
		
	}

}


