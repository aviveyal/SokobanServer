package com.example.SokobanServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.server.model.AdminModel;
/**
 * 
 * @author Aviv Eyal
 *	the server with thread pool open a thread for each client and handle it
 */
public class Server {
	private int port;
	private ClientHandler ch;
	private boolean stop =false;
	private ExecutorService threadPool;
	private static final int THREADS_NUM =30;
	

	public Server(int port, ClientHandler ch) {
		this.port = port;
		this.ch = ch;
		threadPool=Executors.newFixedThreadPool(THREADS_NUM);
	}



	public void runServer() throws Exception {
	
		ServerSocket server=new ServerSocket(port);
		server.setSoTimeout(1000);
		while(!stop){
			try{
				Socket aClient=server.accept(); // blocking call
				threadPool.execute(new Runnable() {
					public void run() {
						try {
							
							AdminModel.getInstance().addClient(aClient.getPort()+" ", aClient);
							ch.HandleClient(aClient,aClient.getInputStream(), aClient.getOutputStream());
							aClient.getInputStream().close();
							aClient.getOutputStream().close();
							aClient.close();
						} catch (IOException e) {/*...*/}
					}
				});
			}catch(SocketTimeoutException e) {/*...*/}
		}
		server.close(); 
	}
	public void stopServer()
	{
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} finally{
			stop=true;
		}
	}
}


