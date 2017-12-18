package model;

import java.util.ArrayList;

public class Documento {
	//Tipos Documento
	public static final int TIPO_CLASE = 0;
	public static final int TIPO_CLASE_ABSTRACTA = 1;
	public static final int TIPO_INTERFAZ = 2;
	//Tipos Acceso
	public static final int TIPO_ACCESO_PRIVATE = 0;
	public static final int TIPO_ACCESO_PUBLIC = 1;
	public static final int TIPO_ACCESO_PROTECTED = 2;
	public static final int TIPO_ACCESO_STATIC = 3;
	public static final int TIPO_ACCESO_CONST = 4;
	 
	private ArrayList<Atributo> listaAtributos;
	private ArrayList<Metodo> listaMetodos;
	
	private String path="";
	private String nameDoc="";
	
	// TAGS
	private final String TAG_INIT = "<?php"; 
	private final String TAG_END = "?>";
	
	public Documento() {
		this.listaAtributos = new ArrayList<>();
		this.listaMetodos = new ArrayList<>();
	}
	
	public void addAtributo(Atributo a) {
		listaAtributos.add(a);
	}
	
	public void addMetodo(Metodo m) {
		listaMetodos.add(m);
	}
	
	public void clearListaAtributos() {
		this.listaAtributos.clear();
	}
	
	public void clearListaMetodos() {
		this.listaMetodos.clear();
	}
	
}
