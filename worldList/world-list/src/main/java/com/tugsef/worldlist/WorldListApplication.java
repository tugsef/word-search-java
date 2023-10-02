package com.tugsef.worldlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tugsef.worldlist.controller.WordController;

@SpringBootApplication
public class WorldListApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldListApplication.class, args);
		WordController controller = new WordController();
		controller.start();		

	}

}
