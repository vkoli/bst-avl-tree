import java.lang.*;
import java.util.*;

class Main {
  public static void main(String[] args) {
    int[] tmp = getRandomArray(6);
    int[] tmp2 = getSortedArray(6);
    printArr(tmp);
    printArr(tmp2);

    /*
      Example tree used to test methods
          15
          / \
        10   20
       /     / \
      8     16  25
    */
    Tree t = new Tree();
    t.insertIter(15);
    t.insertRec(t.root, 10);
    t.insertRec(t.root, 20);
    t.insertIter(8);
    t.insertIter(16);
    t.insertIter(25);

    t.inorder(t.root);
    System.out.println();
  
    Node m = t.findMaxIter();
    Node n = t.findMaxRec(t.root);
    System.out.println("Max Iter = " + m.val);
    System.out.println("Max Rec = " + n.val);

    m = t.findMinIter();
    n = t.findMinRec(t.root);
    System.out.println("Min Iter = " + m.val);
    System.out.println("Min Rec = " + n.val);

    m = t.findNextIter(t.root.val);
    System.out.println("Find Next Iter = " + m.val);
    n = t.findPrevIter(t.root.val);
    System.out.println("Find Prev Iter = " + n.val);
  }

  public static void printArr(int[] arr){
    for(int i =0; i < arr.length; i++){
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }
  
  //======================= QUESTION 3A =====================
  // Funcation takes in an integer and returns an array of n random integers from 0-100 without duplicates
  public static int[] getRandomArray(int n){
    int[] arr = new int[n];
    for(int i = 0; i < arr.length; i++){
      int num = (int) (Math.random() * 101);
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