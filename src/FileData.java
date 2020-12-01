package src;

class FileData
{
    /** Class: FileData
     *  Contains the content of a record found in the input file. Each
     *  FileData object contains exactly one record. An object of this
     *  type will be returned by readNextRecord(..) function on successful
     *  read.
     *  Fields:
     *  id : ID of the record
     *  title : contains the title of the paper
     *  author: contains the author of the paper
     *  keywords is an array of all keywords related to that paper.
     */

  	int id;
  	String title;
  	String author;
  	String keywords[];

	  /* Constructor */
    FileData(int id, String title, String author, int keywordCount) 
    {
		this.id = id;
        this.title = title;
        this.author = author;
        keywords = new String[keywordCount];

        for(int i = 0; i < keywords.length; i++)
        {
          keywords[i] = null;
        }
    }
}
