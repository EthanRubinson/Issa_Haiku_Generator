import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class HaikuModel implements Iterable{
		private ArrayList<HaikuModelElement> model;
		
		public HaikuModel(Haiku h){
			model = new ArrayList<HaikuModelElement>();
			EnglishSyllableCounter counter = new EnglishSyllableCounter();
			String [] lines = {h.getFirstLine(), h.getSecondLine(), h.getThirdLine()};
			
			for(String line : lines){
				StringTokenizer token = new StringTokenizer(line, " ");
				
				while(token.hasMoreTokens()){
					String t = token.nextToken();
					model.add( new HaikuModelElement(counter.countSyllables(t), t.length()));
					
				}
				model.add( new HaikuModelElement());
			}
		}

		@Override
		public Iterator<HaikuModelElement> iterator() {
			return model.iterator();
		}
		
		
	}