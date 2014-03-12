package org.billy.fighter.task;

import org.billy.fighter.Fighter;

public interface Task {
	
	public boolean activate(Fighter fighter);
	public int execute(Fighter fighter);

}
