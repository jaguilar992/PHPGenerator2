package model;

import java.util.ArrayList;

public class Documento {
	private boolean construct = false;
	private boolean tostring = false;
	private boolean getAndSet = false;
	private String path="";
	private String nameDoc="";
	
	
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
	
	
	// TAGS
	private final String TAG_INIT = "<?php"; 
	private final String TAG_END = "?>";
	private final String TAB = "\t";
	private final String END = "\n";
	
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
	
	public void deleteMetodo(int m) {
		listaMetodos.remove(m);
	}
	
	public void deleteAtributo(int a) {
		listaAtributos.remove(a);
	}
	
	public void setMetodo(int i, Metodo m) {
		listaMetodos.set(i, m);
	}
	
	public void setAtributo(int i, Atributo a) {
		listaAtributos.set(i, a);
	}
	
	
	public void clearListaAtributos() {
		this.listaAtributos.clear();
	}
	
	public void clearListaMetodos() {
		this.listaMetodos.clear();
	}


	public void setConstruct(boolean construct) {
		this.construct = construct;
	}


	public void setTostring(boolean tostring) {
		this.tostring = tostring;
	}

	
	public void setGetAndSet(boolean getAndSet) {
		this.getAndSet = getAndSet;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNameDoc() {
		return nameDoc;
	}

	public void setNameDoc(String nameDoc) {
		this.nameDoc = nameDoc;
	}

	public ArrayList<Atributo> getListaAtributos() {
		return listaAtributos;
	}

	public ArrayList<Metodo> getListaMetodos() {
		return listaMetodos;
	}
	// Generador
	public String generar() {
		String doc ="";
		doc+=TAG_INIT+END;
		doc+="class "+nameDoc+"{"+END;
		if(listaAtributos.size()>0) {
			doc+=genAtr();
			doc+=END;
		}
		if (construct) {
			doc+=genConstruct();
			doc+=END;
		}
		if(tostring) {
			doc+=genToString();
			doc+=END;
					}
		
		if(getAndSet) {
			doc+=genGetSet();
			doc+=END;
		}
		
		if (listaMetodos.size()>0) {
			doc+=genMetodos();
			doc+=END;
		}
		
		doc+="}"+END;
		doc+=TAG_END;
		return doc;
	}
	
	public String genAtr() {
		String buffer="";
		for (Atributo a : listaAtributos) {
			boolean valor_default = !a.getValorDefault().equals("");
			switch (a.getAcceso().getId()) {
			case TIPO_ACCESO_PUBLIC:
				buffer+=TAB+"public $";
				buffer+=a.getIdentificador();
				buffer+= (!construct && valor_default) ? " = "+a.getValorDefault()+";"+END: ";"+END;
			break;
			case TIPO_ACCESO_PRIVATE:
				buffer+=TAB+"private $";
				buffer+=a.getIdentificador();
				buffer+= (!construct && valor_default) ? " = "+a.getValorDefault()+";"+END: ";"+END;
			break;
			case TIPO_ACCESO_PROTECTED:
				buffer+=TAB+"protected $";
				buffer+=a.getIdentificador();
				buffer+= (!construct && valor_default) ? " = "+a.getValorDefault()+";"+END: ";"+END;
			break;
			case TIPO_ACCESO_CONST:
				buffer+=TAB+"public const ";
				buffer+=a.getIdentificador();
				buffer+=" = "+a.getValorDefault()+";"+END;
			break;
			case TIPO_ACCESO_STATIC:
				buffer+=TAB+"public static $";
				buffer+=a.getIdentificador();
				buffer+=" = "+a.getValorDefault()+";"+END;
			break;
			default:
			break;
			}
		}
		return buffer;
	}
	
	public String genConstruct() {
		String buffer="";
		//buffer+=TAB+"#Constructor de la clase."+END;
		//buffer+=TAB+"#Soporta \"Sobrecarga\" secuencial."+END;
		buffer+=TAB+"public function __construct(";
		int j = 0;
		for (int i=0; i<listaAtributos.size(); i++) {
			Atributo a = listaAtributos.get(i);
			if ((
				a.getAcceso().getId()!=TIPO_ACCESO_CONST &&
				a.getAcceso().getId()!=TIPO_ACCESO_STATIC
			)) {
				boolean val = !a.getValorDefault().equals("");
				buffer+=(j!=0) ? ",":"";
				buffer+=END+TAB+TAB;
				buffer+="$"+a.getIdentificador()+" = ";
				buffer+= (val) ? a.getValorDefault() :"null";
				j++;
			}
		}
		buffer+=END+TAB+"){";
		buffer+=END;
		for (Atributo a : listaAtributos) {
			if((
				a.getAcceso().getId()!=TIPO_ACCESO_CONST &&
				a.getAcceso().getId()!=TIPO_ACCESO_STATIC	
			)) {
				buffer+=TAB+TAB+"$this->"+a.getIdentificador()+" = $"+a.getIdentificador()+";"+END;
			}
		}
		buffer+=TAB+"}";
		return buffer+END;
		
	}
	
	public String genToString() {
		String buffer="";
		//buffer+=TAB+"#__toString()"+END;
		buffer+=TAB+"public function __toString(){"+END;
		buffer+=TAB+TAB+"$var = \""+nameDoc+"{\"";
		int j=0;
		for (Atributo a:listaAtributos) {
			if((
				a.getAcceso().getId()!=TIPO_ACCESO_CONST &&
				a.getAcceso().getId()!=TIPO_ACCESO_STATIC	
			)) {
				buffer+=(j!=0)?".\" , \"":"";
				buffer+=END+TAB+TAB;
				buffer+=".\""+a.getIdentificador()+": \"."+"$this->"+a.getIdentificador();
				j++;
			}
		}
		buffer+=";"+END;
		buffer+=TAB+TAB+"return $var.\"}\";"+END;
		buffer+=TAB+"}";
		return buffer+END;
	}

	public String genGetSet() {
		String buffer="";
		for (Atributo a:listaAtributos) {
			if((
				a.getAcceso().getId()!=TIPO_ACCESO_PUBLIC &&
				a.getAcceso().getId()!=TIPO_ACCESO_CONST &&
				a.getAcceso().getId()!=TIPO_ACCESO_STATIC
			)) {
				String id = a.getIdentificador();
				buffer+=TAB+"public function get"+title(id)+"(){"+END;
				buffer+=TAB+TAB+"return $this->"+id+";"+END;
				buffer+=TAB+"}"+END;
				buffer+=END;
				buffer+=TAB+"public function set"+title(id)+"($"+id+"){"+END;
				buffer+=TAB+TAB+"$this->"+id+" = $"+id+";"+END;
				buffer+=TAB+"}"+END;
			}
		}
		return buffer;
	}
	
	
	public String genMetodos() {
		String buffer="";
		for(Metodo m : listaMetodos) {
			String id = m.getIdentificador();
			switch (m.getAcceso().getId()) {
			case TIPO_ACCESO_PUBLIC:
				buffer+=TAB+"public ";
			break;
			case TIPO_ACCESO_STATIC:
				buffer+=TAB+"public static ";
			break;
			case TIPO_ACCESO_PRIVATE:
				buffer+=TAB+"private ";
			break;
			case TIPO_ACCESO_PROTECTED:
				buffer+=TAB+"protected ";
			break;
			default:break;
			}
			buffer+="function "+id+"(/*Parametros*/){";
			buffer+=END+TAB+"}"+END;
		}
		return buffer;
	}
	
	
	
	public String title(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
}
