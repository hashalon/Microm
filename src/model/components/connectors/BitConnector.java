package model.components.connectors;

import java.util.HashSet;

import config.Config;
import model.Vector3;
import model.components.Block;
import model.grid.Grid;

public class BitConnector extends Connector{

	public BitConnector(Grid grid, Vector3 position, byte orientation) {
		super(grid, position, orientation);
		this.dataType = Config.TYPE_BIT;
		this.outputType = Config.TYPE_BIT;
	}

	@Override
	public void sendSignal(HashSet<Block> blocks) {
		if( blocks.contains(this) ){
			blocks.remove(this);
			// send signal
		}
	}

	@Override
	public void receiveSignal(HashSet<Block> blocks) {
		if( blocks.contains(this) ){
			blocks.remove(this);
			// receive signal
		}
	}

	
}
