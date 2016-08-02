import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MergeIntervals {
	
	public static void main(String[] args){
		
		Interval i1 = new Interval(1,3);
		
		Interval i2 = new Interval(2,6);
		
		Interval i3 = new Interval(5,10);
		
		Interval i4 = new Interval(9,18);
		
		List<Interval> list = new ArrayList<Interval>();
		list.add(i1);
		list.add(i2);
		list.add(i4);
		list.add(i3);
		
		System.out.println("========== before merge ===========" );
		
		for(int i=0;i<list.size();i++){
			Interval val = list.get(i);
			System.out.println("start = " + val.getStart() + ", end = " + val.getEnd());
		}
		
		
		System.out.println("============== after merge =============");
		
		List<Interval> results = merge(list);
		
		for(int i=0;i<results.size();i++){
			Interval val = results.get(i);
			System.out.println("start = " + val.getStart() + ", end = " + val.getEnd());
		}
		
	}
	
	public static List<Interval> merge(List<Interval> intervals){
		
		List<Interval> reuslts = new ArrayList<Interval>();
		
		if(intervals.size()>0){
			
			// first sort Intervals
			Collections.sort(intervals,new Comparator<Interval>(){  
	            @Override  
	            public int compare(Interval val1, Interval val2) {  
	                return Integer.compare(val1.start, val2.start);  
	            }  
	        });
			
			int start = intervals.get(0).start;  
	        int end = intervals.get(0).end;
	        
	        for(int i=0;i<intervals.size();i++){
	        	
	        	Interval val = intervals.get(i);
	        	
	        	if(val.start <= end){
	        		// 说明有交集，则需要合并,取交集的最大的end
	        		end = Math.max(end, val.getEnd());
	        	}else{
	        		// 说明没有没有交集，直接加入到result
	        		reuslts.add(new Interval(start,end));
	        		start = val.start;
	        		end = val.end;
	        	}
	        }
	        
	        reuslts.add(new Interval(start,end));
			
		}
		
		return reuslts;
	}
}

class Interval{
	
	public int start;
	public int end;
	
	public Interval(int start, int end){
		this.setStart(start);
		this.setEnd(end);
	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getEnd() {
		return end;
	}


	public void setEnd(int end) {
		this.end = end;
	}
	
}
