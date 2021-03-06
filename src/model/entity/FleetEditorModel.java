/**
 * 
 */
package model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import model.config.VehicleType;

/**
 * @author dimitri.haemmerli
 *
 */
@Entity
public class FleetEditorModel extends EntityBase {
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	@Transient
	private ArrayList<Vehicle> vehicleSelection = new ArrayList<Vehicle>();
	
	@Transient
	private int vehicleSelectionPos = 0;
	
	@Transient
	private int vehiclePos = 0;

	
	public FleetEditorModel(){
		

		//testuse only
//		Car c1 = new Car();
//		c1.setIsSelected(true);
//		c1.setVehicleTypes(VehicleType.GREENCAR);
//		vehicles.add(c1);
		
		createVehicleSelection();
	}


private void createVehicleSelection() {

	//green Car
	vehicleSelection.add(new Car(VehicleType.GREENCAR));
	
	//blue Car
	vehicleSelection.add(new Car(VehicleType.BLUECAR));
	
	//red Car
	vehicleSelection.add(new Car(VehicleType.REDCAR));

	//yellow Car
	vehicleSelection.add(new Car(VehicleType.YELLOWCAR));
	
	//mountain bike
	vehicleSelection.add(new Bicycle(VehicleType.MOUNTAINBIKE));

	}


	public void addVehicle(Vehicle vehicle){
		
//		vehicle.setId(vehicles.size()+1);
		vehicles.add(vehicle);
		
		
		super.setChanged();
		super.notifyObservers(this);

	}
	
	/**
	 * @return the vehicles
	 */
	public List<Vehicle> getVehicles() {
		return vehicles;
	}


	/**
	 * @param vehicles the vehicles to set
	 */
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
		super.setChanged();
		super.notifyObservers(this);

	}
			
	
	

	/**
	 * @return the vehicleSelection
	 */
	public ArrayList<Vehicle> getVehicleSelection() {
		return vehicleSelection;
	}


	/**
	 * @param vehicleSelection the vehicleSelection to set
	 */
	public void setVehicleSelection(ArrayList<Vehicle> vehicleSelection) {
		this.vehicleSelection = vehicleSelection;
	}

	
	
	/**
	 * @return the vehicleSelectionPos
	 */
	public int getVehicleSelectionPos() {
		return vehicleSelectionPos;
	}


	/**
	 * @return the vehiclePos
	 */
	public int getVehiclePos() {
		return vehiclePos;
	}


	/**
	 * @param vehiclePos the vehiclePos to set
	 */
	public void setVehiclePos(int vehiclePos) {
		this.vehiclePos = vehiclePos;
	}


	/**
	 * @param vehicleSelectionPos the vehicleSelectionPos to set
	 */
	public void setVehicleSelectionPos(int vehicleSelectionPos) {
		this.vehicleSelectionPos = vehicleSelectionPos;
	}


	public void increaseVehicleSelectionPos(){
		
		if(vehicleSelectionPos < vehicleSelection.size() - 1 ){
			
			vehicleSelectionPos++;
			
		}else{
			
			vehicleSelectionPos = 0;
		}
		
		super.setChanged();
		super.notifyObservers(this);
	}
	
	public void decreaseVehicleSelectionPos(){
		
		if(vehicleSelectionPos > 0){
			
			vehicleSelectionPos--;
			
		}else{
			
			vehicleSelectionPos = vehicleSelection.size() - 1;
		}
		
		super.setChanged();
		super.notifyObservers(this);
	}
	
	public void increaseVehiclePos(){
		
		if(vehiclePos < vehicles.size() - 1 ){
			
			vehiclePos++;
			
		}else{
			
			vehiclePos = 0;
		}
		
		super.setChanged();
		super.notifyObservers(this);
	}
	
	public void decreaseVehiclePos(){
		
		if(vehiclePos > 0){
			
			vehiclePos--;
			
		}else{
			
			vehiclePos = vehicles.size() - 1;
		}
		
		super.setChanged();
		super.notifyObservers(this);
	}
	
	public void loadModel(FleetEditorModel model) {
		this.vehicles = model.getVehicles();
		this.setId(model.getId());
		
		super.setChanged();
		super.notifyObservers(this);
		
	}


	public void deleteVehicle() {

		this.getVehicles().remove(getVehiclePos());
	
		if(vehicles.size() > 0){
			decreaseVehiclePos();

		}
		super.setChanged();
		super.notifyObservers(this);


	}

}
