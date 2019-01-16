package frc.robot;

public enum Side {
	LEFT('L'),
	RIGHT('R'),
	UNKNOWN(' ');
	
	// Constants.
	private static final int INDEX_GAME_DATA_SWITCH = 0;
	private static final int INDEX_GAME_DATA_SCALE = 1;
	
	private char value;
	
	private Side(char c) {
		value = c;
	}
	
	public static Side ofSwitch(String gameData) {
		return of(gameData, INDEX_GAME_DATA_SWITCH);
	}
	
	public static Side ofScale(String gameData) {
		return of(gameData, INDEX_GAME_DATA_SCALE);
	}
	
	private static Side of(String gameData, int index) {
		if ((gameData == null) || (gameData.length() < index)) {
			return Side.UNKNOWN;
		}
		
		char c = gameData.charAt(index);
		for (Side side : values()) {
			if (side.value == c) {
				return side;
			}
		}
		
		return UNKNOWN;		
	}
}