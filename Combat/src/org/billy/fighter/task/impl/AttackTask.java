package org.billy.fighter.task.impl;

import org.billy.fighter.Fighter;
import org.billy.fighter.task.Task;
import org.osbot.script.MethodProvider;

public class AttackTask implements Task {
	
	@Override
	public boolean activate(Fighter fighter) {
		if(fighter.getCurrentHealth() >= fighter.getEatHealth()) {
			if(fighter.getTarget() != null) {
				if(fighter.getTarget().getHealth() == 0
						|| !fighter.getTarget().exists()
						|| (fighter.getTarget().getFacing() != null && !fighter.getTarget().getFacing().getName().equals(fighter.myPlayer().getName()))) {
					fighter.setTarget(null);
				}
			}
			if(fighter.getTarget() == null) {
				fighter.setTarget();
			}
			if(fighter.getTarget() != null) {
				if(!fighter.getTarget().isUnderAttack()) {
					if(!fighter.myPlayer().isMoving()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public int execute(Fighter fighter) {
		try {
			if(fighter.getTarget().interact("Attack")) {
				return MethodProvider.random(1000, 1500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
