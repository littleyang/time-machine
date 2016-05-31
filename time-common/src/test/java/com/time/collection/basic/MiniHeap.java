package com.time.collection.basic;

public class MiniHeap {
	
	private int[] data;
	
	// 新建最小堆
	public MiniHeap(){
		
	}
	
	public MiniHeap(int[] data){
		this.data = data;
		createMiniHeap();
	}
	
	// 创建最小的堆
	public void createMiniHeap(){
		// 只有 data.length/2-1的节点才有子节点
		for(int i=(data.length)/2-1;i>=0;i--){
			heapMan(i);
		}
	}
	

	private void heapMan(int i) {
		// TODO Auto-generated method stub
		// 实际的建堆方法
		
		// 首先求左右节点
		int left = indexLeft(i);
		int right = indexRight(i);
		
		int smallest = i;
		
		//如果左边的子节点的值小于当前父节点,大于则不做交换
		if(left<data.length&&data[left]<data[i]){
			smallest = left;
		}
		
		// 如果右节点的值小于父节点(上述比较的值)，则最小index就是right
		if(right<data.length && data[right]< data[smallest]){
			smallest = right;
		}
		
		// 如果没有任何交换,直接跳出比较
		if(smallest == i){
			return ;
		}
		
		// 进行位置交换
		swap(i,smallest);
		
		//递归调用,重新排堆,维护最小堆
		heapMan(smallest);
		
		
	}

	private void swap(int i, int smallest) {
		// TODO Auto-generated method stub
		int temp = data[i];
		data[i] = data[smallest];
		data[smallest] = temp;
		
	}

	private int indexRight(int i) {
		// TODO Auto-generated method stub
		// 2(i+1)
		return (i+1) << 1;
	}

	private int indexLeft(int i) {
		// TODO Auto-generated method stub
		//2(n+1)-1,可以用移位操作
		return ((i+1)<<1)-1;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}
	
	//获取堆顶点最小值
	public int getMiniHeapTopValue(){
		return data[0];
	}
	
	// 替换最小堆顶点
	public void setMiniHeapTopValue(int value){
		data[0] = value;
		// 维持最小堆
		heapMan(0);
	}

}
