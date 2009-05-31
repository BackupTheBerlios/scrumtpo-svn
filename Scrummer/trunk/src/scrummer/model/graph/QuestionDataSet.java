package scrummer.model.graph;

import java.util.HashMap;

import org.jfree.data.KeyedValues;
import org.jfree.data.general.DefaultPieDataset;

public class QuestionDataSet extends DefaultPieDataset {

	public QuestionDataSet() {}
	
	public void setValues(HashMap<String, Integer> values) {
		this.clear();
		for (String key : values.keySet()) 
			this.setValue(key.toString(), values.get(key));
	}
	
	/// serialization id
	private static final long serialVersionUID = -5718613822666304578L;
}
