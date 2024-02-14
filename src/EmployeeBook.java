import java.util.Random;

public class EmployeeBook {
    private Employee[] employees;
    public EmployeeBook() {
        String[] names = {"Иван", "Василий", "Евгений", "Александр", "Петр"};
        String[] surnames = {"Иванов", "Васильев", "Евгеньев", "Александров", "Петров"};
        String[] patronymics = {"Иванович", "Васильевич", "Евгеньевич", "Александрович", "Петрович"};
        String[] divisions = {"1", "2", "3", "4", "5"};
        int salaryInCentsMin = 45000_00;
        int salaryInCentsMax = 145000_00;
        employees = new Employee[10];
        for (int i = 0; i < employees.length; i++) {
            employees[i] = new Employee(
                    getRandom(surnames),
                    getRandom(names),
                    getRandom(patronymics),
                    getRandom(divisions),
                    generateRandomIntIntRange(salaryInCentsMin, salaryInCentsMax));
        }
    }
    public Employee getEmployee(int id) {return this.employees[id];}
    private String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    private int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void printAll() {
        System.out.println("\nВсе сотрудники организации:\n    ID Фамилия Имя Отчество Отдел Зарплата");
        for (Employee employee : this.employees) {
            System.out.printf("    %s",employee);
        }
    }

    public void printAllFromDivision(String division) {
        System.out.printf("%nВсе сотрудники отдела %s:%n    ID Фамилия Имя Отчество Зарплата", division);
        for (Employee employee : employees) {
            if (employee.getDivision().equals(division)) {
                System.out.printf("%n    %d %s %s %s %.2f",
                        employee.getId(),
                        employee.getSurname(),
                        employee.getName(),
                        employee.getPatronymic(),
                        employee.getSalaryInCents() / 100f);
            }
        }
    }

    public void printFIO() {
        System.out.printf("%nФИО всех сотрудников:%n    Фамилия Имя Отчество");
        for (Employee employee : employees) {
            System.out.printf("%n    %s %s %s",
                    employee.getSurname(),
                    employee.getName(),
                    employee.getPatronymic());
        }
    }

    public int salariesSumInCents() {
        int sum = 0;
        for (Employee employee : employees) {
            sum += employee.getSalaryInCents();
        }
        return sum;
    }
    public int salariesSumInCentsFromDivision(String division) {
        int sum = 0;
        for (Employee employee : employees) {
            if (employee.getDivision().equals(division)){
                sum += employee.getSalaryInCents();
            }
        }
        return sum;
    }
    public void salariesIndexing(float salaryIndex) {
        for (Employee employee : employees) {
            employee.setSalaryInCents((int) (employee.getSalaryInCents() * (salaryIndex + 1f)));
        }
    }

    public void salariesIndexingInDivision(String division, float salaryIndex) {
        for (Employee employee : employees) {
            if (employee.getDivision().equals(division)) {
                employee.setSalaryInCents((int) (employee.getSalaryInCents() * (salaryIndex + 1f)));
            }
        }
    }

    public Employee employeeWithMinSalary() {
        int employeeIndex = 0;
        int minSalaryInCents = employees[0].getSalaryInCents();
        for (int i = 0; i < employees.length; i++) {
            if (minSalaryInCents > employees[i].getSalaryInCents()) {
                minSalaryInCents = employees[i].getSalaryInCents();
                employeeIndex = i;
            }
        }
        return employees[employeeIndex];
    }
    public Employee employeeWithMinSalaryFromDivision(String division) {
        int employeeIndex = 0;
        int minSalaryInCents = employees[0].getSalaryInCents();
        for (int i = 0; i < employees.length; i++) {
            if (minSalaryInCents > employees[i].getSalaryInCents() && employees[i].getDivision().equals(division)) {
                minSalaryInCents = employees[i].getSalaryInCents();
                employeeIndex = i;
            }
        }
        return employees[employeeIndex];
    }

    public Employee employeeWithMaxSalary() {
        int employeeIndex = 0;
        int maxSalaryInCents = employees[0].getSalaryInCents();
        for (int i = 0; i < employees.length; i++) {
            if (maxSalaryInCents < employees[i].getSalaryInCents()) {
                maxSalaryInCents = employees[i].getSalaryInCents();
                employeeIndex = i;
            }
        }
        return employees[employeeIndex];
    }

    public Employee employeeWithMaxSalaryFromDivision(String division) {
        int employeeIndex = 0;
        int maxSalaryInCents = employees[0].getSalaryInCents();
        for (int i = 0; i < employees.length; i++) {
            if (maxSalaryInCents < employees[i].getSalaryInCents() && employees[i].getDivision().equals(division)) {
                maxSalaryInCents = employees[i].getSalaryInCents();
                employeeIndex = i;
            }
        }
        return employees[employeeIndex];
    }

    public int averageSalaryInCents() {
        return salariesSumInCents() / employees.length;
    }
    public int averageSalaryInCentsFromDivision(String division) {
        return salariesSumInCentsFromDivision(division) / employeesFromDivision(division).length;
    }

    public Employee[] employeesFromDivision(String division) {
        int resultLength = 0;
        int[] resultIndexes = new int[employees.length];
        for (int i = 0; i < employees.length; i++) {
            if (employees[i].getDivision().equals(division)) {
                resultIndexes[resultLength++] = i;
            }
        }
        Employee[] resultArray = new Employee[resultLength];
        for (int i = 0; i < resultLength; i++) {
            resultArray[i] = employees[resultIndexes[i]];
        }
        return resultArray;
    }

    public void findAllEmployeesWithLessSalaryInCents(int salaryInCents) {
        System.out.printf("%nВсе сотрудники с зарплатой меньше %.2f: ", salaryInCents / 100f);
        for (Employee employee : employees) {
            if (employee.getSalaryInCents() < salaryInCents) {
                System.out.printf("%n    %d %s %s %s %.2f",
                        employee.getId(),
                        employee.getSurname(),
                        employee.getName(),
                        employee.getPatronymic(),
                        employee.getSalaryInCents() / 100f);
            }
        }
    }

    public void findAllEmployeesWithMoreOrEqualSalaryInCents(int salaryInCents) {
        System.out.printf("%nВсе сотрудники с зарплатой не меньше, чем %.2f: ", salaryInCents / 100f);
        for (Employee employee : employees) {
            if (employee.getSalaryInCents() >= salaryInCents) {
                System.out.printf("%n    %d %s %s %s %.2f",
                        employee.getId(),
                        employee.getSurname(),
                        employee.getName(),
                        employee.getPatronymic(),
                        employee.getSalaryInCents() / 100f);
            }
        }
    }
}
/*Привести структуру проекта к ООП.

+1. Создать класс EmployeeBook.
+2. Перенести хранилище сотрудников в него (массив), закрыть к нему доступ извне (сделать приватным).
+3. Все статические методы по работе с массивом перенести в этот класс и сделать нестатическими.
4. Добавить несколько новых методов:
    1. Добавить нового сотрудника (создаем объект, заполняем поля, кладем в массив).
    Нужно найти свободную ячейку в массиве и добавить нового сотрудника в нее. Искать нужно всегда с начала, так как
    возможно добавление в ячейку удаленных ранее сотрудников.
    2. Удалить сотрудника (находим сотрудника по Ф. И. О. и/или id, обнуляем его ячейку в массиве).
5. Изменить сотрудника (получить сотрудника по Ф. И. О., модернизировать его запись):
    1. Изменить зарплату.
    2. Изменить отдел.
    Придумать архитектуру. Сделать или два метода, или один, но продумать его.
6. Получить Ф. И. О. всех сотрудников по отделам (напечатать список отделов и их сотрудников).*/