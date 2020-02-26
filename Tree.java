import java.util.*;

class Tree{
  Node root;
  int nodecount;
  ArrayList<Integer> arr;

  public Tree(){
    root = null;
    nodecount = 0;
    arr = new ArrayList<Integer>();
  }

  //======================= QUESTION 1C =====================

  public Node insertRec(Node n, int num){
    // case: root is empty
    if(root == null){
      root = new Node(num);
      return root;
    }

    // case: node is null
    if(n == null){
      return new Node(num);
    }
    
    // case: num less than node.val, recur left
    // set n's left child to be tmp and set parent pointer
    if(num < n.val){
      Node tmp = insertRec(n.left, num);
      n.left = tmp;
      tmp.parent = n;
    }

    //case: num greater than node.val, recur right
    // set n's right child to be tmp and set parent pointer
    if(num > n.val){
      Node tmp = insertRec(n.right, num);
      n.right = tmp;
      tmp.parent = n;
    }
    return root;
  }

  public void deleteRec(Node n, int num){
    // case: root is empty
    if(n == root && n == null){
      return;
    }
    
    // find the node by performing binary search
    if(num < n.val){
      deleteRec(n.left, num);
    }
    else if(num > n.val){
      deleteRec(n.right, num);
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
          Node tmp = findPrevRec(n);
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

  public Node findNextRec(Node n){
    // base case: node is null
    if(n == null){
      return null;
    }

    // case 1: node has a right subtree
    // find the maximum value in the minimum set
    if(n.right != null){
      Node tmp = n.right;
      return findNextRec(n.left);
    }
    else{ 
      // case 2: node has no right subtree
      // traverse back up the tree until the parent val > tmp val
      Node tmp = n.parent;
      if(n.val > tmp.val){
        return findNextRec(tmp.parent);
      }
    }
    return n;
  }

  public Node findPrevRec(Node n){
    // base case: node is null
    if(n == null){
      return null;
    }

    // case 1: node has a left subtree
    // find the miniimum value in the large set
    if(n.left != null){
      Node tmp = n.right;
      return findPrevRec(n.right);
    }
    else{ 
      // case 2: node has no right subtree
      // traverse back up the tree until the parent val < tmp val
      Node tmp = n.parent;
      if(n.val < tmp.val){
        return findNextRec(tmp.parent);
      }
    }
    return n;
  }

  public Node findMinRec(Node n){
    // traverse down to the leftmost subtree
    Node curr = n;
    if(curr == null){
      return curr;
    }

    if (curr.left != null){
      curr = curr.left;
      return findMinRec(curr);
    }
    return curr;
  }

  public Node findMaxRec(Node n){
    // traverse down the rightmost subtree
    Node curr = n;
    if(curr == null){
      return curr;
    }
    if (curr.right != null){
      curr = curr.right;
      return findMaxRec(curr);
    }
    return curr;
  }

  //======================= QUESTION 1D =====================

  public void insertIter(int num){
    // case where the root is null
    if(root == null){
      root = new Node(num);
      return;
    }

    // pointer to the current node which starts at the root
    Node curr = root;

    // pointer to the new nodes parent
    Node parent = null;

    // traverse until you find the location of an empty leaf
    while(curr != null){
      parent = curr;
      if(num < curr.val){
        curr = curr.left;
      }
      else{
        curr = curr.right;
      }
    }

    // create a new node and set the new node's parent
    curr = new Node(num);
    curr.parent = parent;

    // set the parent's left/right child appropriately
    if(num > parent.val){
      parent.right = curr;
    }
    else{
      parent.left = curr;
    }
    nodecount++;
  }

  public void deleteIter(int num){
    // find the node first
    Node curr = root;
    while(curr.val != num){
      if(num < curr.val){
        curr = curr.left;
      }
      else{
        curr = curr.right;
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
      Node nextprev = findPrevIter(curr.val);
      nextprev.parent = curr.parent;
      nextprev.left = curr.left;
      nextprev.right = curr.right;
      curr = nextprev;

    } // case 3: node has only one child
    else{
      // set child to the non-null child of curr
      Node child = null;
      if(curr.left != null){
        child = curr.left;
      }
      else{
        child = curr.right;
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

  public Node findNextIter(int n){
    // find the node first and set it to curr
    Node curr = root;
    while(curr != null && curr.val != n){
      if(n < curr.val){
        curr = curr.left;
      }
      else{
        curr = curr.right;
      }
    }

    // node is not in the tree
    if(curr == null){
      return null;
    }

    // case 1: node has a right subtree
    // Go to the leftmost node in the right subtree
    if(curr.right != null){
      Node tmp = curr.right;
      while(tmp.left != null){
        tmp = tmp.left;
      }
      return tmp;
    }
    else{
      // case 2: no right subtree
      // traverse back up the tree until the parent val > curr val
      Node tmp = curr.parent;
      while(curr.val > tmp.val){
          tmp = tmp.parent;
      } 
      return tmp;
    }
  }

  public Node findPrevIter(int n){
    // find the node first and set it to curr
    Node curr = root;
    while(curr != null && curr.val != n){
      if(n < curr.val){
        curr = curr.left;
      }
      else{
        curr = curr.right;
      }
    }

    // node is not in the tree
    if(curr == null){
      return null;
    }
    // case 1: node has a left subtree
    // Go deep to the rightmost node in the left subtree
    if(curr.left != null){
      Node tmp = curr.left;
      while(tmp.right != null){
        tmp = tmp.right;
      }
      return tmp;
    }
    else{
      // traverse back up the tree until the parent val < curr val
      Node tmp = curr.parent;
      while(curr.val < tmp.val){
          tmp = tmp.parent;
      } 
      return tmp;
    }
  }

  public Node findMinIter(){
    // set a pointer to the root node
    Node curr = root;

    // traverse down to the leftmost subtree
    while(curr.left != null){
      curr = curr.left;
    }
    return curr;
  }

  public Node findMaxIter(){
    // set a pointer to the root node
    Node curr = root;

    // traverse down to the rightmost subtree
    while(curr.right != null){
      curr = curr.right;
    }
    return curr;
  }

  //======================= QUESTION 2C =====================

  public ArrayList<Integer> sort(Node n){
    if(n == null){
      return null;
    }
    sort(n.left);
    arr.add(Integer.valueOf(n.val));
    sort(n.right);
    return arr;
  }

  // function to print the inorder traversal of a tree
  public void inorder(Node n){
    if(n == null){
      return;
    }
    inorder(n.left);
    System.out.print(n.val + " ");
    inorder(n.right);
  }
}