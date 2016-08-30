package tests.kryonet.implem.logiqueCoteClient;

import tests.kryonet.implem.logiqueCoteClient.server.PcServer;

public class MainServer {

	public static void main(String[] args) {
		if(args.length>0){
			new PcServer(true);
		}else{
			new PcServer(false);
		}
	}

}
