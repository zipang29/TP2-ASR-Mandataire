import java.util.Vector ;
import java.net.ServerSocket ;
import java.net.Socket ;
import java.io.PrintStream ;
import java.io.BufferedReader ;
import java.io.InputStreamReader ;
import java.io.IOException ;

/**
 * Modélise un serveur de messagerie instantanée.
 */
public class Server {
	
	private Vector<PrintStream> clients ;
	private ServerSocket sSocket ;

	/**
	 * Constructeur
	 * @param port Port sur lequel le seveur se lance.
	 */
	public Server( int port ) throws IOException {
		// On crée le socket du serveur sur le port
		this.sSocket = new ServerSocket( port ) ;
		System.out.println( "[~] socket du serveur : " + this.sSocket ) ;
		// On crée une liste vide de clients
		this.clients = new Vector<PrintStream>() ;
		this.daemon() ;
	}

	/**
	 * Gère les nouvelles connexions.
	 * Un thread par client.
	 */
	private void daemon() throws IOException {
		System.out.println( "[*] Le serveur est démarré." ) ;
		while ( true ) {
			// On accepte la connexion
			Socket call = sSocket.accept() ;
			System.out.println( "[*] Nouvelle connexion entrante depuis : " + call.getRemoteSocketAddress() ) ;
			// On récupère les flux in/out
			BufferedReader in = new BufferedReader( new InputStreamReader( call.getInputStream() ) ) ;
			PrintStream out = new PrintStream( call.getOutputStream() ) ;
			out.println("Utilisez 'quit' pour vous déconnecter du serveur.");
			// On ajoute le client à la liste
			clients.add(out);
			// On crée et on lance le Thread
			myT threadClient = new myT( in , out ) ;
			threadClient.start() ;
		}
	}

	/**
	 * Classe interne gérant la transmission des messages à tout les clients
	 */
	private class myT extends Thread {
		
		private BufferedReader in ;
		private PrintStream out ;
		private String alias ;

		/**
		 * Constructeur
		 * @param cIn Flux in
		 * @param cOut Flux out
		 */
		public myT( BufferedReader cIn , PrintStream cOut ) {
			this.in = cIn ;
			this.out = cOut ;
		}
		
		/**
		 * Gère les nouveaux messages écrit par un utilisateur,
		 * envoi les messages écrits par un client aux autres clients.
		 */
		public void run() {
			try {
				String tmp = "" ;
				while ( tmp.isEmpty() ) {
					out.print( "[>] Entrez votre alias : " ) ;
					out.flush() ;
					tmp = in.readLine() ;
					if ( ! tmp.isEmpty() ) {
						this.alias = tmp ;
					}
					else {
						out.println( "[-] Votre alias ne peut pas être vide !" ) ;
					System.out.println( "[*] Utilisateur '" + this.alias + "' loggé." ) ;
					}
				}
			}
			catch ( IOException e ) {
				System.out.println( "[-] L'alias n'a pas pu être saisi : " + e.getMessage() ) ;
			}
			while(true) {
				String msg = "";
				this.out.print("> ");
				this.out.flush();
				try {
					msg = in.readLine();
				} 
				catch (IOException e) {
					System.out.println( "[-] Un message de '" + this.alias + "' n'a pas pu être lu : " + e.getMessage() ) ;
				}
				if(msg == null || msg.equals("quit")) {
					synchronized (this) {
						clients.remove(this.out);
						out.close();
						try {
							in.close();
						} 
						catch (IOException e) {
							System.out.println( "[-] Le flux in de '" + this.alias + "' n'a pas pu être fermé : " + e.getMessage() ) ;
						}
					}
					break;
				}
				for(PrintStream i : Server.this.clients) {
					if (i != this.out) {
						i.println("\r" + this.alias + " :\t" + msg);
						i.print( "> " ) ;
						i.flush() ;
					}
				}
			}
			System.out.println("[*] Utilisateur " + this.alias + " déconnecté.");
		}
	}
}
