package ibm.training;

public class Book {
	private String title;
	private String author;
	private Boolean available;
	
	public Book(String title, String author, boolean available) {
		this.title = title;
		this.author = author;
		this.available = available;
	}
	
	public void borrowBook() {
		if(available) {
			this.available = false;
		}else {
			System.out.println(this.title + " is already been borrowed.");
		}
	}
	
	public void returnBook() {
		this.available = true;
		System.out.println("Book with title " + title + " successfully returned.");
	}
	
	public void getInfo() {
		System.out.print("Title: " + this.title);
		System.out.print(" Author: " + this.author);
		System.out.println(" Availability: " + this.available);
	}
	
	public String getTitle() {
		return this.title;
	}
}
