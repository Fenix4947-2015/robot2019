package frc.robot.commands.autonomous;

public class DistanceAutoConstants {
	// X sur le sens de la largeur du terrain
	// y sur la longueur du terrain 
	
	// 90 is clockwise (right turn)
	// -90 is counterclockwise ( left turn) 
	
	// Manual offsets
	public static final double FINAL_MOTION_BOOST = 1.25; // ft
	public static final double OFF_PORTAL_PORTAL_X = 0.5 ; // ft
	
	// Physical field dimensions
	public static final double DPORTALTOPORTAL_X = 264.0 /12.0; // inches to feet. 
	public static final double D_EXCHANGE_TO_SWITCH_X = 55.56 /12.0;
	public static final double DWALLTOSWITCH_Y = 168.0 /12.0; // TO VALIDATE. Worked when tested. though refers to the mid of the switch
	public static final double DWALLTOSWITCH_CLOSESIDE_Y = 140.0 /12.0;
	public static final double SWITCH_CENTER_CENTER_X = 9.0; // 9 feet between the middle of the switches
	public static final double OFFSET_FROM_CENTER_X = 5.0 /12.0;
	public static final double DIST_CLEAR_EXCHANGE_Y = 50.0 /12.0;
	private static final double BUMPER_WIDTH_INCH = 3.0 ; // bumper thickness in inches (1 bumper)
	public static final double ROBOT_LENGTH_FORWARD_DIRECTION = (32.5 + 2.0* BUMPER_WIDTH_INCH) / 12.0 ; // total robot length (frame + bumper) -> to feet
	public static final double ROBOT_LENGTH_LATERAL_DIRECTION = (28 + 2.0* BUMPER_WIDTH_INCH) / 12.0 ; // total robot length (frame + bumper) -> to feet
	public static final double D_SWITCH_THICKNESS_Y = 52.0/12.0; // epaisseur de la switch
	
	// constants for balance side crossover
	//public static final double 
	public static final double D_SWITCH_PLATEAU_X = 3.0 ; // ft largeur en X du plateau de la switch
	public static final double DIST_CLEAR_SWITCH_Y = 1.0; // distance pour clearer le cube avec arriere du bumper
	
	// constants for second cube. 
	public static final double CLEAR_PINCE_X = 1.3 ; //ft
	public static final double CLEAR_SWITCH_Y = 4.5 ; // ft // todo calculate
	public static final double GET_SECOND_CUBE_Y = 2.5 ; // ft // todo calculate
	public static final double CUBE_LENGTH = 1.0; // ft
}
