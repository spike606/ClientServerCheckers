package CommonPackage;

import java.io.Serializable;

public class MessageFromClient implements Serializable {

	/**
	 * Message which client sends to server
	 */
	private static final long serialVersionUID = -8365257093223296190L;

	private int row;
	private int col;

	public int getChosenRow() {
		return row;
	}

	public void setChosenRow(int row) {
		this.row = row;
	}

	public int getChosenCol() {
		return col;
	}

	public void setChosenCol(int col) {
		this.col = col;
	}

}
