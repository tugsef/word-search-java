
- WordService 

```java
package com.tugsef.worldlist.business.abstracts;

import java.util.List;

import com.tugsef.worldlist.entities.Word;
// Oluşturuldu
public interface WordService {

	List<Word> allWord();

	void startWordTxt();
	
	void wordsDesc();

}

```

-  WordManager
```java

package com.tugsef.worldlist.business.concretes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tugsef.worldlist.business.abstracts.WordService;
import com.tugsef.worldlist.business.rules.WordBusinesRules;
import com.tugsef.worldlist.entities.Word;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// Service oluşturuldu
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordManager implements WordService {

	private Logger logger = LoggerFactory.getLogger(WordManager.class);
	private WordBusinesRules businesRules = new WordBusinesRules();
	private Random random = new Random();

	@Override
	public List<Word> allWord() {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Word> worList = null;
		// wordList dosyası okunuyor. List olarak oluşturuldu.
		try {
			worList = objectMapper.readValue(new File("wordList.json"), new TypeReference<List<Word>>() {
			});

			logger.info("Created wordlist size: " + worList.size());
		} catch (IOException e) {
			logger.info(WordManager.class.getName() + e);

		}

		return worList;
	}

	@Override
	public void startWordTxt() {
		
		String filePathTxt= "wordList.txt";
		List<Word> words = allWord();
		// list uzunluğu kadar random değer ataması yapılacak
		int randomWord = random.nextInt(words.size() - 1);
		

		long startTime = System.currentTimeMillis();

		// Dosyanın maaksimum boyutu bayt değeri(4 GB)	
		long maksimumBoyut = 4294967296L;

		//Random değere göre dosyaya kelimeleri yazar.
		try {
			File filePath = new File(filePathTxt);

			if (!filePath.exists()) {
				filePath.createNewFile();
				logger.info("Created file: " + filePathTxt);
			}

			FileWriter fileWriter = new FileWriter(filePath, true);
			BufferedWriter writer = new BufferedWriter(fileWriter);

			long writteSize = 0;
			logger.info("Random ilk değer atandı(index): " + randomWord + "dosya yazılıyor...");
			while (writteSize < maksimumBoyut) {
				writer.write(words.get(randomWord) + "\n");
				writteSize = filePath.length();
				randomWord = random.nextInt(words.size() - 1);
			}
			logger.info("Random olarak değerler dosya ya yazıldı(İlk değer): " +"("+ words.get(randomWord)+")");
			writer.close();
			long endTime = System.currentTimeMillis();
			float fileSize = filePath.length() / (1024 * 1024 * 1024);
			logger.info(fileSize + " GB dosya oluşturuldu. " + (endTime-startTime) + " saniye de tamamlandı");
		} catch (IOException e) {
			logger.info(WordManager.class.getName() + e);
		}
		
	}

	@Override
	public void wordsDesc() {
		//Kelimelerin tekrarından kaçınmak için map kullandım.
		Map<String, Integer> wordCounts = null;
		List<String> wordAll = null;
		try {
			long startTime = System.currentTimeMillis();
			wordCounts = new TreeMap<>();
			BufferedReader reader = new BufferedReader(new FileReader("wordList.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] words = line.split("\\s+");
				wordAll = new ArrayList<String>();

				//kelimelerin başında ve sonundaki karakterler silinir.
				for (String word : words) {
					word = word.replaceAll("[.,=:;()-]", "");
					if (word.length() > 4)
						wordAll.add(word);

				}
				for (String word : wordAll) {
					word = word.toLowerCase();
					wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
				}

			}

			reader.close();
			long endTime = System.currentTimeMillis();
			logger.info("En çok kullanılan en çok kelimeler bulundu." + (endTime-startTime) + " sn de tamamlandı.");

			//Kelime tekrar sayısı azalana doğru
			this.businesRules.sortWordAll(wordCounts);
			

		} catch (IOException e) {
			logger.info(WordManager.class.getName() + e);
		}

	}

} 
```

- WordBusinesRules

```java
import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class WordBusinesRules {

	public void sortWordAll(Map<String, Integer> wordCounts){
		// Kelimeler ve tekrarlarını bulur ve ilk on değeri ekrana yazar
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


```

- WordController

```java
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

```
- Word

```java 
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
```
- WorldListApplication

```java

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
```
