package org.billy.fighter.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.billy.fighter.Fighter;

@SuppressWarnings("serial")
public class FighterUI extends JFrame {
	
	private final Fighter fighter;
	private final Dimension textFieldDimension;
	private final JLabel errorLabel;
	private final FighterHintTextField targetNameField;
	private final FighterHintTextField targetResetDelayField;
	private final FighterHintTextField eatHealthField;
	private final FighterHintTextField pickupItemsField;
	private final JButton startButton;
	
	public FighterUI(Fighter fighter) {
		super("Fighter");
		this.fighter = fighter;
		this.errorLabel = new JLabel("SimplisticFighter", SwingConstants.CENTER);
		this.textFieldDimension = new Dimension(250, 50);
		this.targetNameField = new FighterHintTextField("Target Name - e.g Cow");
		this.targetResetDelayField = new FighterHintTextField("Target Reset Delay (in ms) - e.g 15000");
		this.eatHealthField = new FighterHintTextField("Eat Health - e.g 10");
		this.pickupItemsField = new FighterHintTextField("Pickup items - e.g Bones, Raw Beef, Cowhide ");
		this.startButton = new JButton("Start");
	}
		
	private void build() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(0, 1));
		addWindowListener(new FighterWindowAdapter(getFighter()));
		
		getErrorLabel().setFocusable(false);
		getErrorLabel().setPreferredSize(getTextFieldDimension());
		getErrorLabel().setSize(getTextFieldDimension());
		add(getErrorLabel());
		
		getTargetNameField().setPreferredSize(getTextFieldDimension());
		getTargetNameField().setSize(getTextFieldDimension());
		add(getTargetNameField());
		
		getTargetResetDelayField().setPreferredSize(getTextFieldDimension());
		getTargetResetDelayField().setSize(getTextFieldDimension());
		add(getTargetResetDelayField());
		
		getEatHealthField().setPreferredSize(getTextFieldDimension());
		getEatHealthField().setSize(getTextFieldDimension());
		add(getEatHealthField());
		
		getPickupItemsField().setPreferredSize(getTextFieldDimension());
		getPickupItemsField().setSize(getTextFieldDimension());
		add(getPickupItemsField());
		
		getStartButton().setPreferredSize(getTextFieldDimension());
		getStartButton().setSize(getTextFieldDimension());
		
		getStartButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				if(!getTargetNameField().hasTextChanged()) {
					getErrorLabel().setText("No value for target name set.");
					return;
				}

				if(!getTargetResetDelayField().hasTextChanged()) {
					getErrorLabel().setText("No value for target reset delay set.");
					return;
				}
				
				if(!getEatHealthField().hasTextChanged()) {
					getErrorLabel().setText("No value for eat health set.");
					return;
				}
				
				if(!getPickupItemsField().hasTextChanged()) {
					getFighter().setPickupNames(new String[0]);
				}
				
				try {
					getFighter().setTargetResetDelay(Integer.valueOf(getTargetResetDelayField().getText()));
				} catch(NumberFormatException e) {
					getErrorLabel().setText("Invalid target reset delay value.");
					return;
				}
				
				try {
					getFighter().setEatHealth(Integer.valueOf(getEatHealthField().getText()));
				} catch(NumberFormatException e) {
					getErrorLabel().setText("Invalid eat health value.");
					return;
				}

				getFighter().setPickupNames(getPickupItemsField().getText().split(",|.|\\|"));
				
				for(int i = 0; i < getFighter().getPickupNames().length; ++i) {
					getFighter().getPickupNames()[i] = getFighter().getPickupNames()[i].trim().toLowerCase();
				}
				
				getFighter().setTargetName(getTargetNameField().getText());
				dispose();
				
			}
			
		});
		
		add(getStartButton());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		requestFocus();
	}
	
	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					build();
				}
				
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Fighter getFighter() {
		return fighter;
	}

	public Dimension getTextFieldDimension() {
		return textFieldDimension;
	}

	public FighterHintTextField getTargetNameField() {
		return targetNameField;
	}

	public FighterHintTextField getTargetResetDelayField() {
		return targetResetDelayField;
	}

	public FighterHintTextField getEatHealthField() {
		return eatHealthField;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public JLabel getErrorLabel() {
		return errorLabel;
	}

	public FighterHintTextField getPickupItemsField() {
		return pickupItemsField;
	}

}
