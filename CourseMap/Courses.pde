static class Courses {
  static HashMap<String, Course> courses = new HashMap<String, Course>();
  
  static void add(Course c){
    courses.put(c.key, c);
  }
  
  static Course get(String key) {
    return courses.get(key);
  }
  
  static boolean link(){
    for (Course c : courses.values()){
      if (!c.link()){
        return false;
      }
    }
    println("Linked Successfully");
    return true;
  }
}