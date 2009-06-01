package scrummer.model.graph;

import java.util.HashMap;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Data set for displaying questionairse results
 */
public class QuestionCategoryDataSet extends DefaultCategoryDataset {

	public QuestionCategoryDataSet() {
		
	}
	
	public void setValues(HashMap<String, Integer> values) {
		this.clear();
		for (String key : values.keySet())
			this.setValue(values.get(key), key.toString(), key.toString());
	}
	
	/// serialization id
	private static final long serialVersionUID = -5410708514329862648L;

}
