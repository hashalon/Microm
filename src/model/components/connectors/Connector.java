package model.components.connectors;

import java.awt.Color;

import config.Config;
import main.Main;
import model.Vector3;
import model.components.Block;
import model.grid.Grid;

public abstract class Connector extends Block{

	protected byte orientation;
	
	protected byte outputType;
	
	public Connector(Grid grid, Vector3 position, byte orientation) {
		super(grid, position);
		this.orientation = orientation;
	}

	public Block getForwardBlock(){
		return this.getBlockAt(this.orientation);
	}
	
	public Block getBackwardBlock(){
		return this.getBlockAt(
			(byte) ((this.orientation & 1) == 1
			? this.orientation-1
			: this.orientation+1)
		);
	}
	
	@Override
	public short getCharacter() {
		return Main.config.getConnector(this.orientation);
	}

	@Override
	public Color getForeground() {
		return Main.config.getColor(this.dataType, Config.HIGH);
	}
	
	@Override
	public Color getBackground() {
		return Main.config.getColor(this.outputType, Config.LOW);
	}

	@Override
	public boolean canConnect(byte from) {
		// if where the signal is coming from and
		// the orientation of the connector are opposed,
		// they are connected
		if( this.orientation == from ){
			return this.outputType == this.getBlockAt(from).getDataType();
		}else if( (this.orientation ^ from) == 1 ){
			return this.dataType == this.getBlockAt(from).getDataType();
		}
		return false;
	}

}
