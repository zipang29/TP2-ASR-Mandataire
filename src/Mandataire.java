import java.util.Vector;
import java.io.BufferedReader;
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
	private Vector<DataInputStream> receptionIn;
	private Vector<DataOutputStream> receptionOut;
	private Vector<DataOutputStream> envoiOut;
	private Vector<DataInputStream> envoiIn;
	private ServerSocket serveurSocketReception;
	
	
	/**
	 * @throws IOException 
	 * 
	 */
	public Mandataire(int portC, int portServeur, String adresse) throws IOException {
		this.serveurSocketReception = new ServerSocket(portServeur);
		System.out.println("[~] socket du serveur de réception : " + this.serveurSocketReception);
		
		this.receptionIn = new Vector<DataInputStream>();
		this.receptionOut = new Vector<DataOutputStream>();
		this.envoiOut = new Vector<DataOutputStream>();
		this.envoiIn = new Vector<DataInputStream>();
		
		System.out.println("[*] Le serveur est démarré.");
		while(true) {
			// On accepte la connexion
			Socket receptionSocket = this.serveurSocketReception.accept();
			System.out.println( "[*] Nouvelle connexion entrante depuis : " + receptionSocket.getRemoteSocketAddress());
			//On récupère le IN et le OUT du vrai client
			DataInputStream inReception = new DataInputStream(receptionSocket.getInputStream());
			DataOutputStream outReception = new DataOutputStream(receptionSocket.getOutputStream());
			receptionIn.add(inReception);
			receptionOut.add(outReception);
			
			//On récupère le IN et le OUT du vrai serveur
			DataInputStream inEnvoi = new DataInputStream();
			DataOutputStream outEnvoi = new DataOutputStream();
			//On se connecte en tant que client au serveur demandé
			Socket envoiSocket = new Socket(adresse, portServeur);
			System.out.println("[*] Nouvelle connexion sortante vers le serveur " + adresse);
			
		}
	}
}