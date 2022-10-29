package exercises.streams.itcompany_orig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComputerProgrammer implements Comparable<ComputerProgrammer>{
	private static int nextId=0;
	private int id;
	private String name;
	private String surname;
	private String department;
	private int salary;
	private Set<String> skills;
	
	public ComputerProgrammer(String n, String s, String d, int sa, Set<String> ss){
		this.id=nextId++;
		this.name = n;
		this.surname = s;
		this.department = d;
		this.salary = sa;
		this.skills = ss;
	}
	
	private ComputerProgrammer(int i, String n, String s, String d, int sa, Set<String> ss){
		this.id=i;
		this.name = n;
		this.surname = s;
		this.department = d;
		this.salary = sa;
		this.skills = ss;
	}
	
	public ComputerProgrammer(String n, String d, int sa){
		this.id=nextId++;
		this.name = n;
		this.surname = "";
		this.department = d;
		this.salary = sa;
		this.skills = new HashSet<String>();
	}
	public int getSalary(){return salary;}
	public String getDepartment(){return department;}
	public String getName(){return name;}
	
	
	public int getId() {
		return id;
	}
	public String getSurname() {
		return surname;
	}
	public Set<String> getSkills() {
		return skills;
	}

	public boolean equals(Object arg0) {
		if(arg0 instanceof ComputerProgrammer)
			return ((ComputerProgrammer)arg0).getId()==this.getId();
		return false;
	}
	public String toString() {
		return id+","+name+" "+surname+", dep:"+ department+", "+salary+"$ ,"+skills.toString();
	}
	
	public String toStringSimple() {
		return id+","+name+","+surname+","+ department+","+salary+","+skills.toString();
	}
	
	public int compareTo(ComputerProgrammer o) {
		return this.id-o.getId();
	}
	
	public static ComputerProgrammer parse(String x) {
		//0,bmapqopm,ypukgubm,B,3488,[C++, Spark]
		String[] part = x.split("\\[");
		String[] field = part[0].split(",");
		part[1] = part[1].substring(0, part[1].length()-1).replaceAll("\\s+","");
		String[] skills = part[1].split(",");
		return new ComputerProgrammer(Integer.parseInt(field[0]), 
				field[1], field[2], field[3], Integer.parseInt(field[4]), 
				new HashSet<String>(Arrays.asList(skills)));
	}

	public static List<ComputerProgrammer> readFile(String path) throws IOException {
		List<ComputerProgrammer> ret = new ArrayList<>(200);
		File f = new File(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String line = br.readLine();

		while(line!=null) {
			ret.add(parse(line));
			line=br.readLine();
		}

		br.close();

		return ret;
	}
	
}

