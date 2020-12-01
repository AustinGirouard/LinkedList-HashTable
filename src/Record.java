package src;

public class Record
{
    int id;
    String title;
    String author;
    Record next;
  
    Record(int i, String t, String a, Record r)
    {
      this.id = i;
      this.title = t;
      this.author = a;
      this.next = r;
    }
  }
  