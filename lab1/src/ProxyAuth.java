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
		String proxyHost = "192.168.251.1";     // Прокси IP
		int proxyPort = 8080;             	    // Прокси порт
		String proxyLogin = "a0323592";         // Прокси логин
		String proxyPass = "123";     			// Прокси пароль

		// Инициализируем функцию проверки прокси
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

			// Объединяем логин с паролем
			String auth = proxyLogin + ":" + proxyPass;

			// Кодируем в Base64
		        String encodedPassword = new String(DatatypeConverter.printBase64Binary(auth.getBytes()));

		        // Устанавливаем заголовок
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
