package logico;

import java.util.ArrayList;

public class Tienda {
	
    

	private ArrayList<Componente> inventario;
    private ArrayList<Cliente> misClientes;
    private ArrayList<Venta> misVentas;
	public static Tienda tienda;

    
    public static Tienda getInstance() {
		if (tienda == null) {
			tienda = new Tienda();
		}

		return tienda;
	}
    
    public Tienda() {
		super();
		this.inventario = new ArrayList<Componente>();
		this.misClientes = new ArrayList<Cliente>();
		this.misVentas = new ArrayList<Venta>();
	}

    public void agregarComponente(Componente componente) {
        inventario.add(componente);
    }

    public boolean eliminarComponente(String numeroDeSerie) {
        for (Componente componente : inventario) {
            if (componente.getNumeroDeSerie().equals(numeroDeSerie)) {
                inventario.remove(componente);
                return true;
            }
        }
        return false;
    }

    public Componente buscarComponente(String numeroDeSerie) {
        for (Componente componente : inventario) {
            if (componente.getNumeroDeSerie().equals(numeroDeSerie)) {
                return componente;
            }
        }
        return null;
    }

    public boolean actualizarComponente(String numeroDeSerie, Componente nuevoComponente) {
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).getNumeroDeSerie().equals(numeroDeSerie)) {
                inventario.set(i, nuevoComponente);
                return true;
            }
        }
        return false;
    }

    public void mostrarInventario() {
        for (Componente componente : inventario) {
            componente.mostrarInformacion();
        }
    }
}
