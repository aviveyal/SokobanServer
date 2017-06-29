package com.example.server.model;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
/**
 * 
 * @author Aviv Eyal
 *This is the Admin control- controling on clients and running tasks 
 */
public class AdminModel extends Observable{
	private Map<String, Socket> connectedClients =	new HashMap<String, Socket>();
	private List<String> tasks = new ArrayList<>();
	
	private static final AdminModel instance = new AdminModel();
	private AdminModel() {}
	public static AdminModel getInstance() {
		return instance;
	}
	
	public void addClient(String userName, Socket socket){
		connectedClients.put(userName, socket);
		setChanged();
		List<String> params = new LinkedList<String>();
		params.add("Add");
		params.add(userName);
		notifyObservers(params);
	}
	
	public List<String> getClients() {
		List<String> clients = new ArrayList<>();
		for (String client : connectedClients.keySet())
			clients.add(client);
		return clients;
	}
	
	public void disconnectClient(String userName) {
		System.out.println("disconnecting :" +userName);
		Socket socket = connectedClients.get(userName);
		try {
			socket.close();
			setChanged();
			List<String> params = new LinkedList<String>();
			params.add("Remove");
			params.add(userName);
			notifyObservers(params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addTask(String task) throws IOException {
		tasks.add(task);
		setChanged();
		List<String> params = new LinkedList<String>();
		params.add("Addtask");
		params.add(task);
		notifyObservers(params);
	}
	public List<String> getTask() {
		return tasks;
		
	}
	
}
