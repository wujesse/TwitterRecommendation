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
		Document d6 = new Document("a_story_of_the_west_john_habberton.txt");
		Document d7 = new Document("all_he_knew_john_habberton.txt");
		Document d8 = new Document("budge_toddie_john_habberton.txt");
		Document d9 = new Document("ochard_and_vineyard_sackville-west.txt");
		Document d10 = new Document("outlines_of_a_philosophy.txt");
		Document d11 = new Document("the_snare_sabatani.txt");
		Document d12 = new Document("who_was_paul_grayson_john_habberton.txt");
		Document d13 = new Document("the_arkansaw_bear.txt");
		
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
		documents.add(d6);
		documents.add(d7);
		documents.add(d8);
		documents.add(d9);
		documents.add(d10);
		documents.add(d11);
		documents.add(d12);
		documents.add(d13);
		
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
