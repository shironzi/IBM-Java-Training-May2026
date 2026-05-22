package ibm.training;

import java.util.ArrayList;

public class Library {
	private ArrayList<Book> books = new ArrayList<>();
	
	public void addBook(Book b) {
		this.books.add(b);
	}
	
	public void showAllBooks() {
		if(books.size() < 1) {
			System.out.println("There was no books on library");
		}
		
		for(Book book: books) {
			book.getInfo();
		}
		
		System.out.println("------------------------------------------------------------");
	}
	
	public void borrowBook(String title) {
		Boolean isFound = false;
		for(Book book: books) {
			if(book.getTitle().equalsIgnoreCase(title)) {
				book.borrowBook();
				isFound = true;
			}
		}
		
		if(!isFound) {
			System.out.println("Book with title " + title + " is not found.");
		}
	}
	
	public void returnBook(String title) {
		Boolean isFound = false;
		for(Book book: books) {
			if(book.getTitle().equals(title)) {
				book.returnBook();
				isFound = true;
			}
		}
		
		if(!isFound) {
			System.out.println("Book with title " + title + " is not found.");
		}
	}
}
