package com.example.server.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.server.model.AdminModel;
import com.example.viewmodel.DashboardViewModel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
/**
 * 
 * @author Aviv Eyal
 *	control the gui 	
 */
public class DashboardController{

    @FXML
    private Button button;
    //@FXML
   // private Button disconnect;
    @FXML
    private Label label;
    @FXML
    private Text text;

    @FXML
    private ListView myListView;
    
    @FXML
    private ListView tasks;
    
    private DashboardViewModel vm;
    private AdminModel model;

    protected ListProperty<String> listProperty = new SimpleListProperty<>();
    protected ListProperty<String> listProperty2 = new SimpleListProperty<>();

        
            
    private void updateList() {
    	listProperty.set(FXCollections.observableArrayList(model.getClients()));
    	listProperty2.set(FXCollections.observableArrayList(model.getTask()));
    }
    
    public void disconnect()
    {
        myListView.getItems().remove(myListView.getSelectionModel().getSelectedItem());
   	   	listProperty2.add("Disconnecting "+myListView.getSelectionModel().getSelectedItem());
             	
    }
    public void setViewModel(DashboardViewModel vm)
    {
    	this.vm = vm;
    	myListView.itemsProperty().bind(vm.clientsList);
    	tasks.itemsProperty().bind(vm.tasksList);
    }
    
}