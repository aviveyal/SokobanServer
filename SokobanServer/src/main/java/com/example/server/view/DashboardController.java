package com.example.server.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.server.model.AdminModel;

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
public class DashboardController implements Initializable {

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
    
    private AdminModel model;

    protected ListProperty<String> listProperty = new SimpleListProperty<>();
    protected ListProperty<String> listProperty2 = new SimpleListProperty<>();

    @FXML
    private void handleButtonAction(ActionEvent event) {
        //listProperty.set(FXCollections.observableArrayList(europeanCurrencyList));
    	updateList();
    }
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    	model = AdminModel.getInstance();      
        myListView.itemsProperty().bind(listProperty);
        tasks.itemsProperty().bind(listProperty2);

        //This does not work, you can not directly add to a ListProperty
        //listProperty.addAll( asianCurrencyList );
        listProperty.set(FXCollections.observableArrayList(model.getClients()));
    }
    
    private void updateList() {
    	listProperty.set(FXCollections.observableArrayList(model.getClients()));
    	listProperty2.set(FXCollections.observableArrayList(model.getTask()));
    }
    
    public void disconnect()
    {
    	String username =myListView.getSelectionModel().getSelectedItem().toString();
    	model.disconnectClient(username);
    	listProperty2.add("Disconnecting "+username);
             	
    }
}