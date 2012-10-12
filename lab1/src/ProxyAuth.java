//package by.bsuir.iit.aipos.popclient;


import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import javax.xml.bind.DatatypeConverter;


public class ProxyAuth {
private final static String testSite = "http://yandex.ru/";

	public static void main(String argv[]) {
		String proxyHost = "192.168.251.1";     // ������ IP
		int proxyPort = 8080;             	    // ������ ����
		String proxyLogin = "a0323592";         // ������ �����
		String proxyPass = "123";     			// ������ ������

		// �������������� ������� �������� ������
		boolean res = TestProxy(proxyHost, proxyPort, proxyLogin, proxyPass);
		System.out.print(proxyHost + ":" + proxyPort + " " + (res ? "ALIVE" : "DEAD"));
	}

	public static boolean TestProxy(String proxyHost, int proxyPort, String proxyLogin,
			String proxyPass) {
		SocketAddress proxyAddr = new InetSocketAddress(proxyHost, proxyPort);
		Proxy httpProxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
		HttpURLConnection urlConn = null;
		URL url;
		try {
			url = new URL(testSite);
			urlConn = (HttpURLConnection) url.openConnection(httpProxy);

			// ���������� ����� � �������
			String auth = proxyLogin + ":" + proxyPass;

			// �������� � Base64
		        String encodedPassword = new String(DatatypeConverter.printBase64Binary(auth.getBytes()));

		        // ������������� ���������
			urlConn.setRequestProperty("Proxy-Authorization", "Basic "
					+ encodedPassword);

			urlConn.setConnectTimeout(10 * 1000);
			urlConn.setReadTimeout(10 * 1000);
			urlConn.connect();
			return (urlConn.getResponseCode() == 200);
		}
		catch(Exception e) {
			return false;
		}
	}
}
