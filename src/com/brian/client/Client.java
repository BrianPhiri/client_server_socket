package com.brian.client;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{
	private JTextField userText;
	private JTextArea 	chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message ="";
	private String serverIP;
	private Socket connection;
	
//	constructor
	public Client(String host){
		super("Client");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						try {
							sendData(event.getActionCommand());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						userText.setText("");
					}
				}
		);
		
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		setSize(300,150);
		setVisible(true);
		
	}
	
	private void sendData(String message) throws IOException {
		output.writeObject("CLIENT - " + message);
		output.flush();
		showMessage("\n CLIENT - " + message);
	}

	public void startRunning(){
		try{
			connectToServer();
			setUpStreams();
			whileChatting();
			
		}catch(EOFException eofException){
			showMessage("\n Client disconnected");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeConnection();
		}
	}

	private void closeConnection() {
		// TODO Auto-generated method stub
		showMessage("Closing the connection");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	private void ableToType(boolean b) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(b);;
				}
			}
		);
	}

	private void showMessage(final String string) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(string);
					}
				}
		);
	}

	private void whileChatting() throws IOException{
		// TODO Auto-generated method stub
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n "+ message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("Cant find object");
			}
		}while(!message.equals("SERVER - END "));
	}

	private void setUpStreams() throws IOException{
		// TODO Auto-generated method stub
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n You are good to go");
	}

	private void connectToServer() throws IOException {
		// TODO Auto-generated method stub
		showMessage("Attempting connection...");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		showMessage("Connected to "+ connection.getInetAddress().getHostName());
	}
	
}
