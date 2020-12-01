class Element { // self referential architecture
  String keyword;
  Record head;

  Element(String k, Record r){
    keyword = k;
    head = r;
  }
}