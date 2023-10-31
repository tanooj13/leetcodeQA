//It is a lossless Compression Algorithm

//We will take 2 Hashmaps Encoder and Decoder
//Encoder converts a char to bytes
//Decoder converts the bytes into char

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/*
* Steps:
* 1.Pass the string aka feeder
* 2.make a freq map like how many times a single char appears in the string
* 3.for every key in the freq map ,create a node and insert that node in a min heap
* Node : char data and int cost
* 4.Remove 2 elements from the heap and combine them
* 5.Form a tree at every step 4 until u read every char
* rightnode branch = 1 leftnode branch = 0
* 6:DECODE it
* 7. How to encode/decode?
* ----string = abbccda = 7*2*8 == 112 bits
*       a - 0 b - 10 b - 10 c - 110 c - 110 d - 111 a - 0 ---> 15 BITS
* we compressed it from 112 bits to 15 bits
*
* Time Complexity = O(NlogN)
* Space Complexity = O(N)
* */
public class HuffmanCoding {
    HashMap<Character,String> encoder;
    HashMap<String,Character> decoder;

    private  class Node implements Comparable<Node>{
        Character data;
        Node left;
        Node right;
        int cost;
        public Node (Character data,int cost){
            this.cost = cost;
            this.data = data;
            this.left = null;
            this.right = null;
        }
        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }
    public HuffmanCoding(String feeder) throws Exception{
        HashMap<Character,Integer> fmap = new HashMap<>();
        for (int i = 0;i < feeder.length();i++){
            char ch = feeder.charAt(i);
            fmap.put(ch, fmap.getOrDefault(ch,0)+1);
        }
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Map.Entry<Character,Integer>> entrySet = fmap.entrySet();
        for (Map.Entry<Character,Integer> entry : entrySet){
            Node node = new Node(entry.getKey(),entry.getValue());
            pq.offer(node);
        }

        while (pq.size() != 1){
            Node f = pq.poll();
            Node s = pq.poll();

            Node newNode = new Node('\0',f.cost+s.cost);
            newNode.left = f;
            newNode.right = s;
            pq.offer(newNode);
        }
        Node ft = pq.poll();

        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();

        this.initEncoderDecoder(ft,"");
    }
    private void initEncoderDecoder(Node node,String out){
        if (node == null)return;

        if (node.left == null && node.right == null){
            this.encoder.put(node.data,out);
            this.decoder.put(out,node.data);
        }
        initEncoderDecoder(node.left,out+"0");
        initEncoderDecoder(node.right,out+"1");
    }
    public String encode(String feed){
        String ans  = "";

        for (int i = 0;i < feed.length();i++){
            ans += encoder.get(feed.charAt(i));
        }
        return ans;
    }
    public String decode(String out){
        String key = "";
        String ans = "";
        for (int i =0;i < out.length();i++){
            key += out.charAt(i);
            if (decoder.containsKey(key)){
                ans+= decoder.get(key);
                key ="";
            }
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        HuffmanCoding hf = new HuffmanCoding("Tanooj");
        String enc = hf.encode("Tanooj");
        System.out.println(enc);
        System.out.println(hf.decode(enc));


    }
}
