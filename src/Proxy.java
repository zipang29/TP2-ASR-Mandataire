import java.net.Socket ;
import java.net.ServerSocket ;
import java.io.DataOutputStream ;
import java.io.DataInputStream ;

public class Proxy {
	
	private ServerSocket serveur ;
	private String chatIP ;
	private int chatPort ;

	public Proxy( int lport, String serveurIP, int sport ) {
		// vérifier l'input lport
		try {
			serveur = new ServerSocket( lport ) ;
		} catch ( java.io.IOException e ) {
			e.printStackTrace() ; 
		}
		this.chatIP = serveurIP ;
		this.chatPort = sport ;
		this.daemon() ;
	}

	public void daemon() {
		System.out.println( "[+] Serveur proxy lancé " ) ;
		while ( true ) {
			try {
				// Connexion d'un client au proxy
				Socket call = serveur.accept() ;
				DataInputStream inC = new DataInputStream( call.getInputStream() ) ;
				DataOutputStream outC = new DataOutputStream( call.getOutputStream() ) ;
				System.out.println( "[~] Informations sur le client : " + call ) ;
				// Connexion en tant que client au chat
				Socket socket = new Socket( this.chatIP , this.chatPort ) ;
				DataOutputStream outS = new DataOutputStream( socket.getOutputStream() ) ;
				DataInputStream inS = new DataInputStream( socket.getInputStream() ) ;
				System.out.println( "[~] co au chat : " + socket ) ;
				// Création des Treads
				( new myThread( inS , outC ) ).start() ;
				( new myThread( inC , outS ) ).start() ;
			} catch ( java.io.IOException e ) {
				e.printStackTrace() ; 
			}
		}
	}
}
