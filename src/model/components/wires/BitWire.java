package model.components.wires;

import java.util.HashSet;

import config.Config;
import model.Vector3;
import model.components.Block;
import model.grid.Grid;

public class BitWire extends Wire{

	public BitWire(Grid grid, Vector3 position, byte insulated) {
		super(grid, position, insulated);
		this.dataType = Config.TYPE_BIT;
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
