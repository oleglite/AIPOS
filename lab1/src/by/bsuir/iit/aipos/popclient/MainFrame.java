package by.bsuir.iit.aipos.popclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/** Главное окно
 *
 */
public class MainFrame extends JFrame {

	PopConnection popCon = new PopConnection();

	public static int Default_Width = 1000;
	public static int Default_Height = 300;


	// #0_2
	// ввод адреса сервера и порта
	JLabel serverAddressLabel;
	JLabel serverPortLabel;

	JTextField serverAddressTextField;
	JTextField serverPortTextField;
	Box serverBox;
	// #1
	// ввести логин
	JLabel serverLoginLabel;
	JTextField serverLoginTextField;
	Box serverLoginBox;

	// #2
	// ввести пароль
	JLabel serverPassLabel;
	JTextField serverPassTextField;
	Box serverPassBox;
	// #3
	// ввод логина и пароля , проверка статуса
	JButton enterButton;
	Box entersBox;

	// #5
	// получить n-е письмо
	JLabel getLetterLabel;
	JButton getLetterButton;
	JTextField getLetterTextField;
	Box getLetterBox;

	JLabel folderNameLabel;
	JTextField folderNameTextField;
	Box folderNameBox;

	// #6
	// закрыть все соединения
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

		// создание верхнего горизонтального блока

		// ввод адреса сервера и порта
		serverAddressLabel = new JLabel("Адрес сервера");
		serverPortLabel = new JLabel("Порт");

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
		serverLoginLabel = new JLabel("Логин");
		// textfield1 = new JTextField(10);
		serverLoginTextField = new JTextField("mlite@mail.ru");
		serverLoginTextField.setMaximumSize(serverLoginTextField.getPreferredSize());
		serverLoginBox = Box.createHorizontalBox();
		serverLoginBox.add(serverLoginLabel);
		serverLoginBox.add(Box.createHorizontalStrut(10));
		serverLoginBox.add(serverLoginTextField);

		// password
		serverPassLabel = new JLabel("Пароль");
		serverPassTextField = new JTextField("1234qwer");
		// textfield2 = new JTextField(10);
		serverPassTextField.setMaximumSize(serverPassTextField.getPreferredSize());
		serverPassBox = Box.createHorizontalBox();
		serverPassBox.add(serverPassLabel);
		serverPassBox.add(Box.createHorizontalStrut(10));
		serverPassBox.add(serverPassTextField);

		enterButton = new JButton("подключиться");
		entersBox = Box.createHorizontalBox();
		entersBox.add(enterButton);
		entersBox.add(Box.createHorizontalStrut(30));

		// ввод логина и пароля
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

		getLetterLabel = new JLabel("Номер письма");
		getLetterTextField = new JTextField(5);
		getLetterTextField.setMaximumSize(getLetterTextField.getPreferredSize());
		getLetterButton = new JButton("Получить письмо");



		getLetterBox = Box.createHorizontalBox();
		getLetterBox.add(getLetterLabel);
		getLetterBox.add(Box.createHorizontalStrut(4));
		getLetterBox.add(getLetterTextField);
		getLetterBox.add(Box.createHorizontalStrut(4));
		getLetterBox.add(getLetterButton);

		folderNameLabel = new JLabel("Папка для сохранения");
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
		closeConnectionButton = new JButton("Закрыть все соединения");
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


		// добавляем горизонтальные блоки в левый вертикальный блок
		// т.е объединим строки(hbox1,hbox2,hbox3) в один вертикальный
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

		// добавим memo в горизонтальный hbox4
		mainTextArea = new JTextArea(80, 50);
		mainTextAreaScrollPane = new JScrollPane(mainTextArea);
		mainTextAreaBox = Box.createHorizontalBox();
		// hbox10.add(textArea);

		mainTextAreaBox.add(mainTextAreaScrollPane, BorderLayout.CENTER);

		// добавляем TextArea в правый вертикальный блок

		Box vboxRight = Box.createVerticalBox();
		vboxRight.add(mainTextAreaBox);

		// добавим все компаненты на один т.е vboxleft и vboxRight на mainLayout

		Box mainLayout = Box.createHorizontalBox();
		mainLayout.add(vboxleft);
		mainLayout.add(vboxRight);

		Container contentPane = getContentPane();
		contentPane.add(vboxleft, BorderLayout.WEST);
		contentPane.add(vboxRight, BorderLayout.EAST);

	}

}