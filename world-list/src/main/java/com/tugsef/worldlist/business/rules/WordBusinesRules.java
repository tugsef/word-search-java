package com.tugsef.worldlist.business.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class WordBusinesRules {

	public void sortWordAll(Map<String, Integer> wordCounts){
		
		List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCounts.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		int counter = 1;
		for (Map.Entry<String, Integer> entry : list) {
			System.out.println(counter + "." + entry.getKey() + ": " + entry.getValue());

			if (counter >= 10) {
				break;
			}
			++counter;

		}
	
	}
	
}
