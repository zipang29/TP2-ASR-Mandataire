public class ProxyLauncher {

	public static void main( String[] args ) {
		int port = 1100 ;
		String chatIP = "localhost" ;
		int chatPort = 1099 ;
		if ( args.length != 1 ) { 
			help() ; 
		}
		else {
			try { 
				port = Integer.valueOf( args[0] ) ;
				chatIP = args[1] ;
				chatPort = Integer.valueOf( args[2] ) ;
			}
			catch ( NumberFormatException e ) {
				System.out.println( "[-] Un des ports a été mal renseigné : " + e.getMessage() ) ;
			}
		}
		new Proxy( port , chatIP , chatPort ) ;
		System.out.println( "[+] Serveur de chat lancé sur le port " + port ) ;
	}

	private static void help() {
		String help = "\nUsage : java ProxyLauncher <port> <IP du serveur> <port du serveur>\n" ;
		help += "\nSi le port n'est pas défini 1100 sera attribué.\n" ;
		help += "Si l'ip du serveur n'est pas défini localhost sera attribué\n" ;
		help += "Si le port du serveur n'est pas défini 1099 sera attribué\n" ;
		System.out.println( help ) ;
	}
}
