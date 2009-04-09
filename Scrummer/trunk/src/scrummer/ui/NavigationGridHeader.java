package scrummer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JPanel;

import scrummer.Scrummer;
import scrummer.model.ResourceModel;

/**
 * Header for navigation grid
 */
public class NavigationGridHeader extends Box {

	public NavigationGridHeader(int axis) {
		super(axis);
		
		JPanel linkPanel = new JPanel();
		linkPanel.setBackground(Color.WHITE);
		linkPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		linkPanel.setMinimumSize(new Dimension(500, 50));
		linkPanel.setPreferredSize(new Dimension(200, 50));
		
		LeftButton = 
			addLabel(linkPanel, ResourceModel.Image.ArrowLeft, TitleLink.Endpoint.Back, 2);
		UpButton = 
			addLabel(linkPanel, ResourceModel.Image.ArrowUp, TitleLink.Endpoint.Up, 2);
		HomeButton = 
			addLabel(linkPanel, ResourceModel.Image.Home, TitleLink.Endpoint.Home, 0);
				
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.setBackground(Color.WHITE);
		titlePanel.setMinimumSize(new Dimension(500, 30));
		titlePanel.setPreferredSize(new Dimension(500, 30));
		titlePanel.setMaximumSize(new Dimension(1600, 30));
		
		TitleLabel titleLabel = new TitleLabel();
		titleLabel.setSize(200, 10);
		titleLabel.setPreferredSize(new Dimension(200, 20));
		TitleText = titleLabel;
		titlePanel.add(titleLabel);
		
		add(linkPanel);
		add(titlePanel);
	}
	
	/**
	 * Add label to panel
	 * @param panel panel to which to add
	 * @param image image that will be displayed on label
	 * @param endpoint type of link
	 * @param addBottomText additional value to add to bottom text offset
	 */
	private GrowingLabel addLabel(JPanel panel, ResourceModel.Image image, TitleLink.Endpoint endpoint, int addBottomText)
	{
		ResourceModel res = Scrummer.getModels().getResourceModel();	
		// add back, up, home connection
		try {
			GrowingLabel label = new TitleLink(endpoint, res.get(image));
			label.setBorderGrowth(0, 8, 8);
			label.setPictureSideOffset(5);
			label.setPictureTopOffset(5);
			label.setTextBottomOffset(4 + addBottomText);
			label.setPreferredSize(new Dimension(64,76));
			// label.setText(text);
			panel.add(label);
			return label;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Set all buttons enabled value
	 * @param value value to set
	 */
	public void setButtonsEnabled(boolean value)
	{
		LeftButton.setEnabled(value);
		UpButton.setEnabled(value);
		HomeButton.setEnabled(value);
	}

	/// title text
	public TitleLabel TitleText; 
	/// go left
	public GrowingLabel LeftButton;
	/// go up
	public GrowingLabel UpButton;
	/// go home
	public GrowingLabel HomeButton;
	/// serialization id
	private static final long serialVersionUID = -8646759072069754815L;
}
