package by.bsuir.iit.aipos.popclient;

import java.net.*;
import java.io.*;

/** �����, ����������� ������ � POP3 ��������
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


	/** ������������ � �������
	 * @param server ����� �������, � �������� ������������
	 * @param port ���� �����������
	 * @throws Exception ���� ��������� ���������� � ��������
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
			throw new Exception("��� ���������� � ��������");
	}

	/** ����������� �� �������
	 * @param userLogin ��� ������������ �� �������
	 * @param userPassword ������ �� �������
	 * @return true ���� ������� ������������, ����� false
	 * @throws Exception ���� ������� ����� ����� ��� ������
	 * @throws Exception ���� ��� ���������� � ��������
	 */
	public boolean login(String userLogin, String userPassword) throws Exception {
		if(isConnected) {
			if(!checkResponce("USER " + userLogin) || !checkResponce("PASS " + userPassword))
				throw new Exception("�������� ����� ��� ������");
			isLoggedIn = true;
		} else {
			throw new Exception("������� ����� ����������� � ��������");
		}

		return isLoggedIn;
	}

	/** ��������� ������
	 * @return ����� �������
	 * @throws Exception ���� �� �������� �����������
	 */
	public final String status() throws Exception {
		if(!isLoggedIn)
			throw new Exception("������� ����� ��������������");
		sendRequest("STAT");
		return mLastReceivedMessage;
	}


	/** �������� ���������
	 * @param number ����� ���������
	 * @return �������� ������
	 * @throws Exception ���� �� �������� �����������
	 */
	public final Email retrieve(int number) throws Exception {
		if(!isLoggedIn)
			throw new Exception("������� ����� ��������������");
		
		sendRequest("RETR " + number);
		Email email = new Email(mLastReceivedMessage);

		return email;
	}

	/** ������� ����������
	 * @throws Exception ���� ��������� ������ �������� ����������
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
			throw new Exception("������ �������� ����������");
		}
	}

	/** �������� ������ ������� ������ � ��������
	 * @return ������� ������ � ��������
	 */
	public String getLog() {
		return mLog;
	}

	/** �������� ��������� ��������� ������� � ��������� ��������� �����������
	 * @return true, ���� �� � �������
	 * @throws Exception ���� ��������� ������ ��� ��������� ������
	 */
	private boolean checkResponce() throws Exception {
		sendRequest("");
		return mLastReceivedMessage.startsWith("+OK");
	}

	/** �������� ��������� ������� �� ������ � ��������� ��������� �����������
	 * @param request - ������
	 * @return true, ���� �� � �������
	 * @throws Exception ���� ��������� ������ ��� ��������� ������
	 */
	private boolean checkResponce(String request) throws Exception {
		sendRequest(request);
		return mLastReceivedMessage.startsWith("+OK");
	}

	/** ������� ������ �������
	 * @param request - ������
	 * @throws Exception ���� ��������� ������ ��� ��������� ������
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
			throw new Exception("������ ��� ��������� ������");
		}
	}
}
