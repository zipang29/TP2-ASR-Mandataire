import java.io.DataOutputStream ;
import java.io.DataInputStream ;

public class myThread extends Thread {

	private DataOutputStream out ;
	private DataInputStream in ;

	public myThread( DataInputStream in , DataOutputStream out ) {
		this.out = out ;
		this.in = in ;
	}

	public void run() {
		try {
			byte[] b = new byte[256] ;
			int n = this.in.read( b ) ;
			while ( n > 0 ) {
				String msg = new String( b , 0 , n ) ;
				System.out.println( "[~] message transmis : " + msg ) ;
				this.out.write( b , 0 , n ) ;
				this.out.flush() ;
				for ( int i = 0 ; i < b.length-1 ; i++ ) { b[i] = 0x00 ; }
				n = this.in.read( b ) ;
			}
			this.in.close() ;
			this.out.close() ;
		} catch ( java.net.SocketException e ) {
			System.out.println( "[*] Client déconnecté." ) ;
		} catch ( java.io.IOException e ) {
			e.printStackTrace() ;
		}
	}
}
