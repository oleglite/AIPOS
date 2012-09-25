import java.net.*;
import java.io.*;

public class PopConnection {
	private Socket mSocket;
	private BufferedReader mInput;
	private PrintWriter mOutput;
	
	private boolean isConnected = false;
	private boolean isLoggedIn = false;
	
	private String mLog = "";
	private String mLastReceivedMessage;
	
	/** ������������ � �������
	 * @param server - ����� �������, � �������� ������������
	 * @param port - ���� �����������
	 */
	public void connect(String server, int port) throws UnknownHostException, IOException {
		mSocket = new Socket(server, port);
		mSocket.setSoTimeout(600);
		
		mInput = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "CP1251"));
		mOutput = new PrintWriter(mSocket.getOutputStream(), true);
				
		isConnected = checkResponce();
	}
	
	/** ������������
	 * @param userLogin - ��� ������������ �� �������
	 * @param userPassword - ������ �� �������
	 * @return true ���� ������� ������������, ����� false
	 * @throws Exception ���� ��� ����������� � �������
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
	
	/** ��������� ������ 
	 * @return ������ (����� �������)
	 * @throws Exception ���� ��� ����������� � �������
	 */
	public final String status() throws Exception {
		if(!isLoggedIn)
			throw new Exception("you are not logged in");
		sendRequest("STAT");
		return mLastReceivedMessage;
	}
	
	/** �������� ������
	 * @param number - ����� ������, ������� � 1
	 * @return ������ ��������� ������, ������� ���������
	 * @throws Exception ���� ��� ����������� � �������
	 */
	public final String retrieve(int number) throws Exception {
		if(!isLoggedIn)
			throw new Exception("you are not logged in");
		sendRequest("RETR " + number);
		return mLastReceivedMessage;
	}
	
	/** ������� ����������
	 */
	public void close() throws IOException {
		sendRequest("QUIT");
		mSocket.close();
	}
	
	/** �������� ������ ������� ������ � ��������
	 * @return ���
	 */
	public String getLog() {
		return mLog;
	}
	
	/** �������� ��������� ��������� ������� � ��������� ��������� �����������
	 * @return true, ���� �� � �������
	 */
	private boolean checkResponce() throws IOException {
		sendRequest("");
		return mLastReceivedMessage.startsWith("+OK");
	}
	
	/** �������� ��������� ������� �� ������ � ��������� ��������� �����������
	 * @param request - ������
	 * @return true, ���� �� � �������
	 */
	private boolean checkResponce(String request) throws IOException {
		sendRequest(request);
		return mLastReceivedMessage.startsWith("+OK");
	}
	
	/** ������� ������ �������
	 * @param request - ������
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