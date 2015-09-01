package model.components.wires;

import java.awt.Color;

import config.Config;
import main.Main;
import model.Vector3;
import model.components.Block;
import model.grid.Grid;

public abstract class Wire extends Block{

	private byte insulated;
	
	public Wire(Grid grid, Vector3 position, byte insulated) {
		super(grid, position);
		this.insulated = insulated;
	}
	
	/**
	 * The character to display may change based on blocks surrounding the wire
	 * */
	@Override
	public short getCharacter() {
		return Main.config.getWire(
			this.getEastBlock()  != null ? this.getEastBlock() .canConnect(Config.WEST)  : false,
			this.getWestBlock()  != null ? this.getWestBlock() .canConnect(Config.EAST)  : false,
			this.getNorthBlock() != null ? this.getNorthBlock().canConnect(Config.SOUTH) : false,
			this.getSouthBlock() != null ? this.getSouthBlock().canConnect(Config.NORTH) : false,
			this.getHoverBlock() != null ? this.getHoverBlock().canConnect(Config.UNDER) : false,
			this.getUnderBlock() != null ? this.getUnderBlock().canConnect(Config.HOVER) : false
		);
	}

	@Override
	public Color getForeground() {
		return Main.config.getColor(this.dataType, Config.LOW);
	}
	
	@Override
	public Color getBackground() {
		return Main.config.getColor(this.insulated, Config.HIGH);
	}
	
	@Override
	public boolean canConnect( byte from ){
		Block block = this.getBlockAt(from);
		if( block != null ){
			if( block instanceof Wire ){
				return canConnect( this, (Wire) block );
			}else{
				if( this.dataType == block.getDataType() ){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Return true if the two cables can connect to each other
	 * */
	private static boolean canConnect( Wire wire1, Wire wire2 ){
		// if  wires are of the same level
		// and insulations match
		if(
			wire1.insulated == Config.CLEAR ||
			wire2.insulated == Config.CLEAR ||
			wire1.insulated == wire2.insulated
		){
			return true;
		// if  wire1 is one level under wire2
		// and wire1 is insulated
		}else if(
			wire1.dataType == wire2.dataType-1 &&
			wire1.insulated != Config.CLEAR
		){
			return true;
		// if  wire2 is one level under wire1
		// and wire2 is insulated
		}else if(
			wire2.dataType == wire1.dataType-1 &&
			wire2.insulated != Config.CLEAR
		){
			return true;
		}
		return false;
	}
	
}
