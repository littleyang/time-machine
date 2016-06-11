
public class RotateArray {
	
	public static void main(String[] args){
		
		int[] arrays = {1,2,3,4,5,6,8,11,12,24,14,16,45,23};
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);
		}
		
		int k = 3;
		
		int size = arrays.length;
		
		if(size<0||k<0){
		    return;
		}
		
		// first solution
//		for(int i=0;i<k;i++){
//			int temp = arrays[size-k + i];
//			for(int j= size-k+i;j>i;j--){
//				arrays[j] = arrays[j-1];
//			}
//			arrays[i] = temp;
//		}
		
		
//		// second solution
//		if(k > arrays.length){
//			k = k % arrays.length;
//		}
//		int a = arrays.length - k; 
//	 
//		reverse(arrays, 0, a-1);
//		reverse(arrays, a, arrays.length-1);
//		reverse(arrays, 0, arrays.length-1);
		
		
		
		
	}
	
	public static void reverse(int[] arr, int left, int right){
		if(arr == null || arr.length == 1) 
			return;
	 
		while(left < right){
			int temp = arr[left];
			arr[left] = arr[right];
			arr[right] = temp;
			left++;
			right--;
		}
	}

}
