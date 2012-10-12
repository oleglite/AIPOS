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
	public static int Default_Height = 800;


	// #0_1
	// Proxy
	JLabel label_01;
	JLabel label_02;

	JTextField textfield_01;
	JTextField textfield_02;

	JButton ProxyButton;
	Box hbox_01;

	// для кнопки ввод данных
	Box hbox_enter1;
	// #0_2
	// ввод адреса сервера и порта
	JLabel label_03;
	JLabel label_04;

	JTextField textfield_03;
	JTextField textfield_04;
	JButton adressButton;
	Box hbox_02;
	// для кнопки ввод данных
	Box hbox_enter2;
	// #1
	// ввести логин
	JLabel label1;
	JTextField textfield1;
	Box hbox1;

	// #2
	// ввести пароль
	JLabel label2;
	JTextField textfield2;
	Box hbox2;
	// #3
	// ввод логина и пароля , проверка статуса
	JButton enterButton;
	JButton statusButton;
	Box hbox3;
	// #4
	// получить все письма
	JLabel label4;
	JButton get_ALL_Letter_Button;
	Box hbox4;

	// #5
	// получить n- письмо
	JLabel label5;
	JButton get_N_Letter_Button;
	JTextField textfield5;
	Box hbox5;
	// #6
	// закрыть все соединения
	JLabel label6;
	JButton close_Button;
	Box hbox6;
	// #7
	// Получить полную историю работы с сервером
	JLabel label7;
	JButton story_Button;
	Box hbox7;

	// #10
	Box hbox10;
	JTextArea textArea;
	JScrollPane scrollPane;

	public MainFrame() {

		setSize(Default_Width, Default_Height);
		setTitle("pop3 client v.0.1.0 beta ");

		// создание верхнего горизонтального блока

		// Proxy

		label_01 = new JLabel("Имя пользователя:");
		label_02 = new JLabel("пароль:");

		textfield_01 = new JTextField(10);
		textfield_02 = new JTextField(10);

		textfield_01.setMaximumSize(textfield_01.getPreferredSize());
		textfield_02.setMaximumSize(textfield_02.getPreferredSize());
		hbox_01 = Box.createHorizontalBox();
		//hbox_01.add(this.labels.get(Labels.USER_NAME));
		hbox_01.add(label_01);
		hbox_01.add(Box.createHorizontalStrut(3));
		hbox_01.add(textfield_01);
		//hbox_01.add(this.labels.get(Labels.PASSWORD));
		hbox_01.add(label_02);
		hbox_01.add(Box.createHorizontalStrut(3));
		hbox_01.add(textfield_02);

		ProxyButton = new JButton("ввод данных");
		hbox_enter1 = Box.createHorizontalBox();
		hbox_enter1.add(ProxyButton);

		ProxyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				textArea.append("ввести пароль и  " + "логин ");
			}
		});

		// ввод адреса сервера и порта
		label_03 = new JLabel("адрес сервера:");
		label_04 = new JLabel("адрес порта:");

		textfield_03 = new JTextField("pop.mail.ru");
		textfield_04 = new JTextField("110");

		textfield_03.setMaximumSize(textfield_03.getPreferredSize());
		textfield_04.setMaximumSize(textfield_04.getPreferredSize());
		hbox_02 = Box.createHorizontalBox();
		hbox_02.add(label_03);
		hbox_02.add(Box.createHorizontalStrut(10));
		hbox_02.add(textfield_03);
		hbox_02.add(label_04);
		hbox_02.add(Box.createHorizontalStrut(10));
		hbox_02.add(textfield_04);

		adressButton = new JButton("ввод данных");
		hbox_enter2 = Box.createHorizontalBox();
		hbox_enter2.add(adressButton);

		adressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event)
					throws NumberFormatException {
				try {
					String server = textfield_03.getText();
					String string_port = textfield_04.getText();
					int port = Integer.parseInt(string_port);

					popCon.connect(server, port);
					textArea.append(server);
					textArea.append(string_port);
					textArea.append("ok");
				} catch (IOException UnknownHostException) {

				}
			}

		});
		// login
		label1 = new JLabel("login:");
		// textfield1 = new JTextField(10);
		textfield1 = new JTextField("mlite@mail.ru");
		textfield1.setMaximumSize(textfield1.getPreferredSize());
		hbox1 = Box.createHorizontalBox();
		hbox1.add(label1);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(textfield1);

		// password
		label2 = new JLabel("Password:");
		textfield2 = new JTextField("1234qwer");
		// textfield2 = new JTextField(10);
		textfield2.setMaximumSize(textfield2.getPreferredSize());
		hbox2 = Box.createHorizontalBox();
		hbox2.add(label2);
		hbox2.add(Box.createHorizontalStrut(10));
		hbox2.add(textfield2);

		enterButton = new JButton("ENTER");
		statusButton = new JButton("Status");
		hbox3 = Box.createHorizontalBox();
		hbox3.add(enterButton);
		hbox3.add(Box.createHorizontalStrut(30));
		hbox3.add(statusButton);

		// ввод логина и пароля
		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					String userLogin = textfield1.getText();
					String userPassword = textfield2.getText();

					popCon.login(userLogin, userPassword);
					textArea.append(userLogin);
					textArea.append(userPassword);
					textArea.append("ok2");
				} catch (Exception e) {
					textArea.append("you are not connected to the server");
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
					textArea.append(str);
					textArea.append("ok2");
				} catch (Exception e) {
					textArea.append("you are not logged in");
					System.out.print(e.toString());
				}
			}
		});
/*
		label4 = new JLabel("Получить все письма:");
		get_ALL_Letter_Button = new JButton("get ALL Letter");
		hbox4 = Box.createHorizontalBox();
		hbox4.add(label4);
		hbox4.add(Box.createHorizontalStrut(30));
		hbox4.add(get_ALL_Letter_Button);

		get_ALL_Letter_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					Email email = popCon.retrieve(1);
					textArea.append(email.getFieldFrom() + "\n");
					textArea.append(email.getFieldDate() + "\n");
					textArea.append(email.getFieldSubject() + "\n");
					textArea.append("\n" + email.getFieldMessage() + "\n");
					//textArea.append("ok2");
				} catch (Exception e) {
					textArea.append("you are not logged in");
					System.out.print(e.toString());
				}
			}
		});
*/
		label5 = new JLabel("Получить n-е письмо:");
		textfield5 = new JTextField(5);
		textfield5.setMaximumSize(textfield5.getPreferredSize());
		get_N_Letter_Button = new JButton("get n Letter");

		hbox5 = Box.createHorizontalBox();
		hbox5.add(label5);
		hbox5.add(Box.createHorizontalStrut(10));
		hbox5.add(textfield5);
		hbox5.add(Box.createHorizontalStrut(9));
		hbox5.add(get_N_Letter_Button);

		get_N_Letter_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					String str1 = textfield5.getText();
					int n = Integer.parseInt(str1);

					Email email = popCon.retrieve(n);
					textArea.append(email.getFieldFrom() + "\n");
					textArea.append(email.getFieldDate() + "\n");
					textArea.append(email.getFieldSubject() + "\n");
					textArea.append("\n" + email.getFieldMessage() + "\n");

				} catch (Exception e) {
					textArea.append("you are not logged in");
					System.out.print(e.toString());
				}
			}
		});
		// close all connection
		close_Button = new JButton("close all connection");
		label6 = new JLabel("Закрыть все соединеия:");
		hbox6 = Box.createHorizontalBox();
		hbox6.add(label6);
		hbox6.add(Box.createHorizontalStrut(20));
		hbox6.add(close_Button);

		close_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					popCon.close();

					textArea.append("exit");
					System.exit(1);

				} catch (Exception e) {
					textArea.append("not exit");
					System.out.print(e.toString());
				}
			}
		});
		// получить всю историю с сервером
		story_Button = new JButton("get Story");
		label7 = new JLabel("Получить всю историю работы с сервером:");
		hbox7 = Box.createHorizontalBox();
		hbox7.add(label7);
		hbox7.add(Box.createHorizontalStrut(5));
		hbox7.add(story_Button);

		story_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					String str = popCon.getLog();
					textArea.append(str);
					textArea.append("ok2");
				} catch (Exception e) {
					textArea.append("not history");
					System.out.print(e.toString());
				}
			}
		});

		// добавляем горизонтальные блоки в левый вертикальный блок
		// т.е объединим строки(hbox1,hbox2,hbox3) в один вертикальный
		Box vboxleft = Box.createVerticalBox();
		vboxleft.add(hbox_01);
		vboxleft.add(hbox_enter1);
		vboxleft.add(hbox_02);
		vboxleft.add(hbox_enter2);
		vboxleft.add(hbox1);
		vboxleft.add(hbox2);
		vboxleft.add(hbox3);
		//vboxleft.add(hbox4);
		vboxleft.add(hbox5);
		vboxleft.add(hbox7);
		vboxleft.add(hbox6);

		// добавим memo в горизонтальный hbox4
		textArea = new JTextArea(80, 50);
		scrollPane = new JScrollPane(textArea);
		hbox10 = Box.createHorizontalBox();
		// hbox10.add(textArea);

		hbox10.add(scrollPane, BorderLayout.CENTER);

		// добавляем TextArea в правый вертикальный блок

		Box vboxRight = Box.createVerticalBox();
		vboxRight.add(hbox10);

		// добавим все компаненты на один т.е vboxleft и vboxRight на mainLayout

		Box mainLayout = Box.createHorizontalBox();
		mainLayout.add(vboxleft);
		mainLayout.add(vboxRight);

		Container contentPane = getContentPane();
		contentPane.add(vboxleft, BorderLayout.WEST);
		contentPane.add(vboxRight, BorderLayout.EAST);

	}

}