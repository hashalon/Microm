package model.components.connectors;

import java.util.HashSet;

import config.Config;
import model.Vector3;
import model.components.Block;
import model.components.datatypes.BitBlock;
import model.grid.Grid;

public class BitConnector extends Connector implements BitBlock{

	private boolean data;
	
	public BitConnector(Grid grid, Vector3 position, byte orientation) {
		super(grid, position, orientation);
		this.dataType = Config.TYPE_BIT;
		this.outputType = Config.TYPE_BIT;
	}

	@Override
	public void sendSignal(HashSet<Block> blocks) {
		// if the block hasn't been updated
		if( this.hasNotBeenUpdated(blocks) ){
			// we reset the data to false
			this.data = false;
			Block block = this.getBackwardBlock();
			// we must make sure that the previous block has been updated
			if( block != null ){
				block.sendSignal(blocks);
			}
			// we recover the block the connector is pointing at
			Block block_forward = this.getForwardBlock();
			if( block_forward != null ){
				// if the forward block is of the correct type
				if( block_forward instanceof BitBlock ){
					// we set the data of the next block
					((BitBlock) block_forward).setData(this.data);
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
			Block block = this.getBackwardBlock();
			// we must make sure that the previous block has been updated
			if( block != null ){
				block.receiveSignal(blocks);
				// we recover the block at the back of the connector
				if( block instanceof BitBlock ){
					// we get the data from the previous block
					this.data |= ((BitBlock) block).getData();
				}
			}
		}
	}

	@Override
	public void setData(boolean data) {
		// we combine the two data
		this.data |= data;
	}

	@Override
	public boolean getData() {
		return this.data;
	}

	
}
