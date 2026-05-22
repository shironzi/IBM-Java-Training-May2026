package ibm.training.day4;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<>();
		
		
		employees.add(new Employee("Alice", "IT", 55000));
        employees.add(new Employee("Bob", "Finance", 60000));
        employees.add(new Employee("Alice", "HR", 52000)); // duplicate name
        employees.add(new Employee("Ken", "IT", 60000));
        employees.add(new Employee("Maria", "HR", 50000));
        employees.add(new Employee("John", "Finance", 70000));
        employees.add(new Employee("Ken", "Finance", 65000)); // duplicate name
        employees.add(new Employee("Lara", "IT", 62000));
        employees.add(new Employee("Sam", "HR", 48000));
        employees.add(new Employee("Bob", "IT", 59000)); // duplicate name

		HashMap<String, Employee> uniqueEmployee = new HashMap<>();
		
		for(Employee emp: employees) {
			uniqueEmployee.putIfAbsent(emp.getName(), emp);
		}
		
		System.out.println("===== Unique Employees =====");
		
		for(Employee emp: uniqueEmployee.values()) {
			System.out.println(emp.toString());
		}
		
		System.out.println();
		System.out.println("===== Employees By Department =====");
		
		HashMap<String, List<Employee>> deptMap = new HashMap<>();
		
		for(Employee emp: employees) {
			deptMap.putIfAbsent(emp.getDepartment(), new ArrayList<>());
			deptMap.get(emp.getDepartment()).add(emp);
		}
		
		for(String dept: deptMap.keySet()) {
			System.out.println(dept + ":");
			
			for(Employee emp: deptMap.get(dept)) {
				System.out.println("    - " + emp.toString());
			}
			
			System.out.println();
		}
		
		HashMap<String, Employee> highestPaidEmp = new HashMap<>();
		
		for(String dept: deptMap.keySet()) {
			for(Employee emp: deptMap.get(dept)) {
				highestPaidEmp.putIfAbsent(dept, emp);
				
				if(highestPaidEmp.get(dept).getSalary() < emp.getSalary()) {
					highestPaidEmp.replace(dept, emp);
				}
			}
		}
		
		System.out.println("===== Highest Paid Per Department =====");
		
		for(String name: highestPaidEmp.keySet()) {
			Employee emp = highestPaidEmp.get(name);
			System.out.println(name + ": " + emp.toString());
		}
		
		System.out.println();
		System.out.println("===== Employees Sorted by Salary (Desc) =====");
		
		// sort desc order
		employees.sort((a, b) -> Double.compare(b.getSalary(), a.getSalary()));
	
		for(Employee emp: employees) {
			System.out.println(emp.toString());
		}
		
		System.out.println();
		System.out.println("===== Unique Salaries (Sorted) =====");
		
		TreeSet<Double> uniqueSalaries = new TreeSet<>();;
		
		for(Employee emp: employees) {
			uniqueSalaries.add(emp.getSalary());
		}
		
		for(Double salary: uniqueSalaries) {
			System.out.println("$"+salary);
		}
		
	}

}
