package toutdansunpackage.messages;

public class KillMessage {

	private int killerId;
	private int victimeId;

	public KillMessage() {
	}

	public KillMessage(int k, int v) {
		killerId = k;
		victimeId = v;
	}

	public int getKillerId() {
		return killerId;
	}

	public int getVictimeId() {
		return victimeId;
	}

}
