import java.net.*;
import java.util.List;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class PopConnection {
	private Socket mSocket;
	private BufferedReader mInput;
	private PrintWriter mOutput;
	private Proxy mProxy;

	private boolean isConnected = false;
	private boolean isLoggedIn = false;

	private String mLog = "";
	private String mLastReceivedMessage;
	
	public void setProxy(String proxyAddress, int proxyPort, final String proxyLogin, final String proxyPassword) {
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				PasswordAuthentication p = new PasswordAuthentication(proxyLogin, proxyPassword.toCharArray());
				return p;
			}
		});
		
		mProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort));
	}

	/** Подключиться к серверу
	 * @param server - адрес сервера, к которому подключаемся
	 * @param port - порт подключения
	 */
	public void connect(String server, int port) throws UnknownHostException, IOException {
		if(mProxy == null) {
			mSocket = new Socket(server, port);
		} else {
			mSocket = new Socket(mProxy);
			mSocket.connect(new InetSocketAddress(server, port));
		}
		
		mSocket.setSoTimeout(600);

		mInput = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "UTF-8"));
		mOutput = new PrintWriter(mSocket.getOutputStream(), true);

		isConnected = checkResponce();
	}

	/** Залогиниться
	 * @param userLogin - имя пользователя на сервере
	 * @param userPassword - пароль на сервере
	 * @return true если успешно залогинились, иначе false
	 * @throws Exception если нет подключения к серверу
	 */
	public boolean login(String userLogin, String userPassword) throws Exception{
		if(isConnected) {
			if(!checkResponce("USER " + userLogin))
				return false;
			if(!checkResponce("PASS " + userPassword))
				return false;
			isLoggedIn = true;
		} else {
			throw new Exception("you are not connected to the server");
		}

		return isLoggedIn;
	}

	/** Проверить статус
	 * @return статус (ответ сервера)
	 * @throws Exception если нет подключения к серверу
	 */
	public final String status() throws Exception {
		if(!isLoggedIn)
			throw new Exception("you are not logged in");
		sendRequest("STAT");
		return mLastReceivedMessage;
	}

	/** Получить письмо
	 * @param number - номер письма, начиная с 1
	 * @return полную структуру письма, включая заголовки
	 * @throws Exception если нет подключения к серверу
	 */
	public final Email retrieve(int number) throws Exception {
		if(!isLoggedIn)
			throw new Exception("you are not logged in");
		sendRequest("RETR " + number);
		Email email = new Email(mLastReceivedMessage, "CP866");
		
		System.out.println(mLastReceivedMessage);
		
		/*
		System.out.println(mLastReceivedMessage);
		System.out.println(email.getFieldDate() + " | " + email.getFieldFrom() + " | " + email.getFieldSubject());
		System.out.println(email.getFieldMessage());
		List<Image> images = email.getReceivedImages();
		if(images == null || !images.isEmpty()) {
			for (Image image : images) {
				File file = new File("D:/021702/1.png");
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            ImageIO.write((RenderedImage) image, "png", file);
			}
		}*/
		return email;
	}

	/** Закрыть соединение
	 */
	public void close() throws IOException {
		sendRequest("QUIT");
		mSocket.close();
	}

	/** Получить полную историю работы с сервером
	 * @return лог
	 */
	public String getLog() {
		return mLog;
	}

	/** Получить последнее сообщение сервера и проверить состояние подключения
	 * @return true, если всё в порядке
	 */
	private boolean checkResponce() throws IOException {
		sendRequest("");
		return mLastReceivedMessage.startsWith("+OK");
	}

	/** Получить сообщение сервера на запрос и проверить состояние подключения
	 * @param request - запрос
	 * @return true, если всё в порядке
	 */
	private boolean checkResponce(String request) throws IOException {
		sendRequest(request);
		return mLastReceivedMessage.startsWith("+OK");
	}

	/** Послать запрос серверу
	 * @param request - запрос
	 */
	private void sendRequest(String request) throws IOException {
		try {
			if(!request.isEmpty()){
				mOutput.println(request);
				mLog += request + '\n';
			}
			String line = "";
			mLastReceivedMessage = "";
			while(true) {
				line = mInput.readLine();
				if(line == null)
					break;
				mLastReceivedMessage += line + '\n';
			}
		}
		catch(final SocketTimeoutException e) {
			mLog += mLastReceivedMessage;
		}
	}
}
