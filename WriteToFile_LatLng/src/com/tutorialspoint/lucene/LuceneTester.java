package com.tutorialspoint.lucene;




import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;

import java.nio.file.Files;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;




public class LuceneTester {

	String indexDir = "C:\\Lucene\\Index"; // Lucene için gerekli olan verilerin
											// tuttulduðu klasör.
	String dataDir = "C:\\Lucene\\Data"; // Mevcut resimlerin koordinat
											// bilgilerinin tutulduðu klasör.
	String outputDir = "C:\\Lucene\\Output"; // Arama sonrasýnda çakýþan
												// resimlerin toplanacaðý
												// klasör.
	Indexer indexer;
	Searcher searcher;
	public static String QminX = "0", QminY = "0", QmaxX = "0", QmaxY = "0"; // Girilen
																 // koordinatlarýmýz.
	String secilialan = "Alan seçilmedi";
	static String input_url="C:\\Lucene\\Input\\input.txt";
	
	public static void main(String[] args) throws IOException {
		
		String data=GetLatLng(input_url);
		String[] split_data=new String[4];
		split_data=data.split("-");
		QminX =split_data[0];
		QmaxY =split_data[1];
		QmaxX =split_data[2];
		QminY =split_data[3];
		
		System.out.println(QminX+"-"+QmaxY+"-"+QmaxX+"-"+QminY);
		
		LuceneTester tester;
		try {
		 
			tester = new LuceneTester(); // Lucene algoritmasý için nesne
											// tanýlmanýyor.
			tester.createIndex();
			tester.search("00"); // Tüm dosyalarýn search edilip bufferlanmasý
									// saðlanýyor.

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		//try {
		//	String urlmm= getUrlSource("file:///C://Users//saki//Desktop//LuceneFirstApplication//src//com//tutorialspoint//lucene//map.html");
		//	System.out.println(urlmm);
		//} catch (IOException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
	}

	private void createIndex() throws IOException {
		indexer = new Indexer(indexDir);
		int numIndexed;
		long startTime = System.currentTimeMillis();
		numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
		long endTime = System.currentTimeMillis();
		indexer.close();
		System.out.println(numIndexed + " File indexed, time taken: "
				+ (endTime - startTime) + " ms");
		
	}

	private void search(String searchQuery) throws IOException, ParseException {

		String readed_line;
		String[] splited_line;
		ArrayList<String> kesisen_dosyalar = new ArrayList<String>();
		ArrayList<String> dosya_adlari = new ArrayList<String>();

		Double minX, minY, maxX, maxY;

		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(searchQuery);

		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time :"
				+ (endTime - startTime));
		for (ScoreDoc scoreDoc : hits.scoreDocs) {

			Document doc = searcher.getDocument(scoreDoc);

			FileReader in = new FileReader(doc.get(LuceneConstants.FILE_PATH)
					.toString());
			BufferedReader br = new BufferedReader(in);
			readed_line = br.readLine();

			splited_line = readed_line.split("-");
			minX = Double.parseDouble(splited_line[0]);
			maxY = Double.parseDouble(splited_line[1]);
			maxX = Double.parseDouble(splited_line[2]);
			minY = Double.parseDouble(splited_line[3]);

			// Saðlanmasý gereken koþul => if (!(QminX>roo.maxX) && !(roo.minX>
			// QmaxX) && !(QminY>roo.maxY) && !(roo.minY> QmaxY))
			if (!(Double.parseDouble(QminX) > maxX)) {
				if (!(Double.parseDouble(QmaxX) < minX)) {
					if (!(Double.parseDouble(QminY) > maxY)) {
						if (!(Double.parseDouble(QmaxY) < minY)) {
							kesisen_dosyalar.add(doc
									.get(LuceneConstants.FILE_PATH));
							dosya_adlari.add(outputDir + "\\"
									+ doc.get(LuceneConstants.FILE_NAME));
						}
					}
				}

			}

			in.close();
		}

		try {
			for (int i = 0; i < kesisen_dosyalar.size(); i++) {

				File datad = new File(kesisen_dosyalar.get(i).toString());
				File outd = new File(dosya_adlari.get(i).toString());

				Files.copy(datad.toPath(), outd.toPath());
				System.out.println("Dosyalar " + outd.toString()
						+ " adresine kopyalandý.");

			}
		} catch (Exception e) {

			System.out
					.println(e.getMessage()
							+ " Hatasý oldu.\n Muhtemelen kopyalanacak dosya o klasörde mevcut.Lütfen Kontrol Ediniz.");

		}

		searcher.close();

	}

	public static String GetLatLng(String input_url) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(input_url));
		String everything;
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        everything = sb.toString();
	        
	    } finally {
	        br.close();
	    }
		
		return everything;
	}
	
}
