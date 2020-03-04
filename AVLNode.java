class AVLNode{
  int val;
  int bf;   // Balance factor
  AVLNode right;
  AVLNode left;
  AVLNode parent;
  
  public AVLNode(int num){
    val = num;
    bf = 0;
    right = left = parent = null;
  }
}