package by.bsuir.iit.aipos.popclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/** ������� ����
 *
 */
public class MainFrame extends JFrame {

	PopConnection popCon = new PopConnection();

	public static int Default_Width = 1000;
	public static int Default_Height = 300;


	// #0_2
	// ���� ������ ������� � �����
	JLabel serverAddressLabel;
	JLabel serverPortLabel;

	JTextField serverAddressTextField;
	JTextField serverPortTextField;
	Box serverBox;
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
	Box entersBox;

	// #5
	// �������� n-� ������
	JLabel getLetterLabel;
	JButton getLetterButton;
	JTextField getLetterTextField;
	Box getLetterBox;

	JLabel folderNameLabel;
	JTextField folderNameTextField;
	Box folderNameBox;

	// #6
	// ������� ��� ����������
	JLabel closeConnectionLabel;
	JButton closeConnectionButton;
	Box closeConnectionBox;

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

		enterButton = new JButton("������������");
		entersBox = Box.createHorizontalBox();
		entersBox.add(enterButton);
		entersBox.add(Box.createHorizontalStrut(30));

		// ���� ������ � ������
		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					String server = serverAddressTextField.getText();
					String string_port = serverPortTextField.getText();
					int port = Integer.parseInt(string_port);

					popCon.connect(server, port);

					String userLogin = serverLoginTextField.getText();
					String userPassword = serverPassTextField.getText();

					popCon.login(userLogin, userPassword);

					String str = popCon.status();

					mainTextArea.setText(popCon.getLog());
				} catch (Exception e) {
					mainTextArea.setText(popCon.getLog() + "\n\r" + e.getMessage());
				}
			}
		});

		getLetterLabel = new JLabel("����� ������");
		getLetterTextField = new JTextField(5);
		getLetterTextField.setMaximumSize(getLetterTextField.getPreferredSize());
		getLetterButton = new JButton("�������� ������");



		getLetterBox = Box.createHorizontalBox();
		getLetterBox.add(getLetterLabel);
		getLetterBox.add(Box.createHorizontalStrut(4));
		getLetterBox.add(getLetterTextField);
		getLetterBox.add(Box.createHorizontalStrut(4));
		getLetterBox.add(getLetterButton);

		folderNameLabel = new JLabel("����� ��� ����������");
		folderNameTextField = new JTextField("D:/021702/mail");
		folderNameTextField.setMaximumSize(new Dimension(140, 20));
		folderNameBox = Box.createHorizontalBox();
		folderNameBox.add(folderNameLabel);
		folderNameBox.add(Box.createHorizontalStrut(5));
		folderNameBox.add(folderNameTextField);


		getLetterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					String str1 = getLetterTextField.getText();
					int n = Integer.parseInt(str1);

					Email email = popCon.retrieve(n);
					email.saveImage(folderNameTextField.getText());
					email.saveMail(folderNameTextField.getText());
					mainTextArea.setText(popCon.getLog());

				} catch (Exception e) {
					mainTextArea.setText(popCon.getLog() + "\n\r" + e.getMessage());
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
					mainTextArea.setText(popCon.getLog());

				} catch (Exception e) {
					mainTextArea.setText(popCon.getLog() + "\n\r" + e.getMessage());
				}
			}
		});


		// ��������� �������������� ����� � ����� ������������ ����
		// �.� ��������� ������(hbox1,hbox2,hbox3) � ���� ������������
		Box vboxleft = Box.createVerticalBox();
		//vboxleft.add(proxyUserBox);
		//vboxleft.add(proxyEnterBox);
		vboxleft.add(serverBox);
		vboxleft.add(serverLoginBox);
		vboxleft.add(serverPassBox);
		vboxleft.add(entersBox);
		vboxleft.add(folderNameBox);
		vboxleft.add(getLetterBox);
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