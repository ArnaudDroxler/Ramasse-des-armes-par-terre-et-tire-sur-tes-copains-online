package tests.kryonet.implem.logiqueCoteClient;

import tests.kryonet.implem.logiqueCoteClient.server.PcServer;

public class MainServer {

	public static void main(String[] args) {
		new PcServer(args.length>0);
	}

}
