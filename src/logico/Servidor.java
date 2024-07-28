package logico;

import java.io.*;
import java.net.*;

public class Servidor {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(7000);
			System.out.println("Servidor listo para recibir conexiones...");

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Cliente conectado desde: " + socket.getInetAddress());

				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Tienda tienda = (Tienda) ois.readObject();

				ois.close();
				socket.close();

				guardarCopiaRespaldo(tienda);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void guardarCopiaRespaldo(Tienda tienda) {
		try {
			FileOutputStream fileOut = new FileOutputStream("copia_respaldo_Tienda.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fileOut);
			oos.writeObject(tienda);
			oos.close();
			fileOut.close();
			System.out.println("Copia de respaldo de la Tienda guardada exitosamente.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
