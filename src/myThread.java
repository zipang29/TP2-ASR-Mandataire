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
			while ( n != 0 ) {
				this.out.write( b , 0 , n ) ;
				this.out.flush() ;
				System.out.println( "[~] message transmis : '" + (new String( b )).toString() + "'" ) ;
				for ( int i = 0 ; i < b.length-1 ; i++ ) { b[i] = 0x00 ; }
				n = this.in.read( b ) ;
			}
		} catch ( java.io.IOException e ) {
			e.printStackTrace() ;
		}
	}
}
