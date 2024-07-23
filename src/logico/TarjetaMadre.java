package logico;

import java.util.ArrayList;

public class TarjetaMadre extends Componente {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoConectorMicro;
	    private String tipoMemoriaRAM;
	    private ArrayList<String> conexionesDiscosDuros;

	    public TarjetaMadre(String nombre, String marca, String modelo, double precio, int cantidadDisponible,
				String numeroDeSerie, String tipoConectorMicro, String tipoMemoriaRAM,
				ArrayList<String> conexionesDiscosDuros) {
			super(nombre, marca, modelo, precio, cantidadDisponible, numeroDeSerie);
			this.tipoConectorMicro = tipoConectorMicro;
			this.tipoMemoriaRAM = tipoMemoriaRAM;
			this.conexionesDiscosDuros = conexionesDiscosDuros;
		}


	    @Override
	    public void mostrarInformacion() {
	        System.out.println("Tarjeta Madre - Marca: " + marca + ", Modelo: " + modelo + ", Precio: " + precio +
	                ", Cantidad Disponible: " + cantidadDisponible + ", N�mero de Serie: " + numeroDeSerie +
	                ", Tipo de Conector para Micro: " + tipoConectorMicro + ", Tipo de Memoria RAM: " + tipoMemoriaRAM +
	                ", Conexiones para Discos Duros: " + conexionesDiscosDuros);
	    }

	    // Getters y Setters
	    public String getTipoConectorMicro() {
	        return tipoConectorMicro;
	    }

	    public void setTipoConectorMicro(String tipoConectorMicro) {
	        this.tipoConectorMicro = tipoConectorMicro;
	    }

	    public String getTipoMemoriaRAM() {
	        return tipoMemoriaRAM;
	    }

	    public void setTipoMemoriaRAM(String tipoMemoriaRAM) {
	        this.tipoMemoriaRAM = tipoMemoriaRAM;
	    }

	    public ArrayList<String> getConexionesDiscosDuros() {
	        return conexionesDiscosDuros;
	    }

	    public void setConexionesDiscosDuros(ArrayList<String> conexionesDiscosDuros) {
	        this.conexionesDiscosDuros = conexionesDiscosDuros;
	    }
}
