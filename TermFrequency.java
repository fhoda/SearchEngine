/** Class for creating and managing document id and term frequency pair objects to be stored in inverted index */
public class TermFrequency{
	private int docID;
	private int frequency;

	// public TermFrequency(){
	// 	docID = 0;
	// 	frequency = 0;
	// }

	/**
	*	Constructor
	*	@param docID int for document ID
	*	@param frequency int for storing and tallying the total term frequency of a document
	*/
	public TermFrequency(int docID, int frequency){
		this.docID = docID;
		this.frequency = frequency;
	}

	/**
	*	Accessor for docID
	*	@return docID
	*/
	public int getDocID(){
		return docID;
	}

	/**
	*	Accessor for frequency
	*	@return docID
	*/
	public int getFrequency(){
		return frequency;
	}

	/**
	*	Mutator for frequency
	*	@param f int to set the frequeny to
	*/
	public void setFequency(int f){
		frequency = f;
	}

	/**
	*	Mutator/Incrementor for frequency
	*	increment the frequeny by one.
	*/
	public void inrFreq(){
		frequency = frequency + 1;
	}

	/**
	*	Equals method to allow determining whether TermnFrequency object has the same docID
	*	For use by ArrayList contains() method.
	*	@param obj should be TermFrequency object to compare with
	*	@return boolean on whether it is equal or not
	*/
	public boolean equals(Object obj){
		TermFrequency tf = (TermFrequency) obj;
		if(docID==tf.getDocID()){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	*	overriden becaue equals method is overriden
	*/
	public int hashCode() {
    	return this.docID;
	}

	/**
	*	Custom toString for printing contents of TermFrequency object
	*	@return String containing docID and frequency pairs
	*/
	public String toString(){
		return docID+":"+frequency;
	}
}