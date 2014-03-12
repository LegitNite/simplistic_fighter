package org.billy.fighter.ui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FighterHintTextField extends JTextField implements FocusListener {
	
	private final String hint;
	
	public FighterHintTextField(String hint) {
		super(hint);
		this.hint = hint;
		this.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(getText().equalsIgnoreCase(getHint())) {
			setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(getText().length() == 0) {
			setText(getHint());
		}
	}
	
	public boolean hasTextChanged() {
		if(getText().equals(getHint())) { 
			return false;
		}
		return true;
	}
	
	public String getHint() {
		return hint;
	}

}
