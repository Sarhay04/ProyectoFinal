package logico;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class Client {

	public static void enviarCopiaRespaldo() {
		try {
			Socket socket = new Socket("127.0.0.1", 7000);

			Tienda tienda = Tienda.getInstance();

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(tienda);

			oos.close();
			socket.close();
			System.out.println("Copia de respaldo enviada exitosamente.");
			JOptionPane.showMessageDialog(null, "Respaldo enviado exitosamente", "Respaldo",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
