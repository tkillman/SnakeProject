package com.project.snake.server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SnakeGameServer {

	public static void main(String[] args) {
	
		ServerSocket serverSocket = null;
		InetSocketAddress serverAdd = null;;
		Socket clientSocket = null;;
		
		try {
			// ������ ���� ����
			serverSocket = new ServerSocket();								
			//serverAdd = new InetSocketAddress("192.168.20.40", 8686);
			serverAdd = new InetSocketAddress("127.0.0.1", 8686);
			serverSocket.bind(serverAdd);
			
			
			// Ŭ���̾�Ʈ�� ���� ���
			while(true){
				clientSocket = serverSocket.accept();							//Ŭ���̾�Ʈ�� ���ӵǸ�
				ServerThread serverThread = new ServerThread(clientSocket);		//�����带 �����ؼ�
				serverThread.start();											//ȣ��
				System.out.println(clientSocket.getInetAddress()+":connect");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(clientSocket!=null) clientSocket.close();
				if(serverSocket!=null) serverSocket.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
	}
}
