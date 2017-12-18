package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Metodo{
	private TipoAcceso acceso;
	private StringProperty identificador;

	public Metodo(TipoAcceso acceso, String identificador) { 
		this.acceso = acceso;
		this.identificador = new SimpleStringProperty(identificador);
	}

	//Metodos atributo: acceso
	
	public TipoAcceso getAcceso() {
		return acceso;
	}

	public void setAcceso(TipoAcceso acceso) {
		this.acceso = acceso;
	}

	//Metodos atributo: identificador
	public String getIdentificador() {
		return identificador.get();
	}
	
	public void setIdentificador(String identificador) {
		this.identificador = new SimpleStringProperty(identificador);
	}
	public StringProperty IdentificadorProperty() {
		return identificador;
	}
}