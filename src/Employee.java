public class Employee {
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
        return this.id+" "+this.surname+" "+this.name+" "+this.patronymic+" "+this.division+" "+this.salaryInCents/100f;
    }
}

