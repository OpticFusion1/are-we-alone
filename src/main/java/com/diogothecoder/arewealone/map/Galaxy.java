package com.diogothecoder.arewealone.map;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import com.diogothecoder.arewealone.Game;
import com.diogothecoder.arewealone.Player;
import com.diogothecoder.arewealone.Position;
import com.diogothecoder.arewealone.tools.Console;

public class Galaxy extends Map {
	private SolarSystem[] solarSystems;
	
	protected void generateMap() {
		for (String[] strings : this.getMap()) {
			Arrays.fill(strings, "");
		}

		int numOfSolarSystems = this.getRandomInt(Config.MIN_NUM_OF_SOLAR_SYSTEMS_PER_GALAXY, Config.MAX_NUM_OF_SOLAR_SYSTEMS_PER_GALAXY, true);

		int middle = this.getMap().length / 2;
		this.getMap()[middle][middle] = "B";
				
		this.solarSystems = new SolarSystem[numOfSolarSystems];
		for (int i = 0; i < solarSystems.length; i++) {
			this.solarSystems[i] = new SolarSystem();
			
			while (true) {
				int randomLocationX = this.getRandomInt(1, this.getMap().length - 1, false);
				int randomLocationY = this.getRandomInt(1, this.getMap()[randomLocationX].length - 1, false);

				if (this.getMap()[randomLocationX][randomLocationY] == null || this.getMap()[randomLocationX][randomLocationY].isEmpty()) {
					this.getMap()[randomLocationX][randomLocationY] = Integer.toString(i);
					break;
				}
			}
		}
	}
	
	public void display() {
		Console.clear();
		for (int row = 0; row < this.getMap().length; row++) {
			if (row <= 9) {
				System.out.print("0" + row + " ");
			} else {
				System.out.print(row + " ");				
			}
			
			for (int column = 0; column < this.getMap()[row].length; column++) {
				if (row == Game.getUniverse().getGalaxy().getPlayerPosition().getY()
						&& column == Game.getUniverse().getGalaxy().getPlayerPosition().getX()) {
					System.out.print(Player.MAP_KEY + "  ");
				} else if (Objects.equals(this.getMap()[row][column], "B")) {
					System.out.print("B  ");
				} else {
					System.out.print(".  ");
				}
			}
			
			System.out.println();
		}
		
		System.out.print("  ");
		
		for (int column = 0; column < this.getMap().length; column++) {
			if (column <= 9) {
				System.out.print(" " + column + " ");
			} else {
				System.out.print(column + " ");				
			}
		}
		
		System.out.println();
	}
	
	public SolarSystem getSolarSystem(Position galaxyPosition) {
		String index = this.getMap()[galaxyPosition.getX()][galaxyPosition.getY()];
		return this.solarSystems[Integer.parseInt(index)];
	}
	
	public SolarSystem getSolarSystem() {
		Position currentPlayerPosition = Game.getUniverse().getGalaxy().getPlayerPosition();
		String index = this.getMap()[currentPlayerPosition.getX()][currentPlayerPosition.getY()];

		try {
			return this.solarSystems[Integer.parseInt(index)];
		} catch (NumberFormatException e) {
			// We have moved away from the Solar System
			return null;
		}
	}

}
