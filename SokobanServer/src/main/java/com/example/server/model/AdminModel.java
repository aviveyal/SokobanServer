package com.example.server.model;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminModel {
	private Map<String, Socket> connectedClients =
			new HashMap<String, Socket>();
	
	private static final AdminModel instance = new AdminModel();
	private AdminModel() {}
	public static AdminModel getInstance() {
		return instance;
	}
	
	public void addClient(String userName, Socket socket) {
		connectedClients.put(userName, socket);
	}
	
	public List<String> getClients() {
		List<String> clients = new ArrayList<>();
		for (String client : connectedClients.keySet())
			clients.add(client);
		return clients;
	}
	
	public void disconnectClient(String userName) {
		Socket socket = connectedClients.get(userName);
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
