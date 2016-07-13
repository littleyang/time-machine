import java.util.ArrayList;


public class ThreeSum {
	
	public static void main(String[] args){
	
		int[] arrays = new int[2016];
		
		for(int i=0;i<2016;i++){
			arrays[i] = i+1;
		}
		
		int sum = 2016;
		
		ArrayList<ArrayList<Integer>> result = getThreeSum(arrays,sum);
		
		System.out.println(result.size());
		
		
		for(ArrayList<Integer> list : result){
			System.out.println("");
			System.out.println("=========begin==========");
			for(Integer values : list){
				System.out.println(values);
			}
			System.out.println("=========end==========");
		}
	}

	static private ArrayList<ArrayList<Integer>> getThreeSum(int[] arrays, int sum) {
		// TODO Auto-generated method stub
		
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		
		if(arrays.length<3){
			
			return result;
		}
		
		for(int i=0;i<arrays.length-2;i++){
			
			if (i != 0 && arrays[i] == arrays[i - 1]) {
				continue; // to skip duplicate numbers; e.g [0,0,0,0]
			}
			
			int left = i+1;
			
			int right = arrays.length - 1;
			
			while(left<right){
				
				int sumTemp = arrays[i] + arrays[left] + arrays[right];
				
				if(sum == sumTemp){
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(arrays[i]);
					tmp.add(arrays[left]);
					tmp.add(arrays[right]);
					result.add(tmp);
					left++;
					right--;
					
					while(left<right && arrays[left]==arrays[left-1]){
						left++;
					}
					
					while(left<right && arrays[right]==arrays[right+1]){
						right--;
					}
				}else if(sumTemp<sum){
					left++;
				}else{
					right--;
				}
				
			}
			
		}
		
		return result;
	}

}
