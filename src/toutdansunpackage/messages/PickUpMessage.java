package toutdansunpackage.messages;

public class PickUpMessage {

	private int indexOfThing;

	public PickUpMessage(){}
	
	public PickUpMessage(int indexOfThing){
		this.indexOfThing=indexOfThing;
	}

	public int getIndexOfThing() {
		return indexOfThing;
	}
	
}
