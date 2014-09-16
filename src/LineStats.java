import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class LineStats {
	private Map<String, Integer> wordCounts;

	public LineStats() {
		wordCounts = new HashMap<String, Integer>();
	}

	public void processLine(String line) {
		StringTokenizer token = new StringTokenizer(line, " ");
		EnglishSyllableCounter counter = new EnglishSyllableCounter();
		while (token.hasMoreTokens()) {
			String word = token.nextToken();
			word += "|" + counter.countSyllables(word);
			if (wordCounts.containsKey(word)) {
				int num = wordCounts.get(word) + 1;
				wordCounts.put(word, num);
			} else {
				wordCounts.put(word, 1);
			}
		}
	}

	public Map<String, Integer> getWordCountsForLine() {
		return sortByValue(wordCounts);
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
