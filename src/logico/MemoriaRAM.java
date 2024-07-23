package logico;
public class MemoriaRAM extends Componente {
	
    private int cantidadMemoria;
    private String tipoMemoria;

    public MemoriaRAM(String nombre, String marca, String modelo, double precio, int cantidadDisponible,
			String numeroDeSerie, int cantidadMemoria, String tipoMemoria) {
		super(nombre, marca, modelo, precio, cantidadDisponible, numeroDeSerie);
		this.cantidadMemoria = cantidadMemoria;
		this.tipoMemoria = tipoMemoria;
	}

    @Override
    public void mostrarInformacion() {
        System.out.println("Memoria RAM - Marca: " + marca + ", Modelo: " + modelo + ", Precio: " + precio +
                ", Cantidad Disponible: " + cantidadDisponible + ", N�mero de Serie: " + numeroDeSerie +
                ", Cantidad de Memoria: " + cantidadMemoria + " MB/Gb, Tipo de Memoria: " + tipoMemoria);
    }

    // Getters y Setters
    public int getCantidadMemoria() {
        return cantidadMemoria;
    }

    public void setCantidadMemoria(int cantidadMemoria) {
        this.cantidadMemoria = cantidadMemoria;
    }

    public String getTipoMemoria() {
        return tipoMemoria;
    }

    public void setTipoMemoria(String tipoMemoria) {
        this.tipoMemoria = tipoMemoria;
    }
}