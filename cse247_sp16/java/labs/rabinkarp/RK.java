package rabinkarp;

public class RK {

	//
	// Be sure to look at the write up for this assignment
	//  so that you get full credit by satisfying all
	//  of its requirements
	//

	public int size;


	public char[] bufferArray;

	/**
	 * Rabin-Karp string matching for a window of the specified size
	 * @param m size of the window
	 */
	public RK(int m) {
		
		this.size=m;



		this.bufferArray=new char[m];

	}







	int loc = 0;

	int hash=0;


	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return
	 */
// helper method to compute large mods. 

	public int intMod(int a, int n, int b){
		if(n>1){
			if (n%2==0)return (intMod(a,n/2,b)*intMod(a,n/2,b))%b;
			else return 	(intMod(a,n/2,b)*intMod(a,(n+1)/2,b))%b;	
		}

		else return a%b;


	}

	
	
	public int nextCh(char d) {

		if (bufferArray[loc] == '\u0000') {
			bufferArray[loc]=d;
			hash=(hash*31+d)%511;

			loc = (loc + 1) % bufferArray.length;
			System.out.println(hash);
			return hash;

		}



		char oldCharacter = bufferArray[loc];
		int a=511;
		//int c = 2;
		int m = this.size;


		// replacing :      ((int)Math.pow(31, this.size))%511


		//with          :    ((((int)(Math.pow(((int)Math.pow(31, this.size/c))%a,c)))%511)*((int)Math.pow(31, m%c))%511)%a
		hash=(hash*31%511-(intMod(31,m,a)*oldCharacter%511)%511+d%511)%511;

		//((((int)(Math.pow(((int)Math.pow(31, this.size/c))%a,c)))%511)*((int)Math.pow(31, m%c))%511)%a

		if ( hash<0){

			hash=hash+511;

		}


		bufferArray[loc] = d;
		loc = (loc + 1) % bufferArray.length;
		System.out.println(hash);
		return hash;




	}

}



