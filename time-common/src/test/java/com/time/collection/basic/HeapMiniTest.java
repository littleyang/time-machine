package com.time.collection.basic;

import org.junit.Test;

public class HeapMiniTest {
	
	@Test
	public void testMiniHeap(){
		
		//int[] data = {56,275,12,6,45,478,41,1236,456,12,546,45};
		
		int[] data = {5,4,3,10,9,6};
		
		int[] result = topK(data,5);
		
		for(int i=0;i<result.length;i++){
			System.out.println(result[i]);
		}
	}
	
	
	public int[] topK(int[] source,int k){
		
		if(k>0&&k<source.length){
			// 取出K个元素建立最小堆
			int[] topK = new int[k];
			for(int i=0;i<k;i++){
				topK[i] = source[i];
			}
			
			MiniHeap heap = new MiniHeap(topK);
			
			//从K开始遍历后面的元素进行比较
			for(int j=k;j<source.length;j++){
				// 堆的最小顶点
				int dataK = heap.getMiniHeapTopValue();
				if(source[j]>dataK){
					//如果值大于堆的最小顶点，直接设置堆值
					heap.setMiniHeapTopValue(source[j]);
				}
			}
			return topK;
		}else{
			System.out.println("params error");
			return null;
		}
	}

}
