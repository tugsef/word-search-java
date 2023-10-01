package com.tugsef.worldlist.business.abstracts;

import java.util.List;

import com.tugsef.worldlist.entities.Word;

public interface WordService {

	List<Word> allWord();

	void startWordTxt();
	
	void wordsDesc();

}
