package src;

import java.util.*;
import java.io.*;

public class HashTableTest
{
    BufferedReader reader;
    HashTableBuilder table;
    int tableSize;

    public static void main(String[] args)
    {
        HashTableTest test = new HashTableTest("datafile.txt");
        test.table.print();

        // Test delete function for "medical" keyword
        test.table.delete("medical");
        test.table.print();
        
        // Test delete function for "medical" keyword
        test.table.delete("image-retrieval");
        test.table.delete("image-management");
        test.table.print();
    }

    /*
     * HashTableTest constructor.
     * Reads in all data from datafile.txt and inserts into HashTableBuilder.
     */
    HashTableTest(String filename)
    {
        try
        {
            // need to look at BufferedReader
            reader = new BufferedReader(new FileReader(filename));
            ArrayList<String> tempArray = new ArrayList<String>();
            FileData fd;

            // While the datafile is not empty of records...
            while ((fd = this.readNextRecord()) != null) 
            {
                // Add keywords to tempArray to prevent duplicates
                for (int i = 0; i < fd.keywords.length; i++)
                {
                    if (!tempArray.contains(fd.keywords[i]))
                    {
                        tempArray.add(fd.keywords[i]);
                        tableSize++;
                    }
                }
            }

            // Create a HashTable
            // Size = prime following tableSize * 2
            table = new HashTableBuilder(tableSize);

            // Set reader to the beginning of the file again to insert Elements into HashTable
            reader = new BufferedReader(new FileReader(filename));
            /* READS DATAFILE.TXT INTO DATASTRUCTURE  */
            while ((fd = readNextRecord()) != null)
            {
                for (int i = 0; i < fd.keywords.length; i++)
                {
                    // Insert each element
                    table.insert(fd.keywords[i], fd);
                }
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                if (reader != null) reader.close();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /*
     * This function reads the next record in the datafile.
     * Stores in fd.
     */
    public FileData readNextRecord()
    {
        if(reader == null)
        {
            System.out.println("Error: You must open the file first.");
            return null;
        }

        else
        {
            FileData readData;

            try
            {
                String data = reader.readLine();
                if(data == null) return null;

                //             FileData(      int id,            String title,      String author,     int keywordCount)
                readData = new FileData(Integer.parseInt(data), reader.readLine(), reader.readLine(), Integer.parseInt(reader.readLine()));
                
                for (int i = 0; i < readData.keywords.length; i++)
                {
                    readData.keywords[i] = reader.readLine();
                }
                
                String space = reader.readLine();
                if((space != null) && (!space.trim().equals("")))
                {
                    System.out.println("Error in file format");
                    return null;
                }
            }

            catch(NumberFormatException e)
            {
                System.out.println("Error Number Expected! ");
                return null;
            }

            catch(Exception e)
            {
                System.out.println("Fatal Error: " + e);
                return null;
            }

            return readData;
        }
    }
}
