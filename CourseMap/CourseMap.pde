ArrayList<Node> nodes;

void setup() {
  size(1200, 900);
  background(255);
  loadNodes();
}

void draw() {
  clear();
  background(255);
  fill(0);
  
  // Test print
  int y = 10;
  for (Course c : Courses.courses.values()){
    if (y < height) {
      text(c.title, 0, y);
      y += 15;
    }
  }
}

void loadNodes() {
  String[] courseStrings = loadStrings("../courses.csv"); //<>//
  for (String line : courseStrings) {
    String[] tokens = line.split("(?<!\\\\),");
    new Course(tokens);
  }
  Courses.link();
}