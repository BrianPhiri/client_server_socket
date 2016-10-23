import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	public Server(){
		super("Server");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						try {
							sendMessage(event.getActionCommand());
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
		add(new JScrollPane(chatWindow));
		setSize(300, 150);
		setVisible(true);
	}
	
	protected void sendMessage(String message) throws IOException {
		// TODO Auto-generated method stub
		output.writeObject("Sever - " + message);
		output.flush();
		showMessage("\n SERVER - "+message);
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

	void startRunning() throws IOException{
		server = new ServerSocket(6789, 100);
		while(true){
			waitForConnection();
			setupStreams();
			whileChatting();
		}
	}

	private void whileChatting() throws IOException {
		// TODO Auto-generated method stub
		String message = "You are now chatting";
		JOptionPane.showMessageDialog(null, message);
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n "+ message);
			}catch(ClassNotFoundException classNotFoundException){
				JOptionPane.showConfirmDialog(null, "Nope");
			}
		}while(!message.equals("CLIENT - END"));
	}

	private void ableToType(final boolean tof) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						userText.setEditable(tof);
					}
				}
		);
	}

	private void setupStreams() throws IOException {
		// TODO Auto-generated method stub
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush(); //clear data in the buffer
		input = new ObjectInputStream(connection.getInputStream());
		JOptionPane.showMessageDialog(null, "done");
	}

	private void waitForConnection() throws IOException {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Waiting for client to connect");
		connection = server.accept();
		JOptionPane.showMessageDialog(null, "Connected to "+connection.getInetAddress().getHostAddress());
		
	}
	
}
