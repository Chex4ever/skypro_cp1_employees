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

//Привести структуру проекта к ООП.
//
//1. Создать класс EmployeeBook.
//2. Перенести хранилище сотрудников в него (массив), закрыть к нему доступ извне (сделать приватным).
//3. Все статические методы по работе с массивом перенести в этот класс и сделать нестатическими.
//4. Добавить несколько новых методов:
//    1. Добавить нового сотрудника (создаем объект, заполняем поля, кладем в массив).
//    Нужно найти свободную ячейку в массиве и добавить нового сотрудника в нее. Искать нужно всегда с начала, так как возможно добавление в ячейку удаленных ранее сотрудников.
//    2. Удалить сотрудника (находим сотрудника по Ф. И. О. и/или id, обнуляем его ячейку в массиве).
//5. Изменить сотрудника (получить сотрудника по Ф. И. О., модернизировать его запись):
//    1. Изменить зарплату.
//    2. Изменить отдел.
//    Придумать архитектуру. Сделать или два метода, или один, но продумать его.
//6. Получить Ф. И. О. всех сотрудников по отделам (напечатать список отделов и их сотрудников).