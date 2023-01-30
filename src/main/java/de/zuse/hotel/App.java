package de.zuse.hotel;

import de.zuse.hotel.gui.*;

public class App
{
	public static void main(String[] args)
	{
		Layer layer = new Example();

		{
			layer.onStart();
			layer.run(args);
			layer.onClose();
		}

		System.out.println("Github test GG WP");

	}

}
