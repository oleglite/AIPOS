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

	// для кнопки ввод данных
	Box proxyEnterBox;
	
	// #0_2
	// ввод адреса сервера и порта
	JLabel serverAddressLabel;
	JLabel serverPortLabel;

	JTextField serverAddressTextField;
	JTextField serverPortTextField;
	JButton serverEnterButton;
	Box serverBox;
	// для кнопки ввод данных
	Box serverEnterBox;
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
	JButton statusButton;
	Box enterAndStatusBox;

	// #5
	// получить n-е письмо
	JLabel getLetterLabel;
	JButton getLetterButton;
	JTextField getLetterTextField;
	Box getLetterBox;
	// #6
	// закрыть все соединения
	JLabel closeConnectionLabel;
	JButton closeConnectionButton;
	Box closeConnectionBox;
	// #7
	// Получить полную историю работы с сервером
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

		// создание верхнего горизонтального блока

		// Proxy

		proxyUserLabel = new JLabel("Имя пользователя");
		proxyPassLabel = new JLabel("Пароль");

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

		proxyButton = new JButton("ввод данных");
		proxyEnterBox = Box.createHorizontalBox();
		proxyEnterBox.add(proxyButton);

		proxyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainTextArea.append("ввести пароль и " + "логин\n");
			}
		});

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

		serverEnterButton = new JButton("ввод данных");
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

		enterButton = new JButton("ENTER");
		statusButton = new JButton("Status");
		enterAndStatusBox = Box.createHorizontalBox();
		enterAndStatusBox.add(enterButton);
		enterAndStatusBox.add(Box.createHorizontalStrut(30));
		enterAndStatusBox.add(statusButton);

		// ввод логина и пароля
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
		// проверка статуса

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
		
		getLetterLabel = new JLabel("Номер письма");
		getLetterTextField = new JTextField(5);
		getLetterTextField.setMaximumSize(getLetterTextField.getPreferredSize());
		getLetterButton = new JButton("Получить письмо");

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
					mainTextArea.append("\nПисьмо номер " + str1 + "\n");
					mainTextArea.append("Отправитель: " + email.getFieldFrom() + "\n");
					mainTextArea.append("Дата: " + email.getFieldDate() + "\n");
					mainTextArea.append("Тема: " + email.getFieldSubject() + "\n");
					mainTextArea.append("Прикреплено изображений: " + email.getReceivedImages().size() + "\n");
					mainTextArea.append("Сообщение:\n\n" + email.getFieldMessage());

				} catch (Exception e) {
					mainTextArea.append("you are not logged in\n");
					System.out.print(e.toString());
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

					mainTextArea.append("exit");
					System.exit(1);

				} catch (Exception e) {
					mainTextArea.append("not exit");
					System.out.print(e.toString());
				}
			}
		});
		// получить всю историю с сервером
		getStoryButton = new JButton("Получить историю работы с сервером");
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

		// добавляем горизонтальные блоки в левый вертикальный блок
		// т.е объединим строки(hbox1,hbox2,hbox3) в один вертикальный
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