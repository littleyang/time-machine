
public class LeetcodeGuessNumber {
	
	public static void main(String[] args){
		
		
		
		
	}
	
	
	public static int guess(int num){
	
		int L = 1,R = num;
        while(L <= R){
            int mid = L + ((R - L) >> 1);
            int res = guess(mid);
            if(res == 0) return mid;
            else if(res == 1)  L = mid + 1;
            else R = mid - 1;
        }
        return L;
		
	}

}
