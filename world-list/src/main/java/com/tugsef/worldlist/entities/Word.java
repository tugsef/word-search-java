package com.tugsef.worldlist.entities;

import java.util.List;

import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Controller
public class Word {

	private String word;
	private List<String> meanings;

	@Override
	public String toString() {
		String meaning = "";

		for (String string : meanings) {
			meaning += string + ". ";
		}
		return word + " : " + meaning;
	}
}
