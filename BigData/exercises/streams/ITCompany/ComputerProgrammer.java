package BigData.exercises.streams.ITCompany;

import java.util.Set;

public class ComputerProgrammer implements Comparable<ComputerProgrammer> {

    private int id;
    private String name;
    private String surname;
    private String department;
    private int salary;
    private Set<String> skills;

    public ComputerProgrammer(int id, String name, String surname, String department, int salary, Set<String> skills) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.salary = salary;
        this.skills = skills;
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComputerProgrammer other = (ComputerProgrammer) obj;
        if (id != other.id)
            return false;
        return true;
    }


    @Override
    public int compareTo(ComputerProgrammer o) {
        return this.id - o.id;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }


    public String getDepartment() {
        return department;
    }


    public int getSalary() {
        return salary;
    }


    @Override
    public String toString() {
        return "ComputerProgrammer [id=" + id + ", name=" + name + ", surname=" + surname + ", department=" + department
                + ", salary=" + salary + ", skills=" + skills + "]";
    }


    public Set<String> getSkills() {
        return skills;
    }

}