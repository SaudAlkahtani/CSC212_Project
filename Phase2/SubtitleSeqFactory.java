import java.io.*;
import java.util.Scanner;

public class SubtitleSeqFactory {

	private static SubtitleSeq list;

	// Return an empty subtitles sequence
	public static SubtitleSeq getSubtitleSeq() {

		SubtitleSeq l = new SS1();
		return l;
	}

	// Load a subtitle sequence from an SRT file. If the file does not exist or
	// is corrupted (incorrect format), null is returned.
	public static SubtitleSeq loadSubtitleSeq(String fileName) throws Exception, FileNotFoundException {

		// add catch for incorrect
		// format!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		// LinkedList<Subtitle> l1 = new LinkedList<Subtitle>();
		list = new SS1();

		try {

			File file = new File(fileName);

			Scanner input = new Scanner(file);

			String strLine;
			Subtitle s;
			String content;
			int seq = 0;
			int sumOfSeq = 0;
			int sumOfLSeq = 0;
			int stT = 0, enT = 0;
			while (input.hasNextLine()) {
				// making sure sequence number is correct!
				strLine = input.nextLine();// seq number

				seq = Integer.parseInt(strLine);// add all seq numbers together (in the end we check them)
				sumOfSeq += seq;

				strLine = input.nextLine();
				// checking if "-->" is missing
				if (!strLine.contains("-->")) {
					input.close();
					System.err.println(" '-->' is missing from the subtitle at line " + seq + "!");
					return null;
				}

				// setting time to an object
				Time st = parseStartTime(strLine);
				Time et = parseEndTime(strLine);
				// check if end time is less than start time
				stT = convertToM(st);
				enT = convertToM(et);
				if (enT < stT) {
					input.close();
					System.err.println("End time can't be less than start Time!");
					return null;
				}

				// setting contents
				strLine = input.nextLine();// took the first line
				content = "";

				while (!strLine.isEmpty()) {

					content += strLine;
					if (input.hasNext()) {
						strLine = input.nextLine();
						if (!strLine.equals(""))
							content += "\n";
					}

					else {
						break;
					}

				}

				s = new SubtitleClass(st, et, content);

				// displaySubtitles(s);
				list.addSubtitle(s);
				seq++;

			}

			// Check sequence numbering!!
			List<Subtitle> ordList = list.getSubtitles();
			int Lseq = 0;
			if (!ordList.empty()) {

				
				ordList.findFirst();
				while (!ordList.last()) {

					Lseq++;
					ordList.findNext();

				}
				Lseq++;
				

				sumOfLSeq = (Lseq * (Lseq + 1)) / 2;
				
			}
			
			
			if (!(sumOfLSeq == sumOfSeq)) {
				input.close();
				System.err.println("Sequnece number doesnt match number of subtitles!");
				return null;

			}
			input.close();

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + " File not found or corrupted");
			return null;

		} catch (Exception e) {
			System.out.println(e.getMessage() + " an Error happned");
			return null;
		}

		return list;
	}

	private static Time parseStartTime(String line) {
		// start time
		Time st = new TimeI();
		st.setHH(Integer.parseInt(line.substring(0, 2)));// hh
		st.setMM(Integer.parseInt(line.substring(3, 5)));// mm
		st.setSS(Integer.parseInt(line.substring(6, 8)));// ss
		st.setMS(Integer.parseInt(line.substring(9, 12)));// ms
		return st;

	}

	private static Time parseEndTime(String line) {
		// end time
		Time et = new TimeI();
		// System.out.println(line.substring(17, 29));
		et.setHH(Integer.parseInt(line.substring(17, 19)));
		et.setMM(Integer.parseInt(line.substring(20, 22)));
		et.setSS(Integer.parseInt(line.substring(23, 25)));
		et.setMS(Integer.parseInt(line.substring(26, 29)));
		return et;
	}

	// private static void displayTime(Time t1) {
	// System.out.println((t1.getHH() + ":" + t1.getMM() + ":" + t1.getSS() + "," +
	// t1.getMS()));
	//
	// }
	private static void displaySubtitles(Subtitle s) {
		Time t1 = s.getStartTime();

		System.out.print((t1.getHH() + ":" + t1.getMM() + ":" + t1.getSS() + "," + t1.getMS()));
		System.out.print(" --> ");
		Time t2 = s.getEndTime();
		System.out.println((t2.getHH() + ":" + t2.getMM() + ":" + t2.getSS() + "," + t2.getMS()));
		System.out.println(s.getText());
	}

	private static int convertToM(Time t) {// helping method to convert time object to millisecond
		int h = t.getHH() * 3600000;
		int m = t.getMM() * 60000;
		int s = t.getSS() * 1000;
		int ms = t.getMS();
		int Stime = h + m + s + ms;
		return Stime;
	}
}
