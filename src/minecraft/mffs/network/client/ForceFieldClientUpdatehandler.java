package mffs.network.client;

import java.util.EnumSet;
import java.util.Stack;

import mffs.common.ModularForceFieldSystem;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public final class ForceFieldClientUpdatehandler implements IScheduledTickHandler
{
	protected static Stack queue = new Stack();

	public void tickEnd(EnumSet type, Object[] tickData)
	{
		StringBuilder str = new StringBuilder();

		while (!queue.isEmpty())
		{
			str.append(queue.pop());
			str.append("/");
			str.append(queue.pop());
			str.append("/");
			str.append(queue.pop());
			str.append("#");

			if (str.length() > 7500)
			{
				break;
			}
		}

		if (str.length() > 0)
		{
			NetworkHandlerClient.requestForceFieldInitialData(ModularForceFieldSystem.proxy.getClientWorld().provider.dimensionId, str.toString());
			str.setLength(0);
		}
	}

	public void tickStart(EnumSet type, Object[] tickData)
	{
	}

	public EnumSet ticks()
	{
		return EnumSet.of(TickType.PLAYER);
	}

	public String getLabel()
	{
		return "ForceField Client Ticker";
	}

	public int nextTickSpacing()
	{
		return 1;
	}

	public static void addto(int x, int y, int z)
	{
		queue.push(Integer.valueOf(x));
		queue.push(Integer.valueOf(y));
		queue.push(Integer.valueOf(z));
	}
}