package ibm.training;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Creating book class
		Book book1 = new Book("Atomic Habits", "author 1", true);
		Book book2 = new Book("Harry Potter", "author 2", true);
		Book book3 = new Book("Marvel", "author 3", true);
		
		// Creating library class
		Library library = new Library();
		
		library.showAllBooks();
		
		// adding books to library
		library.addBook(book1);
		library.addBook(book2);
		library.addBook(book3);
		
		library.showAllBooks();
		
		// Borrowing books
		library.borrowBook("Book1");
		library.borrowBook("Atomic Habits");
		library.borrowBook("Atomic Habits");
		
		library.showAllBooks();
		
		// returning libraries
		library.returnBook("Atomic");
		library.returnBook("Atomic Habits");
		
		library.showAllBooks();
	}

}
