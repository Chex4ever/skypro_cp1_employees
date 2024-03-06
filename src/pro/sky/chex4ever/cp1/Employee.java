package pro.sky.chex4ever.cp1;

import java.util.Objects;

public class Employee {
	public final static Employee NULL_EMPLOYEE = new Employee("nullSurame","nullName","nullPatronymic","nullDivision",0);
    private static int nextId=0;
    private final int id;
    private String name;
    private String surname;
    private String patronymic;
    private String division;
    private int salaryInCents;
    public Employee(String surname, String name, String patronymic, String division, int salaryInCents) {
        this.id=++nextId;
        this.name=name;
        this.surname=surname;
        this.patronymic=patronymic;
        this.division=division;
        this.salaryInCents=salaryInCents;
    }
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public String getSurname() {return this.surname;}
    public void setSurname(String surname) {this.surname = surname;}
    public String getPatronymic() {return this.patronymic;}
    public void setPatronymic(String patronymic) {this.patronymic = patronymic;}
    public String getDivision() {return this.division;}
    public void setDivision(String division) {this.division = division;}
    public int getSalaryInCents() {return this.salaryInCents;}
    public void setSalaryInCents(int salaryInCents) {this.salaryInCents = salaryInCents;}
    public int getId() {return this.id;}
    public String toString() {
        return "id "+this.id+", ФИО: "+this.surname+" "+this.name+" "+this.patronymic+", отдел "+this.division+", оклад "+this.salaryInCents/100f;
    }
	@Override
	public int hashCode() {
		return Objects.hash(surname, name, patronymic, division, salaryInCents);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Employee other = (Employee) obj;
		return Objects.equals(division, other.division) && Objects.equals(name, other.name)
				&& Objects.equals(patronymic, other.patronymic) && salaryInCents == other.salaryInCents
				&& Objects.equals(surname, other.surname);
	}
    
}

