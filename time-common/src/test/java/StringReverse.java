
public class StringReverse {
	
	public static void main(String[] args){
		
		String str = "hello";
		StringBuilder temp = new StringBuilder("");
		StringBuffer strb = new StringBuffer(str);
		strb.reverse();
		for(int i=str.length()-1;i>=0;i--){
			temp.append(str.charAt(i));
		}
		System.out.println(temp.toString());
	}

}
