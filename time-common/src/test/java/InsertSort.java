
public class InsertSort {
	
	public static void main(String[] args){
		
		int[] arrays = {100,56,34,53,99,65,78,89,49,36,80,69};
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);
		}
		
		int j =0;
		for(int i=0;i<arrays.length;i++){
			int temp = arrays[i];
			// 把第i个元素与前面的元素进行对比，如果第i个元素小于某个元素，则将i插入到该元素之前。
			// 并将元素后移
			for(j=i;j>0;j--){
				if(temp<arrays[j-1]){
					arrays[j] = arrays[j-1];
				}
			}
			arrays[j] = temp;
		}
		
		System.out.println("=======================");
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);
		}
	}

}
