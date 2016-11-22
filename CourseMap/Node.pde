class Node {
  PVector position;
  PVector velocity;
  PVector acceleration;
  float mass = 1;
  
  public Node(float x, float y) {
    position = new PVector(x, y);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    mass = 1;
  }
  
  public void update() {
    velocity.add(acceleration);
    position.add(velocity);
    acceleration.mult(0);
  }
  
  public void applyForce(PVector force) {
    PVector f = PVector.div(force, mass);
    acceleration.add(f);
  }
  
  public void connect(Node other) {
    
  }
}