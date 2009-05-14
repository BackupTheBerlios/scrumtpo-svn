package scrummer.ui.page;

import scrummer.ui.MainFrame;

/**
 * Graph display page
 * 
 * Graph can be either connected or "columnar". It can show arbitrary set of points.
 * Several kinds of metrics can be displayed(Sprint, Task, Release related), they can range
 * from/to different dates and id's. It should also be able to display a specifically specified set
 * of measures.
 */
public class MetricGraphPage extends BasePage {

	public MetricGraphPage(MainFrame mainFrame) {
		super(mainFrame);		
	}
	
	/// serialization id
	private static final long serialVersionUID = 5832667024364710534L;
}
