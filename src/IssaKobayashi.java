import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;


public class IssaKobayashi {

	public static void main(String[] args) {

		try {
			System.out.println("Loading Issa Kobayashi Haiku Database...");
			String result = HaikuUtils.readRawHaikuFile("Issa_Haiku_Archive.html", StandardCharsets.UTF_8);
			System.out.println("Done Loading.");
			System.out.println();
			
			//System.out.println(result);
			String beginKey = "<p class=\"english\">";
			String endKey = "</p>";
			String newLineKey = "<br />";
			
			System.out.println("Parsing Haikus...");
			List<Haiku> haikuList = HaikuUtils.parseRawPoems(result, beginKey, endKey, newLineKey);
			
			System.out.println("Done. Processed " + haikuList.size() + " haikus");
			System.out.println();
			
			System.out.println("Beginnig Line Analysis...");
			Tuple<LineStats[], ArrayList<HaikuModel>> analysis = HaikuUtils.analyzeLineStats(haikuList);
			LineStats [] lineStats = analysis.x;
			ArrayList<HaikuModel> models = analysis.y;
			
			System.out.println("Done Analysis");
			System.out.println();
			
			System.out.println("Beginnig Word Analysis...");
			Triple<HashMap<String, ArrayList<String>>,HashMap<String, ArrayList<String>>,HashMap<String, ArrayList<String>>> tt = HaikuUtils.getWordLinkage(haikuList);
			HashMap<String, ArrayList<String>> wlink1 = tt.x; 
			HashMap<String, ArrayList<String>> wlink2 = tt.y;
			HashMap<String, ArrayList<String>> wlink3 = tt.z;
			
			System.out.println("-----------------------------------");
			System.out.println();
			/*System.out.println(lineStats[0].getWordCountsForLine().entrySet());
			System.out.println();
			System.out.println();
			System.out.println(lineStats[1].getWordCountsForLine().entrySet());
			System.out.println();
			System.out.println();
			System.out.println(lineStats[2].getWordCountsForLine().entrySet());*/
			
			//Pick random model here
			System.out.println("Ready to generate poems. Press [ENTER] to continue or type 'exit' to quit.");
			Random rand = new Random();
			int xx =0;

			Scanner reader = new Scanner(System.in);
			while(reader.nextLine().compareToIgnoreCase("exit") != 0){
				int i = rand.nextInt(models.size());
				HaikuModel chosenModel = models.get(i);
				Iterator<HaikuModelElement> iter = chosenModel.iterator();
				String yolo = "";
				String previousWord = "";
				int lineNumber = 1;
				while(iter.hasNext()){
					HaikuModelElement nextElem = iter.next();
					if(nextElem.isNewLine()){
						yolo += "\n";
						lineNumber++;
						previousWord = "";
					}
					else{
						int sy = nextElem.getSyllables();
						int wl = nextElem.getWordLength();
						//System.out.println(sy + ":" + wl);
						Map<String, Integer> words;
						HashMap<String, ArrayList<String>> wordLinks;
						ArrayList<String> validWords = new ArrayList<String>();
						if(lineNumber == 1){
							words = lineStats[0].getWordCountsForLine();
							wordLinks = wlink1;
						}
						else if(lineNumber == 2){
							words = lineStats[1].getWordCountsForLine();
							wordLinks = wlink2;
						}
						else{
							words = lineStats[2].getWordCountsForLine();
							wordLinks = wlink3;
						}
						//System.out.println(wordLinks.entrySet());
						
						for(String w : words.keySet()){
							StringTokenizer tk = new StringTokenizer(w,"|");
							String baseWord = tk.nextToken();
							int syllables = Integer.parseInt(tk.nextToken());
							if(baseWord.length() == wl && syllables == sy){
								for(int x = 0; x < words.get(w); x++){
									validWords.add(baseWord);
								}
							}
						}
						int index = rand.nextInt(validWords.size());
						if(previousWord.equals("")){
							yolo += validWords.get(index) + " ";
							previousWord = validWords.get(index);
						}
						else{
							ArrayList<String> validWords2 =  wordLinks.get(previousWord);
							if (validWords2 != null && validWords2.size() > 0){
								//System.out.println("YES");
								boolean found = false;
								EnglishSyllableCounter scounter = new EnglishSyllableCounter();
								for (String vword : validWords2){
									if(scounter.countSyllables(vword) >= sy){
										found = true;
										yolo += vword + " ";
										previousWord = vword;
										break;
									}
								}
								if(!found){
									//System.out.println(validWords2.size());
									index = rand.nextInt(validWords2.size());
									yolo += validWords2.get(index) + " ";
									previousWord = validWords2.get(index);
								}
								
							}
							else{
								yolo += validWords.get(index) + " ";
								previousWord = validWords.get(index);
							}
						}
						
					}
				}
				
				System.out.println("Poem #" + (xx+1));
				System.out.println(yolo);
				System.out.println();
				
				xx++;
				//System.out.println("Press [ENTER] to continue or type 'exit' to quit.");
				
			}
			reader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("An error occured parsing the Haiku archive:\n" + e.toString());
		}

	}

}
