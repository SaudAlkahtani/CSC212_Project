
public class SubtitleClass implements Subtitle {
		
	private Time startTime ;
	private Time endTime ;
	private String Text ;
	
	
	
	public SubtitleClass(Time startTime, Time endTime, String text) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.Text = text;
	}

	@Override
	public Time getStartTime() {
		
		return startTime ;
	}

	@Override
	public Time getEndTime() {

		return endTime ;
	}

	@Override
	public String getText() {

		return Text ;
	}

	@Override
	public void setStartTime(Time startTime) {
		this.startTime = startTime ;

		
	}

	@Override
	public void setEndTime(Time endTime) {
		this.endTime = endTime ;
		
	}

	@Override
	public void setText(String text) {
		this.Text = text ;
		
	}
	
	

}
