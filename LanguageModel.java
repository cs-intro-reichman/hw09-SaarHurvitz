import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {
        In in = new In(fileName);
        String text = in.readAll();

        text = text.replace("\r", "");

        for (int i = 0; i < text.length() - windowLength; i++) {
            String window = text.substring(i, i + windowLength);
            char next = text.charAt(i + windowLength);

            List probs = CharDataMap.get(window);
            if (probs == null) {
                probs = new List();
                CharDataMap.put(window, probs);
            }
            probs.update(next);
        }

        for (List list : CharDataMap.values()) {
            calculateProbabilities(list);
        }
	}

    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list.
	public void calculateProbabilities(List probs) {              

    CharData[] chars = probs.toArray();
    int totalChars = 0;
    
    for (int i = 0; i < chars.length; i++) {
        totalChars += chars[i].count;
    }
    
    double prob = 0.0;
    
    for (int i = 0; i < chars.length; i++) {
        CharData cd = chars[i]; 
        
        cd.p = (double) cd.count / totalChars;
        
        prob += cd.p;
        cd.cp = prob;
    }
}

    // Returns a random character from the given probabilities list.
	char getRandomChar(List probs) {
        double r = randomGenerator.nextDouble();
        Node current = probs.getFirstNode();
        while (current != null) {
            if (current.cp.cp > r) {
                return current.cp.chr;
            }
            current = current.next;
        }
        return probs.getFirstNode().cp.chr;
	}

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
    public String generate(String initialText, int textLength) {
   if (initialText.length() < windowLength) {
        return initialText;
    }

    StringBuilder textGen = new StringBuilder(initialText);

    while (textGen.length() < textLength) {
        String window = textGen.substring(textGen.length() - windowLength);
        List probs = CharDataMap.get(window);
        
        if (probs == null) {
            break;
        }

        char nextChar = getRandomChar(probs);
        textGen.append(nextChar);
    }

    return textGen.toString();
}

    /** Returns a string representing the map of this language model. */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}

    public static void main(String[] args) {
		
    }
}
/*
____________36936936936936936
____________36936936936936936
____________369369369369369369
___________36936936936936933693
__________3693693693693693693693
_________369369369369369369369369
_________3693693693693693693693699
________3693693693693693693693699369
_______36936939693693693693693693693693
_____3693693693693693693693693693693636936
___36936936936936936936936936936___369369369
__36936___369336936369369369369________36936
_36936___36936_369369336936936__¶¶__¶¶
36933___36936__36936___3693636_¶¶¶¶¶¶¶¶
693____36936__36936_____369363_¶¶¶¶¶¶¶¶
______36936__36936______369369__¶¶¶¶¶¶
_____36936___36936_______36936___¶¶¶¶
_____36936___36936________36936___¶¶
_____36936___36936_________36936___11,
______369____36936__________369___11,
______________369________________11,
_______________________________11,
_____________________________11,
___________________________11,
________________________¶¶¶_¶¶¶
_______________________¶¶¶¶¶¶¶¶¶
_______________________¶¶¶¶¶¶¶¶¶
________________________¶¶¶¶¶¶¶
_________________________¶¶¶¶¶
__________________________¶¶¶
___________________________¶
______________________________11,
________________________________11,
__________________________________11,
___________________________________11,
___________________________________11,
__________________________________11,
_________________________________11,
_______________________________11,
___________________________¶¶__¶¶
__________________________¶¶¶¶¶¶¶¶
__________________________¶¶¶¶¶¶¶¶
___________________________¶¶¶¶¶¶
____________________________ ¶¶¶
_____________________________ ¶
____________________________11,
__________________________11,
_________________________11,
___________________________11,
_____________________________11,
________________________________11,
__________________________________11,
______________369___________________11,
______369____36936__________369_____11,
_____36936___36936_________36936___11,
_____36936___36936________36936___11,
_____36936___36936_______36936___11,
______36936__36936______369369 _¶¶_¶¶
693____36936__36936_____369363 ¶¶¶¶¶¶¶
36933___36936__36936___3693636 ¶¶¶¶¶¶¶
_36936___36936_369369336936936 _¶¶¶¶¶
__36936___369336936369369369369 _¶¶¶__3696
___36936936936936936936936936936 _¶_336939
_____36936936936936936936936936936936936
_______369369396936936936936936693693
________36936936936936936936999369
_________36936936936936936933699
_________3693693693693693369369
__________36936936936936993693
___________369369369369333693
____________3693693693699369
____________369369369366936
____________36936936936693
 */