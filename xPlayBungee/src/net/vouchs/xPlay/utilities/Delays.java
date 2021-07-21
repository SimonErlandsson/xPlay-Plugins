package net.vouchs.xPlay.utilities;

import java.util.ArrayList;
import java.util.HashMap;

import net.vouchs.xPlay.Main;

public class Delays
{
	public static HashMap<String, Long> delays = new HashMap<String, Long>();

	public static void registerDelay(String key)
	{
		delays.put(key, System.currentTimeMillis());
	}
	
	public static void removeDelay(String key)
	{
		delays.remove(key);
	}

	public static boolean haveToWait(String key, int seconds)
	{
		if (Delays.delays.containsKey(key))
		{
			if ((System.currentTimeMillis() - Delays.delays.get(key)) / 1000 >= seconds)
			{
				Delays.delays.remove(key);
			}
		}
		return Delays.delays.containsKey(key);
	}

	public static String getRemainingTime(String key, int seconds)
	{
		return getTime((int) (seconds - (System.currentTimeMillis() - Delays.delays.get(key)) / 1000));
	}

	public static String getTime(int seconds)
	{
		return getTime((long) seconds);
	}

	public static String getTime(long seconds)
	{
		int sec = 0, min = 0, h = 0, d = 0, w = 0, m = 0, y = 0;
		while (seconds >= 29030400)
		{
			y++;
			y -= 29030400;
		}
		for (int i = 0; i < seconds; ++i)
		{
			++sec;
			if (sec >= 60)
			{
				min++;
				sec = 0;
			}
			if (min >= 60)
			{
				h++;
				min = 0;
			}
			if (h >= 24)
			{
				d++;
				h = 0;
			}
			if (d >= 7)
			{
				w++;
				d = 0;
			}
			if (w >= 4)
			{
				m++;
				w = 0;
			}
		}
		ArrayList<String> l = new ArrayList<String>();
		if (y != 0)
		{
			l.add(y + " year" + (y == 1 ? "" : "s"));
		}
		if (m != 0)
		{
			l.add(m + " month" + (m == 1 ? "" : "s"));
		}
		if (w != 0)
		{
			l.add(w + " week" + (w == 1 ? "" : "s"));
		}
		if (d != 0)
		{
			l.add(d + " day" + (d == 1 ? "" : "s"));
		}
		if (h != 0)
		{
			l.add(h + " hour" + (h == 1 ? "" : "s"));
		}
		if (min != 0)
		{
			l.add(min + " minute" + (min == 1 ? "" : "s"));
		}
		if (sec != 0)
		{
			l.add(sec + " second" + (sec == 1 ? "" : "s"));
		}
		return Main.getInstance().getUtilities().listToString(l, ", ", " and ");
	}
}
