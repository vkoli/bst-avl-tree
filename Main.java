import java.util.*;

class Main {
  public static void main(String[] args) {
    // tests the getRandomArray and getSortedArray functions
    System.out.println("==================Testing Question 3 ===================");
    int[] tmp = getRandomArray(6);
    int[] tmp2 = getSortedArray(6);
    printArr(tmp);
    printArr(tmp2);
    System.out.println();

    /*
      Example tree used to test BST methods both iterative and recursive
          15
          / \
        10   20
       /     / \
      8     16  25
    */
    System.out.println("==================Testing BST===================");
    BSTTree bst = new BSTTree();
    bst.insertRec(bst.root, 15);
    bst.inorder(bst.root);
    System.out.println();

    bst.insertRec(bst.root, 10);
    bst.inorder(bst.root);
    System.out.println();

    bst.insertRec(bst.root, 20); 
    bst.inorder(bst.root);
    System.out.println();

    bst.insertIter(8);
    bst.inorder(bst.root);
    System.out.println();

    bst.insertIter(16);
    bst.inorder(bst.root);
    System.out.println();

    bst.insertIter(25);
    bst.inorder(bst.root);
    System.out.println();
    System.out.println();
  
    BSTNode m = bst.findMaxIter();
    BSTNode n = bst.findMaxRec(bst.root);
    System.out.println("Max Iter = " + m.val);
    System.out.println("Max Rec = " + n.val);

    m = bst.findMinIter();
    n = bst.findMinRec(bst.root);
    System.out.println("Min Iter = " + m.val);
    System.out.println("Min Rec = " + n.val);

    m = bst.findNextIter(bst.root.val);
    System.out.println("Find Next Iter = " + m.val);
    n = bst.findPrevIter(bst.root.val);
    System.out.println("Find Prev Iter = " + n.val);

    bst.deleteRec(bst.root, 16);
    bst.inorder(bst.root);
    System.out.println();
    System.out.println("Deleted Node 16");

    bst.deleteIter(10);
    bst.inorder(bst.root);
    System.out.println();
    System.out.println("Deleted Node 10");
    System.out.println();


    // Testing AVLTree Methods
    /*
      Ending tree used to test AVL iterative methods 
              10
              / \
            8    15
           / \   / \
          7   9 14  18
                /
               13
    */
     System.out.println("==================Testing AVL===================");
    AVLTree avl = new AVLTree();
    avl.insertIter(10);
    avl.inorder(avl.root);
    System.out.println();

    avl.insertIter(15);
    avl.inorder(avl.root);
    System.out.print(" root = " + avl.root.val);
    System.out.println();

    // will trigger a Left Rotate
    avl.insertIter(18); 
    avl.inorder(avl.root);
    System.out.print(" root = " + avl.root.val);
    System.out.println();

    avl.insertIter(8);
    avl.inorder(avl.root);
    System.out.print(" root = " + avl.root.val);
    System.out.println();

    // will trigger a Left-->Right Rotate
    avl.insertIter(9);
    avl.inorder(avl.root);
    System.out.print(" root = " + avl.root.val);
    System.out.println();

    avl.insertIter(14);
    avl.inorder(avl.root);
    System.out.print(" root = " + avl.root.val);
    System.out.println();

    // will trigger a Right Rotate 
    avl.insertIter(7);
    avl.inorder(avl.root);
    System.out.print(" root = " + avl.root.val);
    System.out.println();

    avl.insertIter(13);
    avl.inorder(avl.root);
    System.out.print(" root = " + avl.root.val);
    System.out.println();
    System.out.println();

    AVLNode m1 = avl.findMaxIter();
    System.out.println("Max Iter = " + m1.val);

    m1 = avl.findMinIter();
    System.out.println("Min Iter = " + m1.val);

    m1 = avl.findNextIter(avl.root.val);
    System.out.println("Find Next Iter = " + m1.val);

    AVLNode n1 = avl.findPrevIter(avl.root.val);
    System.out.println("Find Prev Iter = " + n1.val);
    System.out.println();

    // Below is the code for question 5: Constructing Trees
    System.out.println("==================Question 5 AND 6 ===================");
    BSTTree b = new BSTTree();
    AVLTree a = new AVLTree();
    int[] small = getRandomArray(10);
    for(int i = 0; i < small.length; i++){
      b.insertRec(b.root, small[i]);
      a.insertIter(small[i]);
    }
    System.out.println("BST root = " + b.root.val + "  Level count = " + b.levelcount);
    System.out.println("AVL root = " + a.root.val + "  Level count = " + a.levelcount);
    System.out.println("Finished inserting 10 elements in AVL and BST Tree");

    BSTTree b1 = new BSTTree();
    AVLTree a1 = new AVLTree();
    int[] largerec = getRandomArray(10000);
    for(int i = 0; i < largerec.length; i++){
      b1.insertRec(b1.root, largerec[i]);
      a1.insertIter(largerec[i]);
    }
    System.out.println("BST root = " + b1.root.val + "  Level count = " + b1.levelcount);
    System.out.println("AVL root = " + a1.root.val + "  Level count = " + a1.levelcount);
    System.out.println("Finished inserting 10000 elements in AVL(Iter) and BST (Rec) Tree");

    // Question 6B
    BSTTree b2 = new BSTTree();
    AVLTree a2 = new AVLTree();
    int[] largeiter = getRandomArray(10000);
    for(int i = 0; i < largeiter.length; i++){
      b2.insertRec(b2.root, largeiter[i]);
      a2.insertIter(largeiter[i]);
    }
    System.out.println("BST root = " + b2.root.val + "  Level count = " + b2.levelcount);
    System.out.println("AVL root = " + a2.root.val + "  Level count = " + b2.levelcount);
    System.out.println("Finished inserting 10000 elements in AVL (Iter) and BST (Iter) Tree");

    // Question 6C
    BSTTree b3 = new BSTTree();
    AVLTree a3 = new AVLTree();
    int[] largesorted = getSortedArray(10000);
    for(int i = 0; i < largesorted.length; i++){
      b3.insertIter(largeiter[i]);
      a3.insertIter(largeiter[i]);
    }
    System.out.println("BST root = " + b3.root.val + "  Level count = " + b3.levelcount);
    System.out.println("AVL root = " + a3.root.val + "  Level count = " + b3.levelcount);
    System.out.println("Finished inserting 10000 elements in AVL (Iter) and BST (Iter) Tree");
    System.out.println();
  }

  // auxillary function to help print out the array
  public static void printArr(int[] arr){
    for(int i =0; i < arr.length; i++){
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }

  //======================= QUESTION 3A =====================
  // Funcation takes in an integer and returns an array of n random integers from 0-10000 without duplicates
  public static int[] getRandomArray(int n){
    int[] arr = new int[n]; 
    for(int i = 0; i < n; i++){
      int num = (int) (Math.random() * 10001);
      arr[i] = num;
    }
    return arr;
  }

  //======================= QUESTION 3B =====================
  // Function takes in an integer and returns an array of sorted elements
  // getSortedArray(5) --> [5,4,3,2,1]
  public static int[] getSortedArray(int n){
    int[] arr = new int[n]; 
    int num = n;
    for(int i = 0; i < arr.length; i++){
      arr[i] = num;
      num--;
    } 
    return arr;
  }
}