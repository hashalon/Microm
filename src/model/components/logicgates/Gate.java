package model.components.logicgates;

import java.awt.Color;
import java.util.HashSet;

import model.Vector3;
import model.components.Block;
import model.grid.Grid;

public abstract class Gate extends Block{

	public Gate(Grid grid, Vector3 position) {
		super(grid, position);
	}

	@Override
	public boolean canConnect(byte from) {
		return false;
	}

}
