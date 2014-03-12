package org.billy.fighter.task.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.billy.fighter.Fighter;
import org.billy.fighter.task.Task;
import org.osbot.script.MethodProvider;
import org.osbot.script.rs2.model.GroundItem;

public class PickUpTask implements Task {
	
	private GroundItem getPickupItem(final Fighter fighter) {
		List<GroundItem> items = fighter.client.getCurrentRegion().getItems();
		Collections.sort(items, new Comparator<GroundItem>() {

			@Override
			public int compare(GroundItem one, GroundItem two) {
				int first = fighter.distance(one);
				int second = fighter.distance(two);
				if(first == second) {
					return 0;
				}
				if(first > second) {
					return 1;
				}
				return -1;
			}
			
		});
		for(GroundItem item : items) {
			if(item != null) {
				for(String s : fighter.getPickupNames()) {
					if(item.getName().equalsIgnoreCase(s)) {
						if(item.isVisible()) {
							return item;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean activate(Fighter fighter) {
		if(fighter.getCurrentHealth() >= fighter.getEatHealth() && !fighter.myPlayer().isUnderAttack() && !fighter.myPlayer().isMoving() && !fighter.client.getInventory().isFull()) {
			if(fighter.getPickupNames().length > 0 && getPickupItem(fighter) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int execute(Fighter fighter) {
		GroundItem ground = getPickupItem(fighter);
		if(ground != null) {
			try {
				if(ground.interact("Take")) {
					return MethodProvider.random(3000, 3500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}
