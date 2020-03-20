import java.awt.List;
import java.util.ArrayList;

public class Tabla implements Comparable<Tabla> {
	private int [][] allapot= new int[3][3];
	private int rosszHelyen;
	private Tabla szulo;
	private int szint;
	
	private String action;
	int x, y;
	
	public Tabla(Tabla szulo, int[][] szulon, int szint){
		this.allapot= szulon;
		this.szulo= szulo;
		this.szint= szint;
	}
	
    public Tabla(Tabla szulo, int[][] szulon, int szint, String action){ 
    	this.allapot= szulon;
		this.szulo= szulo;
		this.szint= szint;
		this.action= action;
	}
	
	public void setszulo(Tabla szulo) {
		   this.szulo=szulo;
	}

	
	public void setszint(int l) {szint=l;}

	public int setRosszHelyen() {
		
		if(Megoldo.checkHeuristic == 1) {	
			
			rosszHelyen=compare(Megoldo.celAllapot)+szint;
		}
		else {
		
			rosszHelyen= manhattanHeuristic(Megoldo.celAllapot)+szint;   
		}
		
		return rosszHelyen;
	}
	

	public Tabla getSzulo() {
		return szulo; 
	}
	
	public int getszint() {
		return szint;
	}
	
	public void setAllapot(int [][] b) {this.allapot= b;}
	
	public int [][] getAllapot(){ return allapot; }	
		
	public int getX() {return x;	}	
	
	public int getY() {return y;}
	
	public String toString() {	    
		return "\n szint "+szint+"  "+ "\n heuristic f(n)  "+rosszHelyen +"\n Mozgat: "+action ;
	}
	
	public int compare( int [][] b) { //visszaadja a rossz helyen levok szamat
		int m=0;
		for(int i=0; i<b.length; i++) {
			for(int j=0; j<b.length; j++) {
			    if (allapot[i][j]==0) {
						continue; 
				}
				if (allapot [i][j]==b[i][j]) {
					
				    m=m+0;
				    
 				}else {
					m++;
				}
		
			}
		}
		return m;
	}
	
	
	public void uresKocka(int [][] b) { //visszateriti az ures kocka koordinatait
		
		for (int i = 0 ; i < 3; i++)
		    for(int j = 0 ; j < 3 ; j++)
		    {
		         if ( b[i][j] == 0)
		         {
		              x=i;
		              y=j;
		              break;
		         }
	       }
	}

	
	public int manhattanHeuristic( int[][] goal) {    
		
		int manhattan=0;
		int temp=0;
		
				for(int i=0; i<allapot.length; i++) {
					for(int j=0; j<allapot.length; j++) {
						
						if (allapot[i][j]== goal [i][j]|| allapot[i][j]==0) {
							manhattan= manhattan+0;
						}else {
							temp=allapot[i][j];
							manhattan+= manhattanD(temp,i,j,goal);
						}
					}
					
				}
		
		return manhattan;
	}
	
	public int manhattanD(int temp,int sX, int  sY, int [][] goal ) {
		int distance=0;
		for(int i=0; i<goal.length; i++) {
			for(int j=0; j<allapot.length; j++) {
				
				if (goal[i][j]==temp) {
					distance= Math.abs(sX-i)+ Math.abs(sY-j);
				}
			}
		}
		
		return distance;
	}
 	

	@Override
	public int compareTo(Tabla o) {		
		
		if(this.rosszHelyen<o.rosszHelyen) {
			return -1;
			
		}else if(this.rosszHelyen>o.rosszHelyen) {
			return 1;
		}
		else 
			return 0;
		
	}
	

}