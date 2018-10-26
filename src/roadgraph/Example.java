package roadgraph;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Example class to illustrate the use of PriorityQueue
 * @author Solange U. Gasengayire
 */
public class Example {
	
	/**
	 * Application entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		
		Person mark = new Person("Mark", "Knopfler");
		Person debbie = new Person("Debbie", "Harry");		
		Person chrissie = new Person("Chrissie", "Hynde");
		Person george = new Person("George", "Harrison");
		
		PriorityQueue<Person> artists = new PriorityQueue<>(4);
		artists.add(debbie);
		artists.add(george);
		artists.add(mark);
		artists.add(chrissie);
		
		System.out.println("Before any changes");
		System.out.println("******************");
		printQueue(artists);
				
		debbie.firstname = "Ann";
		System.out.println("\nAfter change to element of the queue");
		System.out.println("************************************");
		printQueue(artists);
		
		artists.add(debbie);
		System.out.println("\nAfter adding an already present element to the queue");
		System.out.println("****************************************************");
		printQueue(artists);
		
		debbie.firstname = "Ann-Debbie";
		System.out.println("\nAfter 2nd change to element of the queue");
		System.out.println("****************************************");
		printQueue(artists);
		
	}
	
	/**
	 * Print content of a queue
	 * @param queue
	 */
	private static void printQueue(Queue<Person> queue) {
		Iterator<Person> it = queue.iterator();
		while (it.hasNext()) {
			System.out.println("element of queue is: " + it.next());
		}
	}
}

/**
 * Inner class to represent a Person
 * @author Solange U. Gasengayire
 */
class Person implements Comparable<Person> {
	String firstname;
	String surname;
	
	Person(String first, String name) {
		firstname = first;
		surname = name;
	}
	
	@Override
	public int compareTo(Person o) {
		return firstname.compareTo(o.firstname);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Person))
            return false;
		
        Person n = (Person) obj;
        return n.firstname.equals(firstname) 
        		&& n.surname.equals(surname);
	}

	@Override
	public String toString() {
		return firstname + " " + surname;
	}
	
}