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
		
		long maksimumBoyut = 4294967296L;
		
		long startTime = System.currentTimeMillis();
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
