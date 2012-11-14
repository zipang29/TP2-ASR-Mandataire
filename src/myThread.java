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
		while ( this.in != null ) {
			try {
				String tmp = this.in.readUTF() ;
				if ( tmp == null || tmp.isEmpty() ) {
					this.out.close() ;
					this.in.close() ;
					this.in = null ;
				}
				else {
					System.out.println( "[~] tmp = " + tmp ) ;
					this.out.writeUTF( tmp ) ;
					this.out.flush() ;
				}
			} catch ( java.io.IOException e ) {
				e.printStackTrace() ;
			}
		}
	}
}
