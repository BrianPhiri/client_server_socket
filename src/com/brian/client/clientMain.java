package com.brian.client;

import javax.swing.JFrame;
public class clientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client cli = new Client("127.0.0.1");
		cli.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cli.startRunning();
	}

}
