public class ProxyLauncher {

	public static void main( String[] args ) {
		int port = 1100 ;
		if ( args.length != 1 ) { 
			help() ; 
		}
		else {
			try { 
				port = Integer.valueOf( args[0] ) ; 

			}
			catch ( NumberFormatException e ) {
				System.out.println( "[-] Le n° de port n'a pas été correctement mis : " + e.getMessage() ) ;
			}
		}
		new Proxy( port , "localhost" , 1099) ;
		System.out.println( "[+] Serveur de chat lancé sur le port " + port ) ;
	}

	private static void help() {
		String help = "\nUsage : java ProxyLauncher <port>\n" ;
		help += "\nSi le port n'est pas défini 1100 sera attribué.\n\n" ;
		System.out.println( help ) ;
	}
}
