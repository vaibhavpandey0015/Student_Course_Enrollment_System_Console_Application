import java.util.*;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Student{
	public static int r;
	public int rollNo;
	public String firstName;
	public String lastName;
	public String dob;
	public int courseCount=0;
	public int courseid;
	public String courseName;
	public int fees;
	Student(String firstName, String lastName, String dob){
		r++;
		this.rollNo=r;
		this.firstName=firstName;
		this.lastName=lastName;
		this.dob=dob;
		System.out.println(this.rollNo+" "+this.firstName+" "+this.lastName+" "+this.dob);
	}
	String getName() {
		return this.firstName;
	}
}
class Course{
	public static int c;
	public int courseid;
	public String courseName;
	public int fees;
	public String category;
	Course(String courseName){
		c++;
		this.courseid=c;
		this.courseName=courseName;
		
	}
}
class Science extends Course {
	Science(String courseName,int fees) {
		super(courseName);
		this.fees = fees + (fees/100)*10;
		System.out.println(this.courseid+" "+this.courseName+" "+this.fees +" Science");
	}
}

class Commerce extends Course {
	Commerce(String courseName,int fees) {
		super(courseName);
		this.fees = fees ;
		System.out.println(this.courseid+" "+this.courseName+" "+this.fees +" Commerce");
	}
}

class Arts extends Course {
	Arts(String courseName,int fees) {
		super(courseName);
		this.fees = fees + (fees/100)*5;
		System.out.println(this.courseid+" "+this.courseName+" "+this.fees +" Arts");
	}
}


public class Main {

	static Scanner sc = new Scanner(System.in);
	
	static void insertToDataBase(List<Student> student) throws ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/sces";
		String username = "root";
		String password = "root";
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url,username,password);
			String sql = "INSERT INTO student (rollno, firstname, lastName, dob) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			for(Student s1 : student) {
				statement.setInt(1, s1.rollNo);
				statement.setString(2, s1.firstName);
				statement.setString(3, s1.lastName);
				statement.setString(4, s1.dob);
				
				int rows = statement.executeUpdate();
				if(rows > 0) {
					System.out.println("Inserted");
				}
			}
			System.out.println("Details Inserted Successfully...");
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	static void reterveFromDataBase() throws ClassNotFoundException {
		
		String url = "jdbc:mysql://localhost:3306/sces";
		String username = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url,username,password);
			String sql = "SELECT * FROM student";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			System.out.println("Student Details:");
			while(result.next()) {
				int rollno = result.getInt("rollno");
				String firstname = result.getString("firstname");
				String lastname = result.getString("lastname");
				String dob = result.getString("dob");
				System.out.println(rollno + " " + firstname + " " + lastname + " " + dob);
			}
		} catch(SQLException e) {
			System.out.println("Database not connected");
		}
	}

	
	static void courseSetter(Student s, Course c) {
		s.courseid=c.courseid;
		s.courseName=c.courseName;
		s.fees=c.fees;
		System.out.println("Successful");
	}
    
	static void menu() {
		System.out.println("===============================================");
		System.out.println("1.Create New Student Data");
		System.out.println("2. Create New Course Data");
		System.out.println("3. Enroll Student to a Course");
		System.out.println("4. Display Fees of a Student");
		System.out.println("5. Sort Student Data");
		System.out.println("6. Persist Student Data");
		System.out.println("7. Show All Students with courses");
		System.out.println("8. Search Students");
		System.out.println("9. Exit");
		System.out.println("===============================================");
	}
	static int select() {
		int choice;
		System.out.println("Select option from 1-8:");
		choice=sc.nextInt();
		return choice;
	}
	static int valid_DOB(String D) {
		String s[] = D.split("/");
		int d,m,y;
		if(s.length > 3) {
			return 0;
		}
		else {
			try {
				d = Integer.parseInt(s[0]);
				m = Integer.parseInt(s[1]);
				y = Integer.parseInt(s[2]);
				
				if(y%4==0) {
					if(m==2) {
						if(0 < d && d < 30) {
							return 1;
						}
					}
					else {
						if(m==4 || m==6 || m==9 || m==11) {
							if(0 < d && d < 31) {
								return 1;
							}
						}
						else if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12) {
							if(0 < d && d < 32) {
								return 1;
							}
						}
					}
				}
				else {
					if(m==4 || m==6 || m==9 || m==11) {
						if(0 < d && d < 31) {
							return 1;
						}
					}
					else if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12) {
						if(0 < d && d < 32) {
							return 1;
						}
					}
				}
			}
			catch(Exception e) {
				return 0;
			}
		}
		return 0;
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		int choice=1;
		String firstName, lastName, dob,courseName;
		int fees,type,sorting;
		Student s1 = null;
		Course c1 = null;
		ArrayList<Student> student = new ArrayList<Student>();
		System.out.println("===============================================");
		System.out.println("Student Course Enrollment System");
		System.out.println("===============================================");
		System.out.println("===========================================");
		System.out.println("Registered Students:");
		student.add(new Student("vaibhav","pandey","15/12/1999"));
		student.add(new Student("shailesh","pandey","20/08/1998"));
		student.add(new Student("nakul","gopal","10/02/1999"));
		System.out.println("===========================================");
		ArrayList<Course> course = new ArrayList<Course>();
		System.out.println("===========================================");
		System.out.println("Available course details:");
		course.add(new Science("Mechnics", 199));
		course.add(new Commerce("Econimics", 299));
		course.add(new Arts("Drawing", 149));
		System.out.println("===========================================");
		while(choice!=9) {
			menu();
			choice=select();
			
			System.out.println("Your selected option"+choice);
			if(choice==9) {
				break;
			}
			if(choice==1) {
				System.out.println("Enter Student First Name:");
				firstName=sc.next();
				System.out.println("Enter Student Last Name:");
				lastName=sc.next();
				System.out.println("Enter Date of Birth of Student:(dd/mm/yyyy)");
				dob=sc.next();
				if(valid_DOB(dob)==1) {
					student.add(new Student(firstName,lastName,dob));
				}else {
					System.out.println("Enter valid Date of Birth of Student:(dd/mm/yyyy)");
				}
				
			}
			else if(choice==2) {
				System.out.println("Enter Course Name:");
				courseName=sc.next();
				System.out.println("Enter Course fees:");
				fees=sc.nextInt();
				System.out.println("Select type of course:\n1.Science\n2.Commerce\n3.Arts:");
				type=sc.nextInt();
				if(type==1) {
					course.add(new Science(courseName, fees));
				}
				else if(type==2) {
					course.add(new Commerce(courseName, fees));
				}
				else if(type==3) {
					course.add(new Arts(courseName, fees));
				}
				else {
					System.out.println("Select proper course type...");
				}
				
			}
			else if(choice==3) {
				System.out.println("===============================================");
				System.out.println("Available Students list:");
				for(Student s:student) {
					System.out.println(s.rollNo+" "+s.firstName+" "+s.lastName);
				}
				System.out.println("Enter Student Roll No for assign course:");
				int st=sc.nextInt();
				
				System.out.println("===============================================");
				System.out.println("Available Course list:");
				for(Course c:course) {
					System.out.println(c.courseid+" "+c.courseName+" "+c.fees);
				}
				System.out.println("Enter Course ID to assign:");
				int cr=sc.nextInt();
				System.out.println("===============================================");
				for(Student s:student) {
					if(s.rollNo==st) {
						 s1=s;	 
					}
				}	
				for(Course c:course) {
					if(c.courseid==cr) {
						 c1=c;
					}
				}	
							
				if(s1.courseCount==0) {
					courseSetter(s1, c1);
					System.out.println("Assign course succesfully...");
				}
				else {
					System.out.println("Already one course is Assigned...");
				}
				System.out.println("===============================================");
				
				System.out.println("Student Details with course:");
				for(Student s:student) {
					System.out.println(s.rollNo+" "+s.firstName+" "+s.lastName+" "+s.courseid+" "+s.courseName+" "+s.fees);
				}
				System.out.println("===============================================");

			}
			else if(choice==4) {
				System.out.println("===============================================");
				System.out.println("Student Details with Fees:");
				for(Student s:student) {
					System.out.println(s.rollNo+" "+s.firstName+" "+s.lastName+" "+s.courseid+" "+s.courseName+" "+s.fees);
				}
				System.out.println("===============================================");
			}
			else if(choice==5) {
				System.out.println("===============================================");
				System.out.println("Sort Based On :\n1.Student Names \n2.Student Roll Number\n");
				sorting = sc.nextInt();
				if(sorting == 1) {
					
					List<Student> sortedNameList = student.stream().sorted(Comparator.comparing(Student::getName)).collect(Collectors.toList());
					System.out.println("===============================================");
					System.out.println("Sort Student Details Based on Student Names ");
					for(Student s : sortedNameList) {
						System.out.println(s.rollNo+" "+s.firstName+" "+s.lastName+" "+s.courseid+" "+s.courseName+" "+s.fees);
					}
					System.out.println("===============================================");
					sortedNameList.clear();
				}
				else if(sorting == 2) {
					System.out.println("===============================================");
					System.out.println("Sort Student Details Based on Student Roll no. ");
					for(Student s : student) {
						System.out.println(s.rollNo+" "+s.firstName+" "+s.lastName+" "+s.courseid+" "+s.courseName+" "+s.fees);
					}
					System.out.println("===============================================");
				}
				else {
					System.out.println("Select proper sorting option..");
					System.out.println("===============================================");
				}
			}
			else if(choice==6) {
				System.out.println("Select one ot the option:");
				System.out.println("1.Insert data to database");
				System.out.println("2.Retrive data from database:");
				int n=sc.nextInt();
				if(n==1) {
					insertToDataBase(student);
				}
				else if(n==2) {
					reterveFromDataBase();
				}
				}
			else if(choice==7) {
				System.out.println("===============================================");
				System.out.println("Student Details with Fees:");
				for(Student s:student){
					System.out.println(s.rollNo+" "+s.firstName+" "+s.lastName+" "+s.courseid+" "+s.courseName+" "+s.fees);
				}
				System.out.println("===============================================");
			}
			else if(choice==8) {
				System.out.println("===============================================");
				System.out.println("Enter the Name of student for searching:");
				String name=sc.next();
				int flag=0;
				System.out.println("===============================================");
				for(Student s: student) {
					if(s.firstName.equals(name)) {
						System.out.println("Student found...");
						System.out.println(s.rollNo+" "+s.firstName+" "+s.lastName+" "+s.courseid+" "+s.courseName+" "+s.fees);
					    flag=1;
					    break;
					}
				}
				if(flag==0) {
					System.out.println("Student not found...");
				}
				System.out.println("===============================================");
				
			}
			
			

		}
				
	}

}
