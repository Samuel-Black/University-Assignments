/*Samuel Ian Black
  SIB979
  6025821
  CSCI203
  Assignment 1
*/

#include <iostream>
#include <string>
#include <fstream>

using namespace std;

struct Node//This is the BST I use for sorting and eliminating multiples of words found in sample.txt
{
	string key;
	Node* left;
	Node* right;
};
//Methods associated with the Node tree, all used in step 3.
void insert(string key, Node* node);
Node* insertFirst(string key);
void visit(Node* node);

struct AnagramNode//This is the BST I use to store all prime values of words in the Dictionary for finding anagrams. Idea for prime numbers to find algorithms from https://hackernoon.com/an-algorithm-for-finding-anagrams-2fe7655de85b
{
	double key;
	int count;
	string anagrams[20];
	AnagramNode* left;
	AnagramNode* right;
};
//Methods associated with the AnagramNode tree, all used in step 4.
AnagramNode* anagramInsertFirst(double key, string anagram);
void anagramInsert(double key, AnagramNode* node, string anagram);
void findAnagrams(double key, AnagramNode* node, string anagram, int& anagramPrintCount);

//Arrays + counters
string dictionary[400000];
int foundPalindromes[400000];//I use this in part 2 to store the locations of found palindromes so that I don't need to search again.
string sampleWordsArray[10000];//Array used to store all unique words from the sample.txt found in the dictionary
int wordCount = 0;
int numOfUniqueWords = 0;
int numOfValidWords = 0;
int numOfUniqueWordsInDictionary = 0;
int numOfAnagrams = 0;
string wordWithMostAnagrams;
int lengthOfWordWithAnagrams;
string longestWordWithAnagrams;
int numOfWordsWithAnagrams;
int totalNumberOfAnagrams;

void dictionaryInsert(string word);

//Methods involved in step 2.
void binaryPalindrome();
void reverseStr(string& word1, char* word2, int n);

//Methods involved in step 3.
bool binarySearchValidWords(string& key, string thisArray[]);
void lowerCase(string& word);

int assignPrimeNumbers(char thisChar);


int main()
{
	//Read from dictionary
	string word;
	ifstream inFile; 
	inFile.open("dictionary-v2.txt");
	if (!inFile)
	{
		exit(1);
	}
	while (inFile >> word)
	{
		dictionaryInsert(word);
	}
	inFile.close();

	/*********Part 2.*********/

	binaryPalindrome();

	/*********Part 3.*********/

	Node* root = NULL;//I use a BST for step 3 since it doesn't add duplicates, and puts the data in order for when I store it in the array.
	string sampleWord;
	inFile.open("sample.txt");

	inFile >> sampleWord;
	lowerCase(sampleWord);
	root = insertFirst(sampleWord);
	numOfValidWords++;

	while (inFile >> sampleWord)
	{
		lowerCase(sampleWord);
		if (sampleWord.length() > 0)
		{
			numOfValidWords++;
			insert(sampleWord, root);
		}
	}
	inFile.close();

	visit(root);//Call tree in ascending order and binary search for words in the dictionary, then store them in a string array.

	cout << "Valid Words: " << numOfValidWords << endl;
	cout << "Unique Words: " << numOfUniqueWords << endl;
	cout << "Unique Words in Dictionary: " << numOfUniqueWordsInDictionary << endl;

	delete root;

	/*********Part 4.*********/
	double clo1 = clock();

	double factor = 1;
	for (int j = 0; j < dictionary[0].length(); j++)
	{
		int charFactor = assignPrimeNumbers(sampleWordsArray[0][j]);
		factor = factor * charFactor;
	}

	AnagramNode* aRoot = NULL;//I calculate and store all the values of dictionary in a string array inside a BST, sorted by their prime values.

	aRoot = anagramInsertFirst(factor, dictionary[0]);

	for (int i = 1; i < wordCount; i++)
	{
		factor = 1;
		int length = dictionary[i].length();
		for (int j = 0; j < length; j++)//Loop to calc prime value for each char in string, then sum
		{
			int charFactor = assignPrimeNumbers(dictionary[i][j]);
			factor = factor * charFactor;
		}
		anagramInsert(factor, aRoot, dictionary[i]);
	}

	int anagramPrintCount = 0;
	for (int i = 0; i < numOfUniqueWordsInDictionary; i++)//I loop through and calculate the prime values of all the unique words found in the dictionary, and search the previously made BST then print the string array if found.
	{
		factor = 1;
		int length = sampleWordsArray[i].length();
		for (int j = 0; j < length; j++)//Loop to calc prime value for each char in string, then sum
		{
			int charFactor = assignPrimeNumbers(sampleWordsArray[i][j]);
			factor = factor * charFactor;
		}

		findAnagrams(factor, aRoot, sampleWordsArray[i], anagramPrintCount);
	}
	double clo2 = clock();

	clo1 = clo1 / CLOCKS_PER_SEC;
	clo2 = clo2 / CLOCKS_PER_SEC;

	cout << "It took " << (clo2 - clo1) << " seconds to construct the BST and find all possible anagrams." << endl;

	cout << "The word with the most anagrams is: " << wordWithMostAnagrams << " with " << numOfAnagrams << " anagrams." << endl;
	cout << "The longest word with anagrams is: " << longestWordWithAnagrams << " at " << lengthOfWordWithAnagrams << " characters." << endl;
	cout << "The total number of words with anagrams is: " << numOfWordsWithAnagrams << endl;
	cout << "The total number of anagrams found is: " << totalNumberOfAnagrams << endl;

	delete aRoot;

	return 0;
}


void dictionaryInsert(string word)
{
	if (word != "")
	{
		dictionary[wordCount] = word;
		wordCount++;
	}
	else
	{
		return;
	}
}


/***Methods for part 2.***/

void binaryPalindrome()//https://www.geeksforgeeks.org/binary-search-a-string/
{
	char word2[35];
	int longestPalindrome = 0;
	int longestPalindromeLocation = 0;

	const int cMaxPals = 10;
	int count = 0;
	cout << "First 10 palindromes:" << endl;

	double clo1 = clock();
	double clo2 = 0;

	for (int i = 0; i < wordCount; i++)
	{
		int length = dictionary[i].length();
		int start = 0;
		int end = wordCount;

		if (length > 1)
		{
			reverseStr(dictionary[i], word2, length);
			if (word2 < (dictionary[i]))//Break loop before searching if dictionary[i] has already iterated since the palindrome has already been found.
			{
				if (word2 == dictionary[foundPalindromes[i]])//I record the position of the palindrome in validPalindromes int array so search can be O(1).
				{
					if (count < cMaxPals)
					{
						cout << dictionary[i] << " : " << dictionary[foundPalindromes[i]] << endl;
						count++;
					}
					continue;
				}
				continue;
			}
		}
		else
			continue;

		while (start <= end)
		{
			int middle = (start + end) / 2;
			if (word2 > (dictionary[middle]))
			{
				start = middle + 1;
			}
			else if (word2 == (dictionary[middle]))
			{									   
				foundPalindromes[middle] = i;	   //I record the position of the valid palindrome.
				if (count < 10)
				{
					cout << dictionary[i] << " : " << word2 << endl;
					count++;
				}
				if (length > longestPalindrome)
				{
					longestPalindrome = length;
					longestPalindromeLocation = i;
				}
				break;
			}
			else
			{
				end = middle - 1;
			}
		}

	}
	clo2 = clock();
	clo1 = clo1 / CLOCKS_PER_SEC;
	clo2 = clo2 / CLOCKS_PER_SEC;
	cout << "The longest palindrome in the dictionary is " << dictionary[longestPalindromeLocation].length() << " characters long: " << dictionary[longestPalindromeLocation] << endl;
	cout << "It took " << (clo2 - clo1) << " seconds to find all palindromes in the dictionary." << endl;
}

void reverseStr(string& word1, char* word2, int n)
{
	int i, j;
	for (i = 0, j = n - 1; i < n; i++, j--)
	{
		word2[i] = word1[j];
	}
	word2[i] = '\0';
}


/***Methods for part 3.***/

bool binarySearchValidWords(string& key, string thisArray[])//Search dictionary to see if word is present, add to array if it is.
{
	int start = 0;
	int end = wordCount;

	while (start <= end)
	{
		int middle = (start + end) / 2;

		if (key > (dictionary[middle])) { start = middle + 1; }

		else if (key == (dictionary[middle]))
		{
			thisArray[numOfUniqueWordsInDictionary] = key;
			numOfUniqueWordsInDictionary++;
			return true;
		}

		else { end = middle - 1; }
	}
	return false;
}

void lowerCase(string& word)
{
	for (int i = 0; i < word.length(); i++)
	{
		if (word[i] >= 'A' && word[i] <= 'Z')
		{
			word[i] = word[i] + 32;
		}
		if (word[i] < 'a' || word[i] > 'z')
		{
			word.erase(i);
		}
	}
}
Node* find(string key, Node* node)
{
	if (node == NULL) { NULL; }
	if (key == node->key) { return node; }
	else if (key < node->key)
	{
		find(key, node->left);
	}
	else
	{
		find(key, node->right);
	}
}

void insert(string key, Node* node)
{
	Node* next;
	bool left;
	if (key == node->key)
	{
		return;
	}
	else if (key < node->key)
	{
		next = node->left;
		left = true;
	}
	else
	{
		next = node->right;
		left = false;
	}
	if (next != NULL)
	{
		insert(key, next);
	}
	else
	{
		next = new Node;
		next->key = key;
		next->left = NULL;
		next->right = NULL;
		if (left)
		{
			node->left = next;
		}
		else
		{
			node->right = next;
		}
	}
}

Node* insertFirst(string key)
{
	Node* node = new Node;
	node->key = key;
	node->left = NULL;
	node->right = NULL;
	return node;
}

void visit(Node* node)
{
	if (node->left != NULL)
	{
		visit(node->left);
	}

	binarySearchValidWords(node->key, sampleWordsArray);//Call the binary search.

	if (node->right != NULL)
	{
		visit(node->right);
	}

	numOfUniqueWords++;
}

/***Methods for part 4.***/

int assignPrimeNumbers(char thisChar)//assign unique prime number to each char in a string.
{
	switch (thisChar)
	{
		case 'a': return 2;
		case 'b': return 3;
		case 'c': return 5;
		case 'd': return 7;
		case 'e': return 11;
		case 'f': return 13;
		case 'g': return 17;
		case 'h': return 19;
		case 'i': return 23;
		case 'j': return 29;
		case 'k': return 31;
		case 'l': return 37;
		case 'm': return 41;
		case 'n': return 43;
		case 'o': return 47;
		case 'p': return 53;
		case 'q': return 59;
		case 'r': return 61;
		case 's': return 67;
		case 't': return 71;
		case 'u': return 73;
		case 'v': return 79;
		case 'w': return 83;
		case 'x': return 89;
		case 'y': return 97;
		case 'z': return 101;
	}
}

void findAnagrams(double key, AnagramNode* node, string anagram, int& anagramPrintCount)
{
	if (node == NULL) { return; }

	if (key == node->key) 
	{

		totalNumberOfAnagrams += node->count-1;
		if (node->count > 1)
		{
			numOfWordsWithAnagrams++;
		}
		if (node->count > numOfAnagrams)
		{
			numOfAnagrams = node->count;
			wordWithMostAnagrams = anagram;
		}
		if (anagram.length() > lengthOfWordWithAnagrams)
		{
			lengthOfWordWithAnagrams = anagram.length();
			longestWordWithAnagrams = anagram;
		}
		if (anagramPrintCount < 10)
		{
			if (node->count == 1)
			{
				return;
			}

			cout << anagram << " : ";
			anagramPrintCount++;

			for (int i = 0; i < node->count; i++)
			{
				if (node->anagrams[i] == anagram)
				{
					continue;
				}
				else
				cout << node->anagrams[i] << " ";
			}
			cout << endl;
		}
	}

	else if (key < node->key)
	{
		findAnagrams(key, node->left, anagram, anagramPrintCount);
	}
	else
	{
		findAnagrams(key, node->right, anagram, anagramPrintCount);
	}
}

void anagramInsert(double key, AnagramNode* node, string anagram)
{
	AnagramNode* next;
	bool left;
	if (key == node->key)
	{
		node->anagrams[node->count] = anagram;
		node->count++;
		return;
	}
	else if (key < node->key)
	{
		next = node->left;
		left = true;
	}
	else
	{
		next = node->right;
		left = false;
	}
	if (next != NULL)
	{
		anagramInsert(key, next, anagram);
	}
	else
	{
		next = new AnagramNode;
		next->key = key;
		next->count = 0;
		next->anagrams[next->count] = anagram;
		next->count++;
		next->left = NULL;
		next->right = NULL;
		if (left)
		{
			node->left = next;
		}
		else
		{
			node->right = next;
		}
	}
}

AnagramNode* anagramInsertFirst(double key, string anagram)
{
	AnagramNode* node = new AnagramNode;
	node->key = key;
	node->count = 0;
	node->anagrams[node->count] = anagram;
	node->count++;
	node->left = NULL;
	node->right = NULL;
	return node;
}

//For step 2 I estimated that my linear search would take around 6-7 hours to complete. 
//I knew that a binary search on this data size would have worst case around 18 checks before determining the outcome I estimated that it would take less than 10 seconds for the binary search

//For step 2 once a reversed palindrome has been found, I store the current value of i at the found position in a foundPalindromes array.
//Before the binary search has started, I have an if statement that checks to see if the reversed word < dictionary[i]
//if it is I then check if the word I'm about to search for has been stored in my seperate array, if found I print it and continue.
//If It's not in the array, I can still skip searching for that word as if It's less than dictionary[i] It's either found or doesn't exist.
//I also did small things like changing the first if to check in the binary search from checking the middle to checking if I can eliminate half the data
//as the former is more likely.
//Initially step 2 would take around 5-6 seconds, after all my enhancements I've gotten it down to 1.6 ish.

//For step 3 I read the files and use a BST to store them initially as it eliminates duplicates and orders the data for me. I then use a
//binary search to check if a word is valid and insert it into an array if it is.

//For step 4, rather than iterating and binary searching for each anagram of a word, I assign each character a value correspoding to a unique
//primary number. Source(https://hackernoon.com/an-algorithm-for-finding-anagrams-2fe7655de85b), I then multiply the values and store the entire
//dictionary in a BST, corresponding to their prime number values, and storing each word with the same prime number value in a string array in that node.
//Constructing the tree takes around 7 seconds of the entire time, searching, printing and counting only takes a fraction of a second.
//Before finding the prime number method I would binary search for each possible permutation of a string and I never let it run long enough
//to see how long that would take (waited maybe 1 minute tops), so this way is a substantial speed increase.

//Note that all times recorded in output.txt were compiled on Banshee.