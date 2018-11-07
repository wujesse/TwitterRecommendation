import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * This class represents one document.
 * It will keep track of the term frequencies.
 * @author swapneel
 *
 */
public class Document implements Comparable<Document> {
	
	/**
	 * A hashmap for term frequencies.
	 * Maps a term to the number of times this terms appears in this document. 
	 */
	private HashMap<String, Integer> termFrequency;
	private String text;
	private long id;
	
	/**
	 * The constructor.
	 * It takes in the text to read.
	 * It will read the file and pre-process it.
	 * @param a String text to analyze
	 */
	public Document(long id, String text) {
		this.text = text;
		this.id = id;
		termFrequency = new HashMap<String, Integer>();

		readFileAndPreProcess();
	}
	
	/**
	 * This method will read in the file and do some pre-processing.
	 * The following things are done in pre-processing:
	 * Every word is converted to lower case.
	 * Every character that is not a letter or a digit is removed.
	 * We don't do any stemming.
	 * Once the pre-processing is done, we create and update the 
	 */
	private void readFileAndPreProcess() {
		Scanner in = new Scanner(text);
		
		while (in.hasNext()) {
			String nextWord = in.next();
			
			String filteredWord = nextWord.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
			
			if (!(filteredWord.equalsIgnoreCase(""))) {
				if (termFrequency.containsKey(filteredWord)) {
					int oldCount = termFrequency.get(filteredWord);
					termFrequency.put(filteredWord, ++oldCount);
				} else {
					termFrequency.put(filteredWord, 1);
				}
			}
		}
		
		in.close();
	}
	
	/**
	 * This method will return the term frequency for a given word.
	 * If this document doesn't contain the word, it will return 0
	 * @param word The word to look for
	 * @return the term frequency for this word in this document
	 */
	public double getTermFrequency(String word) {
		if (termFrequency.containsKey(word)) {
			return termFrequency.get(word);
		} else {
			return 0;
		}
	}
	
	/**
	 * This method will return a set of all the terms which occur in this document.
	 * @return a set of all terms in this document
	 */
	public Set<String> getTermList() {
		return termFrequency.keySet();
	}
	
	public long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Document) {
			return id == ((Document) o).getId();
		}
		else {
			return false;
		}
	}
	
	@Override
	/**
	 * The overriden method from the Comparable interface.
	 */
	public int compareTo(Document other) {
		return text.compareTo(other.toString());
	}
	
	/**
	 * This method is used for pretty-printing a Document object.
	 * @return the filename
	 */
	public String toString() {
		return text;
	}
}