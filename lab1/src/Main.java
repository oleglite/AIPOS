//package by.bsuir.iit.aipos.popclient;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
	//	PopConnection pop = new PopConnection();
		try {
			MainFrame mainframe = new MainFrame();
			mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainframe.show();

//			pop.connect("pop.mail.ru", 110);
//			pop.login("mlite@mail.ru", "1234qwer");
//			pop.status();
//			pop.retrieve(1);
//			pop.close();

		//	System.out.print(pop.getLog());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
