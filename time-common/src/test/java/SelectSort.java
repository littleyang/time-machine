
public class SelectSort {
	
	public static void main(String[] args){
		
		int[] arrays = {100,56,34,53,99,65,78,89,49,36,80,69};
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);
		}
		
		for(int i=0;i<arrays.length;i++){
			int k = i;
			for(int j=i;j<arrays.length;j++){
				if(arrays[j]<arrays[k]){
					k = j;
				}
			}
			int tempValue = arrays[i];
			arrays[i] = arrays[k];
			arrays[k] = tempValue;
		}
		
		System.out.println("=======================");
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);
		}
		
	}

}
