import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class HaikuUtils {

	public static String readRawHaikuFile(String path, Charset encoding) throws IOException {
		String baseDir = System.getProperty("user.dir");
		byte[] encoded = Files.readAllBytes(Paths.get(baseDir + "/src/" + "Issa_Haiku_Archive.html"));
		return new String(encoded, encoding);
	}
	
	public static List<Haiku> parseRawPoems(String rawHTML, String beginKey, String endKey, String newLineKey){
		List<Haiku> poems = new ArrayList<>();
		
		int numPoems = 0;
		while(rawHTML.indexOf(beginKey) != -1){
			rawHTML = rawHTML.substring(rawHTML.indexOf(beginKey) + beginKey.length());
			
			
			int endKeyIndex = rawHTML.indexOf(endKey);
			if(endKeyIndex == -1){
				System.err.println("Failure parsing raw HTML: Begin-key without matching end-key.");
				return null;
			}
			
			String rawPoem = rawHTML.substring(0,endKeyIndex);
			rawPoem = rawPoem.replaceAll(newLineKey, "");
			rawPoem = rawPoem.trim();
			rawPoem = rawPoem.toLowerCase();
			rawPoem = rawPoem.replaceAll("[^a-z0-9 \n']", "");
			
			Haiku newHaiku = new Haiku(rawPoem);
			poems.add(newHaiku);
			
			numPoems++;
			
			/*System.out.println("Poem #" + numPoems);
			System.out.println(newHaiku.getFirstLine());
			System.out.println(newHaiku.getSecondLine());
			System.out.println(newHaiku.getThirdLine());
			System.out.println();*/
			
			rawHTML = rawHTML.substring(endKeyIndex + endKey.length());
			
		}
		
		
		return poems;
	}
	
	
	public static Tuple<LineStats [], ArrayList<HaikuModel>> analyzeLineStats(List<Haiku> haikuList) {
		LineStats stat1 = new LineStats();
		LineStats stat2 = new LineStats();
		LineStats stat3 = new LineStats();
		
		/*Map<String,Integer> forms = new HashMap<String,Integer>();*/
		ArrayList<HaikuModel> models = new ArrayList<HaikuModel>();
		for (Haiku haiku : haikuList){
			
			/*String form = HaikuStats.calcHaikuForm3(haiku);
			if(forms.containsKey(form)){
				forms.put(form, forms.get(form) + 1);
			}
			else{
				forms.put(form, 1);
			}*/
			models.add(new HaikuModel(haiku));
			stat1.processLine(haiku.getFirstLine());
			stat2.processLine(haiku.getSecondLine());
			stat3.processLine(haiku.getThirdLine());
		}
		
		//System.err.println(sortByValue(forms).entrySet());
		
		LineStats [] lineStats = {stat1, stat2, stat3};
		
		return new Tuple<LineStats[], ArrayList<HaikuModel>>(lineStats, models);
		
	}
	
	private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue()) * -1;
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
}
