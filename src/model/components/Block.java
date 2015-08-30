package model.components;

import model.Vector3;
import model.grid.Grid;

public class Block {

	private Grid grid;
	private Vector3 position;
	
	private Block east_block;
	private Block west_block;
	private Block north_block;
	private Block south_block;
	private Block hover_block;
	private Block under_block;
	
	public Block( Grid grid, Vector3 position ){
		super();
		this.grid = grid;
		this.position = position;
	}
	public Block( Grid grid, int x, int y, int z ){
		this( grid, new Vector3(x,y,z) );
	}
	
	public Grid getGrid(){
		return this.grid;
	}
	
	public Vector3 getPosition(){
		return this.position;
	}
}
