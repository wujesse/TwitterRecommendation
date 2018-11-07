import java.util.Scanner;

public class TestClass {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a twitter username to get recommendations for (ommit the '@').");
		String name = in.next();
		Recommender rec = new Recommender(name);
		
		System.out.println("You should follow these users for content you like:\n\n");
		for (String str : rec.getRecommendedUsers(10)) {
			if (!str.equals(name)){
				System.out.println(str);
			}
		}
		in.close();
	}
}