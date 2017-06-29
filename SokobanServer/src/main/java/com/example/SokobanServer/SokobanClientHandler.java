package com.example.SokobanServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.server.model.AdminModel;

import Adapter.Plan.SokobanAdapter;
import Solver.SokobanSolver;

/**
 * 
 * @author Aviv Eyal The sokoban client handler - recieve info of level from
 *         client and connect to a web service to get exisiting solution or
 *         solving it and return the solution for the client
 */
public class SokobanClientHandler implements ClientHandler {

	private ObjectInputStream ois = null;
	private PrintWriter writer = null;
	
	@Override
	public void HandleClient(Socket socket, InputStream in, OutputStream out) {
		try {

			AdminModel.getInstance().addClient(socket.getPort()+" ", socket);
			ois = new ObjectInputStream(in);
			writer = new PrintWriter(out);

			String levelname = (String) ois.readObject();
			System.out.println("recieved level :" + levelname);
			AdminModel.getInstance().addTask(socket.getPort() + "-" + "sent level");

			// AdminModel.getInstance().addClient(socket.toString(),socket);

			// need send level name to server
			AdminModel.getInstance().addTask(socket.getPort() + "-" + "checking if level exists in Database");
			String sol = getSolutionfromService(levelname);

			if (sol == null) {
				AdminModel.getInstance().addTask(socket.getPort() + "-" + "level not exists!");
				AdminModel.getInstance().addTask(socket.getPort() + "-" + "server trying to solve the level");
				String size = (String) ois.readObject();
				System.out.println("recieved size :" + size);

				String line = "";
				int maxrow = Integer.parseInt(size.substring(0, size.indexOf(',')));
				int maxcol = Integer.parseInt(size.substring(size.indexOf(',') + 1, size.length() - 1));
				System.out.println("printing max : " + maxrow + "," + maxcol);
				char[][] leveldata = new char[maxrow][maxcol];
				for (int i = 0; i < maxrow; i++) {
					line = (String) ois.readObject();
					for (int j = 0; j < maxcol; j++) {
						leveldata[i][j] = line.charAt(j);
					}
				}

				// prints the level that received
				for (int x = 0; x < maxrow; x++) {
					for (int y = 0; y < maxcol; y++) {
						System.out.print(leveldata[x][y]);

						if (y == maxcol - 1)
							System.out.println();
					}
				}

				SokobanAdapter SA = new SokobanAdapter(leveldata);
				Solver.StripsLib.Strips s = new Solver.StripsLib.Strips();
				String makeSolution = "";
				List<Solver.StripsLib.Action> list = s.plan(SA.readLevel()); // list
																				// of
				// solve the level
				SokobanSolver solver = new SokobanSolver();
				List<String> Solution = solver.solve(leveldata);
				if (Solution !=null) {
					AdminModel.getInstance().addTask(socket.getPort() + "-" + "server successed solve");
					for (String action : Solution) {
						switch (action) {
						case "move up":
							makeSolution += 'U';
							break;
						case "move down":
							makeSolution += 'D';
							break;
						case "move left":
							makeSolution += 'L';
							break;
						case "move right":
							makeSolution += 'R';
							break;
						}

					}
					addSolutionToService(levelname, makeSolution);
					AdminModel.getInstance().addTask(socket.getPort() + "-" + "server sending solution to client");
					// send the solutoin to client
					writer.println(makeSolution);
					writer.flush();
				} else {
					// cant solve - post null
					addSolutionToService(levelname, null);
					AdminModel.getInstance().addTask(socket.getPort() + "-" + "server couldnt solve level");
					writer.println("");
					writer.flush();
				}

				

			} else {
				AdminModel.getInstance().addTask(socket.getPort() + "-" + "solution already exists - sending to client");
				String buffer = (String) ois.readObject(); // clean
				buffer = (String) ois.readObject(); // clean
				writer.println(sol);
				writer.flush();

			}

		} catch (IOException e) {
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}

	}

	private String getSolutionfromService(String name) {

		String url = "http://localhost:8080/SokobanService/webapi/solutions/" + name;
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);
		Response response = webTarget.request(MediaType.TEXT_PLAIN).get(Response.class);
		if (response.getStatus() == 200) {
			String Solution = response.readEntity(new GenericType<String>() {
			});
			System.out.println("Solution : " + Solution);
			return Solution;
		} else {
			System.out.println(response.getHeaderString("errorResponse"));
			return null;
		}

	}

	private void addSolutionToService(String levelname, String solution) {

		String url = "http://localhost:8080/SokobanService/webapi/solutions/";
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);

		Form form = new Form();
		form.param("name", levelname);
		form.param("solution", solution);

		Response response = webTarget.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

		if (response.getStatus() == 204) {
			System.out.println("Solution added successfully");
		} else {
			System.out.println(response.getHeaderString("errorResponse"));
		}
	}

}
