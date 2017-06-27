package com.example.SokobanServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


import Adapter.Search.SokobanStateMove;

public class SokobanClientHandler implements ClientHandler {

	@Override
	public void HandleClient(InputStream in, OutputStream out) {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			ois = new ObjectInputStream(in);
			oos = new ObjectOutputStream(out);
			
			SokobanStateMove game = (SokobanStateMove)ois.readObject();
			
			
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (oos != null)
					oos.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}		

	}

}
