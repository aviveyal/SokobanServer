package com.example.viewmodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.example.server.model.AdminModel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class DashboardViewModel implements Observer {

	private AdminModel adminModel;
	private ObservableList<String> observableList;
	public ListProperty<String> clientsList;
	
	private ObservableList<String> observableList2;
	public ListProperty<String> tasksList;
	
	public DashboardViewModel(AdminModel adminModel) {
		this.adminModel = adminModel;
		observableList = FXCollections.observableArrayList();
		observableList2 = FXCollections.observableArrayList();
		clientsList = new SimpleListProperty<String>();
		tasksList = new SimpleListProperty<String>();
		
		tasksList.set(observableList2);
		clientsList.set(observableList);
		
		observableList.addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				if (c.getRemovedSize() > 0) {
					
					for (String client: c.getRemoved()) {
						adminModel.disconnectClient(client);
					}
				}
				
			}
		});
		
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == adminModel) {
			List<String> params = (LinkedList<String>)arg;
			String op = params.get(0);
			String param = params.get(1);
			
			if (op.equals("Add"))
			{
				observableList.add(param);
				
			}
			else if(op.equals("Addtask")){
				observableList2.add(param);
			}
						
				
			else
				observableList.remove(param);
		}		
	}

}
