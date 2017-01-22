package com.cs442.apipalia.memorygame;

import android.widget.Button;

public class Tile {

	public int x;
	public int y;
	public Button button;
	
	public Tile(int x, int y, Button button) {
		this.x = x;
		this.y = y;
		this.button = button;
	}
}
