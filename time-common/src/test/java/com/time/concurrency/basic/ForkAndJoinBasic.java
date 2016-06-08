package com.time.concurrency.basic;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ForkAndJoinBasic {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException{
		
		 ForkJoinPool forkJoinPoolOne = new ForkJoinPool();
		 PrintAction action = new PrintAction(0,200);
		 forkJoinPoolOne.submit(action);
		 forkJoinPoolOne.awaitTermination(2, TimeUnit.SECONDS);//阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束 
		 forkJoinPoolOne.shutdown();  
		 
		 
		 
		 int[] arr = new int[100];  
	     Random random = new Random();  
	     int total = 0;  
	     // 初始化100个数字元素  
	     for (int i = 0; i < arr.length; i++) {  
	    	 int temp = random.nextInt(100);  
	         // 对数组元素赋值,并将数组元素的值添加到total总和中  
	         total += (arr[i] = temp);  
	     }  
	     System.out.println("初始化时的总和=" + total);  
	     SumTask sumTask = new SumTask(arr,0,100);
	     ForkJoinPool forkJoinPoolTwo = new ForkJoinPool();
	     Future<Integer> result  = forkJoinPoolTwo.submit(sumTask);
	     System.out.println("计算出来的总和=" + result.get()); 
	     forkJoinPoolTwo.shutdown();
	     
	     
	     Fibonacci task = new Fibonacci(3);
	     ForkJoinPool forkJoinPoolThree = new ForkJoinPool();
	     Future<Long> resulto =  forkJoinPoolThree.submit(task);
	     System.out.println("计算出来  " + resulto.get()); 
	     forkJoinPoolThree.shutdown();
		 
	}

}


class PrintAction extends RecursiveAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7846068323913110324L;
	
	
	 //每个"小任务"最多只打印50个数  
    private static final int MAX = 50;  
  
    private int start;  
    private int end;  
    
    public PrintAction(int start,int end){
    	this.start = start;
    	this.end = end;
    }

	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		// 当end-start的值小于MAX时候，开始打印  
        if ((end - start) < MAX) {  
            for (int i = start; i < end; i++) {  
                System.out.println(Thread.currentThread().getName() + "的i值:"  + i);  
            }  
        } else {  
            // 将大任务分解成两个小任务  
            int middle = (start + end) / 2;  
            PrintAction left = new PrintAction(start, middle);  
            PrintAction right = new PrintAction(middle, end);  
            // 并行执行两个小任务  
            left.fork();  
            right.fork();  
        }  

	}
}


class SumTask extends RecursiveTask<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8693036236784105269L;
	
	private final static int MAX_COUNT = 20;
	
	private int start;
	
	private int end;
	
	private int[] array ;
	
	public SumTask(int[] array, int start,int end){
		this.array = array;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		// TODO Auto-generated method stub
		int sum = 0;
		if((end-start)<MAX_COUNT){
			for(int i=start;i<end;i++){
				sum +=array[i];
			}
			return sum;
		}else{
			System.err.println("=====任务分解======");  
			// 将大任务分解成两个小任务
			int middle = (start+end)/2;
			SumTask left = new SumTask(array,start,middle);
			SumTask right = new SumTask(array,middle,end);
			left.fork();
			right.fork();
			return left.join() + right.join();
		}
	}
}

class Fibonacci extends RecursiveTask<Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3392819389700441082L;
	
	
	private long num;
	
	
	Fibonacci(long num){
		this.num = num;
	}
	

	@Override
	protected Long compute() {
		// TODO Auto-generated method stub
		if(num<=1){
			return num;
		}else{
			Fibonacci first = new Fibonacci(num-1);
			Fibonacci second = new Fibonacci(num-2);
			first.fork();
			second.fork();
			return first.join() + second.join();
			
//			Fibonacci first = new Fibonacci(num-1);
//			Fibonacci second = new Fibonacci(num-2);
//			first.fork();
//			return second.compute() + first.join();
		}
	}
	
}