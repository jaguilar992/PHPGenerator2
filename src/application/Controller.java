package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Atributo;
import model.Documento;
import model.Metodo;
import model.TipoAcceso;
import model.TipoDocumento;

public class Controller implements Initializable{
	private int selectedMetodo  = -1;
	private int selectedAtributo = -1;
	
	private Documento mainDoc = new Documento();
	//private Documento importDoc = new Documento();

	//Listas
	private ObservableList<Atributo> listaAtributos;
	private ObservableList<Metodo> listaMetodos;
	private ObservableList<TipoDocumento> listaTipoDocumento;
	private ObservableList<TipoAcceso> listaTipoAccesoAtributos;
	private ObservableList<TipoAcceso> listaTipoAccesoMetodos;

	//Componentes
		//	TableView
	@FXML private TableView<Atributo> tblAtributos;
	@FXML private TableColumn<Atributo,String> colAtributoAcceso;
	@FXML private TableColumn<Atributo,String> colAtributoIdentif;
	@FXML private TableColumn<Atributo,String> colAtributoDefault;
	@FXML private TableView<Metodo> tblMetodos;
	@FXML private TableColumn<Metodo,String> colMetodoAcceso;
	@FXML private TableColumn<Metodo,String> colMetodoIdentificador;
	
		//	ComboBox
	@FXML private ComboBox<TipoDocumento> cmbTipoDocumento;
	@FXML private ComboBox<TipoAcceso> cmbTipoAccesoAtributo;
	@FXML private ComboBox<TipoAcceso> cmbTipoAccesoMetodo;
	
		//	Botones
	@FXML Button btnAddAtributo;
	@FXML Button btnSetAtributo;
	@FXML Button btnDelAtributo;
	@FXML Button btnDelAllAtributos;
	@FXML Button btnEditAtributo;
	
	@FXML Button btnAddMetodo;
	@FXML Button btnSetMetodo;
	@FXML Button btnDelMetodo;
	@FXML Button btnDelAllMetodos;
	@FXML Button btnEditMetodo;

	@FXML Button btnCleanEditor;
	@FXML Button btnGenerate;
	
		//	TextArea
	@FXML TextArea txtEditor;
		//	TextFields
	@FXML TextField txtNombreDocumento;
	@FXML TextField txtIdentificadorAtributo;
	@FXML TextField txtDefaultAtributo;
	@FXML TextField txtIdentificadorMetodo;
		//	CheckBox
	@FXML CheckBox cbxConstruct;
	@FXML CheckBox cbxToString;
	@FXML CheckBox cbxGetSet;
	
		//	MenuItem
	@FXML MenuItem menuNuevo;
	@FXML MenuItem menuGuardar;
	@FXML MenuItem menuGuadarComo;
	@FXML MenuItem menuSalir;
	@FXML MenuItem menuAcerca;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializar Listas
		listaAtributos = FXCollections.observableArrayList();
		listaMetodos = FXCollections.observableArrayList();
		listaTipoDocumento = FXCollections.observableArrayList();
		listaTipoAccesoAtributos = FXCollections.observableArrayList();
		listaTipoAccesoMetodos = FXCollections.observableArrayList();
		
		//Llenar elementos en Listas
		this.llenarTipoDocumentos(listaTipoDocumento);
		this.llenarTipoAcceso(listaTipoAccesoAtributos, 0);
		this.llenarTipoAcceso(listaTipoAccesoMetodos, 1);
				
		// Enlazar listas con TableViews
		tblAtributos.setItems(listaAtributos);
		tblMetodos.setItems(listaMetodos);
		cmbTipoDocumento.setItems(listaTipoDocumento);
		cmbTipoAccesoAtributo.setItems(listaTipoAccesoAtributos);
		cmbTipoAccesoMetodo.setItems(listaTipoAccesoMetodos);
		
		//Seleccionar primer elemento de la lista
		cmbTipoDocumento.getSelectionModel().select(listaTipoDocumento.get(0));
		cmbTipoAccesoAtributo.getSelectionModel().select(listaTipoAccesoAtributos.get(0));
		cmbTipoAccesoMetodo.getSelectionModel().select(listaTipoAccesoMetodos.get(2));
		
		// Enlazar Columnas de TableViews
		colAtributoAcceso.setCellValueFactory(new PropertyValueFactory<Atributo,String>("acceso"));
		colAtributoIdentif.setCellValueFactory(new PropertyValueFactory<Atributo,String>("identificador"));
		colAtributoDefault.setCellValueFactory(new PropertyValueFactory<Atributo,String>("valorDefault"));

		colMetodoAcceso.setCellValueFactory(new PropertyValueFactory<Metodo, String>("acceso"));
		colMetodoIdentificador.setCellValueFactory(new PropertyValueFactory<Metodo, String>("identificador"));
		
		
		// EVENTOS
		//ENTER EN ID ATRIB
		txtIdentificadorAtributo.setOnKeyPressed((event) -> { 
			if(event.getCode() == KeyCode.ENTER) { 
				if (selectedAtributo==-1) {
					addItemAtributos();
				}else {
					setItemAtributos();
				}
		}});
		// ENTER EN ID DEFAULT
		txtDefaultAtributo.setOnKeyPressed((event) -> {
			if(event.getCode() == KeyCode.ENTER) { 
				if (selectedAtributo==-1) {
					addItemAtributos();
				}else {
					setItemAtributos();
				}
		}});
		// ENTER EN MET ATRIB
		txtIdentificadorMetodo.setOnKeyPressed((event) -> { 
			if(event.getCode() == KeyCode.ENTER) { 
				if (selectedMetodo!=-1) {
					addItemMetodos();
				}else {
					addItemMetodos();
				}
		}});
		// TABLE VIEW
		tblAtributos.setOnKeyPressed((event)->{
			int index = tblAtributos.getSelectionModel().getSelectedIndex();
			if (index!=-1) {
				switch (event.getCode()) {
					case DELETE:
						listaAtributos.remove(index);
					break;
					case ENTER:
						EditAtributo();
					break;
					default:break;
				}
				
			}
		});
		
		tblMetodos.setOnKeyPressed((event)->{
			int index = tblMetodos.getSelectionModel().getSelectedIndex();
			if(index!=-1) {
				switch (event.getCode()) {
					case DELETE:
						listaMetodos.remove(index);
					break;
					case ENTER:
						EditMetodo();
					break;
					default:break;
				}
				
			}
		});
	}
	
	public void llenarTipoDocumentos(ObservableList<TipoDocumento> lista) {
		lista.add(new TipoDocumento(Documento.TIPO_CLASE, "Clase"));
		lista.add(new TipoDocumento(Documento.TIPO_CLASE_ABSTRACTA, "Clase Abstracta"));
		lista.add(new TipoDocumento(Documento.TIPO_INTERFAZ, "Interfaz"));
	}
	
	public void llenarTipoAcceso(ObservableList<TipoAcceso> lista, int type) {
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_PRIVATE, "private"));
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_PROTECTED, "protected"));
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_PUBLIC, "public"));
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_STATIC, "static"));
		if(type==0) {
			lista.add(new TipoAcceso(Documento.TIPO_ACCESO_CONST, "const"));
		}
	} 
	
	//Agregar elementos
	@FXML
	public void addItemAtributos() {
		TipoAcceso acceso = cmbTipoAccesoAtributo.getSelectionModel().getSelectedItem();
		String identificador = txtIdentificadorAtributo.getText();
		String default_valor = txtDefaultAtributo.getText();
		if(!identificador.equals("")) {
			if(
				(acceso.getId()==Documento.TIPO_ACCESO_CONST || 
				acceso.getId()==Documento.TIPO_ACCESO_STATIC) &&
				default_valor.equals("")
			) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Alerta");
				alert.setHeaderText("No se encontró valor por defecto");
				alert.setContentText("Atributo "+acceso.getNombre()+", necesita un valor.");
				alert.showAndWait();
			}else {
				Atributo nuevo = new Atributo(acceso, identificador, default_valor);
				listaAtributos.add(nuevo);
				mainDoc.addAtributo(nuevo);
				cleanFormAtributos();
			}
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta");
			alert.setContentText("Defina un identificador.");
			alert.showAndWait();
		}
		
	}
	
	@FXML
	public void addItemMetodos() {
		TipoAcceso acceso = cmbTipoAccesoMetodo.getSelectionModel().getSelectedItem();
		String identificador = txtIdentificadorMetodo.getText();
		if(!identificador.equals("")) {
			Metodo nuevo = new Metodo(acceso, identificador);
			listaMetodos.add(nuevo);
			mainDoc.addMetodo(nuevo);
			cleanFormMetodos();
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta");
			alert.setContentText("Defina un identificador.");
			alert.showAndWait();
		}
	}
	
	
	//Actualizar elementos
		@FXML
		public void setItemAtributos() {
			TipoAcceso acceso = cmbTipoAccesoAtributo.getSelectionModel().getSelectedItem();
			String identificador = txtIdentificadorAtributo.getText();
			String default_valor = txtDefaultAtributo.getText();
			if(!identificador.equals("")) {
				if(
					(acceso.getId()==Documento.TIPO_ACCESO_CONST || 
					acceso.getId()==Documento.TIPO_ACCESO_STATIC) &&
					default_valor.equals("")
				) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Alerta");
					alert.setHeaderText("No se encontró valor por defecto");
					alert.setContentText("Atributo "+acceso.getNombre()+", necesita un valor.");
					alert.showAndWait();
				}else {
					Atributo actualizado = new Atributo(acceso, identificador, default_valor);
					listaAtributos.set(selectedAtributo, actualizado);
					mainDoc.setAtributo(selectedAtributo, actualizado);
					cleanFormAtributos();
					btnSetAtributo.setDisable(true);
					btnAddAtributo.setDisable(false);
					btnEditAtributo.setDisable(false);
					selectedAtributo=-1;
				}
			}else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Alerta");
				alert.setContentText("Defina un identificador.");
				alert.showAndWait();
			}
			
		}
		
		@FXML
		public void setItemMetodos() {
			TipoAcceso acceso = cmbTipoAccesoMetodo.getSelectionModel().getSelectedItem();
			String identificador = txtIdentificadorMetodo.getText();
			if(!identificador.equals("")) {
				Metodo actualizado = new Metodo(acceso, identificador);
				listaMetodos.set(selectedMetodo, actualizado);
				mainDoc.setMetodo(selectedMetodo, actualizado);
				cleanFormMetodos();
				btnSetMetodo.setDisable(true);
				btnAddMetodo.setDisable(false);
				btnEditMetodo.setDisable(false);
				selectedMetodo=-1;
			}else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Alerta");
				alert.setContentText("Defina un identificador.");
				alert.showAndWait();
			}
		}
	
	//Borrar elementos
	@FXML
	public void deleteItemAtributos() {
		int item = tblAtributos.getSelectionModel().getSelectedIndex();
		if(item!=-1) {
			listaAtributos.remove(item);
			mainDoc.deleteAtributo(item);
		}
	}
	@FXML
	public void deleteItemMetodos() {
		int item = tblMetodos.getSelectionModel().getSelectedIndex();
		if(item!=-1) {
			listaMetodos.remove(item);
			mainDoc.deleteMetodo(item);
		}
	}
	
	// BorrarTodos los elementos
	@FXML
	public void deleteAllItemAtributos() {
		listaAtributos.clear();
		mainDoc.clearListaAtributos();
	}
	
	@FXML
	public void deleteAllItemMetodos() {
		listaMetodos.clear();
		mainDoc.clearListaMetodos();
	}
	
	// Editar Atributos
	@FXML
	public void EditAtributo() {
		selectedAtributo = tblAtributos.getSelectionModel().getSelectedIndex();
		if(selectedAtributo!=-1) {
			btnSetAtributo.setDisable(false);
			btnAddAtributo.setDisable(true);
			btnEditAtributo.setDisable(true);
			Atributo selec = tblAtributos.getSelectionModel().getSelectedItem();
			
			txtIdentificadorAtributo.setText(selec.getIdentificador());
			txtDefaultAtributo.setText(selec.getValorDefault());
			cmbTipoAccesoAtributo.getSelectionModel().select(selec.getAcceso());
			
		}
	}
	
	//Editar Metodos
	@FXML
	public void EditMetodo() {
		selectedMetodo = tblMetodos.getSelectionModel().getSelectedIndex();
		if(selectedMetodo!=-1) {
			btnSetMetodo.setDisable(false);
			btnAddMetodo.setDisable(true);
			btnEditMetodo.setDisable(true);
			Metodo selec = tblMetodos.getSelectionModel().getSelectedItem();
			
			txtIdentificadorMetodo.setText(selec.getIdentificador());
			cmbTipoAccesoMetodo.getSelectionModel().select(selec.getAcceso());
			
		}
	}
	
	
	//Limpiar entradas
	//Atributos
	public void cleanFormAtributos() {
		cmbTipoAccesoAtributo.getSelectionModel().select(0);
		txtIdentificadorAtributo.clear();
		txtDefaultAtributo.clear();
		txtIdentificadorAtributo.requestFocus();
	}
	//Metodos
	public void cleanFormMetodos() {
		cmbTipoAccesoMetodo.getSelectionModel().select(2);
		txtIdentificadorMetodo.clear();
		txtIdentificadorMetodo.requestFocus();
	}

	// LIMPIAR EDITOR
	@FXML
	public void clearEditor() {
		txtEditor.clear();
	}
	
	@FXML
	public void generarDoc() {
		if(!txtNombreDocumento.getText().equals("")) {
			mainDoc.setNameDoc(txtNombreDocumento.getText());
			mainDoc.setConstruct(cbxConstruct.isSelected());
			mainDoc.setGetAndSet(cbxGetSet.isSelected());
			mainDoc.setTostring(cbxToString.isSelected());
			txtEditor.setText(mainDoc.generar());
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta");
			alert.setHeaderText("Defina un nombre para la Clase");
			alert.showAndWait();
		}
	}
	
	@FXML 
	public void Salir() {
		Stage stage = (Stage)btnGenerate.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void Nuevo() {
		try {
			Stage stage = new Stage();
			AnchorPane raiz = (AnchorPane)FXMLLoader.load(getClass().getResource("View.fxml"));
			Scene scene = new Scene(raiz);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("PHP Generator 2.0");
			stage.getIcons().add(new Image("img/logo.png"));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void About() {
		try {
			Stage stage = new Stage();
			AnchorPane raiz = (AnchorPane)FXMLLoader.load(getClass().getResource("About.fxml"));
			
			String buffer;
			buffer = "";
			buffer+="Red Lion Studio"+"\n";
			buffer+="Generador de Clases para PHP - © 2017"+"\n";
			buffer+=""+"\n";
			
			Text text1 = new Text(buffer);
			
			buffer = "";
			buffer+="Sitio Web:"+"\n";
			Text text2 = new Text(buffer);
			Hyperlink url1 = new Hyperlink("http://jaguilar992.github.io/");
			
			buffer = "";
			buffer+=""+"\n";
			buffer+="Repositorio:\n";
			Text text3 = new Text(buffer);
			Hyperlink url2 = new Hyperlink("https://github.com/jaguilar992/PHPGenerator2");
			
		    TextFlow desc = new TextFlow(text1,text2, url1, text3, url2);
			desc.setLayoutX(331);
			desc.setLayoutY(54);
			desc.setPrefWidth(229);
			desc.setPrefHeight(292);
			raiz.getChildren().add(desc);
			
			
			Scene scene = new Scene(raiz);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("About");
			stage.getIcons().add(new Image("img/logo.png"));
			stage.show();
					
			url1.setOnMouseClicked((event)->{
			    try {
			    	Desktop.getDesktop().browse(new URL(url1.getText()).toURI());
			    } catch (IOException e) {
			        e.printStackTrace();
			    } catch (URISyntaxException e) {
					e.printStackTrace();
			    }
			});
			
			url2.setOnMouseClicked((event)->{
			    try {
			    	Desktop.getDesktop().browse(new URL(url2.getText()).toURI());
			    } catch (IOException e) {
			        e.printStackTrace();
			    } catch (URISyntaxException e) {
					e.printStackTrace();
			    }
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}



