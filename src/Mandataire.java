import java.util.Vector;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Guillaume
 *
 */
public class Mandataire {
	private Vector<DataInputStream> reception;
	private Vector<DataOutputStream> envoi;
	private ServerSocket serveurSocketReception;
	
	
	/**
	 * @throws IOException 
	 * 
	 */
	public Mandataire(int portC, int portServeur, String adresse) throws IOException {
		this.serveurSocketReception = new ServerSocket(portServeur);
		System.out.println("[~] socket du serveur de réception : " + this.serveurSocketReception);
		
		this.reception = new Vector<DataInputStream>();
		this.envoi = new Vector<DataOutputStream>();
		
		System.out.println("[*] Le serveur est démarré.");
		while(true) {
			// On accepte la connexion
			Socket receptionSocket = this.serveurSocketReception.accept();
			System.out.println( "[*] Nouvelle connexion entrante depuis : " + receptionSocket.getRemoteSocketAddress());
			//On se connecte en tant que client au serveur demandé
			Socket envoiSocket = new Socket(adresse, portServeur);
			
		}
	}
}