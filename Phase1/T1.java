
public class T1 implements Time {
	
	private int hh ;
	private int mm ;
	private int ss ;
	private int ms ;
	
	
	public T1() {
		this.hh = 0 ;
		this.mm = 0 ;
		this.ss = 0 ;
		this.ms = 0 ;

	}
	
	public T1(int hh , int mm , int ss , int ms) {
		this.hh = hh ;
		this.mm = mm ;
		this.ss = ss ;
		this.ms	= ms ;
	}



	public int getHH() {

		return hh;
	}

	@Override
	public int getMM() {

		return mm;
	}

	@Override
	public int getSS() {

		return ss;
	}

	@Override
	public int getMS() {

		return ms;
	}

	@Override
	public void setHH(int hh) {
		this.hh = hh ;
		
	}

	@Override
	public void setMM(int mm) {
		this.mm = mm ;
		
	}

	@Override
	public void setSS(int ss) {
		this.ss = ss; 
		
	}

	@Override
	public void setMS(int ms) {
		this.ms = ms ;
		
	}
	
	

}
