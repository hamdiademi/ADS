package Lab8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

class BNode<E> {

    public E info;
    public BNode<E> left;
    public BNode<E> right;

    static int LEFT = 1;
    static int RIGHT = 2;

    public BNode(E info) {
        this.info = info;
        left = null;
        right = null;
    }

    public BNode() {
        this.info = null;
        left = null;
        right = null;
    }

    public BNode(E info, BNode<E> left, BNode<E> right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

}

class BTree<E extends Comparable<E>> {

    public BNode<E> root;

    public BTree() {
        root = null;
    }

    public BTree(E info) {
        root = new BNode<E>(info);
    }

    public void makeRoot(E elem) {
        root = new BNode<>(elem);
    }

    public void makeRootNode(BNode<E> node) {
        root = node;
    }

    public BNode<E> addChild(BNode<E> node, int where, E elem) {

        BNode<E> tmp = new BNode<E>(elem);

        if (where == BNode.LEFT) {
            if (node.left != null)  // veke postoi element
                return null;
            node.left = tmp;
        } else {
            if (node.right != null) // veke postoi element
                return null;
            node.right = tmp;
        }

        return tmp;
    }

    public BNode<E> addChildNode(BNode<E> node, int where, BNode<E> tmp) {

        if (where == BNode.LEFT) {
            if (node.left != null)  // veke postoi element
                return null;
            node.left = tmp;
        } else {
            if (node.right != null) // veke postoi element
                return null;
            node.right = tmp;
        }

        return tmp;
    }

}

public class BinaryTreeSum {


    public static void main(String[] args) throws Exception {
        int i, j, k;
        int index;
        String action;

        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        @SuppressWarnings("unchecked")
        BNode<Integer>[] nodes = new BNode[N];
        BTree<Integer> tree = new BTree<Integer>();

        for (i=0;i<N;i++)
            nodes[i] = new BNode<Integer>();

        for (i = 0; i < N; i++) {
            line = br.readLine();
            st = new StringTokenizer(line);
            index = Integer.parseInt(st.nextToken());
            nodes[index].info = Integer.parseInt(st.nextToken());
            action = st.nextToken();
            if (action.equals("LEFT")) {
                tree.addChildNode(nodes[Integer.parseInt(st.nextToken())], BNode.LEFT, nodes[index]);
            } else if (action.equals("RIGHT")) {
                tree.addChildNode(nodes[Integer.parseInt(st.nextToken())], BNode.RIGHT, nodes[index]);
            } else {
                // this node is the root
                tree.makeRootNode(nodes[index]);
            }
        }

        int baranaVrednost=Integer.parseInt(br.readLine());

        br.close();

        // vasiot kod ovde   // your code here

        BNode<Integer> node;
        node= findNodebyInfo(tree.root,baranaVrednost);


        System.out.println(sumRightorLeftSubtree(node.left,baranaVrednost,0,'<')+" "+sumRightorLeftSubtree(node.right,baranaVrednost,0,'>'));

        // System.out.println(sumLeftSubtree(node.left,baranaVrednost,0)+" "+sumRightSubtree(node.right,baranaVrednost,0));
    }
    public static BNode<Integer> findNodebyInfo(BNode<Integer> node, int num){
        Stack<BNode<Integer>> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()){
            BNode<Integer> temp=stack.pop();
            if (temp.info==num){
                return temp;
            }
            if (temp.right!=null){
                stack.push(temp.right);
            }
            if (temp.left!=null){
                stack.push(temp.left);
            }
        }
        return null;
    }
    public static int sumRightorLeftSubtree(BNode<Integer> node, int baranaVrednost,int sum,char ch) {
        if (node == null) {
            return sum;
        }

        if (node.info < baranaVrednost && ch=='<') {
            sum += node.info;
        }

        if (node.info > baranaVrednost && ch=='>') {
            sum += node.info;
        }

        sum=sumRightorLeftSubtree(node.left, baranaVrednost,sum,ch);
        sum=sumRightorLeftSubtree(node.right, baranaVrednost,sum,ch);

        return sum;
    }


//    public static int sumLeftSubtree(BNode<Integer> node, int baranaVrednost,int sum) {
//        if (node == null) {
//            return sum;
//        }
//        if (node.info < baranaVrednost) {
//            sum+=node.info;
//        }
//        sum+=sumLeftSubtree(node.left, baranaVrednost,sum);
//        sum+=sumLeftSubtree(node.right, baranaVrednost,sum);
//
//        return sum;
//    }
//    public static int sumRightSubtree(BNode<Integer> node, int baranaVrednost,int sum) {
//        if (node == null) {
//            return sum;
//        }
//        if (node.info > baranaVrednost) {
//            sum += node.info;
//        }
//        sum=sumRightSubtree(node.left, baranaVrednost,sum);
//        sum=sumRightSubtree(node.right, baranaVrednost,sum);
//
//        return sum;
//    }
}
