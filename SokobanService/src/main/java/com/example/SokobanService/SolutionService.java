package com.example.SokobanService;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import com.example.DB.DbHandler;
import com.example.DB.SokobanSolution;

@Path("solutions")
public class SolutionService {

	private DbHandler Dhandler = new DbHandler();
	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 @Path("{name}")
	    public String getSolution(@PathParam("name")String name) {
	        return Dhandler.getSolution(name);
	    }
	 
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void addSolution(@FormParam("name") String name,@FormParam("solution") String solution) {
		 SokobanSolution sol = new SokobanSolution(name,solution);
	        Dhandler.addSolution(sol);
	    }
	
}
