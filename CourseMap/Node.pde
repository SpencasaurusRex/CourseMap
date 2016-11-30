class Node {
  PVector position;
  
  public Node(float x, float y) {
    position = new PVector(x, y);
  }
  
  void draw() {
      
  }
  
  float x() {
    return position.x;
  }
  
  float y() {
    return position.y;
  }
}