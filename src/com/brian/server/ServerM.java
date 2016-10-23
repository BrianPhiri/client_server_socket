package com.brian.server;
import java.io.IOException;

import javax.swing.JFrame;

public class ServerM {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Server sv = new Server();
		sv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sv.startRunning();
	}

}
