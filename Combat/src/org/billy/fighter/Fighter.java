package org.billy.fighter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import org.billy.fighter.task.Task;
import org.billy.fighter.task.impl.AttackTask;
import org.billy.fighter.task.impl.EatTask;
import org.billy.fighter.task.impl.PickUpTask;
import org.billy.fighter.ui.FighterUI;
import org.osbot.script.MethodProvider;
import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;
import org.osbot.script.rs2.model.NPC;
import org.osbot.script.rs2.skill.Skill;

@ScriptManifest(author = "Billy", info = "A simplistic combat script.", name = "SimplisticFighter", version = 1.0)
public class Fighter extends Script {

	private final FighterUI fighterUI;
	private final Color transparentBlack;
	private final Task[] tasks;
	private NPC target;
	private String targetName;
	private Integer targetResetDelay;
	private Long targetTime;
	private Integer eatHealth;
	private Long runtime;
	private String[] pickupNames;
	
	public Fighter() {
		fighterUI = new FighterUI(this);
		transparentBlack = new Color(0, 0, 0, 140);
		tasks = new Task[] { new EatTask(), new PickUpTask(), new AttackTask() };
		setRuntime(System.currentTimeMillis());
		setPickupNames(new String[0]);
	}

	@Override
	public int onLoop() throws InterruptedException {
		if(!getFighterUI().isVisible()) {
			if(getTarget() != null && isTargetTimeExpired()) {
				setTarget(null);
			}
			for(Task task : tasks) {
				if(task.activate(this)) {
					return task.execute(this);
				}
			}
		}
		return 0;
	}

	@Override
	public void onStart() {
		getFighterUI().init();
		setTargetName("cow");
		setTargetTime();
		setEatHealth(10);
		setTargetResetDelay(15000);
		client.setMouseSpeed(MethodProvider.random(6, 8));
		experienceTracker.startAll();
	}
	
	@Override
	public void onPaint(Graphics graphics) {
		if(getFighterUI().isVisible()) {
			return;
		}
		Point p = client.getMousePosition();
		graphics.setColor(transparentBlack);
		graphics.fillRect(p.x, p.y, 300, 130);
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Runtime: " + ((System.currentTimeMillis() - getRuntime()) / 1000) + " seconds", p.x + 20, p.y + 20);
		graphics.drawString("Hitpoints Experience Gained: " + experienceTracker.getGainedXP(Skill.HITPOINTS), p.x + 20, p.y + 40);
		graphics.drawString("Attack Experience Gained: " + experienceTracker.getGainedXP(Skill.ATTACK), p.x + 20, p.y + 60);
		graphics.drawString("Strength Experience Gained: " + experienceTracker.getGainedXP(Skill.STRENGTH), p.x + 20, p.y + 80);
		graphics.drawString("Defence Experience Gained: " + experienceTracker.getGainedXP(Skill.DEFENCE), p.x + 20, p.y + 100);
		graphics.drawString("Current Health: " + getCurrentHealth(), p.x + 20, p.y + 120);
	}
	
	@Override
	public void onExit() throws InterruptedException {
		getFighterUI().dispose();
	}
	
	public int getCurrentHealth() {
		return myPlayer().getClient().getSkills().getCurrentLevel(Skill.HITPOINTS);
	}
	
	public FighterUI getFighterUI() {
		return fighterUI;
	}

	public NPC getTarget() {
		return target;
	}

	public void setTarget() {
		List<NPC> npcs = closestAttackableNPCListForName(getTargetName());
		for(NPC npc : npcs) {
			if(!npc.isUnderAttack() && npc.canAttack()) {
				if(canReach(npc)) {
					setTarget(npc);
					setTargetTime();
					break;
				}
			}
		}
	}
	
	public void setTarget(NPC target) {
		this.target = target;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public boolean isTargetTimeExpired() {
		return (System.currentTimeMillis() - targetTime) > getTargetResetDelay() ? true : false;
	}

	public void setTargetTime() {
		this.targetTime = System.currentTimeMillis();
	}

	public Integer getEatHealth() {
		return eatHealth;
	}

	public void setEatHealth(Integer eatHealth) {
		this.eatHealth = eatHealth;
	}
	
	public Long getRuntime() {
		return runtime;
	}

	public void setRuntime(Long runtime) {
		this.runtime = runtime;
	}

	public Integer getTargetResetDelay() {
		return targetResetDelay;
	}

	public void setTargetResetDelay(Integer targetResetDelay) {
		this.targetResetDelay = targetResetDelay;
	}

	public String[] getPickupNames() {
		return pickupNames;
	}

	public void setPickupNames(String[] pickupNames) {
		this.pickupNames = pickupNames;
	}

}
