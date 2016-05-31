import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


public class TopKFrequent {
	
	public static void main(String[] args){
		
		int[] data = {1,1,1,1,2,2,2,4};
		
		int[] list = topFrequentK(data,2);
		
		for(int i=0;i<list.length;i++){
			System.out.println(list[i]);
		}
		
//		TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();
//		
//		for(int i=0;i<data.length;i++){
//			if(!map.containsKey(data[i])){
//				// 如果map里面不存在则出现一次
//				map.put(data[i], 1);
//			}else{
//				int before = map.get(data[i]);
//				map.put(data[i], before+1);
//			}
//		}
//		
//		List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer,Integer>>(map.entrySet());
//		
//		Collections.sort(list,new Comparator<Map.Entry<Integer,Integer>>() {
//            //升序排序
//            public int compare(Entry<Integer, Integer> o1,Entry<Integer, Integer> o2) {
//                return o1.getValue().compareTo(o2.getValue());
//            }
//            
//        });
//		
//		for(Entry<Integer, Integer> entity : map.entrySet()){
//			System.out.println(entity.getKey() + " ::::: " + entity.getValue());
//		}
	}
	
	public static int[] topFrequentK(int[] source,int k){
		
		int[] results = new int[k];
		
		TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();
		
		for(int i=0;i<source.length;i++){
			if(!map.containsKey(source[i])){
				// 如果map里面不存在则出现一次
				map.put(source[i], 1);
			}else{
				int before = map.get(source[i]);
				map.put(source[i], before+1);
			}
		}
		
		List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer,Integer>>(map.entrySet());
		
		Collections.sort(list,new Comparator<Map.Entry<Integer,Integer>>() {
            //升序排序
            public int compare(Entry<Integer, Integer> o1,Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
            
        });
		
		Set<Integer> keySets = map.keySet();
		
		for(int i=0;i<k;i++){
			results[i] = (Integer) keySets.toArray()[i];
		}
		return results;
	}

}


