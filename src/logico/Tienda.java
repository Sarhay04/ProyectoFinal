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
    
    public void agregarCliente(Cliente clt)
    {
    	misClientes.add(clt);
    	return;
    }
    
    public void agregarVenta(Venta vta)
    {
    	misVentas.add(vta);
    	Venta.codigoVenta++;
    	for(VentaComponente aux: vta.getMisComps())
    	{
    		for(Componente aux2: inventario)
    		{
    			if(aux.getComp().equals(aux2))
    			{
    				aux2.setCantidadDisponible(aux2.getCantidadDisponible() - aux.getQty());
    			}
    		}
    	}
    	return;
    }
    
    
    public Cliente getClienteByCedula(String Cedula)
    {
    	for(Cliente clt: misClientes)
    	{
    		if(clt.getCedula().equalsIgnoreCase(Cedula))
    		{
    			return clt;
    		}
    	}
    	return null;
    }
    
    public boolean checkClienteDelete(Cliente clt)
    {
    	for(Venta aux: misVentas)
    	{
    		if(aux.getMiCliente().equals(clt));
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    public void borrarCliente(Cliente clt)
    {
    	misClientes.remove(clt);
    	return;
    }


	public ArrayList<Componente> getInventario() {
		return inventario;
	}

	public ArrayList<Cliente> getMisClientes() {
		return misClientes;
	}

	public void setMisClientes(ArrayList<Cliente> misClientes) {
		this.misClientes = misClientes;
	}

	public ArrayList<Venta> getMisVentas() {
		return misVentas;
	}

	public void setMisVentas(ArrayList<Venta> misVentas) {
		this.misVentas = misVentas;
	}

	public void setInventario(ArrayList<Componente> inventario) {
		this.inventario = inventario;
	}
    

}
