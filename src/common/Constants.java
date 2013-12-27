package common;

public interface Constants {
	
	/**
	 * Höhe eines Knoten in px
	 */
	public static final int NODE_HEIGHT = 	10;
	
	/**
	 * Breite eines Knoten in px
	 */
	public static final int NODE_WIDTH = 	NODE_HEIGHT;
	
	/**
	 * Knotenradius in px
	 */
	public static final int NODE_RADIUS = 	NODE_HEIGHT / 2;
	
	public static final String SIMULATION_FINISHED = "SIMULATION_FINISHED";
	
	public static final String MSG_NO_PATH = "No path exists for car %s, please change Start/Finish positions"; 

}
