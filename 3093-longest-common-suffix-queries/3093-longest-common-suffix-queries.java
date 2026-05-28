class Solution {

    class TrieNode {
        TrieNode[] child = new TrieNode[26];
        int idx = -1;
    }

    TrieNode root = new TrieNode();

    //to check if word at index a is better than index b
    private boolean better(String[] words, int a, int b) {
        if(b == -1) return true;

        if(words[a].length() != words[b].length()) {
            return words[a].length() < words[b].length();
        }

        return a < b;
    }

    private void insert(String word,int index, String[] words) {
        TrieNode node = root;

        //update root candidate

        if (better(words, index, node.idx)) {
            node.idx = index;
        }
        
        //Insert reversed word
        for(int i = word.length()-1; i>=0; i--) {
            int c = word.charAt(i) - 'a';

            if(node.child[c] == null) {
                node.child[c] = new TrieNode();
            }

            node = node.child[c];

            //Store the best candidate at each node
            if(better(words, index, node.idx)) {
                node.idx = index;
            }
        }
    }

    private int search(String word) {
        TrieNode node = root;

        //Traverse reversed array
        for(int i = word.length() - 1; i >= 0; i--) {
            int c = word.charAt(i) - 'a';

            if(node.child[c] == null) {
                break;
            }

            node = node.child[c];
        }
        return node.idx;
    }

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        
        //Build Trie
        for (int i = 0; i < wordsContainer.length; i++) {
            insert(wordsContainer[i], i, wordsContainer);
        }

        int[] ans = new int[wordsQuery.length];

        //Answer queries
        
        for(int i = 0; i < wordsQuery.length; i++) {
            ans[i] = search(wordsQuery[i]);
        }

        return ans;
    }
}