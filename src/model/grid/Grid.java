package model.grid;

import java.util.HashSet;
import java.util.Iterator;

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
	
	public Block getBlockAt( Vector3 position ){
		Iterator<Block> it = this.blocks.iterator();
		while( it.hasNext() ){
			Block block = it.next();
			if( position.isEqual(block.getPosition()) ){
				return block;
			}
		}
		return null;
	}
	
	public Block[] getBlocksAt( Vector3... positions ){
		Block[] blocks = new Block[positions.length];
		Iterator<Block> it = this.blocks.iterator();
		while( it.hasNext() ){
			Block block = it.next();
			for( byte i=0; i<positions.length; ++i ){
				if( positions[i].isEqual(block.getPosition()) ){
					blocks[i] = block;
				}
			}
		}
		return blocks;
	}
}
