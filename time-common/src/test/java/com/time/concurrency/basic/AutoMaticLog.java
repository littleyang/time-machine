package com.time.concurrency.basic;

import java.util.concurrent.atomic.AtomicLong;

public class AutoMaticLog {
	
	public static void main(String[] args){
		
		AtomicLong mAtoLong = new AtomicLong();
		mAtoLong.set(0x0123456789ABCDEFL);
		
		System.out.printf("%20s : 0x%016X\n", "get()", mAtoLong.get());
		
		System.out.printf("%20s : 0x%016X\n", "getAndDecrement()", mAtoLong.getAndDecrement());
		
		System.out.printf("%20s : 0x%016X\n", "decrementAndGet()", mAtoLong.decrementAndGet());
		
		System.out.printf("%20s : 0x%016X\n", "addAndGet(0x10)", mAtoLong.addAndGet(0x10));
		
		System.out.printf("%20s : %s\n", "compareAndSet()", mAtoLong.compareAndSet(0x12345679L, 0xFEDCBA9876543210L));
	}

}
