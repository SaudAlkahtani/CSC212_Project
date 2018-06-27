
public class TimeInterval implements Comparable<TimeInterval> {

	TimeI startTime;
	TimeI endTime;

	public TimeInterval() {
		startTime = null;
		endTime = null;
	}

	public TimeInterval(TimeI s, TimeI e) {
		startTime = s;
		endTime = e;
	}

	@Override
	public int compareTo(TimeInterval that) {
		if (startTime.compareTo(that.endTime) > 0) {
			return 1;// greater than that.endtime
		}
		if (endTime.compareTo(that.startTime) < 0) {
			return -1;// less than that.starTime
		}
		return 0;// equal to each other
	}
}
