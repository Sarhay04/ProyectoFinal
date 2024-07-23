package logico;
public class Microprocesador extends Componente {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoConexion;
    private double velocidadProcesamiento;

    public Microprocesador(String nombre, String marca, String modelo, double precio, int cantidadDisponible,
			String numeroDeSerie, String tipoConexion, double velocidadProcesamiento) {
		super(nombre, marca, modelo, precio, cantidadDisponible, numeroDeSerie);
		this.tipoConexion = tipoConexion;
		this.velocidadProcesamiento = velocidadProcesamiento;
	}


    @Override
    public void mostrarInformacion() {
        System.out.println("Microprocesador - Marca: " + marca + ", Modelo: " + modelo + ", Precio: " + precio +
                ", Cantidad Disponible: " + cantidadDisponible + ", N�mero de Serie: " + numeroDeSerie +
                ", Tipo de Conexi�n: " + tipoConexion + ", Velocidad de Procesamiento: " + velocidadProcesamiento + " GHz");
    }

    // Getters y Setters
    public String getTipoConexion() {
        return tipoConexion;
    }

    public void setTipoConexion(String tipoConexion) {
        this.tipoConexion = tipoConexion;
    }

    public double getVelocidadProcesamiento() {
        return velocidadProcesamiento;
    }

    public void setVelocidadProcesamiento(double velocidadProcesamiento) {
        this.velocidadProcesamiento = velocidadProcesamiento;
    }
}