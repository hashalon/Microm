package model.components.logicgates;

import java.awt.Color;
import java.util.HashSet;

import config.Config;
import main.Main;
import model.Vector3;
import model.components.Block;
import model.components.datatypes.BitBlock;
import model.grid.Grid;
import utils.Util;

public abstract class BitGate extends Gate implements BitBlock{

	protected boolean data;
	
	public BitGate(Grid grid, Vector3 position) {
		super(grid, position);
		this.dataType = Config.TYPE_BIT;
	}

	@Override
	public void setData(boolean data) {
		this.data |= data;
	}

	@Override
	public boolean getData() {
		return this.data;
	}

	@Override
	public Color getForeground() {
		return Main.config.getColor(Config.RED, Config.HIGH);
	}
	
	@Override
	public Color getBackground() {
		return Util.CLEAR;
	}

	@Override
	public void sendSignal(HashSet<Block> blocks) {
		// if the block hasn't been updated
		if( this.hasNotBeenUpdated(blocks) ){
			// we reset the data to false
			this.data = false;
			// for each block around the wire
			for( byte i=0; i<Config.NUM_OF_DIRECTIONS; ++i ){
				Block block = this.getBlockAt(i);
				if( block != null ){
					// we update the block
					block.sendSignal(blocks);
					// if the block is of the same data type
					if( block instanceof BitBlock ){
						((BitBlock) block).setData(this.data);
					}
				}
			}
		}
	}

	@Override
	public void receiveSignal(HashSet<Block> blocks) {
		// if the block hasn't been updated
		if( this.hasNotBeenUpdated(blocks) ){
			// we reset the data to false
			this.data = false;
			// for each block around the wire
			for( byte i=0; i<Config.NUM_OF_DIRECTIONS; ++i ){
				Block block = this.getBlockAt(i);
				if( block != null ){
					// we update the block
					block.receiveSignal(blocks);
					// if the block is of the same data type
					if( block instanceof BitBlock ){
						this.data |= ((BitBlock) block).getData();
					}
				}
			}
		}
	}

}
