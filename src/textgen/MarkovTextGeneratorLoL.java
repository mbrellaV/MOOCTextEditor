package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		ListNode	newNode;

		this.wordList = new LinkedList<ListNode>();
		String[] allWords = sourceText.split("\\s+");
		for (int i = 0; i < allWords.length - 1; i++)
		{
			if ((newNode = FindWord(allWords[i])) == null)
			{
				newNode = new ListNode(allWords[i]);
				this.wordList.add(newNode);
			}
			newNode.addNextWord(allWords[i + 1]);
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords)
	{
		StringBuilder result = new StringBuilder();
		ListNode word;
		String		GeneratedWord;

		word = this.wordList.get(0);
		for (int i = 0; i < numWords; i++)
		{
			GeneratedWord = word.getRandomNextWord(this.rnGenerator);
			if (GeneratedWord == null)
				break ;
			result.append(GeneratedWord);
			result.append(" ");
			word = FindWord(GeneratedWord);
			if (word == null)
				break ;
		}
		return (result.toString());
	}
	
	private ListNode FindWord(String wordToFind)
	{
		for (ListNode node : wordList)
		{
			if (node.getWord().equals(wordToFind))
				return (node);
		}
		return (null);
	}
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		ListNode	newNode;

		this.wordList = new LinkedList<ListNode>();
		String[] allWords = sourceText.split("\\s+");
		for (int i = 0; i < allWords.length - 1; i++)
		{
			if ((newNode = FindWord(allWords[i])) == null)
			{
				newNode = new ListNode(allWords[i]);
				wordList.add(newNode);
			}
			newNode.addNextWord(allWords[i + 1]);
		}
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen + "//\n//");
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
	    // The random number generator should be passed from
	    // the MarkovTextGeneratorLoL class
		//System.out.print(this.nextWords.size());
		if (this.nextWords.size() < 1)
			return (null);
	    return (this.nextWords.get(generator.nextInt(this.nextWords.size())));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


