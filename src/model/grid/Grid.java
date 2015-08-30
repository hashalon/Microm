package model.grid;

import java.util.HashSet;

import model.Vector3;
import model.components.Block;

public class Grid {

	private Vector3 size;
	private HashSet<Block> blocks;
	
	public Grid(){
		this( 256, 256, 256 );
	}
	
	public Grid( int x, int y, int z ){
		super();
		this.size = new Vector3(x,y,z);
		this.blocks = new HashSet<Block>();
	}
	
	public boolean contains( Block block ){
		if( block.getPosition().isSmaller(this.size) ){
			if( this.blocks.contains(block) ){
				return true;
			}
		}
		return false;
	}
}
