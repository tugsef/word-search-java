package com.tugsef.worldlist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.tugsef.worldlist.business.abstracts.WordService;
import com.tugsef.worldlist.business.concretes.WordManager;

import lombok.NoArgsConstructor;

@Controller
@NoArgsConstructor
public class WordController {

	private WordService wordService = new WordManager();
	private Logger logger = LoggerFactory.getLogger(WordController.class);

	public void start() {
		long startTime = System.currentTimeMillis();
		this.wordService.startWordTxt();
		this.wordService.wordsDesc();
		long endTime = System.currentTimeMillis();
		logger.info("Toplam Okuma Yazma Geçen Süre :" + (endTime - startTime) / 1000 / 60 / 60 + " saat "
				+ (endTime - startTime) / 1000 / 60 + " dk " + ((endTime - startTime) / 1000) % 60 + " sn tamamlandı.");
	}

}
