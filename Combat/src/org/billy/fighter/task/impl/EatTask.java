package org.billy.fighter.task.impl;

import org.billy.fighter.Fighter;
import org.billy.fighter.task.Task;
import org.osbot.script.MethodProvider;
import org.osbot.script.rs2.model.Item;

public class EatTask implements Task {

	@Override
	public boolean activate(Fighter fighter) {
		if(fighter.getCurrentHealth() < fighter.getEatHealth() && fighter.getEatHealth() > 0) {
			if(getFood(fighter) != -1) {
				return true;
			}
			try {
				fighter.stop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public int execute(Fighter fighter) {
		int food = getFood(fighter);
		if(food != -1) {
			try {
				if(fighter.client.getInventory().interactWithId(food, "Eat")) {
					return MethodProvider.random(2000, 2500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	private int getFood(Fighter fighter) {
		Item[] items = fighter.client.getInventory().getItems();
		for(Item item : items) {
			if(item != null && item.getDefinition() != null) {
				String[] actions = item.getDefinition().getActions();
				for(int i = 0; actions != null && i < actions.length; ++i) {
					if(actions[i] != null && actions[i].toLowerCase().startsWith("eat")) {
						return item.getId();
					}
				}
			}
		}
		return -1;
	}

}
