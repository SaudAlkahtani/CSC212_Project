
public class SS1 implements SubtitleSeq {
	// random comment
	SortedBST<TimeInterval, Subtitle> srtList;

	public SS1() {
		srtList = new SortedBST<TimeInterval, Subtitle>();

	}

	private int convertToM(Time t) {// helping method to convert time object to millisecond
		int h = t.getHH() * 3600000;
		int m = t.getMM() * 60000;
		int s = t.getSS() * 1000;
		int ms = t.getMS();
		int Stime = h + m + s + ms;
		return Stime;
	}

	private Time convertToR(int i) {// helping method to covnert milli second to time object
		int hh, mm, ss, ms;
		ms = (i % 1000);
		ss = ((i / 1000) % 60);
		mm = ((i / (1000 * 60)) % 60);
		hh = ((i / (1000 * 60 * 60)) % 24);

		Time t = new TimeI(hh, mm, ss, ms);

		return t;
	}

	@Override
	public void addSubtitle(Subtitle st) {

		TimeInterval Key = new TimeInterval((TimeI) st.getStartTime(), (TimeI) st.getEndTime());
		srtList.insert(Key, st);

	}

	@Override
	public List<Subtitle> getSubtitles() {

		LinkedListWC<Subtitle> ordList = new LinkedListWC<Subtitle>();
		if (srtList.empty()) {
			return ordList;
		}
		srtList.findFirst();
		while (!srtList.last()) {
			ordList.insert(srtList.retrieve().second);
			srtList.findNext();
		}
		ordList.insert(srtList.retrieve().second);

		return ordList;
	}

	@Override
	public Subtitle getSubtitle(Time time) {
		
		TimeInterval key = new TimeInterval(((TimeI)time),((TimeI)time));
		if(srtList.find(key)) {
			return srtList.retrieve().second;	
		}
		else {
			return null;
		}
		
		
		
		
	}

	@Override
	public int nbNodesInSearchPath(Time time) {
		Subtitle s = getSubtitle(time);
		if(s==null) {
			return 0;
		}
         
		TimeInterval p = new TimeInterval(((TimeI) s.getStartTime()), ((TimeI) s.getEndTime()));
		
		int i = srtList.nbNodesInSearchPath(p);

		return i;
	}

	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {

		
		if (srtList.empty())
			return null;
		
		LinkedListWC<Subtitle> sList = new LinkedListWC<Subtitle>();
		int st = ((TimeI)startTime).timeInMs();
		int et = ((TimeI)endTime).timeInMs();
		srtList.findFirst();
		while(!srtList.last()) {
			int stl = convertToM(srtList.retrieve().second.getStartTime());
			int etl = convertToM(srtList.retrieve().second.getEndTime());
			
		if ( stl <= st && etl >= st) {
			sList.insert(srtList.retrieve().second);
			srtList.findNext();
			continue;
		}
		else if (stl >= st && stl <= et) {
			sList.insert(srtList.retrieve().second);
			srtList.findNext();
			continue;
		}
		srtList.findNext();
			
			
		}
		int stl = convertToM(srtList.retrieve().second.getStartTime());
		int etl = convertToM(srtList.retrieve().second.getEndTime());
		
	if ( stl <= st && etl >= st) {
		sList.insert(srtList.retrieve().second);
		
		
	}
	else if (stl >= st && stl <= et) {
		sList.insert(srtList.retrieve().second);
					
	}
		
		return sList;
	}


	@Override
	public void shift(int offset) {

//		LinkedListWC<Subtitle> shifted = new LinkedListWC<Subtitle>();
//		if (srtList.empty()) {
//			System.out.println("Subtitle List is empty");
//		}
//		srtList.findFirst();
//		while (!srtList.last()) {
//			shifted.insert(srtList.retrieve().second);
//			srtList.findNext();
//		}
//		shifted.insert(srtList.retrieve().second);
//		// we got the whole list in a linked list , now we remove the data from
//		// sortedBst
//		srtList.findFirst();
//		while (!srtList.last()) {
//			srtList.remove();
//		}
//		srtList.remove();
//
//		shifted.findFirst();
//
//		while (!shifted.last()) {
//
//			int st = ((TimeI) shifted.retrieve().getStartTime()).timeInMs();
//			int ed = ((TimeI) shifted.retrieve().getEndTime()).timeInMs();
//			st = st + offset;
//			ed = ed + offset;
//			if (st <= 0) {
//				st = 0;
//			}
//			if (ed <= 0) {
//				shifted.remove();
//				continue;
//			}
//
//			shifted.retrieve().setStartTime(((TimeI) convertToR(st)));
//			shifted.retrieve().setEndTime(((TimeI) convertToR(ed)));
//
//			shifted.findNext();
//		}
//		int st = ((TimeI) shifted.retrieve().getStartTime()).timeInMs();
//		int ed = ((TimeI) shifted.retrieve().getEndTime()).timeInMs();
//		st = st + offset;
//		ed = ed + offset;
//		if (st <= 0) {
//			st = 0;
//		}
//		if (ed <= 0) {
//			shifted.remove();
//
//		}
//
//		shifted.retrieve().setStartTime(((TimeI) convertToR(st)));
//		shifted.retrieve().setEndTime(((TimeI) convertToR(ed)));
//        
//		
//		while (!shifted.last()) {
//			Subtitle s = shifted.retrieve();
//			TimeInterval t1 = new TimeInterval(((TimeI) s.getStartTime()), ((TimeI) s.getEndTime()));
//			srtList.insert(t1, s);
//		}
//		Subtitle s = shifted.retrieve();
//		TimeInterval t1 = new TimeInterval(((TimeI) s.getStartTime()), ((TimeI) s.getEndTime()));
//		srtList.insert(t1, s);
//  
		if (srtList.empty()) {
			return;
		}
	
			srtList.findFirst();

			while (!srtList.last()) {

				int st = convertToM(srtList.retrieve().second.getStartTime());
				int ed = convertToM(srtList.retrieve().second.getEndTime());
				st = st + offset;
				ed = ed + offset;
				if (st <= 0) {
					st = 0;
				}
				if (ed <= 0) {
					srtList.remove();
					continue;
				}

				srtList.retrieve().second.setStartTime(convertToR(st));
				srtList.retrieve().second.setEndTime(convertToR(ed));

				srtList.findNext();
			}
			int st = convertToM(srtList.retrieve().second.getStartTime());
			int ed = convertToM(srtList.retrieve().second.getEndTime());
			st = st + offset;
			ed = ed + offset;
			if (st <= 0) {
				st = 0;
			}
			if (ed <= 0) {
				srtList.remove();
			}

			if (srtList.empty()) {
				return;
			}
			srtList.retrieve().second.setStartTime(convertToR(st));
			srtList.retrieve().second.setEndTime(convertToR(ed));
		}
	}
	


