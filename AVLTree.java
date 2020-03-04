import java.util.*;

class AVLTree{
  AVLNode root;
  int levelcount;
  boolean debug; // used to perform debugging outputs

  public AVLTree(){
    root = null;
    levelcount = 0;
    debug = false;
  }

  // function to print the inorder traversal of a tree
  public void inorder(AVLNode n){
    if(n == null){
      return;
    }
    inorder(n.left);
    System.out.print(n.val + " ");
    inorder(n.right);
  }

  // helper function to traverse back up the tree and update the balance factors for the parents of each node
  public void updateBalances(AVLNode n){

    if(debug){
      System.out.println("Node inserted for rebalancing = " + n.val);
    }

    AVLNode par = n.parent;

    // while the parent node is not null
    while(par != null){
      // bf = left_height - right_height
      // if n is left child, add 1 
      if(n == par.left){
        par.bf += 1;
      }
      // if n is right child, subtract 1
      if(n == par.right){
        par.bf -= 1;
      }
      
      if(debug){
        System.out.println("IN updateBalance:   Val = " + par.val + " BF = " + par.bf);
      }

      // if after updating the bf, the current node is imbalanced, then rebalance the tree
      if(par.bf < -1 || par.bf > 1){
        rebalance(par);
        //break; // (?????)
      }
      // are not changing n at each iteration
      n = par;
      par = par.parent;
      if(debug && n != null && par != null){
        System.out.println("n = " + n.val + "  curr = " + par.val);
      }
      //System.out.println("Hitting line 82");
    }
  }

  // helper function to figure out which side of the tree the node is on(left/right) and perform the appropraite rotations
  public void rebalance(AVLNode n){
    if(debug){
      System.out.println("IN rebalance     n = " + n.val);
    }
    // if n.bf is less than 0 (if the tree is right heavy), perform a leftRotation
    if(n.bf < 0){
      levelcount++;
      // check if the right child's bf is cauing the imbalance
      if(n.right.bf > 0){
        rightRotate(n.right);
        leftRotate(n);
      }
      else{
        leftRotate(n);
      }
    }
    // if n.bf is greater than 0 (if the tree is left heavy), perform a rightRotation
    else if(n.bf > 0){
      levelcount++;
      // check if the left child's bf is causing the imbalance
      if(n.left.bf < 0){
        leftRotate(n.left);
        rightRotate(n);
      }
      else{
        rightRotate(n);
      }
    }
  }

  public void leftRotate(AVLNode n){
    // internal visualization to help coding
    /*  n                     b
      /  \                   /  \
     a    b      -->        n    c
        /   \              /  \
     b.left  c            a    b.left
    */
    AVLNode b = n.right;
    levelcount++;
    n.right = b.left; 
    levelcount++;

    // update parent pointers
    if(b.left != null){ // if b.left is not null then set n to be b.left's parent
      b.left.parent = n;
    }
    b.parent = n.parent;
    if(n.parent == null){// n was the root
      root = b;
    }
    else if(n == n.parent.left){// if n was the left child
      n.parent.left = b;
    }
    else{ // if n was the right child
      n.parent.right = b;
    }

    // set new b.left and set n's new parent to b
    b.left = n;
    n.parent = b;

    // update balance factors
    n.bf = n.bf + 1 - Math.max(0, b.bf);
    b.bf = b.bf + 1 + Math.min(0, n.bf);
  }

  public void rightRotate(AVLNode n){
    AVLNode b = n.left;
    levelcount++;
    n.left = b.right;
    levelcount++;

    if(debug){
      System.out.println("n = " + n.val + "  b = " + b.val + "  n.left = " + n.left.val);
    }

    // update parent pointers
    if(b.right != null){ // if b.right is not null then set n to be b.left's parent
      b.right.parent = n;
    }
    b.parent = n.parent;
    if(n.parent == null){// n was the root
      root = b;
    }
    else if(n == n.parent.left){// if n was the left child
      n.parent.left = b;
    }
    else{ // if n was the right child
      n.parent.right = b;
    }

    // set new b.right and set n's new parent to b
    b.right = n;
    n.parent = b;

    // update balance factors
    n.bf = n.bf - 1 - Math.min(0, b.bf);
    b.bf = b.bf - 1 + Math.max(0, n.bf);
  }

  public void insertIter(int n){
    // if the root is null
    if(root == null){
      root = new AVLNode(n);
      root.bf = 0;
      levelcount++;
      return;
    }

     // pointer to the current node which starts at the root and the current nodes parent
    AVLNode curr = root;
    AVLNode parent = null;

    // traverse until you find the location of an empty leaf
    while(curr != null){
      parent = curr;
      if(n < curr.val){
        curr = curr.left;
        levelcount++;
      }
      else{
        curr = curr.right;
        levelcount++;
      }
    }

    // create a new node and set the new node's parent
    curr = new AVLNode(n);
    curr.parent = parent;

    // set the parent's left/right child appropriately
    if(n > parent.val){
      parent.right = curr;
    }
    else{
      parent.left = curr;
    }

    // update the height of the current node's parent
    updateBalances(curr);
  }

  public void deleteIter(int n){
    // find the node first
    AVLNode curr = root;
    while(curr.val != n){
      if(n < curr.val){
        curr = curr.left;
        levelcount++;
      }
      else{
        curr = curr.right;
        levelcount++;
      }
    }

    // if it is null, then it is not in the tree
    if(curr == null){
      return;
    }

    // case 1: node has no left/right child
    if(curr.left == null && curr.right == null){
      if(curr.parent.left == curr){
        curr.parent.left = null;
      }
      else{
        curr.parent.right = null;
      }
    } // case 2: node has both left and right children
    else if(curr.left != null && curr.right != null){
      // find the next prev value of curr
      // replace next prev values with curr values
      AVLNode nextprev = findPrevIter(curr.val);
      nextprev.parent = curr.parent;
      nextprev.left = curr.left;
      levelcount++;
      nextprev.right = curr.right;
      levelcount++;
      curr = nextprev;
    } // case 3: node has only one child
    else{
      // set child to the non-null child of curr
      AVLNode child = null;
      if(curr.left != null){
        child = curr.left;
        levelcount++;
      }
      else{
        child = curr.right;
        levelcount++;
      }
      // determine if curr id left/right child of parent and set accordingly
      if(curr.parent.left == curr){
        curr.parent.left = child;
      }
      else{
        curr.parent.right = child;
      }
    }

    // update the height of the current node's parent
    updateBalances(curr);
  }

  public AVLNode findNextIter(int n){
    // find the node first and set it to curr
    AVLNode curr = root;
    while(curr != null && curr.val != n){
      if(n < curr.val){
        curr = curr.left;
        levelcount++;
      }
      else{
        curr = curr.right;
        levelcount++;
      }
    }

    // node is not in the tree
    if(curr == null){
      return null;
    }

    // case 1: node has a right subtree
    // Go to the leftmost node in the right subtree
    if(curr.right != null){
      AVLNode tmp = curr.right;
      levelcount++;
      while(tmp.left != null){
        tmp = tmp.left;
        levelcount++;
      }
      return tmp;
    }
    else{
      // case 2: no right subtree
      // traverse back up the tree until the parent val > curr val
      AVLNode tmp = curr.parent;
      while(curr.val > tmp.val){
          tmp = tmp.parent;
      } 
      return tmp;
    }
  }

  public AVLNode findPrevIter(int n){
    // find the node first and set it to curr
    AVLNode curr = root;
    while(curr != null && curr.val != n){
      if(n < curr.val){
        curr = curr.left;
        levelcount++;
      }
      else{
        curr = curr.right;
        levelcount++;
      }
    }

    // node is not in the tree
    if(curr == null){
      return null;
    }
    // case 1: node has a left subtree
    // Go deep to the rightmost node in the left subtree
    if(curr.left != null){
      AVLNode tmp = curr.left;
      levelcount++;
      while(tmp.right != null){
        tmp = tmp.right;
        levelcount++;
      }
      return tmp;
    }
    else{
      // traverse back up the tree until the parent val < curr val
      AVLNode tmp = curr.parent;
      while(curr.val < tmp.val){
          tmp = tmp.parent;
      } 
      return tmp;
    }
  }

  public AVLNode findMinIter(){
    // set a pointer to the root node
    AVLNode curr = root;

    // traverse down to the leftmost subtree
    while(curr.left != null){
      curr = curr.left;
      levelcount++;
    }
    return curr;
  }

  public AVLNode findMaxIter(){
    // set a pointer to the root node
    AVLNode curr = root;

    // traverse down to the rightmost subtree
    while(curr.right != null){
      curr = curr.right;
      levelcount++;
    }
    return curr;
  }
}