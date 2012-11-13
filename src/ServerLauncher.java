public class ServerLauncher {

	public static void main( String[] args ) {
		int port = 1099 ;
		if ( args.length != 1 ) { 
			help() ; 
		}
		else {
			try { port = Integer.valueOf( args[0] ) ; }
			catch ( NumberFormatException e ) {
				System.out.println( "[-] Le n° de port n'a pas été correctement mis : " + e.getMessage() ) ;
			}
		}
		try {
			new Server( port ) ;
		}
		catch ( java.io.IOException e ) {
			System.out.println( "[-] Problème d'IO : " + e.getMessage() ) ;
		}
	}

	private static void help() {
		String help = "\nUsage : java Launcher <port>\n" ;
		help += "\nSi le port n'est pas défini 1099 sera attribué.\n\n" ;
		System.out.println( help ) ;
	}
}
