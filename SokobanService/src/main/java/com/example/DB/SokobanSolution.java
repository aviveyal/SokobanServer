package com.example.DB;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 
 * @author Aviv Eyal
 *the SQL tabke that hold the solutions
 */
@Entity(name="SokobanSolution")
public class SokobanSolution {

	@Id
	private String Name;
	private String Solution;
	

	public SokobanSolution(String Name, String Solution) {
		super();
		this.Name = Name;
		this.Solution = Solution;
	}
	public SokobanSolution(){
		
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getSol() {
		return Solution;
	}

	public void setSol(String sol) {
		this.Solution = sol;
	}
	
	
}
