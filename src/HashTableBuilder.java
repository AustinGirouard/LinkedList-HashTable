package src;

class HashTableBuilder
{
    Element[] hashTable;
    int tableSize;

    /*
     * HashTableBuilder constructor.
     * Creates a HashTable of size nextPrime(numKeyWords * 2)
     */
    HashTableBuilder(int numKeyWords) 
    {
        // Set tableSize and hashTable
        tableSize = nextPrime(numKeyWords * 2);
        hashTable = new Element[tableSize];
    }


    /*
     * This function creates a new element.
     */
    private Element createNewElement(String keyword, Record recordToAdd) 
    {
        return new Element(keyword, recordToAdd);
    }


    /*
     * Helper function for insert(String keyword, Record recordToAdd)
     * If the keyword already exists, the record is added directly,
     * if the keyword does not exist, the main insert function is called.
     */
    void insert(String keyword, FileData fd) 
    {
        Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
        int index = find(keyword);
        if (index == -1)
        {
            insert(keyword, recordToAdd);
        }

        else 
        {
            //System.out.printf("%s: %d\n", keyword, index);
            recordToAdd.next = hashTable[index].head;
            hashTable[index].head = recordToAdd;
        }
    }


    /*
     * This method adds the keyword to the HashTable and creates a new
     * linkedList at that index.
     */
    private void insert(String keyword, Record recordToAdd)
    {
        // Convery keyword to integer representation
        int key = convertStringToInt(keyword);

        // Use modulo arithmetic to find index
        int index = key % tableSize;

        // Set probe integer for collision handler
        int probe = 1;

        // While collisions occur, rehash using quadratic probing.
        // Prints all collisions to console.
        while (hashTable[index] != null)
        {
            System.out.printf("%s conflicts with %s at index: %d\n", keyword, hashTable[index].keyword, index);
            index = getNextQuadProbIndex(key, probe++);
        }

        // When open index is found, create new Element
        hashTable[index] = createNewElement(keyword, recordToAdd);
    }


    /*
     * This function deletes a keyword from the HashTable.
     * The index of the keyword being removed is returned.
     * If the keyword is not found, return -1.
     */
    public int delete(String keyword)
    {
        // Locate index of keyword to be deleted
        int index = find(keyword);

        // If the keyword is found, remove from HashTable
        if(index != -1)
        {
            hashTable[index].head = null;
            hashTable[index] = null;
        }

        // Return index of keyword deleted.
        // If keyword not found, returns -1.
        return index;
    }


    /*
     * This function returns the index of the keyword in the HashTable.
     * If the keyword is not found, returns -1.
     */
    int find(String keyword)
    {
        // Convery keyword to integer representation
        int key = convertStringToInt(keyword);

        // Use modulo arithmetic to find index
        int index = key % tableSize;

        // Set probe integer for collision handler
        int probe = 1;

        // While collisions occur, rehash using quadratic probing
        while (hashTable[index] != null)
        {
            if (hashTable[index].keyword.compareTo(keyword) == 0)
            {
                return index;
            }

            index = getNextQuadProbIndex(key, probe++);
        }

        return -1;
    }


    /*
     * This function probes for the next index in the case of a collision
     * on insertion using quadratic probing.
     */
    private int getNextQuadProbIndex(int key, int probe)
    {
        return ((key % tableSize) + (int) Math.pow(probe, 2)) % tableSize;
    }


    /*
     * This function converts the keyword to its integer representation
     * by adding ASCII values of all characters.
     */
    private int convertStringToInt(String keyword)
    {
        int sum = 0;
        for (int i = 0; i < keyword.length(); i++)
        {
            sum += (int) keyword.charAt(i);
        }

        return sum;
    }
    

    /*
     * This function finds the first prime number after 
     * an integer (num).
     */
    private int nextPrime(int num)
    {
        num++;
        for (int i = 2; i < num; i++)
        {
            if(num % i == 0)
            {
                num++;
                i = 2;
            }

            else
            {
                continue;
            }
        }

        return num;
    }


    /*
     * This function prints all indexes and respective linkedLists
     * in the HashTable to console.
     */
    public void print()
    {
        int printWrap = 0;
        System.out.print("\n\n\n");
        
        for (int index = 0; index < tableSize; index++)
        {
            if (hashTable[index] != null)
            {
                // Print index, keyword, and integer representation of keyword
                System.out.printf("index [%d]: %s (E ---> int): %d\n", index, hashTable[index].keyword, convertStringToInt(hashTable[index].keyword));
                
                // Point rec to head of linkedList of the current keyword
                Record rec = hashTable[index].head;

                System.out.print("\t\t");

                // Print all records in linkedList
                while(rec != null)
                {
                    if (++printWrap % 3 == 0)
                    {
                        System.out.println();
                        System.out.print("\t\t");
                    }

                    //System.out.printf("\t%s\n",r.title);
                    System.out.printf("%d|%s|%s ---> ", rec.id, rec.author, rec.title);
                   
                    rec = rec.next;
                    //printWrap++;
                }
                
                System.out.println("null\n");
                printWrap = 0;
            }
        }
   }
}
