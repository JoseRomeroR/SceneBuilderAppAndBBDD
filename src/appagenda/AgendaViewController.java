package appagenda;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import entidades.Persona;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Kyle7
 */
public class AgendaViewController implements Initializable {
    private Persona personaSeleccionada;

    
    private EntityManager entityManager;
    @FXML
    private TableView<Persona> tableViewAgenda;
    @FXML
    private TableColumn<Persona, String > columnNombre;
    @FXML
    private TableColumn<Persona, String> columnApellidos;
    @FXML
    private TableColumn<Persona, String> columnEmail;
    @FXML
    private TableColumn<Persona,String > columnProvincia;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelApellidos;
    @FXML
    private TextField textFieldApellidos;
    @FXML
    private TextField textFieldnombre;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button Nuevo;
    @FXML
    private Button Editar;
    @FXML
    private Button Suprimir;
    @FXML
    private AnchorPane rootAgendaView;
    
    public void setEntityManager(EntityManager entityManager){
        
        this.entityManager=entityManager;
    }

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos")); 
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        columnProvincia.setCellValueFactory(
                        cellData->{
                        SimpleStringProperty property=new SimpleStringProperty();
                        if (cellData.getValue().getProvincia()!=null){
                         property.setValue(cellData.getValue().getProvincia().getNombre());
                        }
                        return property;
         });
        
        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(
 (observable,oldValue,newValue)->{
personaSeleccionada=newValue;
});
        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(
(observable,oldValue,newValue)->{
personaSeleccionada=newValue;
if (personaSeleccionada != null){
textFieldnombre.setText(personaSeleccionada.getNombre());
textFieldApellidos.setText(personaSeleccionada.getApellidos());
} else {
textFieldnombre.setText("");
textFieldApellidos.setText("");
}
});


    }    
    
    public void cargarTodasPersonas(){
        Query queryPersonaFindAll=
        entityManager.createNamedQuery("Persona.findAll");
        List<Persona> listPersona=queryPersonaFindAll.getResultList();
        tableViewAgenda.setItems(FXCollections.observableArrayList(listPersona)
);
}

    @FXML
    private void onActionButtonGuardar( ) {
        if (personaSeleccionada != null){
            personaSeleccionada.setNombre(textFieldnombre.getText());
            personaSeleccionada.setApellidos(textFieldApellidos.getText());

            entityManager.getTransaction().begin();
            entityManager.merge(personaSeleccionada);
            entityManager.getTransaction().commit();
            int numFilaSeleccionada =
            tableViewAgenda.getSelectionModel().getSelectedIndex();
            tableViewAgenda.getItems().set(numFilaSeleccionada,personaSeleccionada);

            TablePosition pos = new
            TablePosition(tableViewAgenda,numFilaSeleccionada,null);
            tableViewAgenda.getFocusModel().focus(pos);
            tableViewAgenda.requestFocus();




        }
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        
        try{
// Cargar la vista de detalle
FXMLLoader fxmlLoader=new
FXMLLoader(getClass().getResource("PersonaDetalleView.fxml"));
Parent rootDetalleView=fxmlLoader.load();
PersonaDetalleViewController personaDetalleViewController =
(PersonaDetalleViewController) fxmlLoader.getController();
personaDetalleViewController.setRootAgendaView(rootAgendaView);

// Ocultar la vista de la lista
rootAgendaView.setVisible(false);
 //Añadir la vista detalle al StackPane principal para que se muestre
StackPane rootMain =
 (StackPane) rootAgendaView.getScene().getRoot();
rootMain.getChildren().add(rootDetalleView);
} catch (IOException ex){
Logger.getLogger(AgendaViewController.class.getName()).log(Level.SEVERE,null,ex);
}
        
        

    }

    @FXML
    private void onActionButtonEditar(ActionEvent event) {
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
    }

    
}
