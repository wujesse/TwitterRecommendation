import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * the tester class.
 * @author swapneel
 */
public class VectorSpaceModelTester {

	public static void main(String[] args) {
		
		Document query = new Document("query.txt");
//		Document hpcs = new Document("hp-cs.txt");
//		Document hpdh = new Document("hp-dh.txt");
//		Document gray = new Document("50-shades.txt");
		Document d1 = new Document("alice-in-wonderland.txt");
		Document d2 = new Document("huck-finn.txt");
		Document d3 = new Document("les-mis.txt");
		Document d4 = new Document("tom-sawyer.txt");
		Document d5 = new Document("pride-prejudice.txt");
		
		ArrayList<Document> documents = new ArrayList<Document>();
		documents.add(query);
//		documents.add(hpcs);
//		documents.add(hpdh);
//		documents.add(gray);
		documents.add(d1);
		documents.add(d2);
		documents.add(d3);
		documents.add(d4);
		documents.add(d5);
		
		Corpus corpus = new Corpus(documents);
		
		VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
		
//		for (int i = 0; i < documents.size(); i++) {
//			for (int j = i + 1; j < documents.size(); j++) {
//				Document doc1 = documents.get(i);
//				Document doc2 = documents.get(j);
//				System.out.println("\nComparing " + doc1 + " and " + doc2);
//				System.out.println(vectorSpace.cosineSimilarity(doc1, doc2));
//			}
//		}
		
		for(int i = 1; i < documents.size(); i++) {
			Document doc = documents.get(i);
			System.out.println("\nComparing to " + doc);
			System.out.println(vectorSpace.cosineSimilarity(query, doc));
		}
		
		
		
	}

}
