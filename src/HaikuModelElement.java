public class HaikuModelElement{
		private int syllables;
		private int wordLength;
		private boolean isNewLine;
		
		public HaikuModelElement(){
			syllables = 0;
			wordLength = 0;
			isNewLine = true;
		}
		
		public HaikuModelElement(int sy, int wo){
			syllables = sy;
			wordLength = wo;
			isNewLine = false;
		}
		
		public boolean isNewLine(){
			return isNewLine;
		}
		public int getSyllables(){
			return syllables;
		}
		public int getWordLength(){
			return wordLength;
		}
		
	}