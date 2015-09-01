package model.components;

import java.awt.Color;
import java.util.HashSet;

import config.Config;
import model.Vector3;
import model.grid.Grid;

public abstract class Block {

	private Grid grid;
	private Vector3 position;
	
	private Block[] adjacents;
	
	protected boolean isSource;
	protected boolean isEmiter;
	
	protected byte dataType;
	
	public Block( Grid grid, Vector3 position ){
		super();
		
		// we store the grid
		this.grid = grid;
		
		// we set the position of the block
		this.position = position;
		
		// we recover the adjacent blocks
		this.adjacents = new Block[Config.NUM_OF_DIRECTIONS];
		
		Block[] blocks = grid.getBlocksAt(
			new Vector3( this.position,  1,  0,  0 ),
			new Vector3( this.position, -1,  0,  0 ),
			new Vector3( this.position,  0,  1,  0 ),
			new Vector3( this.position,  0, -1,  0 ),
			new Vector3( this.position,  0,  0,  1 ),
			new Vector3( this.position,  0,  0, -1 )
		);
		for( byte i=0; i<Config.NUM_OF_DIRECTIONS; ++i ){
			this.setAdjacentBlock( i, blocks[i] );
		}
	}
	
	public Grid getGrid(){
		return this.grid;
	}
	public Vector3 getPosition(){
		return this.position;
	}

	public boolean isSource(){
		return this.isSource;
	}
	public boolean isEmiter(){
		return this.isEmiter;
	}
	public byte getDataType(){
		return this.dataType;
	}
	
	public Block getBlockAt( byte direction ){
		return this.adjacents[direction];
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
	
	public abstract short getCharacter();
	public abstract Color getForeground();
	public abstract Color getBackground();
	
	public abstract boolean canConnect( byte from );
	
	public abstract void sendSignal   ( HashSet<Block> blocks );
	public abstract void receiveSignal( HashSet<Block> blocks );
}
