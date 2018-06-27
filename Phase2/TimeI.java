

public class TimeI implements Time , Comparable<TimeI> {
	private int hh;
	private int mm;
	private int ss;
	private int ms;
	public TimeI() {
		this.hh = 0 ;
		this.mm = 0 ;
		this.ss = 0 ;
		this.ms = 0 ;

	}
	public TimeI(int hh, int mm, int ss, int ms) {
		this.hh = hh;
		this.mm = mm;
		this.ss = ss;
		this.ms = ms;
	}

	public TimeI(String hh, String mm, String ss, String ms) {
		this.hh = Integer.parseInt(hh);
		this.mm = Integer.parseInt(mm);
		this.ss = Integer.parseInt(ss);
		this.ms = Integer.parseInt(ms);
	}

	@Override
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
		this.hh = hh;
	}

	@Override
	public void setMM(int mm) {
		this.mm = mm;
	}

	@Override
	public void setSS(int ss) {
		this.ss = ss;
	}

	@Override
	public void setMS(int ms) {
		this.ms = ms;
	}
	
	public int timeInMs() {
		int milliseconds=0;
		milliseconds+=hh*3600000;
		milliseconds+=mm*60000;
		milliseconds+=ss*1000;
		milliseconds+=ms;
		return milliseconds;
		
	}

		
	public  int compareTo(TimeI a) {
		int sender=((TimeI)a).timeInMs();
		int caller = this.timeInMs();
		if(caller > sender)
			return 1;
		else if (sender > caller)
			return -1;
		else
			return 0;
	}



}