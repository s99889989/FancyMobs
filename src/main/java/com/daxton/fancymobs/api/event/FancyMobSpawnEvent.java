package com.daxton.fancymobs.api.event;

import com.daxton.fancymobs.api.FancyMob;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FancyMobSpawnEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final FancyMob fancyMob;

	public FancyMobSpawnEvent(FancyMob fancyMob){
		this.fancyMob = fancyMob;
	}

	public FancyMob getFancyMob() {
		return fancyMob;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

}
