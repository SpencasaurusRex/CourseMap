class Course {
  String key;
  String title;
  String description;
  ArrayList<Course> prereqs = new ArrayList<Course>();
  ArrayList<String> prereqStrings = new ArrayList<String>();
  
  Course (String[] tokens) {
    // Must at least have key and title
    if (tokens.length >= 2) { //<>//
      key = tokens[0];
      title = tokens[1].replaceAll("\\\\,",",");
      int index = 0;
      for (String token : tokens) {
        switch(index++) {
          case 0: break;
          case 1: break;
          case 2: description = token.replaceAll("\\\\,",","); break;
          default: prereqStrings.add(token);
        }
      }
      Courses.add(this);
    }
    else throw new IllegalArgumentException("Bad data: course without key or title");
  }
  
  boolean link(){
    for (String prereq : prereqStrings) {
      Course c = Courses.get(prereq);
      if (c == null){
        return false;
      }
      prereqs.add(Courses.get(prereq));
    }
    return true;
  }
  
  public String toString(){
    return String.format("%s %s %s %s", key, title, description, prereqStrings);
  }
}