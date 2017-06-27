package com.example.SokobanServer;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {

	public void HandleClient(InputStream in , OutputStream out);
}
