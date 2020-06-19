package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size = 0;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		word = word.toLowerCase();
		TrieNode dopTrie = this.root;
		TrieNode saveTrie;
		char[] char_word = word.toCharArray();
		int i = 0;

		if (this.isWord(word))
			return (false);
		while (dopTrie != null && i < char_word.length)
		{
			if (dopTrie.getText().equals(word))
				return (false);
			saveTrie = dopTrie;
			dopTrie = dopTrie.getChild(char_word[i]);
			if (dopTrie == null)
			{
				saveTrie.insert(char_word[i]);
				dopTrie = saveTrie.getChild(char_word[i]);
			}
			i++;
		}
		if (dopTrie != null)
			dopTrie.setEndsWord(true);
		this.size++;
		return (true);
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return (this.size);
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
		TrieNode result;
		s = s.toLowerCase();
		result = this.findNodeByWord(s.toCharArray());
		if (result != null)
			return (result.endsWord());
		return (false);
	}

	public TrieNode findNodeByWord(char[] word)
	{
		TrieNode dop_trie = this.root;
		int i = 0;

		while (dop_trie != null && i < word.length)
		{
			dop_trie = dop_trie.getChild(word[i]);
			i++;
		}
		return (dop_trie);
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */

	 @Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
     	 TrieNode dop;
		 prefix = prefix.toLowerCase();
		 char[] word = prefix.toCharArray();
		 LinkedList<String> result = new LinkedList<>();

		 if (word.length == 0)
		 {
		 	 dop = this.root;
			 checkOneNode(result, dop, numCompletions);
			 this.recursiveSearch(result, dop, numCompletions);
		 }
		 else
		 {
			 dop = this.findNodeByWord(word);
			 checkOneNode(result, dop, numCompletions);
			 this.recursiveSearch(result, dop, numCompletions);
		 }
		 return (result);
     }

     public void recursiveSearch(LinkedList<String> list, TrieNode node, int numCompletions)
	 {
	 	char c;

	 	if (list.size() >= numCompletions)
	 		return ;
	 	if (node == null)
	 		return ;
	 	for(c = 'a'; c <= 'z'; ++c)
	 	{
			checkOneNode(list, node.getChild(c), numCompletions);
		}
		 if (list.size() >= numCompletions)
			 return ;
		 for(c = 'a'; c <= 'z'; ++c)
		 {
			 recursiveSearch(list, node.getChild(c), numCompletions);
		 }
	 }

	 public void checkOneNode(LinkedList<String> list, TrieNode node, int numCompletions)
	 {
		 if (list.size() >= numCompletions)
			 return ;
		 if (node == null)
			 return ;
		 if (node.endsWord())
		 {
			 list.add(node.getText());
		 }
	 }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}