package model.components;

import config.Config;
import model.Vector3;
import model.grid.Grid;

public abstract class Block {

	private Grid grid;
	private Vector3 position;
	
	private Block[] adjacents;
	
	public Block( Grid grid, Vector3 position ){
		super();
		this.grid = grid;
		this.position = position;
		this.adjacents = new Block[Config.NUM_OF_DIRECTIONS];
		for( byte i=0; i<Config.NUM_OF_DIRECTIONS; ++i ){
			
		}
	}
	
	public Grid getGrid(){
		return this.grid;
	}
	public Vector3 getPosition(){
		return this.position;
	}

	public Block getEastBlock() {
		return this.adjacents[Config.EAST];
	}
	public Block getWestBlock() {
		return this.adjacents[Config.WEST];
	}
	public Block getNorthBlock() {
		return this.adjacents[Config.NORTH];
	}
	public Block getSouthBlock() {
		return this.adjacents[Config.SOUTH];
	}
	public Block getHoverBlock() {
		return this.adjacents[Config.HOVER];
	}
	public Block getUnderBlock() {
		return this.adjacents[Config.UNDER];
	}

	private void setAdjacentBlock( byte direction, Block block ){
		if( direction > -1 && direction < Config.NUM_OF_DIRECTIONS ){
			this.adjacents[direction] = block;
			if( block != null ){
				byte opposed = (byte) (
					(direction & 1) == 1
					? direction-1
					: direction+1
				);
				block.adjacents[opposed] = this;
			}
		}
	}
	
}
