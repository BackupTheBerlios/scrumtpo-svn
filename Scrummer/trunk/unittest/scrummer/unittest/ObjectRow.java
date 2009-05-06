package scrummer.unittest;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import junit.framework.TestCase;

/**
 * Object row testing class
 */
@RunWith(JUnit4.class)
public class ObjectRow extends TestCase{

	public ObjectRow() {}

	@org.junit.Test
	public void construction() {
		scrummer.util.ObjectRow row = new scrummer.util.ObjectRow(5);
		assertEquals(5, row.getColumnCount());
		
		for (int i = 0; i < 5; i++)
			assertTrue(row.get(i) == null);		
	}
		
	@org.junit.Test
	public void constructionZero() {
		scrummer.util.ObjectRow row = new scrummer.util.ObjectRow(0);
		assertEquals(0, row.getColumnCount());		
	}
	
	@org.junit.Test(expected=Exception.class)
	public void constructionLowBounds() {
		scrummer.util.ObjectRow row = new scrummer.util.ObjectRow(4);
		row.get(-1);
	}
	
	@org.junit.Test(expected=IndexOutOfBoundsException.class) 
	public void constructionHighBounds() {
		scrummer.util.ObjectRow row = new scrummer.util.ObjectRow(4);
		row.get(4);
	}
	
	@org.junit.Test(expected=IndexOutOfBoundsException.class)
	public void testConstructionFail() {
		new scrummer.util.ObjectRow(-1);
	}
	
	/*
	public static Suite suite()
	{			
		Suite suite = new org.junit.runners.JUnit4();
		// TestSuite suite = new TestSuite();
		suite.addTest(new ObjectRow("testConstruction"));
		suite.addTest(new ObjectRow("testConstructionZero"));
		suite.addTest(new ObjectRow("testConstructionFail"));
		suite.addTest(new ObjectRow("testConstructionLowBounds"));
		suite.addTest(new ObjectRow("testConstructionHighBounds"));		
		return suite;
	}
	*/
	
}
