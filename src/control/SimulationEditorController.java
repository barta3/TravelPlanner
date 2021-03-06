/**
 * 
 */
package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import model.UserDisruption;
import model.config.SimulationOption;
import model.entity.Car;
import model.entity.Node;
import model.entity.SimulationEditorModel;
import model.entity.Street;
import model.entity.Vehicle;
import model.simulation.SolverMapGraph;
import view.SimulationEditorView;

/**
 * @author dimitri.haemmerli
 *
 */
public class SimulationEditorController implements Controller{
	
	private SimulationEditorView simulationEditorView;
	private SimulationEditorModel simulationEditorModel;
	private List<SolverMapGraph> solver = new ArrayList<SolverMapGraph>();

	
	public SimulationEditorController(SimulationEditorView sev, SimulationEditorModel sem){
		
		this.simulationEditorView = sev;
		this.simulationEditorModel = sem;
		this.simulationEditorView.setMapEditorModel(simulationEditorModel.getMapEditorModel());
		this.simulationEditorView.setFleetEditorModel(simulationEditorModel.getFleetEditorModel());
		this.simulationEditorModel.addObserver(simulationEditorView);
		addListener();

		
	}
	private void addListener() {
		simulationEditorView.addKeyListener(new SimulationKeyListener());
		simulationEditorView.getMapArea().addMouseListener(new MapMouseListener());
		simulationEditorView.getStartJB().addActionListener(new BtnSetStart());
		simulationEditorView.getFinishJB().addActionListener(new BtnSetFinish());
		simulationEditorView.getSimulationJB().addActionListener(new BtnSetSimulation());
		simulationEditorView.getNextVehicleJB().addActionListener(new BtnNextVehicle());
		simulationEditorView.getPreviousVehicleJB().addActionListener(new BtnPreviousVehicle());
		simulationEditorView.getCloseStreetJB().addActionListener(new BtnCloseStreet());
		simulationEditorView.getShortestPathJRB().addActionListener(new RBtnShortestPath());
		simulationEditorView.getFastestPathJRB().addActionListener(new RBtnFastestPath());
		simulationEditorView.getLowestGasConsumptionJRB().addActionListener(new RBtnLowestGasConsumption());
		simulationEditorView.getIgnoreSpeedLimitJRB().addActionListener(new RBtnIgnoreSpeedLimit());
		simulationEditorView.getDelayJCB().addActionListener(new JCBDelayListener());


	}
	
	@Override
	public Component showView() {
		this.simulationEditorView.setVisible(true);
		return this.simulationEditorView;
	}
	

	class SimulationKeyListener implements KeyListener {


		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_S){
				
				setStartPosition();
				
			}else if(key == KeyEvent.VK_F){
			
				setFinishPosition();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {		}
		@Override
		public void keyTyped(KeyEvent e) {		}
	}
	
	class MapMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			
			Node node = new Node(e.getX(), e.getY());
			
			Node selectedNode = clickedOnNode(node);
			
			if(selectedNode != null) {
				simulationEditorModel.getMapEditorModel().setSelectedStreet(null);
			} else {
				Street selectedStreet = clickedOnStreet(node);
				
				if(selectedStreet != null && selectedStreet.isClosed()) {
					simulationEditorView.getCloseStreetJB().setText("Open street");
				} else {
					simulationEditorView.getCloseStreetJB().setText("Close street");
				}
				
				simulationEditorModel.getMapEditorModel().setSelectedStreet(selectedStreet);
			}
			
			simulationEditorModel.getMapEditorModel().setSelectedNode(selectedNode);
			simulationEditorModel.changed(null);
			
			
		}
		
		private Node clickedOnNode(Node k) {
			
			int x = k.getX();
			int y = k.getY();
			int toleranz = 5;
			
			Node returnNode = null;

			for(Street street : simulationEditorModel.getMapEditorModel().getStreets()) {
				
				
				// On Start Node?
				int xdiff = Math.abs(street.getStart().getX() - x);
				int ydiff = Math.abs(street.getStart().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					returnNode = street.getStart();
				}
				
				// On End?
				xdiff = Math.abs(street.getEnd().getX() - x);
				ydiff = Math.abs(street.getEnd().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					returnNode = street.getEnd();
				}
			}
			
			return returnNode;
		}
		
		private Street clickedOnStreet(Node point) {
			for(Street street : simulationEditorModel.getMapEditorModel().getStreets()) {
				if(street.isPointOnStreet(point.getX(), point.getY())) {
					return street;
				}
			}
			return null;
		}

		@Override
		public void mouseEntered(MouseEvent e) {		}
		@Override
		public void mouseExited(MouseEvent e) {		}
		@Override
		public void mousePressed(MouseEvent e) {	}
		@Override
		public void mouseReleased(MouseEvent e) {	}

	}
	class BtnSetStart implements ActionListener {

		public void actionPerformed(ActionEvent e) {
		
			setStartPosition();			
		}
			

	}
	
	class BtnSetFinish implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			setFinishPosition();					
		
		}
	}

	class BtnSetSimulation implements ActionListener {

		public void actionPerformed(ActionEvent e) {
		
			solver.clear();

			simulationEditorView.setInSimulation(true);
			
			for(int i = 0; i< simulationEditorModel.getFleetEditorModel().getVehicles().size(); i++){

				//initialize statistics
				simulationEditorModel.getFleetEditorModel().getVehicles().get(i).setExpectedTime(0.0);
				simulationEditorModel.getFleetEditorModel().getVehicles().get(i).setActualTime(0.0);
				simulationEditorModel.getFleetEditorModel().getVehicles().get(i).setActualTimeTemp(0.0);
				simulationEditorModel.getFleetEditorModel().getVehicles().get(i).setPathLength(0.0);
				simulationEditorModel.getFleetEditorModel().getVehicles().get(i).setPath(new ArrayDeque<Node>());

				if(simulationEditorModel.getFleetEditorModel().getVehicles().get(i) instanceof Car){
					Car car = (Car) simulationEditorModel.getFleetEditorModel().getVehicles().get(i);
					car.setGasUsage(0.0);
				}
				
				SolverMapGraph s = new SolverMapGraph(simulationEditorModel);

				simulationEditorModel.addObserver(s);
				solver.add(s);
				solver.get(i).setVehicle(simulationEditorModel.getFleetEditorModel().getVehicles().get(i));
				solver.get(i).getVehicle().setThread(new Thread(solver.get(i)));
				solver.get(i).getVehicle().setSimulationDelay(solver.get(i).getVehicle().getDelay());
				solver.get(i).getVehicle().getThread().start();
				SimulationEditorModel.incRunningSimulations();
			}

		}
	}

	class BtnNextVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().increaseVehiclePos();
			simulationEditorModel.changed(null);
		
		}
	}
	class BtnPreviousVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().decreaseVehiclePos();
			simulationEditorModel.changed(null);
		}
	}
	
	class BtnCloseStreet implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			Street selectedStreet = simulationEditorModel.getMapEditorModel().getSelectedStreet();
			
			if(selectedStreet == null) {
				return;
			}
			
			if(selectedStreet.isClosed()) {
				simulationEditorModel.getMapEditorModel().reOpenStreet(selectedStreet);
				simulationEditorModel.changed(new UserDisruption());
				return;
			}
			
			boolean isVehicleOnStreet = false;
			
			for(Vehicle vehicle : simulationEditorModel.getFleetEditorModel().getVehicles()) {
				
				
				if(selectedStreet.getStart().equals(vehicle.getCurrentNode()) && selectedStreet.getEnd().equals(vehicle.getNextNode()) ||
						selectedStreet.getStart().equals(vehicle.getNextNode()) && selectedStreet.getEnd().equals(vehicle.getCurrentNode()))
					
					isVehicleOnStreet = true;
			}
			
			if(!isVehicleOnStreet) {
//				SimulationEditorModel.incRunningSimulations();
				simulationEditorModel.getMapEditorModel().closeStreet(selectedStreet);
				simulationEditorModel.changed(new UserDisruption());
			}
			
		}
	}
		
	class RBtnShortestPath implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setSimulationOption(SimulationOption.SHORTEST_PATH);;
			simulationEditorModel.changed(null);
		}
	}
	
	class RBtnFastestPath implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setSimulationOption(SimulationOption.FASTEST_PATH);;
			simulationEditorModel.changed(null);
		}
	}	
	
	class RBtnLowestGasConsumption implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setSimulationOption(SimulationOption.LOWEST_GAS_CONSUMPTION);;
			simulationEditorModel.changed(null);
		}
	}	
	class RBtnIgnoreSpeedLimit implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setSimulationOption(SimulationOption.IGNORE_SPEEDLIMIT);;
			simulationEditorModel.changed(null);
		}
	}	
	
	class JCBDelayListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			
			Integer delay = (Integer) simulationEditorView.getDelayJCB().getSelectedItem();
					
			simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setDelay(delay.intValue());;
			//simulationEditorModel.changed(null);
		}
	}

		
	private void setStartPosition() {
		
		
		for(Street s : simulationEditorModel.getMapEditorModel().getStreets()){
			
			if(s.getStart().equals(simulationEditorModel.getMapEditorModel().getSelectedNode())){
				
				simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setStartNode(s.getStart());
				simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setCurrentNode(s.getStart());
				simulationEditorModel.getMapEditorModel().setSelectedNode(null);
				simulationEditorModel.changed(null);
				
			}						
			
			if(s.getEnd().equals(simulationEditorModel.getMapEditorModel().getSelectedNode())){
				
				simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setStartNode(s.getEnd());
				simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setCurrentNode(s.getEnd());
				simulationEditorModel.getMapEditorModel().setSelectedNode(null);
				simulationEditorModel.changed(null);

			}						
		}
		
	}
	
	private void setFinishPosition() {
		
		for(Street s : simulationEditorModel.getMapEditorModel().getStreets()){
			
			if(s.getStart().equals(simulationEditorModel.getMapEditorModel().getSelectedNode())){
				
				simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setFinishNode(s.getStart());
				simulationEditorModel.getMapEditorModel().setSelectedNode(null);
				simulationEditorModel.changed(null);
				
			}						
			
			if(s.getEnd().equals(simulationEditorModel.getMapEditorModel().getSelectedNode())){
				
				simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setFinishNode(s.getEnd());
				simulationEditorModel.getMapEditorModel().setSelectedNode(null);
				simulationEditorModel.changed(null);

			}						

		}

		
	}
	
	@Override
	public void setModel(Object o) {
		// no implementation needed
	}

	

}

