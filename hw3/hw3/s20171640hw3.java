package hw3;

class Employee{
	private long id;
	private String name;
	private int age;
	
	public Employee(long e_id, String e_name, int e_age){
		id = e_id;
		name = e_name;
		age = e_age;
	}
	
	public void e_print(){
		System.out.format("%03d", id);
		System.out.print(", "+name+", "+age);
	}
}

class Manager extends Employee{
	private String department;
	
	public Manager(long m_id, String m_name, int m_age, String m_dpm) {
		super(m_id, m_name, m_age);
		department = m_dpm;
	}
	
	public void m_print(){
		e_print();
		System.out.print(", "+department);
	}
}

public class s20171640hw3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Employee[] e = {new Employee(1, "John", 27), new Employee(2, "Eujin", 25),
				new Employee(3, "Alex", 26), new Employee(4, "Jenny", 23), new Employee(5, "Tom", 25)};
		Manager[] m = {new Manager(1, "Andy", 33, "Marketting"), new Manager(2, "Kate", 30, "Sales")};
		int i;
		
		System.out.println("===Employee===");
		for (i = 0; i < e.length; i++) {
			System.out.print("[e");
			e[i].e_print();
			System.out.println("]");
		}
		System.out.println("===Manager===");
		for (i = 0; i < m.length; i++) {
			System.out.print("[m");
			m[i].m_print();
			System.out.println("]");
		}
	}

}
