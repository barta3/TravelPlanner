package dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.TypedQuery;

import model.FleetEditorModel;

public class FleetEditorDao extends BaseDao<FleetEditorModel> {
	private static FleetEditorDao instance;
	
	public static FleetEditorDao getInstance() {
		if (instance == null) {
			instance = new FleetEditorDao();
		}
		return instance;
	}
	
	public FleetEditorModel saveFleet(FleetEditorModel fleet) {
		
		fleet.setSaveDate(Calendar.getInstance());
		
		em.getTransaction().begin();
		em.persist(fleet);
		em.getTransaction().commit();
		
		return fleet;
	}

	@Override
	public FleetEditorModel getModelById(long id) {
		FleetEditorModel fleet = em.find(FleetEditorModel.class, id);
		return fleet;
		
	}
	
	public List<FleetEditorModel> getFleets() {

		TypedQuery<FleetEditorModel> query = em.createQuery("select f from FleetEditorModel f", FleetEditorModel.class);
		return query.getResultList();
	}

}