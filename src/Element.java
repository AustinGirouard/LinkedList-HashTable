package src;

// self referential architecture
class Element { 
  String keyword;
  Record head;

  Element(String k, Record r){
    keyword = k;
    head = r;
  }
}
