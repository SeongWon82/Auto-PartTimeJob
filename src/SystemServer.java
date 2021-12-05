import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class SystemServer {
	static HashMap<Integer, String> recruiter= new HashMap<Integer, String>();
	static HashMap<String, Boolean> user = new HashMap<String, Boolean>();
	static int ClientID;
	static SystemSocket system;
	
	public static void main(String[] args) throws Exception {
		ServerSocket server=new ServerSocket(9100);
		try {
			while(true) {
				ClientID++;
				system=new SystemSocket(server.accept(),ClientID);
				system.start();
			}
		}finally {
			server.close();
		}
	}
}
