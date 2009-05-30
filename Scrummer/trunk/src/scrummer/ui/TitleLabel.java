package scrummer.ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import scrummer.exception.ValueInvalid;

/**
 * Title label is drawn with a fading line on top and bottom
 */
public class TitleLabel extends JLabel {

	/**
	 * Default Constructor
	 */
	public TitleLabel() {}

	/**
	 * Constructor
	 * @param text label text
	 */
	public TitleLabel(String text) {
		super(text);
	}

	/**
	 * @return left text offset
	 */
	public int getLeftTextOffset()
	{
		return _leftTextOffset;
	}
	
	/**
	 * Set left text offset value
	 * @param value value to set
	 * @throws ValueInvalid value < 0
	 */
	public void setLeftTextOffset(int value)
	{
		if (value < 0)
		{
			throw new ValueInvalid(Integer.toString(value), "Value should be > 0");
		}
		_leftTextOffset = value;
	}
	
	/**
	 * @return line width
	 */
	public int getLineWidth()
	{
		return _lineWidth;
	}
	
	/**
	 * Set line width
	 * @throws ValueInvalid when value <= 0
	 */
	public void setLineWidth(int value)
	{
		if (value <= 0)
		{
			throw new ValueInvalid(Integer.toString(value), "Line width has to be >= 0.");
		}
		_lineWidth = value;
	}
	
	/** 
	 * @return line falloff
	 */
	float getFalloff()
	{
		return _falloff;
	}
	
	/**
	 * Set speed of line diminuation 
	 */
	public void setFalloff(float value)
	{
		_falloff = Math.abs(value);
	}
	
	
	public void setLineColor(Color value)
	{
		if (value == null)
		{
			throw new NullPointerException("Cannot set null value");
		}
		_lineColor = value;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int textHeight = 
			Math.round(g.getFontMetrics().getLineMetrics(
				getText(), g).getAscent());

		Color current = g.getColor();
		
		// g.setColor(_lineColor);
		// renderLine(g, 1, 1, _lineWidth, - _falloff);
		
		g.setColor(current);
		g.drawString(getText(), _leftTextOffset, (getHeight() / 2) + (textHeight / 2));
		
		g.setColor(_lineColor);
		renderLine(g, 1, getHeight() - 2, _lineWidth, - _falloff);
	}
	
	/**
	 * Render line from point x,y by width right and blend it into background.
	 * Blending is done using fallof that represents the relative drop between 0 and 1
	 * 
	 * @parma g graphics object
	 * @param x starting point x coord.
	 * @param y starting point y coord.
	 * @param width line width
	 * @param falloff line falloff
	 */
	private void renderLine(Graphics g, int x, int y, int width, float falloff)
	{
		Color previous = g.getColor();
		
		float currentAlpha = 1.0f;
		for (int i = x; i < (x + width); i++)
		{
			Color nc = new Color(
				previous.getRed(), 
				previous.getGreen(), 
				previous.getBlue(), 
				Math.round(currentAlpha * 255.0f));
			
			g.setColor(nc);
			g.drawLine(i, y, i, y);
			
			currentAlpha += falloff;
			currentAlpha = Math.max(currentAlpha, .0f);
		}
	}
	
	/// text offset from left
	private int _leftTextOffset = 5;
	/// line widths
	private int _lineWidth = 200;
	/// falloff
	private float _falloff = 0.005f;
	/// line color
	private Color _lineColor = Color.black;
	/// serialization id
	private static final long serialVersionUID = -102855135534482874L;
}
