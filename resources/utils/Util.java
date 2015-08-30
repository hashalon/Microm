package utils;

import java.awt.Color;

public abstract class Util {

	public static final Color TRANSPARENT = new Color(0,true);
	
	public static final String[] HEXA_PREFIX = {
		"#X", "#x", "0X", "0x", "#", "&h", "0h"
	};
	
	public static final String[] OCTAL_PREFIX = {
		"#O", "#o", "0O", "0o", "&O"
	};
	
	public static final String[] BINARY_PREFIX = {
		"#B", "#b", "0B", "0b"
	};
	
	public static short parseShort( String sequence ){
		int value = parse(sequence);
		if( value > -1 ){
			return (short) value;
		}
		return (short) 0;
	}
	
	public static Color parseColor( String sequence ){
		int value = parse(sequence);
		if( value > -1 ){
			return new Color(value);
		}
		return TRANSPARENT; // we return transparent
	}
	
	/**
	 * Return the parsed unsigned integer
	 * return -1 if it failed
	 * */
	public static int parse( String sequence ){
		int value;
		
		// we test hexa prefixes
		value = parseWithBase(sequence, (byte) 16, HEXA_PREFIX);
		if( value != -1 ) return value;
		
		// we test octal prefixes
		value = parseWithBase(sequence, (byte) 8, OCTAL_PREFIX);
		if( value != -1 ) return value;
		
		// we test binary prefixes
		value = parseWithBase(sequence, (byte) 2, BINARY_PREFIX);
		if( value != -1 ) return value;
		
		// we test decimal prefixes
		try{
			value = Integer.parseInt(sequence);
		}catch( NumberFormatException e ){
			value = -1;
		}
		return value;
	}
	
	private static int parseWithBase( String sequence, byte base, String[] prefixes ){
		int value = -1;
		for( byte i=0; i<prefixes.length; ++i ){
			if( sequence.startsWith(prefixes[i]) ){
				try{
					value = Integer.parseInt(
							sequence.substring( prefixes[i].length() ),
							base
					);
				}catch( NumberFormatException e ){
					return -1;
				}
			}
		}
		return value;
	}
	
}
