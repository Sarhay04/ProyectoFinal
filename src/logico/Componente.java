package logico;

public abstract class Componente {
	
	protected String marca;
    protected String modelo;
    protected double precio;
    protected int cantidadDisponible;
    protected String numeroDeSerie;

    // Constructor
    public Componente(String marca, String modelo, double precio, int cantidadDisponible, String numeroDeSerie) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
        this.numeroDeSerie = numeroDeSerie;
    }

    // Getters y Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public abstract void mostrarInformacion();


}
