import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;



public class HaikuStats {
	
	
	public static String calcHaikuForm(Haiku h){
		int syllableCount1 = 0;
		int syllableCount2 = 0;
		int syllableCount3 = 0;
		
		EnglishSyllableCounter counter = new EnglishSyllableCounter();
		String [] lines = {h.getFirstLine(), h.getSecondLine(), h.getThirdLine()};
		
		int index =1;
		for(String line : lines){
			StringTokenizer token = new StringTokenizer(line, " ");
			int runningCount = 0;
			while(token.hasMoreTokens()){
				runningCount += counter.countSyllables(token.nextToken());
			}
			if(index == 1){
				syllableCount1 = runningCount;
			}
			else if(index == 2){
				syllableCount2 = runningCount;
			}
			else{
				syllableCount3 = runningCount;
			}
			runningCount = 0;
			index++;
		}
		return syllableCount1 + " " + syllableCount2 + " " + syllableCount3;
	}
	
	public static String calcHaikuForm2(Haiku h){
		String format = "";
		
		EnglishSyllableCounter counter = new EnglishSyllableCounter();
		String [] lines = {h.getFirstLine(), h.getSecondLine(), h.getThirdLine()};
		
		for(String line : lines){
			StringTokenizer token = new StringTokenizer(line, " ");
			boolean first = true;
			while(token.hasMoreTokens()){
				if(first){
					format += counter.countSyllables(token.nextToken());
				}
				else{
					format += " " + counter.countSyllables(token.nextToken());
				}
			}
			format += ":";
		}
		return format;
	}
	
	public static String calcHaikuForm3(Haiku h){
		String format = "";
		
		EnglishSyllableCounter counter = new EnglishSyllableCounter();
		String [] lines = {h.getFirstLine(), h.getSecondLine(), h.getThirdLine()};
		
		for(String line : lines){
			StringTokenizer token = new StringTokenizer(line, " ");
			boolean first = true;
			while(token.hasMoreTokens()){
				String t = token.nextToken();
				if(first){
					
					format += counter.countSyllables(t) + "|" + t.length();
					first = false;
				}
				else{
					format += " " + counter.countSyllables(t) + "|" + t.length();
				}
			}
			format += ":";
		}
		return format;
	}
	
	
	
	
}
