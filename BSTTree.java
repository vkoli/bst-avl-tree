import java.util.*;

class BSTTree{
  BSTNode root;
  int levelcount;
  ArrayList<Integer> arr;

  public BSTTree(){
    root = null;
    levelcount = 0;
    arr = new ArrayList<Integer>();
  }

  //======================= QUESTION 1C =====================

  public BSTNode insertRec(BSTNode n, int num){
    // case: root is empty
    if(root == null){
      root = new BSTNode(num);
      levelcount++;
      return root;
    }

    // case: node is null
    if(n == null){
      return new BSTNode(num);
    }
    
    // case: num less than node.val, recur left
    // set n's left child to be tmp and set parent pointer
    if(num < n.val){
      BSTNode tmp = insertRec(n.left, num);
      levelcount++;
      n.left = tmp;
      tmp.parent = n;
    }

    //case: num greater than node.val, recur right
    // set n's right child to be tmp and set parent pointer
    if(num > n.val){
      BSTNode tmp = insertRec(n.right, num);
      levelcount++;
      n.right = tmp;
      tmp.parent = n;
    }
    return n;
  }

  public void deleteRec(BSTNode n, int num){
    // case: root is empty
    if(n == root && n == null){
      return;
    }
    
    // find the node by performing binary search
    if(num < n.val){
      deleteRec(n.left, num);
      levelcount++;
    }
    else if(num > n.val){
      deleteRec(n.right, num);
      levelcount++;
    }
    else{ // found the location of the node
      // case 1: node has no children
      if(n.left == null && n.right == null){
        // set n's parent left/right child to null
        if(n.parent.left == n){
          n.parent.left = null;
        }
        else{
          n.parent.right = null;
        }
        n = null;
      }
      // case 2: node has both children
      else  if(n.left != null && n.right != null){
          // find the previous biggest value(in the left subtree) and copy that into n
          // then call delete on the right subtree
          BSTNode tmp = findPrevRec(n);
          n.val = tmp.val;
          deleteRec(n.right, num);
      }
      // node only has one child
      else {
        // left child is not null
        if(n.left != null){
          // set n's parent left.right child the n's left child
          if(n.parent.left == n){
            n.parent.left = n.left;
          }
          else{
            n.parent.right = n.left;
          }
        }
        else{
          // same thing if the right child is not null
          if(n.parent.left == n){
            n.parent.left = n.right;
          }
          else{
            n.parent.right = n.right;
          }
        }
      }
    }
  }

  public BSTNode findNextRec(BSTNode n){
    // base case: node is null
    if(n == null){
      return null;
    }

    // case 1: node has a right subtree
    // find the maximum value in the minimum set
    if(n.right != null){
      levelcount++;
      BSTNode tmp = n.right;
      levelcount++;
      return findNextRec(n.left);
    }
    else{ 
      // case 2: node has no right subtree
      // traverse back up the tree until the parent val > tmp val
      BSTNode tmp = n.parent;
      if(n.val > tmp.val){
        return findNextRec(tmp.parent);
      }
    }
    return n;
  }

  public BSTNode findPrevRec(BSTNode n){
    // base case: node is null
    if(n == null){
      return null;
    }

    // case 1: node has a left subtree
    // find the miniimum value in the large set
    if(n.left != null){
      levelcount++;
      BSTNode tmp = n.right;
      levelcount++;
      return findPrevRec(n.right);
    }
    else{ 
      // case 2: node has no right subtree
      // traverse back up the tree until the parent val < tmp val
      BSTNode tmp = n.parent;
      if(n.val < tmp.val){
        return findNextRec(tmp.parent);
      }
    }
    return n;
  }

  public BSTNode findMinRec(BSTNode n){
    // traverse down to the leftmost subtree
    BSTNode curr = n;
    if(curr == null){
      return curr;
    }

    if (curr.left != null){
      curr = curr.left;
      levelcount++;
      return findMinRec(curr);
    }
    return curr;
  }

  public BSTNode findMaxRec(BSTNode n){
    // traverse down the rightmost subtree
    BSTNode curr = n;
    if(curr == null){
      return curr;
    }
    if (curr.right != null){
      curr = curr.right;
      levelcount++;
      return findMaxRec(curr);
    }
    return curr;
  }

  //======================= QUESTION 1D =====================

  public void insertIter(int num){
    // case where the root is null
    if(root == null){
      root = new BSTNode(num);
      levelcount++;
      return;
    }

    // pointer to the current node which starts at the root
    BSTNode curr = root;

    // pointer to the new nodes parent
    BSTNode parent = null;

    // traverse until you find the location of an empty leaf
    while(curr != null){
      parent = curr;
      if(num < curr.val){
        curr = curr.left;
        levelcount++;
      }
      else{
        curr = curr.right;
        levelcount++;
      }
    }

    // create a new node and set the new node's parent
    curr = new BSTNode(num);
    curr.parent = parent;

    // set the parent's left/right child appropriately
    if(num > parent.val){
      parent.right = curr;
    }
    else{
      parent.left = curr;
    }
  }

  public void deleteIter(int num){
    // find the node first
    BSTNode curr = root;
    while(curr.val != num){
      if(num < curr.val){
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
      BSTNode nextprev = findPrevIter(curr.val);
      nextprev.parent = curr.parent;
      nextprev.left = curr.left;
      levelcount++;
      nextprev.right = curr.right;
      levelcount++;
      curr = nextprev;
    } // case 3: node has only one child
    else{
      // set child to the non-null child of curr
      BSTNode child = null;
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
  }

  public BSTNode findNextIter(int n){
    // find the node first and set it to curr
    BSTNode curr = root;
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
      BSTNode tmp = curr.right;
      levelcount++;
      while(tmp.left != null){
        levelcount++;
        tmp = tmp.left;
      }
      return tmp;
    }
    else{
      // case 2: no right subtree
      // traverse back up the tree until the parent val > curr val
      BSTNode tmp = curr.parent;
      while(curr.val > tmp.val){
          tmp = tmp.parent;
      } 
      return tmp;
    }
  }

  public BSTNode findPrevIter(int n){
    // find the node first and set it to curr
    BSTNode curr = root;
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
      BSTNode tmp = curr.left;
      levelcount++;
      while(tmp.right != null){
        tmp = tmp.right;
        levelcount++;
      }
      return tmp;
    }
    else{
      // traverse back up the tree until the parent val < curr val
      BSTNode tmp = curr.parent;
      while(curr.val < tmp.val){
          tmp = tmp.parent;
      } 
      return tmp;
    }
  }

  public BSTNode findMinIter(){
    // set a pointer to the root node
    BSTNode curr = root;

    // traverse down to the leftmost subtree
    while(curr.left != null){
      curr = curr.left;
      levelcount++;
    }
    return curr;
  }

  public BSTNode findMaxIter(){
    // set a pointer to the root node
    BSTNode curr = root;

    // traverse down to the rightmost subtree
    while(curr.right != null){
      curr = curr.right;
      levelcount++;
    }
    return curr;
  }

  //======================= QUESTION 2C =====================

  public ArrayList<Integer> sort(BSTNode n){
    if(n == null){
      return null;
    }
    sort(n.left);
    arr.add(Integer.valueOf(n.val));
    sort(n.right);
    return arr;
  }

  // function to print the inorder traversal of a tree
  public void inorder(BSTNode n){
    if(n == null){
      return;
    }
    inorder(n.left);
    System.out.print(n.val + " ");
    inorder(n.right);
  }
}