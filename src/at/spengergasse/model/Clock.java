package at.spengergasse.model;

public class Clock {
	private long hr; // store hours
	private long min; // store minutes
	private long sec; // store seconds
	private long milliSec;

	public Clock() {
		this.hr = 0;
		this.min = 5;
		this.sec = 0;
		this.milliSec = 0;
	}


	// Method to return the hours
	public long getHours() {
		return hr;
	}

	// Method to return the minutes
	public long getMinutes() {
		return min;
	}

	// Method to return the seconds
	public long getSeconds() {
		return sec;
	}
	
	public void incrementMilliSeconds() {
		milliSec ++;
		if(milliSec > 59) {
			milliSec = 0;
			incrementSeconds(); // increment seconds
		}
	}

	public void incrementSeconds() {
		sec--;

		if (sec < 0) {
			sec = 59;
			incrementMinutes(); // increment minutes
		}
	}

	public void incrementMinutes() {
		min--;

		if (min < 0) {
			min = 59;
			incrementHours(); // increment hours
		}
	}

	public void incrementHours() {
		hr++;
		if (hr > 23)
			hr = 0;
	}

	public void printTime() {
		if (hr < 10)
			System.out.print("0");
		System.out.print(hr + ":");
		if (min < 10)
			System.out.print("0");
		System.out.print(min + ":");
		if (sec < 10)
			System.out.print("0");
		System.out.print(sec);
		System.out.println("\n");
	}


	@Override
	public String toString() {
		String text = "";
		if(min < 10) {
			text = "0";
		}
		text += min + ":";
		if(sec < 10) {
			text += "0";
		}
		text += sec;
		return text;
	}
	
	
}