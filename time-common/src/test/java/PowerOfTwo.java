
public class PowerOfTwo {
	
	public static void main(String[] args){
		
		System.out.println("isPowerOfTwo(0) " + isPowerOfTwo(0));
		
		System.out.println("isPowerOfTwo(2) " + isPowerOfTwo(2));
		
		System.out.println("isPowerOfTwo(4) " + isPowerOfTwo(4));
		
		System.out.println("isPowerOfTwo(6) " + isPowerOfTwo(6));
		
		System.out.println("isPowerOfTwo(8) " + isPowerOfTwo(8));
		
		System.out.println("isPowerOfTwo(10) " + isPowerOfTwo(10));
		
		System.out.println("isPowerOfTwo(12) " + isPowerOfTwo(14));
		
		System.out.println("isPowerOfTwo(16) " + isPowerOfTwo(16));
		
		System.out.println("isPowerOfTwo(18) " + isPowerOfTwo(18));
	}
	
	public static boolean isPowerOfTwo(int n){
		
		if(n==0||n<0){
			return false;
		}
		
		return (n&(n-1))==0?true:false;
	}

}
