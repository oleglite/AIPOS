//package by.bsuir.iit.aipos.popclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class MainFrame extends JFrame {

	PopConnection popCon = new PopConnection();

	public static int Default_Width = 1000;
	public static int Default_Height = 500;


	// #0_1
	// Proxy
	JLabel proxyUserLabel;
	JLabel proxyPassLabel;

	JTextField proxyUserTextField;
	JTextField proxyPassTextField;

	JButton proxyButton;
	Box proxyUserBox;
	Box proxyPassBox;

	// ��� ������ ���� ������
	Box proxyEnterBox;
	
	// #0_2
	// ���� ������ ������� � �����
	JLabel serverAddressLabel;
	JLabel serverPortLabel;

	JTextField serverAddressTextField;
	JTextField serverPortTextField;
	JButton serverEnterButton;
	Box serverBox;
	// ��� ������ ���� ������
	Box serverEnterBox;
	// #1
	// ������ �����
	JLabel serverLoginLabel;
	JTextField serverLoginTextField;
	Box serverLoginBox;

	// #2
	// ������ ������
	JLabel serverPassLabel;
	JTextField serverPassTextField;
	Box serverPassBox;
	// #3
	// ���� ������ � ������ , �������� �������
	JButton enterButton;
	JButton statusButton;
	Box enterAndStatusBox;

	// #5
	// �������� n-� ������
	JLabel getLetterLabel;
	JButton getLetterButton;
	JTextField getLetterTextField;
	Box getLetterBox;
	// #6
	// ������� ��� ����������
	JLabel closeConnectionLabel;
	JButton closeConnectionButton;
	Box closeConnectionBox;
	// #7
	// �������� ������ ������� ������ � ��������
	JLabel getStoryLabel;
	JButton getStoryButton;
	Box getStoryBox;

	// #10
	Box mainTextAreaBox;
	JTextArea mainTextArea;
	JScrollPane mainTextAreaScrollPane;

	public MainFrame() {

		setSize(Default_Width, Default_Height);
		setMinimumSize(new Dimension(Default_Width, Default_Height));
		setMaximumSize(new Dimension(Default_Width, Default_Height));
		setTitle("pop3 client v.0.1.0 beta ");

		// �������� �������� ��������������� �����

		// Proxy

		proxyUserLabel = new JLabel("��� ������������");
		proxyPassLabel = new JLabel("������");

		proxyUserTextField = new JTextField(10);
		proxyPassTextField = new JTextField(10);

		proxyUserTextField.setMaximumSize(proxyUserTextField.getPreferredSize());
		proxyPassTextField.setMaximumSize(proxyPassTextField.getPreferredSize());
		proxyUserBox = Box.createHorizontalBox();
		proxyUserBox.add(proxyUserLabel);
		proxyUserBox.add(Box.createHorizontalStrut(3));
		proxyUserBox.add(proxyUserTextField);
		proxyUserBox.add(proxyPassLabel);
		proxyUserBox.add(Box.createHorizontalStrut(3));
		proxyUserBox.add(proxyPassTextField);

		proxyButton = new JButton("���� ������");
		proxyEnterBox = Box.createHorizontalBox();
		proxyEnterBox.add(proxyButton);

		proxyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainTextArea.append("������ ������ � " + "�����\n");
			}
		});

		// ���� ������ ������� � �����
		serverAddressLabel = new JLabel("����� �������");
		serverPortLabel = new JLabel("����");

		serverAddressTextField = new JTextField("pop.mail.ru");
		serverPortTextField = new JTextField("110");

		serverAddressTextField.setMaximumSize(serverAddressTextField.getPreferredSize());
		serverPortTextField.setMaximumSize(serverPortTextField.getPreferredSize());
		serverBox = Box.createHorizontalBox();
		serverBox.add(serverAddressLabel);
		serverBox.add(Box.createHorizontalStrut(6));
		serverBox.add(serverAddressTextField);
		serverBox.add(Box.createHorizontalStrut(10));
		serverBox.add(serverPortLabel);
		serverBox.add(Box.createHorizontalStrut(6));
		serverBox.add(serverPortTextField);

		serverEnterButton = new JButton("���� ������");
		serverEnterBox = Box.createHorizontalBox();
		serverEnterBox.add(serverEnterButton);

		serverEnterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event)
					throws NumberFormatException {
				try {
					String server = serverAddressTextField.getText();
					String string_port = serverPortTextField.getText();
					int port = Integer.parseInt(string_port);

					popCon.connect(server, port);
					mainTextArea.append(server);
					mainTextArea.append(string_port);
					mainTextArea.append("ok\n");
				} catch (IOException UnknownHostException) {

				}
			}

		});
		// login
		serverLoginLabel = new JLabel("�����");
		// textfield1 = new JTextField(10);
		serverLoginTextField = new JTextField("mlite@mail.ru");
		serverLoginTextField.setMaximumSize(serverLoginTextField.getPreferredSize());
		serverLoginBox = Box.createHorizontalBox();
		serverLoginBox.add(serverLoginLabel);
		serverLoginBox.add(Box.createHorizontalStrut(10));
		serverLoginBox.add(serverLoginTextField);

		// password
		serverPassLabel = new JLabel("������");
		serverPassTextField = new JTextField("1234qwer");
		// textfield2 = new JTextField(10);
		serverPassTextField.setMaximumSize(serverPassTextField.getPreferredSize());
		serverPassBox = Box.createHorizontalBox();
		serverPassBox.add(serverPassLabel);
		serverPassBox.add(Box.createHorizontalStrut(10));
		serverPassBox.add(serverPassTextField);

		enterButton = new JButton("ENTER");
		statusButton = new JButton("Status");
		enterAndStatusBox = Box.createHorizontalBox();
		enterAndStatusBox.add(enterButton);
		enterAndStatusBox.add(Box.createHorizontalStrut(30));
		enterAndStatusBox.add(statusButton);

		// ���� ������ � ������
		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					String userLogin = serverLoginTextField.getText();
					String userPassword = serverPassTextField.getText();

					popCon.login(userLogin, userPassword);
					mainTextArea.append(userLogin);
					mainTextArea.append(userPassword);
					mainTextArea.append("ok2\n");
				} catch (Exception e) {
					mainTextArea.append("you are not connected to the server\n");
					System.out.print(e.toString());
				}
			}
		});
		// �������� �������

		statusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					String str = popCon.status();
					mainTextArea.append(str);
					mainTextArea.append("ok2\n");
				} catch (Exception e) {
					mainTextArea.append("you are not logged in\n");
					System.out.print(e.toString());
				}
			}
		});
		
		getLetterLabel = new JLabel("����� ������");
		getLetterTextField = new JTextField(5);
		getLetterTextField.setMaximumSize(getLetterTextField.getPreferredSize());
		getLetterButton = new JButton("�������� ������");

		getLetterBox = Box.createHorizontalBox();
		getLetterBox.add(getLetterLabel);
		getLetterBox.add(Box.createHorizontalStrut(10));
		getLetterBox.add(getLetterTextField);
		getLetterBox.add(Box.createHorizontalStrut(9));
		getLetterBox.add(getLetterButton);

		getLetterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					String str1 = getLetterTextField.getText();
					int n = Integer.parseInt(str1);

					Email email = popCon.retrieve(n);
					mainTextArea.append("\n������ ����� " + str1 + "\n");
					mainTextArea.append("�����������: " + email.getFieldFrom() + "\n");
					mainTextArea.append("����: " + email.getFieldDate() + "\n");
					mainTextArea.append("����: " + email.getFieldSubject() + "\n");
					mainTextArea.append("����������� �����������: " + email.getReceivedImages().size() + "\n");
					mainTextArea.append("���������:\n\n" + email.getFieldMessage());

				} catch (Exception e) {
					mainTextArea.append("you are not logged in\n");
					System.out.print(e.toString());
				}
			}
		});
		// close all connection
		closeConnectionButton = new JButton("������� ��� ����������");
		closeConnectionBox = Box.createHorizontalBox();
		closeConnectionBox.add(closeConnectionButton);

		closeConnectionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					popCon.close();

					mainTextArea.append("exit");
					System.exit(1);

				} catch (Exception e) {
					mainTextArea.append("not exit");
					System.out.print(e.toString());
				}
			}
		});
		// �������� ��� ������� � ��������
		getStoryButton = new JButton("�������� ������� ������ � ��������");
		getStoryBox = Box.createHorizontalBox();
		getStoryBox.add(Box.createHorizontalStrut(5));
		getStoryBox.add(getStoryButton);

		getStoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					String str = popCon.getLog();
					mainTextArea.append(str);
					mainTextArea.append("ok2");
				} catch (Exception e) {
					mainTextArea.append("not history");
					System.out.print(e.toString());
				}
			}
		});

		// ��������� �������������� ����� � ����� ������������ ����
		// �.� ��������� ������(hbox1,hbox2,hbox3) � ���� ������������
		Box vboxleft = Box.createVerticalBox();
		vboxleft.add(proxyUserBox);
		vboxleft.add(proxyEnterBox);
		vboxleft.add(serverBox);
		vboxleft.add(serverEnterBox);
		vboxleft.add(serverLoginBox);
		vboxleft.add(serverPassBox);
		vboxleft.add(enterAndStatusBox);
		//vboxleft.add(hbox4);
		vboxleft.add(getLetterBox);
		vboxleft.add(getStoryBox);
		vboxleft.add(closeConnectionBox);

		// ������� memo � �������������� hbox4
		mainTextArea = new JTextArea(80, 50);
		mainTextAreaScrollPane = new JScrollPane(mainTextArea);
		mainTextAreaBox = Box.createHorizontalBox();
		// hbox10.add(textArea);

		mainTextAreaBox.add(mainTextAreaScrollPane, BorderLayout.CENTER);

		// ��������� TextArea � ������ ������������ ����

		Box vboxRight = Box.createVerticalBox();
		vboxRight.add(mainTextAreaBox);

		// ������� ��� ���������� �� ���� �.� vboxleft � vboxRight �� mainLayout

		Box mainLayout = Box.createHorizontalBox();
		mainLayout.add(vboxleft);
		mainLayout.add(vboxRight);

		Container contentPane = getContentPane();
		contentPane.add(vboxleft, BorderLayout.WEST);
		contentPane.add(vboxRight, BorderLayout.EAST);

	}

}