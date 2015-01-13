/**
 * @author Daniel Wester
 */

import java.io.*;
import java.util.*;

public class Dictionary implements DictionaryADT {

	private String fileLocation;
	private Hashtable<String, WordADT> hash;

	/**
	 * Default constructor. Requires a file to given to build from / to.
	 * 
	 * @param txt File location to be used.
	 * @throws FileNotFoundException Thrown if a file cannot be found.
	 */
	public Dictionary(String txt) throws FileNotFoundException {
		hash = new Hashtable<String, WordADT>(400);
		fileLocation = txt;

		Scanner scanner = new Scanner(new File(fileLocation));

		while(scanner.hasNext()){
			add(scanner.next(),scanner.next() + scanner.nextLine());
		}
	}

	public boolean add(String name, String description) {
		WordADT word = new Word(name, description);
		hash.put(name.toLowerCase(), word);
		return true;
	}

	public boolean remove(String name) {
		WordADT word = hash.remove(name.toLowerCase());
		if (word == null)
			return false;
		return true;
	}

	public String lookup(String word) {

		long startTime = System.nanoTime();
		WordADT worked = hash.get(word.toLowerCase());
		long  iterationTime = System.nanoTime() - startTime;

		if (worked == null)
			return "Word is not in the list.";
		
		String output = (worked.getWord() + " - " + worked.getDescription() + "\n"
				+ "It took " + iterationTime + " nanoseconds to find this.");

		return output;
	}

	public String list() {
		Collection<WordADT> l = hash.values();
		Iterator<WordADT> iter = l.iterator();
		String list = "";
		WordADT word;

		while(iter.hasNext()){
			word = iter.next();
			list += word.getWord() + " - " + word.getDescription() + "\n";
		}

		return list;
	}

	public void save() throws IOException {

		FileWriter filewrite = new FileWriter(fileLocation);
		BufferedWriter bw = new BufferedWriter(filewrite);

		WordADT word;
		Collection<WordADT> list = hash.values();
		Iterator<WordADT> iter = list.iterator();

		while (iter.hasNext()){
			word = iter.next();
			String next = (word.getWord() + " " + word.getDescription());
			bw.write(next);
			if(iter.hasNext())
				bw.newLine();
		}

		bw.close();
	}

}
