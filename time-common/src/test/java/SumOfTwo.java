import java.util.HashMap;


public class SumOfTwo {
	
	public static void main(String[] args){
		
		int[] nums = {1,2,5,7,9,4};
		int target = 8;
		
		int[] result = getSumPos(nums,target);
		
		for( int i=0;i<result.length;i++){
			System.out.println(result[i]);
		}
		
	}
	
	public static int[] getSumPos(int[] arrays, int target){
		
		HashMap<Integer, Integer> maps = new HashMap<Integer, Integer>();
		
		for(int i=0;i<arrays.length;i++){
			
			Integer position = maps.get(target - arrays[i]);
			
			if(position == null){
				maps.put(arrays[i], i);
			}else{
				 return new int[]{ i, position};
			}
		}
		
		return null;
		
	}

}
