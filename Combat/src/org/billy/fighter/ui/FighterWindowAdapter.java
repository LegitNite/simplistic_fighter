package org.billy.fighter.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.billy.fighter.Fighter;

public class FighterWindowAdapter extends WindowAdapter {
	
	private final Fighter fighter;
	
	public FighterWindowAdapter(Fighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public void windowClosing(WindowEvent event) {
		try {
			fighter.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
