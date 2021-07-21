package me.Simonsigge.xPlayCasino;

import org.bukkit.Location;
import org.bukkit.Material;

public class PrintNumber {
	int[][] numbers;
	
	public PrintNumber(){
		numbers = new int[10][15];
		numbers[0] = new int[]{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
		numbers[1] = new int[]{ 0,1,0,1,1,0,0,1,0,0,1,0,1,1,1 };
		numbers[2] = new int[]{ 1,1,1,0,0,1,1,1,1,1,0,0,1,1,1 };
		numbers[3] = new int[]{ 1,1,1,0,0,1,1,1,1,0,0,1,1,1,1 };
		numbers[4] = new int[]{ 1,0,1,1,0,1,1,1,1,0,0,1,0,0,1 };
		numbers[5] = new int[]{ 1,1,1,1,0,0,1,1,1,0,0,1,1,1,1 };
		numbers[6] = new int[]{ 1,1,1,1,0,0,1,1,1,1,0,1,1,1,1 };
		numbers[7] = new int[]{ 1,1,1,0,0,1,0,0,1,0,0,1,0,0,1 };
		numbers[8] = new int[]{ 1,1,1,1,0,1,1,1,1,1,0,1,1,1,1 };
		numbers[9] = new int[]{ 1,1,1,1,0,1,1,1,1,0,0,1,0,0,1 };
	}
	
	public void DoPrint(int number, Material mat, Location loc1, Location loc2, Location loc3){
		number--;
		Location[] loc = { loc1, loc2, loc3 };
		int doneLoc = 0;
		for(int i = 0 ; i <= 14 ; i++){
			if(numbers[number][i] == 1)
				loc[doneLoc].getBlock().setType(mat);
			else
				loc[doneLoc].getBlock().setType(Material.AIR);
			doneLoc++;
			if(doneLoc == 3){
				doneLoc = 0;
				for(int i2 = 0 ; i2 < loc.length ; i2++)
					loc[i2].add(0, -1, 0);
			}
		}
	}
}
