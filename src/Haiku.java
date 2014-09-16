import java.util.StringTokenizer;


public class Haiku {

	private String firstLine;
	private String secondLine;
	private String thirdLine;
	
	public Haiku(String rawHaiku){
		StringTokenizer tokener = new StringTokenizer(rawHaiku, "\n");
		firstLine = tokener.nextToken();
		secondLine = tokener.nextToken();
		thirdLine = tokener.nextToken();
	}
	
	public String getFirstLine(){
		return firstLine;
	}
	
	public String getSecondLine(){
		return secondLine;
	}
	
	public String getThirdLine(){
		return thirdLine;
	}
	
}
