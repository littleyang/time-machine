
public class BubboSort {
	
	public static void main(String[] args){
		
		int[] arrays = {100,34,2,7,101,45,76,53,32,29};
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);	
		}
		
//		// 实现大的元素下沉
//		for(int i=1;i<arrays.length;i++){
//			for(int j=0;j<arrays.length-i;j++){
//				if(arrays[j]>arrays[j+1]){
//					// 如果前面一个元素大于后面一个元素，则交换
//					int temp = arrays[j];
//					arrays[j] = arrays[j+1];
//					arrays[j+1] = temp;
//				}
//			}
//		}
		
		//小的元素的上浮
		for(int i=0;i<arrays.length;i++){
			for(int j=i-1;j>=0;j--){
				if(arrays[j+1]<arrays[j]){
					int temp = arrays[j];
					arrays[j] = arrays[j+1];
					arrays[j+1] = temp;
				}
			}
		}
		
		System.out.println("==========================");
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);	
		}
		
	}

}
