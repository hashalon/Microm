package config;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import utils.Util;

public class Config {
	
	public static final byte EAST  = 0;
	public static final byte WEST  = 1;
	public static final byte NORTH = 2;
	public static final byte SOUTH = 3;
	public static final byte HOVER = 4;
	public static final byte UNDER = 5;
	
	public static final byte BLACK   = 0;
	public static final byte RED     = 1;
	public static final byte GREEN   = 2;
	public static final byte YELLOW  = 3;
	public static final byte BLUE    = 4;
	public static final byte MAGENTA = 5;
	public static final byte CYAN    = 6;
	public static final byte WHITE   = 7;
	
	public static final boolean LOW  = false;
	public static final boolean HIGH = true;
	
	public static final byte OP_NONE = 0;
	public static final byte OP_AND  = 1;
	public static final byte OP_OR   = 2;
	public static final byte OP_XOR  = 3;
	public static final byte OP_NOT  = 4;
	public static final byte OP_NAND = 5;
	public static final byte OP_NOR  = 6;
	public static final byte OP_XNOR = 7;
	
	public static final byte OP_PLUS     = 0;
	public static final byte OP_MINUS    = 1;
	public static final byte OP_MULTIPLY = 2;
	public static final byte OP_DIVIDE   = 3;
	public static final byte OP_MODULO   = 4;
	
	public static final byte OP_EQUAL         = 0;
	public static final byte OP_LESSER        = 1;
	public static final byte OP_GREATER       = 2;
	public static final byte OP_LESSER_EQUAL  = 3;
	public static final byte OP_GREATER_EQUAL = 4;
	public static final byte OP_DIFFERENT     = 5;
	
	public static final byte NUM_OF_NUMBERS         = 10;
	public static final byte NUM_OF_LETTERS         = 26;
	public static final byte NUM_OF_BINARY_OP       =  8;
	public static final byte NUM_OF_ARITHMETICAL_OP =  5;
	public static final byte NUM_OF_RELATIONAL_OP   =  6;
	public static final byte NUM_OF_COLORS          = 16;
	public static final byte NUM_OF_DIRECTIONS      =  6;
	public static final byte NUM_OF_BORDERS         =  8;
	public static final byte NUM_OF_BLOCKS          =  5;
	public static final byte NUM_OF_POSSIBLE_CONN   =  4;
	
	/* CHARSET */
	private BufferedImage charset;
	
	/* CHARACTER SIZE */
	private byte charWidth;
	private byte charHeight;
	
	/* UNDEFINED CHARACTER */
	private short undefined;
	
	/* COLORS */
	private Color[] colors;
	
	/* REGULAR CHARACTERS */
	private short numbers_begin;
	private short maj_letters_begin;
	private short min_letters_begin;
	
	/* COMPONENT CUSTOM CHARACTERS */
	private short custom_characters_begin;
	private short custom_characters_end;
	
	/* BITWISE OPERATORS CHARACTERS */
	private short[] op_binaries;
	
	/* ARITHMETICAL OPERATORS CHARACTERS */
	private short[] op_arithmeticals;

	/* RELATIONAL OPERATORS CHARACTERS */
	private short[] op_relationals;
	
	/* GRID BLOCKS CHARACTERS */
	private short[] grid_blocks;
	
	/* USERS CHARACTERS */
	private short user_cursor;
	private short user_blink;
	private short user_pointer;
	private short user_border;
	
	/* CONNECTORS CHARACTERS */
	private short[] connectors;
	private short[] component_connectors;
	
	/* COMPONENT BORDERS CHARACTERS */
	private short[] component_borders;
	
	/* WIRES CHARACTERS */
	// values are stored the following way :
	// [west-east][north-south][top-bottom]
	// 0 : no positive, no negative
	// 1 : positive
	// 2 : negative
	// 3 : positive and negative
	private short[][][] wires;
	
	public Config( String folder ){
		InputStream inputStream = null;
		
		try {
			Properties prop = new Properties();
			String propFileName = "charset.properties";
			String path = folder+File.separatorChar+propFileName;
			
			inputStream = getClass().getClassLoader().getResourceAsStream(path);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			// get the property value and print it out
			String tileset = prop.getProperty("tileset");
			
			// character size
			this.charWidth  = (byte) extractProp(prop,"charWidth");
			this.charHeight = (byte) extractProp(prop,"charHeight");
			
			// undefined character
			this.undefined = extractProp(prop, "undefined");
			
			// standard character starting index
			this.numbers_begin     = extractProp(prop,"numbers");
			this.maj_letters_begin = extractProp(prop, "maj_letters");
			this.min_letters_begin = extractProp(prop, "min_letters");
			
			// custom characters
			this.custom_characters_begin = extractProp(prop, "custom_min");
			this.custom_characters_end   = extractProp(prop, "custom_max");
			
			this.user_cursor  = extractProp(prop, "user_cursor");
			this.user_blink   = extractProp(prop, "user_blink");
			this.user_pointer = extractProp(prop, "user_pointer");
			this.user_border  = extractProp(prop, "user_border");
			
			// binary operators
			this.op_binaries = new short[NUM_OF_BINARY_OP];
			String[] binOpNames = {
				"none","and","or","xor","not","nand","nor","xnor"	
			};
			for( byte i=0; i<NUM_OF_BINARY_OP; ++i ){
				this.op_binaries[i] = extractProp(prop, "op_"+binOpNames[i]);
			}
			
			// arithmetical operators
			this.op_arithmeticals = new short[NUM_OF_ARITHMETICAL_OP];
			String[] arithmOpNames = {
					"plus","minus","multiply","divide","modulo"
			};
			for( byte i=0; i<NUM_OF_ARITHMETICAL_OP; ++i ){
				this.op_arithmeticals[i] = extractProp(prop, "op_"+arithmOpNames[i]);
			}
			
			// equality operators
			this.op_relationals = new short[NUM_OF_RELATIONAL_OP];
			String[] relaOpNames = {
				"equal","lesser","greater","lesser_equal","greater_equal","different"
			};
			for( byte i=0; i<NUM_OF_RELATIONAL_OP; ++i ){
				this.op_relationals[i] = extractProp(prop, "op_"+relaOpNames[i]);
			}
			
			// colors
			this.colors = new Color[NUM_OF_COLORS];
			String[] colorNames = {
				"black","red","green","yellow","blue","magenta","cyan","white"	
			};
			for( byte i=0; i<NUM_OF_COLORS>>1; ++i ){
				this.colors[i] = Util.parseColor(
						prop.getProperty( "low_"+colorNames[i] )
				);
			}
			for( byte i=NUM_OF_COLORS>>1; i<NUM_OF_COLORS; ++i ){
				this.colors[i] = Util.parseColor(
						prop.getProperty( "high_"+colorNames[i-NUM_OF_COLORS>>1] )
				);
			}
			
			// grid blocks
			this.grid_blocks = new short[NUM_OF_BLOCKS];
			for( byte i=0; i<NUM_OF_BLOCKS; ++i ){
				this.grid_blocks[i] = extractProp(prop,"grid_"+i);
			}
			
			// connectors
			this.connectors = new short[NUM_OF_DIRECTIONS];
			this.component_connectors = new short[NUM_OF_DIRECTIONS];
			char[] directions = {
				'e','w','n','s','t','b'	
			};
			for( byte i=0; i<NUM_OF_DIRECTIONS; ++i ){
				this.connectors[i]           = extractProp(prop,"connector_"+directions[i]);
				this.component_connectors[i] = extractProp(prop,"comp_conn_"+directions[i]);
			}
			
			// borders
			this.component_borders = new short[NUM_OF_BORDERS];
			String[] edges = {
				"_e","_w","_n","_s","_e_n","_e_s","_w_n","_w_s"
			};
			for( byte i=0; i<NUM_OF_BORDERS; ++i ){
				this.component_borders[i] = extractProp(prop,"component"+edges[i]);
			}
			
			// wires
			this.wires = new short
					[NUM_OF_POSSIBLE_CONN]
					[NUM_OF_POSSIBLE_CONN]
					[NUM_OF_POSSIBLE_CONN];
			String x = "";
			String y = "";
			String z = "";
			for( byte i=0; i<NUM_OF_POSSIBLE_CONN; ++i ){
				if( i == 0 ) x = "";
				if( i == 1 ) x = "_e";
				if( i == 2 ) x = "_w";
				if( i == 3 ) x = "_e_w";
				for( byte j=0; j<NUM_OF_POSSIBLE_CONN; ++j ){
					if( j == 0 ) y = "";
					if( j == 1 ) y = "_n";
					if( j == 2 ) y = "_s";
					if( j == 3 ) y = "_n_s";
					for( byte k=0; k<NUM_OF_POSSIBLE_CONN; ++k ){
						if( k == 0 ) z = "";
						if( k == 1 ) z = "_h";
						if( k == 2 ) z = "_u";
						if( k == 3 ) z = "_h_u";
						this.wires[i][j][k] = extractProp(prop,"wire"+x+y+z);
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static short extractProp(Properties prop, String label){
		return Util.parseShort(prop.getProperty(label));
	}
	
	public short getStandardCharacter( char a ){
		if( a >= '0' && a <= '9' ){
			return (short) (this.numbers_begin + a - '0');
		}
		if( a >= 'A' && a <= 'Z' ){
			return (short) (this.maj_letters_begin + a - 'A');
		}
		if( a >= 'a' && a <= 'z' ){
			return (short) (this.min_letters_begin + a - 'a');
		}
		return this.undefined;
	}
	
	public short getCustomCharacter( byte character ){
		character += this.custom_characters_begin;
		if( character < this.custom_characters_end ){
			return character;
		}
		return this.undefined;
	}
	
	public short getUserCursor(){
		return this.user_cursor;
	}
	public short getUserBlink(){
		return this.user_blink;
	}
	public short getUserPointer(){
		return this.user_pointer;
	}
	public short getUserBorder(){
		return this.user_border;
	}
	
	public short getBinaryOperator( byte op ){
		if( op > -1 && op < NUM_OF_BINARY_OP ){
			return this.op_binaries[op];
		}
		return this.undefined;
	}
	public short getArithmeticalOperator( byte op ){
		if( op > -1 && op < NUM_OF_ARITHMETICAL_OP ){
			return this.op_arithmeticals[op];
		}
		return this.undefined;
	}
	public short getRelationalOperator( byte op ){
		if( op > -1 && op < NUM_OF_RELATIONAL_OP ){
			return this.op_relationals[op];
		}
		return this.undefined;
	}
	
	public Color getColor( byte color, boolean intensity ){
		if( intensity ){
			color += 8;
		}
		if( color > -1 && color < NUM_OF_COLORS ){
			return this.colors[color];
		}
		return Util.TRANSPARENT;
	}
	
	public short getGridBlock( byte intensity ){
		if( intensity > -1 && intensity < NUM_OF_BLOCKS ){
			return this.grid_blocks[intensity];
		}
		return this.undefined;
	}
	
	public short getConnector( byte orientation ){
		byte index = getIndexForOrientation(orientation);
		if( index > -1 && index < NUM_OF_DIRECTIONS ){
			return this.connectors[index];
		}
		return this.undefined;
	}
	public short getComponentConnector( byte orientation ){
		byte index = getIndexForOrientation(orientation);
		if( index > -1 && index < NUM_OF_DIRECTIONS ){
			return this.component_connectors[index];
		}
		return this.undefined;
	}
	private static byte getIndexForOrientation( byte orientation ){
		if( orientation == EAST  ) return 0;
		if( orientation == WEST  ) return 1;
		if( orientation == NORTH ) return 2;
		if( orientation == SOUTH ) return 3;
		if( orientation == HOVER ) return 4;
		if( orientation == UNDER ) return 5;
		return -1;
	}
	
	public short getComponentBorder( byte orientation ){
		if( orientation == EAST  ) return this.component_borders[0];
		if( orientation == WEST  ) return this.component_borders[1];
		if( orientation == NORTH ) return this.component_borders[2];
		if( orientation == SOUTH ) return this.component_borders[3];
		return this.undefined;
	}
	public short getComponentBorder( byte orien1, byte orien2 ){
		if(
			( orien1 == EAST  && orien2 == NORTH ) ||
			( orien1 == NORTH && orien2 == EAST  )
		){
			return this.component_borders[4];
		}
		if(
			( orien1 == EAST  && orien2 == SOUTH ) ||
			( orien1 == SOUTH && orien2 == EAST  )
		){
			return this.component_borders[5];
		}
		if(
			( orien1 == WEST  && orien2 == NORTH ) ||
			( orien1 == NORTH && orien2 == WEST  )
		){
			return this.component_borders[6];
		}
		if(
			( orien1 == WEST  && orien2 == SOUTH ) ||
			( orien1 == SOUTH && orien2 == WEST  )
		){
			return this.component_borders[5];
		}
		return this.undefined;
	}
	
	public short getWire(
			boolean east,
			boolean west,
			boolean north,
			boolean south,
			boolean hover,
			boolean under
	){
		byte x = 0;
		byte y = 0;
		byte z = 0;
		
		if(east)  x += 1;
		if(west)  x += 2;
		if(north) y += 1;
		if(south) y += 2;
		if(hover) z += 1;
		if(under) z += 2;
		
		return this.wires[x][y][z];
	}
}
