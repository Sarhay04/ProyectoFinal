package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Tienda implements Serializable {
private static final long serialVersionUID = 1L;
	
	private ArrayList<Componente> inventario;
    private ArrayList<Cliente> misClientes;
    private ArrayList<Venta> misVentas;
    private ArrayList<User> misUsuarios;
	public static Tienda tienda;
	private static User loginUser;


    
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
		this.misUsuarios = new ArrayList<User>();
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
    
    public Venta getVentaByCodigo(String codigo)
    {
    	for(Venta vta: misVentas)
    	{
    		if(vta.getCodigo().equals(codigo))
    		{
    			return vta;
    		}
    	}
    	return null;
    }
    
    public boolean confirmLoginInfo(String usuario, String password) {
		boolean login = false;
		for (User user : misUsuarios) {
			if (user.getUsuario().equals(usuario) && user.getPassword().equals(password)) {
				loginUser = user;
				login = true;
			}
		}
		return login;
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
    
    public void agregarUsuario(User usuario)
    {
    	misUsuarios.add(usuario);
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
    
    public User getUsuariobyTipo(String string) {

		User temp = null;
		boolean encontrado = false;
		int ind = 0;

		while (!encontrado && ind < misUsuarios.size()) {
			if (misUsuarios.get(ind).getUsuario().equalsIgnoreCase(string)) {
				temp = misUsuarios.get(ind);
				encontrado = true;
			}
			ind++;
		}
		return temp;
	}
    
    public void EliminarUser(User user) {
		misUsuarios.remove(user);
	}
    
    public ArrayList<User> getUsuariosbyTipo(String tipoSeleccionado) {
		ArrayList<User> usuariosPorTipo = new ArrayList<>();

		for (User user : misUsuarios) {
			if (user.getTipo().equals(tipoSeleccionado)) {
				usuariosPorTipo.add(user);
			}
			if (tipoSeleccionado.equalsIgnoreCase("<Todos>")) {
				usuariosPorTipo = getMisUsuarios();
			}
		}

		return usuariosPorTipo;
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

	public ArrayList<User> getMisUsuarios() {
		return misUsuarios;
	}

	public void setMisUsuarios(ArrayList<User> misUsuarios) {
		this.misUsuarios = misUsuarios;
	}

	public static User getLoginUser() {
		return loginUser;
	}

	public static Tienda getTienda() {
		return tienda;
	}

	public static void setTienda(Tienda tienda) {
		Tienda.tienda = tienda;
	}
    

}
