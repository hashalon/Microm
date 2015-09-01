package main;

import config.Config;

public abstract class Main {

	public static Config config;
	
	public static void main(String[] args) {
		config = new Config("");
	}

}
