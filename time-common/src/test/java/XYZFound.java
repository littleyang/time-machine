
public class XYZFound {
	
	public static void main(String[] args){
		
		// x+y+z = 2016
		// 0<x<=2016
		// 0<y<=2016
		// 0<z<=2016
		for(int x=1;x<=2016;x++){
			int yz = 2016 - x;
			for(int y=1;y<=2016;y++){
				int z = yz - y;
				if(z>0&&z<2016){
					System.out.println("x=" +x + ",y=" + y + ",z="+z);
				}
			}
			
		}
		
	}

}
