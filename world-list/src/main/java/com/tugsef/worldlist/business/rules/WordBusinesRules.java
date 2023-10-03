package com.tugsef.worldlist.business.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WordBusinesRules {
	

	
	public void sortWordAll(Map<String, Integer> wordCounts) {

		Formatter fmt = new Formatter();

		List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCounts.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});	
	   
		fmt.format("%15s  %15s\n", "Kelime", "Tekrar Sayısı");
		 list.stream().limit(10).forEach(entry -> {			
				fmt.format("%14s  %17s\n", entry.getKey(), entry.getValue());		
		 });;

		System.out.println(fmt);

	}

}
