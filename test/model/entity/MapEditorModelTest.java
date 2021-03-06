package model.entity;

import static org.junit.Assert.assertTrue;
import model.DuplicateStreetException;
import model.entity.MapEditorModel;
import model.entity.Node;
import model.entity.Street;

import org.junit.Test;

public class MapEditorModelTest {
	
	@Test
	public void testAddStreet() {
		MapEditorModel model = new MapEditorModel();
		Street s1 = new Street(new Node(10, 20), new Node(30, 40));
		model.addStreet(s1);
		
		model.addStreet(new Street(new Node(10, 20), new Node(40, 10)));
		
		boolean exception = false;
		try {
			model.addStreet(new Street(new Node(30, 40), new Node(10, 20)));
		} catch (DuplicateStreetException e) {
			exception = true;
		}
		
		assertTrue(exception);
		
	}

}
