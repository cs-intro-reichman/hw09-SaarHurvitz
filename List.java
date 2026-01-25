/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likwise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    private Node first;

    // The number of elements in this list
    private int size;
	
    /** Constructs an empty list. */
    public List() {
        first = null;
        size = 0;
    }
    
    /** Returns the number of elements in this list. */
    public int getSize() {
 	      return size;
    }

    /** Returns the CharData of the first element in this list. */
    public CharData getFirst() {
        if(first == null){
            return null;
        }
        return first.cp;
  }    

  public Node getFirstNode() {
    return first;
}

    /** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) {
        CharData cd = new CharData(chr);
        Node nd = new Node(cd, first);
        first = nd;
        size++;
    }
    
    /** GIVE Textual representation of this list. */
    public String toString() {
        if(size == 0) return "()";

        StringBuilder sb = new StringBuilder("(");
        Node node = first;
        
        while (node != null) {
            sb.append(node.cp.toString());
            if(node.next != null){
                sb.append(" ");
            }
            node = node.next;
        }
        return sb.toString() + ")";
    }

    /** Returns the index of the first CharData object in this list
     *  that has the same chr value as the given char,
     *  or -1 if there is no such object in this list. */
    public int indexOf(char chr) {
        Node node = first;
        int index = 0;
        while (node != null) {
            if(node.cp.equals(chr)){
                return index;
            }
            node = node.next;
            index++;
        }
        return -1;
    }

    /** If the given character exists in one of the CharData objects in this list,
     *  increments its counter. Otherwise, adds a new CharData object with the
     *  given chr to the beginning of this list. */
    public void update(char chr) {
        Node node = first;
        
        while (node != null) {
            if(node.cp.equals(chr)){
                node.cp.count++;
                return;
            }
            node = node.next;
        }
            addFirst(chr);
        
    }

    /** GIVE If the given character exists in one of the CharData objects
     *  in this list, removes this CharData object from the list and returns
     *  true. Otherwise, returns false. */
    public boolean remove(char chr) {
        if(first == null){
            return false;
        }
        if(first.cp.equals(chr)){
            first = first.next;
            size--;
            return true;
        }
        Node node = first;
        while (node.next != null) {
            if(node.next.cp.equals(chr)){
                node.next = node.next.next;
                size--;
                return true;
            }
            node = node.next;
        }
        return false;
    }

    /** Returns the CharData object at the specified index in this list. 
     *  If the index is negative or is greater than the size of this list, 
     *  throws an IndexOutOfBoundsException. */
    public CharData get(int index) {
        Node node = first;
        if(index >= size|| index < 0){
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
        else{
            for(int i = 0; i < index; i++){
                node = node.next;
            }
        }
        return node.cp;
    }

    /** Returns an array of CharData objects, containing all the CharData objects in this list. */
    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cp;
    	    current = current.next;
        }
        return arr;
    }

    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {  
	    if (size == 0) return null;
	    Node current = first;
	    int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Returns an iterator that starts in that element
	    return new ListIterator(current);
    }
}