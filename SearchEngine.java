/**
*	Faisal Hoda and Paul Nathan
*	Homework 5
*	11/21/14
*/

import java.util.*;
import java.io.*;


/** 
*	Search Engine with BM25 ranking - To use "java SearchEngine <query>" 
*	Program will print rankings to the terminal so you may opt to send it to a file when
*	you execute.
*	Note: You must have tccopus.txt in the same directory as this class
*/
public class SearchEngine{

	public static void main(String[] args)throws FileNotFoundException, IOException{

		HashMap<String, ArrayList<TermFrequency>> invertedIndex = new HashMap<String, ArrayList<TermFrequency>>();
		ArrayList<Integer> docLengths = new ArrayList<Integer>(); // index number the docID and value is the document length
		
		buildIndex("tccorpus.txt", invertedIndex, docLengths); // Build inverted index and get/store doc lenghts

		/* Get user query and store as a single string*/
		/*String query = "";
		for(int i = 0; i<args.length; i++){
			query = query + " " + args[i];
		}

		bm25(query, invertedIndex, docLengths);
		*/
		// removed the above code block in order take in queries from the queries.txt file.

		// get block of queries from queries.txt, place it in an array of size 7.

		String[] query_array = new String[7];
		FileReader file = new FileReader("queries.txt");
		BufferedReader reader = new BufferedReader(file);

		Scanner scan;

		int index = 0;
		while (reader.ready())
		{
			// removes the first 
			String line = reader.readLine();
			line = line.substring(line.indexOf(" "));
			query_array[index] = line;
			index++;
			if(index >= 7)
			{
				// expecting 7 queries for this assignment. If more is provided they will not be accepted.
				break;
			}
		}

		file.close();
		reader.close();

		// now we have all of the queries. Let's run it through the bm25 index.

		for(int x = 0; x < 7; x++)
		{ // going to have to change the bm25 functino in order to print out to a sepratef ile so the screen isn't flooded with results.
			String docName = Integer.toString(x) + " query.txt";
			bm25(query_array[x], invertedIndex, docLengths, docName);
		}

		// Set<String> keys = invertedIndex.keySet();
		// for(String key : keys){
		// 	ArrayList pairs = invertedIndex.get(key);
		// 	System.out.println(key + " - " + pairs.toString()+ " ");
		// }

		// System.out.println("\n");
		// System.out.println("Document Lengths:");
		// 	for(int i=1; i<docLengths.size(); i++){
		// 		System.out.println(i+":"+docLengths.get(i));
		// 	}
	}
	/**
	*	Method to read file and create an inverted index of terms and document length table
	*	@param fileName String name of file to index
	*	@param invertedIndex Hashtable for storing inverted index
	*	@param docLengths ArrayList for storing document lenghts
	*/
	public static void buildIndex(String fileName, HashMap<String, ArrayList<TermFrequency>> invertedIndex, ArrayList docLengths)throws FileNotFoundException, IOException{
		FileReader file = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(file);
		// HashMap<String, ArrayList<TermFrequency>> invertedIndex = new HashMap<String, ArrayList<TermFrequency>>();

		Scanner scan;	
		int docID = 0; 		// For storing document ID throughout the loop.
		int docLength = 0; 	//	For tallying document length throughout the loop.
		ArrayList<TermFrequency> termfrequencies; // Store docID-tf pairs for each term in the hashtable invertedIndex	
		// ArrayList<Integer> docLengths = new ArrayList<Integer>();


		// System.out.println("Initialized variables and now building Index...");
		
		

		/* 	
		*	Loop to create invertedIndex.
		*	Read each line of the file. If it has a '#' in it then the next token is the docID
		*	Else, see if the term is in the index already
		*		If it is then see if the docID is in the corresponding list (array).
		*			If it is not in the list then add it as an object containing docID-tf pair
		*			Else, increment the frequency of that document at that term
		*			Finally inrement the docLength to tally the processed term
		*		Else, add the term and docID-tf(1) pair object to the inverted index and incement docLength
		*/
		while(reader.ready()){
			String line = reader.readLine();
			scan = new Scanner(line);
			// System.out.println(line);
			while(scan.hasNext()){
				String term = scan.next();
				// System.out.println(term);
				if(term.equals("#")){
					docLengths.add(docID, docLength);
					term = scan.next();
					docID = Integer.parseInt(term);
					// System.out.println(docID);
					docLength = 0;
				} else if(invertedIndex.containsKey(term)){
					termfrequencies = invertedIndex.get(term);
					TermFrequency id = new TermFrequency(docID, 0);
					if(termfrequencies.contains(id)==false){
						TermFrequency tf = new TermFrequency(docID, 1);
						termfrequencies.add(tf);
					} else{
						// System.out.println("in the else");
						int index = termfrequencies.indexOf(id);	
						// System.out.println(index);
						termfrequencies.get(index).inrFreq();
					}
					docLength = docLength+1;
				} else{
					termfrequencies = new ArrayList<TermFrequency>();
					TermFrequency tf = new TermFrequency(docID, 1);
					termfrequencies.add(tf);
					invertedIndex.put(term, termfrequencies);
					docLength = docLength+1;
				}
			}
		}
		
		docLengths.add(docID, docLength); // add last document and document length to array

		// System.out.println("index has been built");

		// Set<String> keys = invertedIndex.keySet();
		// for(String key : keys){
		// 	ArrayList pairs = invertedIndex.get(key);
		// 	System.out.println(key + " - " + pairs.toString()+ " ");
		// }

		// System.out.println("\n");
		// System.out.println("Document Lengths:");
		// 	for(int i=1; i<docLengths.size(); i++){
		// 		System.out.println(i+":"+docLengths.get(i));
		// 	}
	}

	/**
	*	Compute BM25 scores/rankings
	*	@param query String query to be ranked for
	*	@param invertedIndex HashMap of inverted index precomputed to use for calculating scores
	*	@param docLenghts ArrayList of document lenghts - index number corresponds to doc ID.
	*/
	public static void bm25(String query, HashMap<String, ArrayList<TermFrequency>> invertedIndex, ArrayList docLengths, String outputFile){
		
		/* Variables for computing BM25 score*/
		double k1 = 1.2;
		double k2 = 100;
		double b = 0.75;
		int dl=1;
		
		/* Calculate average document legnth in corpus - used for computing score*/
		double avdl=0;
		for(int i = 1; i<docLengths.size(); i++){
			int length = (Integer)docLengths.get(i);
			avdl = avdl + length;
		}
		avdl = avdl/docLengths.size();
		/*more variables for computing score*/
		double bigK = k1*((1-b)+b*(dl/avdl));
		double n;
		double bigN = (double) docLengths.size();
		double qf = 1;
		double score;

		Scanner scanQuery = new Scanner(query);
		// int termCount = 0;

		ArrayList<BM25> rankings = new ArrayList<BM25>(); // For storing score objects
		
		/* Compute score of each document for each term */
		while(scanQuery.hasNext()){	
			// termCount++;
			String term = scanQuery.next();
			// invertedLists.put(term, invertedIndex.get(term));
			ArrayList<TermFrequency> termfrequencies = invertedIndex.get(term);
			n = termfrequencies.size();
			for(int i = 0; i<termfrequencies.size(); i++){
				int docID = termfrequencies.get(i).getDocID();
				int f = termfrequencies.get(i).getFrequency();
				dl = (Integer)docLengths.get(docID);

				score = Math.log((1/((n+0.5)/(bigN-n+0.5))))*(((k1+1)*f)/(bigK+f))*(((k2+f)*qf)/(k2+qf));
				BM25 id = new BM25(docID, 0);
				if(rankings.contains(id)){
					int index = rankings.indexOf(id);
					score = score + rankings.get(index).getScore();
					rankings.add(index, new BM25(docID, score));

				} else{	
					rankings.add(new BM25(docID, score));
				}	
			}
		}

		Collections.sort(rankings);

		try
		{
			PrintWriter writer = new PrintWriter(outputFile, "UTF-8");

			//instantiating rankings.size() outside of the scope of the forloop so it's not calling that function 100 times within the loop. boom. knowledge.
			int rank_size = rankings.size();
			int limit = rank_size-101;
			int top_100_count = 1;
			for(int j = rank_size-1; j>=limit; j--)
			{
				writer.print(top_100_count + "\t");
				writer.println(rankings.get(j).toString());
				top_100_count++;
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// Don't want to print out all the results. Print it to teh specified
		//for(int j = rankings.size()-1; j>=0; j--){
		//	System.out.println(rankings.get(j).toString());
		//} 
	}
}