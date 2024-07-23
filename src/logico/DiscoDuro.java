package logico;
public class DiscoDuro extends Componente {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int capacidadAlmacenamiento;
    private String tipoConexion;

    public DiscoDuro(String nombre, String marca, String modelo, double precio, int cantidadDisponible,
			String numeroDeSerie, int capacidadAlmacenamiento, String tipoConexion) {
		super(nombre, marca, modelo, precio, cantidadDisponible, numeroDeSerie);
		this.capacidadAlmacenamiento = capacidadAlmacenamiento;
		this.tipoConexion = tipoConexion;
	}

    @Override
    public void mostrarInformacion() {
        System.out.println("Disco Duro - Marca: " + marca + ", Modelo: " + modelo + ", Precio: " + precio +
                ", Cantidad Disponible: " + cantidadDisponible + ", N�mero de Serie: " + numeroDeSerie +
                ", Capacidad de Almacenamiento: " + capacidadAlmacenamiento + " GB/TB, Tipo de Conexi�n: " + tipoConexion);
    }

    // Getters y Setters
    public int getCapacidadAlmacenamiento() {
        return capacidadAlmacenamiento;
    }

    public void setCapacidadAlmacenamiento(int capacidadAlmacenamiento) {
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
    }

    public String getTipoConexion() {
        return tipoConexion;
    }

    public void setTipoConexion(String tipoConexion) {
        this.tipoConexion = tipoConexion;
    }
}