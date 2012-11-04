package by.bsuir.iit.aipos.popclient;

import java.net.*;
import java.io.*;

/**  ласс, реализующий работу с POP3 сервером
 *
 */
public class PopConnection {
	private Socket mSocket;
	private BufferedReader mInput;
	private PrintWriter mOutput;

	private boolean isConnected = false;
	private boolean isLoggedIn = false;

	private String mLog = "";
	private String mLastReceivedMessage;


	/** ѕодключитьс€ к серверу
	 * @param server адрес сервера, к которому подключаемс€
	 * @param port порт подключени€
	 * @throws Exception если неудалось соединени€ с сервером
	 */
	public void connect(String server, int port) throws Exception {
		mSocket = new Socket(server, port);
		try {
			mSocket.setSoTimeout(600);
			
			mInput = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			mOutput = new PrintWriter(mSocket.getOutputStream(), true);

			isConnected = checkResponce();
			
		} catch(SocketException e) {
			isConnected = false;
		}		
		
		if(!isConnected)
			throw new Exception("нет соединени€ с сервером");
	}

	/** јвторизаци€ на сервере
	 * @param userLogin им€ пользовател€ на сервере
	 * @param userPassword пароль на сервере
	 * @return true если успешно залогинились, иначе false
	 * @throws Exception если неверно введЄн логин или пароль
	 * @throws Exception если нет соединени€ с сервером
	 */
	public boolean login(String userLogin, String userPassword) throws Exception {
		if(isConnected) {
			if(!checkResponce("USER " + userLogin) || !checkResponce("PASS " + userPassword))
				throw new Exception("неверный логин или пароль");
			isLoggedIn = true;
		} else {
			throw new Exception("сначала нужно соединитьс€ с сервером");
		}

		return isLoggedIn;
	}

	/** ѕроверить статус
	 * @return ответ сервера
	 * @throws Exception елси не пройдена авторизаци€
	 */
	public final String status() throws Exception {
		if(!isLoggedIn)
			throw new Exception("сначала нужно авторизоватьс€");
		sendRequest("STAT");
		return mLastReceivedMessage;
	}


	/** ѕолучить сообщение
	 * @param number номер сообщени€
	 * @return прин€тое письмо
	 * @throws Exception если не пройдена авторизаци€
	 */
	public final Email retrieve(int number) throws Exception {
		if(!isLoggedIn)
			throw new Exception("сначала нужно авторизоватьс€");
		
		sendRequest("RETR " + number);
		Email email = new Email(mLastReceivedMessage);

		return email;
	}

	/** «акрыть соединение
	 * @throws Exception если произошла ошибка закрыти€ соединени€
	 */
	public void close() throws Exception {
		try {
			if(isConnected) {
				sendRequest("QUIT");
				mSocket.close();
				isConnected = false;
				isLoggedIn = false;
			}
		} catch (IOException e) {
			throw new Exception("ошибка закрыти€ соединени€");
		}
	}

	/** ѕолучить полную историю работы с сервером
	 * @return истори€ работы с сервером
	 */
	public String getLog() {
		return mLog;
	}

	/** ѕолучить последнее сообщение сервера и проверить состо€ние подключени€
	 * @return true, если всЄ в пор€дке
	 * @throws Exception если произошла ошибка при получении данных
	 */
	private boolean checkResponce() throws Exception {
		sendRequest("");
		return mLastReceivedMessage.startsWith("+OK");
	}

	/** ѕолучить сообщение сервера на запрос и проверить состо€ние подключени€
	 * @param request - запрос
	 * @return true, если всЄ в пор€дке
	 * @throws Exception если произошла ошибка при получении данных
	 */
	private boolean checkResponce(String request) throws Exception {
		sendRequest(request);
		return mLastReceivedMessage.startsWith("+OK");
	}

	/** ѕослать запрос серверу
	 * @param request - запрос
	 * @throws Exception если произошла ошибка при получении данных
	 */
	private void sendRequest(String request) throws Exception {
		try {
			if(!request.isEmpty()){
				mOutput.println(request);
				mLog += "CLIENT: " + request + '\n';
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
			mLog += "SERVER: " + mLastReceivedMessage;
		} catch (IOException e) {
			throw new Exception("ошибка при получении данных");
		}
	}
}
