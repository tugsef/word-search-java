
# İş Görüşmesi Mülakat Sorusu ve Cevabı(Java)
1. NodeJS yada java ile https://raw.githubusercontent.com/bilalozdemir/tr-word-list/master/files/words.json buradaki listede bulunan kelimeleri tamamen random kullanarak 4gb büyüklüğünde bir txt dosyası oluştur
2. 4GB büyüklüğündeki dosyada en çok geçen 10 kelimeyi bul. Bu adımda kelimenin karakter sayısı 4'ten büyük  çok sık kullanılan bağlaçları kolayca filtrelemiş olur.

```log
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.4)

2023-10-02T11:55:59.113+03:00  INFO 2489 --- [           main] c.tugsef.worldlist.WorldListApplication  : Starting WorldListApplication using Java 17.0.8 with PID 2489 (/Users/sefademirtas/Desktop/worldList/world-list/target/classes started by sefademirtas in /Users/sefademirtas/Desktop/worldList/world-list)
2023-10-02T11:55:59.114+03:00  INFO 2489 --- [           main] c.tugsef.worldlist.WorldListApplication  : No active profile set, falling back to 1 default profile: "default"
2023-10-02T11:55:59.516+03:00  INFO 2489 --- [           main] c.tugsef.worldlist.WorldListApplication  : Started WorldListApplication in 0.563 seconds (process running for 0.722)
2023-10-02T11:55:59.690+03:00  INFO 2489 --- [           main] c.t.w.business.concretes.WordManager     : Created wordlist size: 92406
2023-10-02T11:55:59.690+03:00  INFO 2489 --- [           main] c.t.w.business.concretes.WordManager     : Created file: wordList.txt
2023-10-02T11:55:59.690+03:00  INFO 2489 --- [           main] c.t.w.business.concretes.WordManager     : Random ilk değer atandı(index): 72815 dosya yazılıyor...
2023-10-02T11:58:21.985+03:00  INFO 2489 --- [           main] c.t.w.business.concretes.WordManager     : Random olarak değerler dosya ya yazıldı(İlk değer): (kuvve : Düşünce, niyet. Bir devletin silahlı kuvvetlerinin durumu veya gücü. Yeti. )
2023-10-02T11:58:21.988+03:00  INFO 2489 --- [           main] c.t.w.business.concretes.WordManager     : 4.0 GB dosya oluşturuldu. 0 saat 2 dk 22 sn tamamlandı.
2023-10-02T12:04:32.567+03:00  INFO 2489 --- [           main] c.t.w.business.concretes.WordManager     : En çok kullanılan en çok kelimeler bulundu.0 saat 6 dk 10 sn tamamlandı.
           Sıra          Kelime   Tekrar Sayısı
             1         durumu           3597683
             2          kimse           2978002
             3     kullanılan           2559100
             4          olmak           2294841
             5          etmek           2231271
             6       bulunmak           1782450
             7         olarak           1527319
             8      anlamında           1335877
             9        olmayan           1308213
            10         duruma           1256157

2023-10-02T12:04:32.615+03:00  INFO 2489 --- [           main] c.t.worldlist.controller.WordController  : Toplam Okuma Yazma Geçen Süre :0 saat 8 dk 33 sn tamamlandı.
```




- WordService 

```java
import java.util.List;

import com.tugsef.worldlist.entities.Word;

public interface WordService {

	List<Word> allWord();

	void startWordTxt();
	
	void wordsDesc();

}

```

-  WordManager
```java

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
import lombok.NoArgsConstructor;

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

		String filePathTxt = "wordList.txt";
		List<Word> words = allWord();
		int randomWord = random.nextInt(words.size() - 1);

		long startTime = System.currentTimeMillis();
		long maksimumBoyut = 4294967296L;
		try {
			File filePath = new File(filePathTxt);

			if (!filePath.exists()) {
				filePath.createNewFile();
				logger.info("Created file: " + filePathTxt);
			}

			FileWriter fileWriter = new FileWriter(filePath, true);
			BufferedWriter writer = new BufferedWriter(fileWriter);

			long writteSize = 0;
			logger.info("Random ilk değer atandı(index): " + randomWord + " dosya yazılıyor...");
			while (writteSize < maksimumBoyut) {
				writer.write(words.get(randomWord) + "\n");
				writteSize = filePath.length();
				randomWord = random.nextInt(words.size() - 1);
			}
			logger.info("Random olarak değerler dosya ya yazıldı(İlk değer): " + "(" + words.get(randomWord) + ")");
			writer.close();
			long endTime = System.currentTimeMillis();
			float fileSize = filePath.length() / (1024 * 1024 * 1024);
			logger.info(fileSize + " GB dosya oluşturuldu. " + (endTime - startTime) / 1000 / 60 / 60 + " saat "
					+ (endTime - startTime) / 1000 / 60 + " dk " + ((endTime - startTime) / 1000) % 60
					+ " sn tamamlandı.");
		} catch (IOException e) {
			logger.info(WordManager.class.getName() + e);
		}

	}

	@Override
	public void wordsDesc() {
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
			logger.info("En çok kullanılan en çok kelimeler bulundu." + (endTime - startTime) / 1000 / 60 / 60
					+ " saat " + (endTime - startTime) / 1000 / 60 + " dk " + ((endTime - startTime) / 1000) % 60
					+ " sn tamamlandı.");
			this.businesRules.sortWordAll(wordCounts);

		} catch (IOException e) {
			logger.info(WordManager.class.getName() + e);
		}

	}

}

```

- WordBusinesRules

```java

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


```

- WordController

```java
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

```
- Word

```java 
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
