
public class SS1 implements SubtitleSeq {

	private List<Subtitle> srtList;

	public SS1() {

		this.srtList = new LinkedList<Subtitle>();
	}

	private int convertToM(Time t) {// helping method to convert time object to millisecond
		int h = t.getHH() * 3600000;
		int m = t.getMM() * 60000;
		int s = t.getSS() * 1000;
		int ms = t.getMS();
		int Stime = h + m + s + ms;
		return Stime;
	}

	private T1 convertToR(int i) {// helping method to covnert milli second to time object
		int hh, mm, ss, ms;
		ms = (i % 1000);
		ss = ((i / 1000) % 60);
		mm = ((i / (1000 * 60)) % 60);
		hh = ((i / (1000 * 60 * 60)) % 24);

		T1 t = new T1(hh, mm, ss, ms);

		return t;
	}

	@Override
	// requires none , adds subtitle to the list of subtitles (in chronological
	// order)
	public void addSubtitle(Subtitle st) {
		int Stime = convertToM(st.getStartTime());
		int Etime = convertToM(st.getEndTime()); 
		
		if (srtList.empty()) {
			if(Etime<Stime) {//end time of the sent sub is less that its start time
				System.err.println("End time can't be less than start time!");
				return;
			}
			srtList.insert(st);

		} else {

			Subtitle tmp; // if it was the first subtitle this will be the the second one!!
			// subtitle time in ms
			
			
			if(Etime<Stime) {//end time of the sent sub is less that its start time
				System.err.println("End time can't be less than start time!");
				return;
			}

			int Lstime;
			int Letime;
			srtList.findFirst();
			while (!srtList.last()) {
               // System.out.println("while in add");
				Lstime = convertToM(srtList.retrieve().getStartTime());
				Letime = convertToM(srtList.retrieve().getEndTime());
				
				if ( Lstime <= Stime && Letime > Stime) { // started before interval and ended inside it 
				  System.err.println("cant add! , overlapping subtitle");
				  return;
				}
				 else if (Lstime >= Stime && Lstime <= Etime) {
					 System.err.println("cant add! , overlapping subtitle");
					 return;
				 }
			
				if (Stime < Lstime) {
					
					tmp = srtList.retrieve();
					srtList.update(st);
					srtList.insert(tmp);
					return;
				}
                
				srtList.findNext();
				
                 
			}

			Lstime = convertToM(srtList.retrieve().getStartTime());
			Letime = convertToM(srtList.retrieve().getEndTime());
			if ( Lstime <= Stime && Letime > Stime) { // started before interval and ended inside it
			    
			  System.err.println("cant add! , overlapping subtitle");
			  return;
			}
			 else if (Lstime >= Stime && Lstime <= Etime) {
				 System.err.println("cant add! , overlapping subtitle");
				 return;
			 }
			
			if (Stime < Lstime) {
				// System.out.println("stime is less than ltime at 50");
				tmp = srtList.retrieve();
				srtList.update(st);
				srtList.insert(tmp);
				return;
			
			}

			srtList.insert(st); // if its not less than any one its will be added at the last
			//srtList.findNext();
		}

	}

	@Override
	// requires list is not empty , this method returns the list of subtitles in
	// chronological order
	public List<Subtitle> getSubtitles() {

		return srtList;

	}

	@Override
	// requires list not empty and time is not negative , this method returns the
	// subtitle at specific time
	// if not found returns null
	public Subtitle getSubtitle(Time time) {
		// edit convert
		if (!srtList.empty()) {
			srtList.findFirst();
			int sT, eT, tt;

			while (!srtList.last()) {
				sT = convertToM(srtList.retrieve().getStartTime());
				eT = convertToM(srtList.retrieve().getEndTime());
				tt = convertToM(time);

				if (tt >= sT && tt <= eT) {
					return srtList.retrieve();
				}
				srtList.findNext();
			}
			sT = convertToM(srtList.retrieve().getStartTime());
			eT = convertToM(srtList.retrieve().getEndTime());
			tt = convertToM(time);

			if (tt >= sT && tt <= eT) {
				return srtList.retrieve();
			}
		}

		return null;
	}

	@Override
	// requires list not empty and end time not less than start time , this method
	// returns a list of subtitles
	// that is displayed between start time and end time
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {
		srtList.findFirst();
		List<Subtitle> a = new LinkedList<Subtitle>();
		// needs convert method
		int st = convertToM(startTime);
		int et = convertToM(endTime);
		if (et < st) {
			System.out.println("End time cannot be less than start time! try again");
			return null;
		}
		if (srtList.empty()) {
			return null;
		}
		while (!srtList.last()) {
			int Lst = convertToM(srtList.retrieve().getStartTime());
			int Let = convertToM(srtList.retrieve().getEndTime());
			if (Lst <= st && Let >= st) {
				a.insert(srtList.retrieve());
				srtList.findNext();
				continue;

			} else if (Lst >= st && Lst <= et) {
				a.insert(srtList.retrieve());
				srtList.findNext();
				continue;
			}

			srtList.findNext();

		}

		int Lst = convertToM(srtList.retrieve().getStartTime());
		int Let = convertToM(srtList.retrieve().getEndTime());
		if (Lst <= st && Let >= st) {
			a.insert(srtList.retrieve());

		} else if (Lst >= st && Lst <= et) {
			a.insert(srtList.retrieve());

		}
		return a;
	}

	@Override
	// requires list not empty, this method returns list of subtitles (in
	// chronological) order
	// that contains a specific sub-string in a subtitle
	public List<Subtitle> getSubtitles(String str) {
		srtList.findFirst();
		List<Subtitle> a = new LinkedList<Subtitle>();

		while (!srtList.last()) {
			if (srtList.retrieve().getText().toLowerCase().contains(str.toLowerCase())) {
				a.insert(srtList.retrieve());
			}
			srtList.findNext();
		}

		if (srtList.retrieve().getText().toLowerCase().contains(str.toLowerCase())) {
			a.insert(srtList.retrieve());
		}

		return a;
	}

	@Override
	// requires list not empty, this method removes all subtitles that contain
	// specific sub-string in a subtitle
	public void remove(String str) {
		srtList.findFirst();
		while (!srtList.last()) {
			if (srtList.retrieve().getText().contains(str)) {
				srtList.remove();

			}
			else {
			srtList.findNext();
			}
		}

		if (srtList.retrieve().getText().contains(str))
			srtList.remove();

	}

	@Override
	// requires list not empty, this methods replaces all subtitles that contains
	// specific
	// word with given text
	public void replace(String str1, String str2) {
		String a = "" ;
		srtList.findFirst();
		while(!srtList.last()){
			if(srtList.retrieve().getText().toLowerCase().contains(str1.toLowerCase())){
				a = srtList.retrieve().getText().replaceAll(str1, str2);
						srtList.retrieve().setText(a);
					
					
			}
				srtList.findNext();
		}
		
		if(srtList.retrieve().getText().toLowerCase().contains(str1.toLowerCase())){
			a = srtList.retrieve().getText().replaceAll(str1, str2);
					srtList.retrieve().setText(a);
		}
		

	}

	@Override
	// requires list not empty , this method shifts the time (right or left) depending on
	// the offset given , when the end time of a subtitle reaches zero or less
	// it will be removed 
	public void shift(int offset) {
		if (srtList.empty()) {
			return;
		}
	
			srtList.findFirst();

			while (!srtList.last()) {

				int st = convertToM(srtList.retrieve().getStartTime());
				int ed = convertToM(srtList.retrieve().getEndTime());
				st = st + offset;
				ed = ed + offset;
				if (st <= 0) {
					st = 0;
				}
				if (ed <= 0) {
					srtList.remove();
					continue;
				}

				srtList.retrieve().setStartTime(convertToR(st));
				srtList.retrieve().setEndTime(convertToR(ed));

				srtList.findNext();
			}
			int st = convertToM(srtList.retrieve().getStartTime());
			int ed = convertToM(srtList.retrieve().getEndTime());
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
			srtList.retrieve().setStartTime(convertToR(st));
			srtList.retrieve().setEndTime(convertToR(ed));
		}

	

	@Override
	// requires list not empty and end time is greater than start time , 
	//this method will remove all subtitle in the given interval and shifts the rest 
	//  of the subtitles to accommodate the time    
	public void cut(Time startTime, Time endTime) {
          if(srtList.empty()) {
        	  System.err.println("List is empty!");
        	  return;
          }
		int st = convertToM(startTime);
		int et = convertToM(endTime);
		// System.out.println("235");
		srtList.findFirst();
		Subtitle s = srtList.retrieve();
		// System.out.println("237");
		// int j = 1;
		if (et < st) {
			System.out.println("End time cannot be less than start time! try again");
			return;
		}
		while (!srtList.last()) {
			// //System.out.println("239");
			//
			int stl = convertToM(srtList.retrieve().getStartTime());
			int etl = convertToM(srtList.retrieve().getEndTime());
			if (stl <= st && etl >= st) { // started before interval and ended inside it
				// System.out.println("st: " + st + " et: " + et + " stl: " + stl + " etl: " +
				// etl);
				// System.out.println("delete 1 ");
				srtList.remove();
				continue;
			} else if (stl >= st && stl <= et) {
				// System.out.println("st: " + st + " et: " + et + " stl: " + stl + " etl: " +
				// etl);
				// System.out.println("delete 2 ");
				srtList.remove();
				continue;

			}

			if (stl > et && !s.equals(srtList.retrieve())) {
				int Shift = et - st+1;
				
				stl =stl- Shift;
				etl =etl- Shift;

				srtList.retrieve().setStartTime(convertToR(stl));
				srtList.retrieve().setEndTime(convertToR(etl));
			}

			srtList.findNext();

		}
		int stl = convertToM(srtList.retrieve().getStartTime());
		int etl = convertToM(srtList.retrieve().getEndTime());

		if (stl <= st && etl >= st) { // started before interval and ended inside it
			// System.out.println("st: " + st + " et: " + et + " stl: " + stl + " etl: " +
			// etl);
			// System.out.println("delete 1 ");
			srtList.remove();

		} else if (stl >= st && stl <= et) {
			// System.out.println("st: " + st + " et: " + et + " stl: " + stl + " etl: " +
			// etl);
			// System.out.println("delete 2 ");
			srtList.remove();

		}

		if (stl > et && !s.equals(srtList.retrieve())) {
			int Shift = et - st+1;

			stl =stl- Shift;
			etl =etl- Shift;
            
			srtList.retrieve().setStartTime(convertToR(stl));
			srtList.retrieve().setEndTime(convertToR(etl));
		}

	}

}
