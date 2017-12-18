package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Atributo{
	private TipoAcceso acceso;
	private StringProperty identificador;
	private StringProperty valorDefault;

	public Atributo(TipoAcceso acceso, String identificador, String valorDefault) { 
		this.acceso = acceso;
		this.identificador = new SimpleStringProperty(identificador);
		this.valorDefault = new SimpleStringProperty(valorDefault);
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
	//Metodos atributo: valorDefault
	public String getValorDefault() {
		return valorDefault.get();
	}
	public void setValorDefault(String valorDefault) {
		this.valorDefault = new SimpleStringProperty(valorDefault);
	}
	public StringProperty ValorDefaultProperty() {
		return valorDefault;
	}
}