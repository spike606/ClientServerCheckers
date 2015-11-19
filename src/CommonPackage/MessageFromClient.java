package CommonPackage;

import java.io.Serializable;

public class MessageFromClient implements Serializable {

	/**
	 * Message which client sends to server
	 */
	private static final long serialVersionUID = -8365257093223296190L;

	private int chosenRow;
	private int chosenCol;

	public int getChosenRow() {
		return chosenRow;
	}

	public void setChosenRow(int chosenRow) {
		this.chosenRow = chosenRow;
	}

	public int getChosenCol() {
		return chosenCol;
	}

	public void setChosenCol(int chosenCol) {
		this.chosenCol = chosenCol;
	}

}
