package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Atributo;
import model.Documento;
import model.Metodo;
import model.TipoAcceso;
import model.TipoDocumento;

public class Controller implements Initializable{
	private Documento mainDoc = new Documento();
	//private Documento importDoc = new Documento();

	//Listas
	private ObservableList<Atributo> listaAtributos;
	private ObservableList<Metodo> listaMetodos;
	private ObservableList<TipoDocumento> listaTipoDocumento;
	private ObservableList<TipoAcceso> listaTipoAcceso;

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
	
	@FXML Button btnAddMetodo;
	@FXML Button btnSetMetodo;
	@FXML Button btnDelMetodo;
	@FXML Button btnDelAllMetodos;
	
		//	TextArea
	@FXML TextArea txtEditor;
		//	TextFields
	@FXML TextField txtNombreDocumento;
	@FXML TextField txtIdentificadorAtributo;
	@FXML TextField txtDefaultAtributo;
	@FXML TextField txtIdentificadorMetodo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializar Listas
		listaAtributos = FXCollections.observableArrayList();
		listaMetodos = FXCollections.observableArrayList();
		listaTipoDocumento = FXCollections.observableArrayList();
		listaTipoAcceso = FXCollections.observableArrayList();
		
		//Llenar elementos en Listas
		this.llenarTipoDocumentos(listaTipoDocumento);
		this.llenarTipoAcceso(listaTipoAcceso);
				
		// Enlazar listas con TableViews
		tblAtributos.setItems(listaAtributos);
		tblMetodos.setItems(listaMetodos);
		cmbTipoDocumento.setItems(listaTipoDocumento);
		cmbTipoAccesoAtributo.setItems(listaTipoAcceso);
		cmbTipoAccesoMetodo.setItems(listaTipoAcceso);
		
		//Seleccionar primer elemento de la lista
		cmbTipoDocumento.getSelectionModel().select(listaTipoDocumento.get(0));
		cmbTipoAccesoAtributo.getSelectionModel().select(listaTipoAcceso.get(0));
		cmbTipoAccesoMetodo.getSelectionModel().select(listaTipoAcceso.get(0));
		
		// Enlazar Columnas de TableViews
		colAtributoAcceso.setCellValueFactory(new PropertyValueFactory<Atributo,String>("acceso"));
		colAtributoIdentif.setCellValueFactory(new PropertyValueFactory<Atributo,String>("identificador"));
		colAtributoDefault.setCellValueFactory(new PropertyValueFactory<Atributo,String>("valorDefault"));

		colMetodoAcceso.setCellValueFactory(new PropertyValueFactory<Metodo, String>("acceso"));
		colMetodoIdentificador.setCellValueFactory(new PropertyValueFactory<Metodo, String>("identificador"));
		
		
		//Manejar Modelo
		
	}
	
	public void llenarTipoDocumentos(ObservableList<TipoDocumento> lista) {
		lista.add(new TipoDocumento(Documento.TIPO_CLASE, "Clase"));
		lista.add(new TipoDocumento(Documento.TIPO_CLASE_ABSTRACTA, "Clase Abstracta"));
		lista.add(new TipoDocumento(Documento.TIPO_INTERFAZ, "Interfaz"));
	}
	
	public void llenarTipoAcceso(ObservableList<TipoAcceso> lista) {
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_PRIVATE, "private"));
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_PROTECTED, "protected"));
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_PUBLIC, "public"));
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_CONST, "const"));
		lista.add(new TipoAcceso(Documento.TIPO_ACCESO_STATIC, "static"));
	} 
	
	//Agregar elementos
	@FXML
	public void addItemAtributos() {
		TipoAcceso acceso = cmbTipoAccesoAtributo.getSelectionModel().getSelectedItem();
		String identificador = txtIdentificadorAtributo.getText();
		String default_valor = txtDefaultAtributo.getText();
		Atributo nuevo = new Atributo(acceso, identificador, default_valor);
		listaAtributos.add(nuevo);
		mainDoc.addAtributo(nuevo);
		
	}
	
	@FXML
	public void addItemMetodos() {
		TipoAcceso acceso = cmbTipoAccesoMetodo.getSelectionModel().getSelectedItem();
		String identificador = txtIdentificadorMetodo.getText();
		Metodo nuevo = new Metodo(acceso, identificador);
		listaMetodos.add(nuevo);
		mainDoc.addMetodo(nuevo);
		cleanFormAtributos();
	}
	
	//Borrar elementos
	@FXML
	public void deleteItemAtributos() {
		int item = tblAtributos.getSelectionModel().getSelectedIndex();
		if(item!=-1) {
			listaAtributos.remove(item);
		}
	}
	@FXML
	public void deleteItemMetodos() {
		int item = tblMetodos.getSelectionModel().getSelectedIndex();
		if(item!=-1) {
			listaMetodos.remove(item);
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
	
	//Limpiar entradas
	//Atributos
	public void cleanFormAtributos() {
		cmbTipoAccesoAtributo.getSelectionModel().select(0);
		txtIdentificadorAtributo.clear();
		txtDefaultAtributo.clear();
	}
	public void cleanFormMetodos() {
		cmbTipoAccesoMetodo.getSelectionModel().select(0);
		txtIdentificadorMetodo.clear();
	}
	//Metodos
	
}
