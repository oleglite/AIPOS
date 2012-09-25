import java.io.IOException;
import java.net.UnknownHostException;


public class Main {

	public static void main(String[] args) {
		PopConnection pop = new PopConnection();
		try {
			pop.connect("pop.mail.ru", 110);
			pop.login("mlite@mail.ru", "1234qwer");
			pop.status();
			pop.retrieve(1);
			pop.close();
			
			System.out.print(pop.getLog());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
