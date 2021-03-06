package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.entity.Bicycle;
import model.entity.Car;
import model.entity.FleetEditorModel;
import model.entity.Vehicle;

public class FleetEditorView extends JPanel implements Observer {

	private FleetEditorModel fleetEditorModel = new FleetEditorModel();

	private JPanel fleetAreaJP;
	
	//vehicle Selection
	private JPanel fleetSelectionAreJP;
	private JPanel fleetSelectionTitleJP;
	private JLabel fleetSelectionTitleJL;
	private JPanel fleetPreviousVehicleJP;
	private JLabel fleetPreviousVehicleJL;
	private ImageIcon fleetPreviousVehicleII;
	private JPanel fleetCurrentVehicleJP;
	private JLabel fleetCurrentVehicleJL;
	private ImageIcon fleetCurrentVehicleII;
	private JPanel fleetNextVehicleJP;
	private JLabel fleetNextVehicleJL;
	private ImageIcon fleetNextVehicleII;	
	private JButton nextFleetVehicleJB;
	private JButton previousFleetVehicleJB;


	// vehicle definition
	private JPanel fleetDefinitionAreJP;
	private JPanel fleetDefinitionTitleJP;
	private JLabel fleetDefinitionTitleJL;
	
	private JPanel fleetDefinitionOptionsJP;
	
	private JLabel vehicleNameJL;
	private JTextField vehicleNameJTF;
	
	private JLabel vehicleIDJL;
	private JTextField vehicleIDJTF;

	
	private JLabel vehicleSpeedJL;
	private JComboBox<Integer> vehicleSpeedJCB;
	private Integer[] vehicleSpeed;

	private JLabel vehicleGasUsageLowJL;
	private JComboBox<Float> vehicleGasUsageLowJCB;
	private JLabel vehicleGasUsageMediumJL;
	private JComboBox<Float> vehicleGasUsageMediumJCB;
	private JLabel vehicleGasUsageHighJL;
	private JComboBox<Float> vehicleGasUsageHighJCB;
	private Float[] vehicleGasUsage;

	private JButton saveVehicleJB;
	private JButton deleteVehicleJB;
	
	//add vehicle 
	private JPanel addVehicleArea;
	private JPanel addVehicleTitleJP;
	private JLabel addVehicleTitleJL;

	private JPanel vehicleSelectionAreaJP;
	private JLabel vehicleSelectionJL;
	private ImageIcon vehicleSelectionII;

	private JButton nextVehicleJB;
	private JButton previousVehicleJB;
	private JButton addVehicleJB;

	private JButton saveFleetJB;
	private JButton loadFleetJB;
	private JButton deleteFleetJB;
	
	private JButton resetFleetJB;
	private JButton deleteCurrentVehcleJB;


	public FleetEditorView() {

		initComponents();

	}

	private void initComponents() {

		this.setLayout(null);

		
		//fleetArea
		fleetAreaJP = new JPanel(){
	        @Override
            public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    draw(g);
            }
		};

		fleetAreaJP.setLayout(null);
		fleetAreaJP.setBounds(10, 10, 900, 800);

		this.add(fleetAreaJP);


		//fleet selctionArea
		fleetSelectionAreJP = new JPanel();
		fleetSelectionAreJP.setBounds(0, 0, 300, 800);
		fleetSelectionAreJP.setBackground(Color.WHITE);
		fleetSelectionAreJP.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		fleetAreaJP.add(fleetSelectionAreJP);
		
		fleetSelectionAreJP.setLayout(null);
		
		fleetSelectionTitleJP = new JPanel();
		fleetSelectionTitleJP.setBounds(10, 10, 280, 50);
		fleetSelectionTitleJL = new JLabel("Fleet - Vehicle Selection");
		fleetSelectionTitleJP.add(fleetSelectionTitleJL);
		
		fleetPreviousVehicleJP = new JPanel();
		fleetPreviousVehicleJP.setBounds(300/2-75, 100, 150, 150);
		fleetPreviousVehicleJL = new JLabel();
		fleetPreviousVehicleJP.add(fleetPreviousVehicleJL);
		
		fleetCurrentVehicleJP = new JPanel();
		fleetCurrentVehicleJP.setBounds(300/2-125, 275, 250, 250);
		fleetCurrentVehicleJL = new JLabel();
		fleetCurrentVehicleJP.add(fleetCurrentVehicleJL);
		
		fleetNextVehicleJP = new JPanel();
		fleetNextVehicleJP.setBounds(300/2-75, 550, 150, 150);
		fleetNextVehicleJL = new JLabel();
		fleetNextVehicleJP.add(fleetNextVehicleJL);
		
		fleetSelectionAreJP.add(fleetSelectionTitleJP);
		fleetSelectionAreJP.add(fleetPreviousVehicleJP);
		fleetSelectionAreJP.add(fleetCurrentVehicleJP);
		fleetSelectionAreJP.add(fleetNextVehicleJP);
		
		nextFleetVehicleJB = new JButton(new ImageIcon("images/arrow_up.png"));	
		nextFleetVehicleJB.setBounds(265, 140, 25, 70);
		
		previousFleetVehicleJB = new JButton(new ImageIcon("images/arrow_down.png"));
		previousFleetVehicleJB.setBounds(265, 590, 25, 70);
		
		fleetSelectionAreJP.add(previousFleetVehicleJB);
		fleetSelectionAreJP.add(nextFleetVehicleJB);
		
		
		//fleet definition area
		fleetDefinitionAreJP = new JPanel();
		fleetDefinitionAreJP.setBounds(310, 0, 600, 800);
		fleetDefinitionAreJP.setBackground(Color.WHITE);
		fleetDefinitionAreJP.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		fleetAreaJP.add(fleetDefinitionAreJP);
		
		fleetDefinitionAreJP.setLayout(null);
		
		fleetDefinitionTitleJP = new JPanel();
		fleetDefinitionTitleJP.setBounds(10, 10, 570, 50);
		fleetDefinitionTitleJL = new JLabel("Fleet - Vehicle Options");
		fleetDefinitionTitleJP.add(fleetDefinitionTitleJL);
		
		fleetDefinitionOptionsJP = new JPanel();
		fleetDefinitionOptionsJP.setBounds(10, 100, 570, 690);
		fleetDefinitionOptionsJP.setLayout(null);
		
		vehicleIDJL = new JLabel("Vehicle - ID");
		vehicleIDJL.setBounds(75, 50, 200, 30);
		vehicleIDJTF = new JTextField();
		vehicleIDJTF.setBounds(285, 50, 200, 30);
		vehicleIDJTF.setEnabled(false);
		
		vehicleNameJL = new JLabel("Vehicle - Name");
		vehicleNameJL.setBounds(75, 100, 200, 30);
		vehicleNameJTF = new JTextField();
		vehicleNameJTF.setBounds(285, 100, 200, 30);
		vehicleNameJTF.setEnabled(false);
		
		vehicleSpeed = new Integer[350];
		
		for(int i = 1; i < 350; i++){
			vehicleSpeed[i] = new Integer(i);
		}
		
		vehicleSpeedJL = new JLabel("Vehicle - Speed");
		vehicleSpeedJL.setBounds(75, 150, 200, 30);
		vehicleSpeedJCB = new JComboBox<Integer>(vehicleSpeed);
		vehicleSpeedJCB.setBounds(285, 150, 200, 30);
		vehicleSpeedJCB.setSelectedIndex(120);
	
		vehicleGasUsage = new Float[290];
		Float f = new Float(1.0);
		for(int i = 1; i <= 290; i++){
			
			vehicleGasUsage[i-1] = new Float(f);
			f= new Float (Math.rint((f.floatValue()+0.1)*10)/10);
			
		}

		vehicleGasUsageLowJL = new JLabel("Gas usage urban highway");
		vehicleGasUsageMediumJL = new JLabel("Gas usage rural highway");
		vehicleGasUsageHighJL = new JLabel("Gas usage highway");
		vehicleGasUsageLowJL.setBounds(75, 200, 200, 30);
		vehicleGasUsageMediumJL.setBounds(75, 250, 200, 30);
		vehicleGasUsageHighJL.setBounds(75, 300, 200, 30);
		
		vehicleGasUsageLowJCB = new JComboBox<Float>(vehicleGasUsage);
		vehicleGasUsageLowJCB.setBounds(285, 200, 200, 30);
		vehicleGasUsageMediumJCB = new JComboBox<Float>(vehicleGasUsage); 
		vehicleGasUsageMediumJCB.setBounds(285, 250, 200, 30);
		vehicleGasUsageHighJCB = new JComboBox<Float>(vehicleGasUsage); 
		vehicleGasUsageHighJCB.setBounds(285, 300, 200, 30);

		saveVehicleJB = new JButton("Save Vehicle");
		saveVehicleJB.setBounds(75, 350, 100, 30);
		saveVehicleJB.setEnabled(false);;

		deleteVehicleJB = new JButton("Delete Vehicle");
		deleteVehicleJB.setBounds(185, 350, 100, 30);
		deleteVehicleJB.setEnabled(false);;

		
		
		fleetDefinitionOptionsJP.add(vehicleIDJL);
		fleetDefinitionOptionsJP.add(vehicleIDJTF);
		fleetDefinitionOptionsJP.add(vehicleNameJL);
		fleetDefinitionOptionsJP.add(vehicleNameJTF);
		fleetDefinitionOptionsJP.add(vehicleSpeedJL);
		fleetDefinitionOptionsJP.add(vehicleSpeedJCB);
	
		
		fleetDefinitionOptionsJP.add(vehicleGasUsageLowJL);
		fleetDefinitionOptionsJP.add(vehicleGasUsageLowJCB);
		fleetDefinitionOptionsJP.add(vehicleGasUsageMediumJL);
		fleetDefinitionOptionsJP.add(vehicleGasUsageMediumJCB);
		fleetDefinitionOptionsJP.add(vehicleGasUsageHighJL);
		fleetDefinitionOptionsJP.add(vehicleGasUsageHighJCB);

		fleetDefinitionOptionsJP.add(saveVehicleJB);
		fleetDefinitionOptionsJP.add(deleteVehicleJB);
		
		fleetDefinitionAreJP.add(fleetDefinitionTitleJP);
		fleetDefinitionAreJP.add(fleetDefinitionOptionsJP);

		
		// Vehicle Area
		addVehicleArea = new JPanel();
		addVehicleArea.setBounds(920, 10, 250, 800);
		addVehicleArea.setBackground(Color.WHITE);
		addVehicleArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		addVehicleArea.setLayout(null);		
		
		addVehicleTitleJP = new JPanel();
		addVehicleTitleJP.setBounds(10, 10, 230, 50);
		addVehicleTitleJL = new JLabel("Fleet - Add New Vehicle");
		addVehicleTitleJP.add(addVehicleTitleJL);

		addVehicleArea.add(addVehicleTitleJP);
		
		vehicleSelectionAreaJP = new JPanel();
		vehicleSelectionAreaJP.setPreferredSize(new Dimension(addVehicleArea.getWidth(), 250));
		vehicleSelectionAreaJP.setBounds(10, 100, addVehicleArea.getWidth()-20, 250);
		
		vehicleSelectionAreaJP.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();		
		gbc.insets = new Insets( 0, 0, 5, 5 );	
		gbc.anchor = GridBagConstraints.WEST;	

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		previousVehicleJB = new JButton("<<<");
		vehicleSelectionAreaJP.add (previousVehicleJB, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		addVehicleJB = new JButton("Add  Vehicle");
		gbc.anchor = GridBagConstraints.CENTER;
		vehicleSelectionAreaJP.add (addVehicleJB, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		nextVehicleJB = new JButton(">>>");
		vehicleSelectionAreaJP.add (nextVehicleJB, gbc);

	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;			

		vehicleSelectionII = new ImageIcon(fleetEditorModel.getVehicleSelection().get(fleetEditorModel.getVehicleSelectionPos()).getVehicleTypes().getUrlVehicle());
		vehicleSelectionII.setImage(vehicleSelectionII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
		vehicleSelectionJL = new JLabel(vehicleSelectionII);

		vehicleSelectionAreaJP.add(vehicleSelectionJL, gbc);			

		addVehicleArea.add(vehicleSelectionAreaJP);	

		this.add(addVehicleArea);

		// Save Button
		saveFleetJB = new JButton("Save Fleet");
		saveFleetJB.setBounds(10, 480, 100, 30);
		addVehicleArea.add(saveFleetJB);
		
		// Load Button
		loadFleetJB = new JButton("Load Fleet");
		loadFleetJB.setBounds(120, 480, 100, 30);
		addVehicleArea.add(loadFleetJB);
		
		// Reset Button
		resetFleetJB = new JButton("Reset Fleet");
		resetFleetJB.setBounds(10, 440, 100, 30);
		addVehicleArea.add(getResetFleetJB());
		
		deleteFleetJB = new JButton("Delete Fleet");
		deleteFleetJB.setBounds(120, 440, 100, 30);
		addVehicleArea.add(deleteFleetJB);

	}

	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		

		//enable user input
		if(fleetEditorModel.getVehicles().size() > 0){
			
			saveVehicleJB.setEnabled(true);
			deleteVehicleJB.setEnabled(true);
			enableUserInput();

			//for all vehicles
			Vehicle vehicle = fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos());
			
			vehicleIDJTF.setText(String.valueOf(vehicle.getId()));
			vehicleNameJTF.setText(vehicle.getName());
			vehicleSpeedJCB.setSelectedItem(new Integer(vehicle.getMaxSpeed()));

			//if car
			if(vehicle instanceof Car){
				
				Car c = (Car) vehicle;
				
				vehicleGasUsageLowJCB.setSelectedItem(new Float(c.getGasConsumptionLow()));
				vehicleGasUsageLowJCB.setEnabled(true);
				vehicleGasUsageMediumJCB.setSelectedItem(new Float(c.getGasConsumptionMedium()));
				vehicleGasUsageMediumJCB.setEnabled(true);
				vehicleGasUsageHighJCB.setSelectedItem(new Float(c.getGasConsumptionHigh()));
				vehicleGasUsageHighJCB.setEnabled(true);
				
			//if bicycle
			}else if(vehicle instanceof Bicycle){
				
				vehicleGasUsageLowJCB.setSelectedIndex(-1);
				vehicleGasUsageLowJCB.setEnabled(false);
				vehicleGasUsageMediumJCB.setSelectedIndex(-1);
				vehicleGasUsageMediumJCB.setEnabled(false);
				vehicleGasUsageHighJCB.setSelectedIndex(-1);
				vehicleGasUsageHighJCB.setEnabled(false);
			}			
		}else{
			
			saveVehicleJB.setEnabled(false);
			deleteVehicleJB.setEnabled(false);
		
			
		}
		
		
		// draw vehicles in fleetSelection
		if(fleetEditorModel.getVehicles().size() > 2){
			
			if(fleetEditorModel.getVehiclePos() - 1 == -1){
				fleetPreviousVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehicles().size()-1).getVehicleTypes().getUrlVehicle());
				fleetPreviousVehicleII.setImage(fleetPreviousVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));					
				fleetPreviousVehicleJL.setIcon(fleetPreviousVehicleII);
			}else {
				fleetPreviousVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()-1).getVehicleTypes().getUrlVehicle());
				fleetPreviousVehicleII.setImage(fleetPreviousVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));					
				fleetPreviousVehicleJL.setIcon(fleetPreviousVehicleII);
			}
			
			fleetCurrentVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()).getVehicleTypes().getUrlVehicle());
			fleetCurrentVehicleII.setImage(fleetCurrentVehicleII.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
			fleetCurrentVehicleJL.setIcon(fleetCurrentVehicleII);
	
			if(fleetEditorModel.getVehiclePos() +1 == fleetEditorModel.getVehicles().size()){
				fleetNextVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(0).getVehicleTypes().getUrlVehicle());
				fleetNextVehicleII.setImage(fleetNextVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
				fleetNextVehicleJL.setIcon(fleetNextVehicleII);
			}else {
				fleetNextVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()+1).getVehicleTypes().getUrlVehicle());
				fleetNextVehicleII.setImage(fleetNextVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
				fleetNextVehicleJL.setIcon(fleetNextVehicleII);
			}

		}else if(fleetEditorModel.getVehicles().size() > 1){
			
			fleetCurrentVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()).getVehicleTypes().getUrlVehicle());
			fleetCurrentVehicleII.setImage(fleetCurrentVehicleII.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
			fleetCurrentVehicleJL.setIcon(fleetCurrentVehicleII);

			if(fleetEditorModel.getVehiclePos() +1 == fleetEditorModel.getVehicles().size()){
				fleetNextVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(0).getVehicleTypes().getUrlVehicle());
				fleetNextVehicleII.setImage(fleetNextVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
				fleetNextVehicleJL.setIcon(fleetNextVehicleII);
			}else {
				fleetNextVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()+1).getVehicleTypes().getUrlVehicle());
				fleetNextVehicleII.setImage(fleetNextVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
				fleetNextVehicleJL.setIcon(fleetNextVehicleII);
			}
			
			fleetPreviousVehicleJL.setIcon(null);

			
		}else if(fleetEditorModel.getVehicles().size() > 0){
			
			fleetCurrentVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()).getVehicleTypes().getUrlVehicle());
			fleetCurrentVehicleII.setImage(fleetCurrentVehicleII.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
			fleetCurrentVehicleJL.setIcon(fleetCurrentVehicleII);
			
			fleetNextVehicleJL.setIcon(null);
			fleetPreviousVehicleJL.setIcon(null);

		}else{
			
			fleetCurrentVehicleJL.setIcon(null);
			fleetNextVehicleJL.setIcon(null);
			fleetPreviousVehicleJL.setIcon(null);
			
		}
		
		
		// draw vehicle in vehicleSelection
		vehicleSelectionII = new ImageIcon(fleetEditorModel.getVehicleSelection().get(fleetEditorModel.getVehicleSelectionPos()).getVehicleTypes().getUrlVehicle());
		vehicleSelectionII.setImage(vehicleSelectionII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
		vehicleSelectionJL.setIcon(vehicleSelectionII);

		}
	
	private void enableUserInput() {

		
		vehicleNameJTF.setEnabled(true);
		
	}

	public void update(Observable model, Object value) {
		
		if (model instanceof FleetEditorModel) {
			
			this.fleetEditorModel = (FleetEditorModel) model; 
			
		}
		repaint();
	}

	/**
	 * @return the nextFleetVehicleJB
	 */
	public JButton getNextFleetVehicleJB() {
		return nextFleetVehicleJB;
	}

	/**
	 * @return the previousFleetVehicleJB
	 */
	public JButton getPreviousFleetVehicleJB() {
		return previousFleetVehicleJB;
	}

	/**
	 * @return the nextVehicleJB
	 */
	public JButton getNextVehicleJB() {
		return nextVehicleJB;
	}

	/**
	 * @return the previousVehicleJB
	 */
	public JButton getPreviousVehicleJB() {
		return previousVehicleJB;
	}

	/**
	 * @return the addVehicleJB
	 */
	public JButton getAddVehicleJB() {
		return addVehicleJB;
	}

	/**
	 * @return the saveFleetJB
	 */
	public JButton getSaveFleetJB() {
		return saveFleetJB;
	}
	
	public JButton getLoadFleetJB() {
		return this.loadFleetJB;
	}

	/**
	 * @return the deleteFleetJB
	 */
	public JButton getDeleteFleetJB() {
		return deleteFleetJB;
	}

	/**
	 * @return the deleteCurrentVehcleJB
	 */
	public JButton getDeleteCurrentVehcleJB() {
		return deleteCurrentVehcleJB;
	}

	/**
	 * @param deleteCurrentVehcleJB the deleteCurrentVehcleJB to set
	 */
	public void setDeleteCurrentVehcleJB(JButton deleteCurrentVehcleJB) {
		this.deleteCurrentVehcleJB = deleteCurrentVehcleJB;
	}

	/**
	 * @return the saveVehicleJB
	 */
	public JButton getSaveVehicleJB() {
		return saveVehicleJB;
	}

	/**
	 * @return the vehicleNameJTF
	 */
	public JTextField getVehicleNameJTF() {
		return vehicleNameJTF;
	}


	/**
	 * @return the vehicleSpeedJCB
	 */
	public JComboBox<Integer> getVehicleSpeedJCB() {
		return vehicleSpeedJCB;
	}

	/**
	 * @return the vehicleGasUsageLowJCB
	 */
	public JComboBox<Float> getVehicleGasUsageLowJCB() {
		return vehicleGasUsageLowJCB;
	}

	/**
	 * @return the vehicleGasUsageMediumJCB
	 */
	public JComboBox<Float> getVehicleGasUsageMediumJCB() {
		return vehicleGasUsageMediumJCB;
	}

	/**
	 * @return the vehicleGasUsageHighJCB
	 */
	public JComboBox<Float> getVehicleGasUsageHighJCB() {
		return vehicleGasUsageHighJCB;
	}

	/**
	 * @return the deleteVehicleJB
	 */
	public JButton getDeleteVehicleJB() {
		return deleteVehicleJB;
	}

	public JButton getResetFleetJB() {
		return resetFleetJB;
	}
	
}
