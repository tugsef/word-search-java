package com.tugsef.worldlist.controller;

import com.tugsef.worldlist.business.abstracts.WordService;
import com.tugsef.worldlist.business.concretes.WordManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordController {

    
 
    private WordService wordService = new WordManager();
    
    public void start() {
    	this.wordService.startWordTxt();
    	this.wordService.wordsDesc();
    }
	
}
