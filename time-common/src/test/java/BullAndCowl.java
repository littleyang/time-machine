import java.util.HashMap;


public class BullAndCowl {
	
	public static void main(String[] args){
		
		String result = hint("4567","3462");
		
		System.out.println(result);
		
	}

	
	public static String hint(String secret, String guess) {
		
		
		// user hash map to store each number and times
		
		HashMap<Character,Integer> secretMap = new HashMap<Character,Integer>();
		
		for(int n=0;n<secret.length();n++){
			if(secretMap.containsKey(secret.charAt(n))){
				// if char had exist in map ,time + 1
				secretMap.put(secret.charAt(n), secretMap.get(secret.charAt(n)) + 1);
			}else{
				// if char not exist in map , times = 1
				secretMap.put(secret.charAt(n), 1);
			}
		}
		
		//bull and cowl
		
		int bull = 0;
		int cowl = 0;
		
		for(int g=0;g<guess.length();g++){
			// if secret[g] != guess[g]
			// cowl
			if(secret.charAt(g) != guess.charAt(g)){
				// if secret map has guess[g]
				if(secretMap.containsKey(guess.charAt(g))){
					if(secretMap.get(guess.charAt(g))>0){
						// 
						cowl++;
						secretMap.put(guess.charAt(g), secretMap.get(guess.charAt(g)) - 1);
					}
				}
			}
			
			// if secret[g] == guess[g]
			// bull
			if(secret.charAt(g) == guess.charAt(g)){
				if(secretMap.get(guess.charAt(g))>0){
					secretMap.put(guess.charAt(g), secretMap.get(guess.charAt(g)) - 1);
				}else{
					cowl--;
				}
				bull++;
				
			}
		}
		return Integer.toString(bull)+"A"+Integer.toString(cowl)+"B";
	}
}
