package com.example.SokobanServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface ClientHandler {

	public void HandleClient(Socket socket,InputStream in , OutputStream out);
}
