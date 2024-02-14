import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //данные для генерации тестовых значений
        String[] names = {"Иван", "Василий", "Евгений", "Александр", "Петр"};
        String[] surnames = {"Иванов", "Васильев", "Евгеньев", "Александров", "Петров"};
        String[] patronymics = {"Иванович", "Васильевич", "Евгеньевич", "Александрович", "Петрович"};
        String[] divisions = {"1", "2", "3", "4", "5"}; //Ну ведь написано: "должны быть названы от 1 до 5", а не пронумерованы
        int salaryInCentsMin = 45000_00;
        int salaryInCentsMax = 145000_00;

        //создаём фиксированный массив по заданию и заполняем тестовыми значениями
        Employee[] employees = new Employee[10];
        for (int i=0; i<employees.length;i++) {
            employees[i] = new Employee(
                    getRandom(surnames), 
                    getRandom(names), 
                    getRandom(patronymics),
                    getRandom(divisions), 
                    generateRandomIntIntRange(salaryInCentsMin,salaryInCentsMax));
        }

        //1. Получить список всех сотрудников со всеми имеющимися по ним данными
        printAll(employees);
        //2. Посчитать сумму затрат на зарплаты в месяц.
        System.out.printf("%nСумма зарплат: %.2f",salariesSumInCents(employees)/100f);
        //3. Найти сотрудника с минимальной зарплатой.
        System.out.printf("%nСотрудник с минимальной зарплатой: %s",employeeWithMinSalary(employees));
        //4. Найти сотрудника с максимальной зарплатой.
        System.out.printf("%nСотрудник с максимальной зарплатой: %s",employeeWithMaxSalary(employees));
        //5. Подсчитать среднее значение зарплат
        System.out.printf("%nСредняя зарплата: %.2f",averageSalaryInCents(employees)/100f);
        //6. Получить Ф. И. О. всех сотрудников (вывести в консоль)
        printFIO(employees);
        //Part 2
        //1. Проиндексировать зарплату
        float salaryIndex=0.05f;
        System.out.printf("Индексирую зарплату всех сотрудников на %.2f%%",
                salaryIndex*100);
        salariesIndexing(employees,salaryIndex);
        //2. Функции для отделов:
        //Для этого я сделал функцию employeesFromDivision, которая возвращает массив с сотрудниками из нужного отдела
        //и можно применить методы из предыдущих заданий. Почти.
        //Проиндексировать у меня не получилось. Для этого надо чтобы строки массива сотрудников из отдела,
        //содержал ссылки на оригинальный массив, но я так понял это выходит за рамки этого задания
        String selectedDivision=employees[0].getDivision();
        //    2.1. Сотрудника с минимальной зарплатой.
        System.out.printf("%n%nСотрудник с минимальной зарплатой из отдела %s: %n%s",
                selectedDivision,
                employeeWithMinSalary(employeesFromDivision(employees, selectedDivision)));
        //    2.2. Сотрудника с максимальной зарплатой.
        System.out.printf("%n%nСотрудник с максимальной зарплатой из отдела %s: %n    %s",
                selectedDivision,
                employeeWithMaxSalary(employeesFromDivision(employees, selectedDivision)));
        //    2.3. Сумму затрат на зарплату по отделу.
        System.out.printf("%n%nСумма зарплат по отделу %s: %n    %s",
                selectedDivision,
                salariesSumInCents(employeesFromDivision(employees, selectedDivision))/100f);
        //    2.4. Среднюю зарплату по отделу (учесть, что количество людей в отделе отличается от employees.length).
        System.out.printf("%n%nСредняя зарплата по отделу %s:%n    %s",
                selectedDivision,
                averageSalaryInCents(employeesFromDivision(employees, selectedDivision))/100f);
        //    2.5. Проиндексировать зарплату всех сотрудников отдела на процент, который приходит в качестве параметра.
        //salaryIndex=0.05f;
        salariesIndexingInDivision(employees,selectedDivision,salaryIndex);
        //    2.6. Напечатать всех сотрудников отдела (все данные, кроме отдела).
        printAll(employees,selectedDivision);
        //    3.1. Всех сотрудников с зарплатой меньше числа (вывести id, Ф. И. О. и зарплатой в консоль).
        int salaryToFind=100000_00;
        findAllEmployeesWithLessSalaryInCents(employees, salaryToFind);
        //    3.2. Всех сотрудников с зарплатой больше (или равно) числа (вывести id, Ф. И. О. и зарплатой в консоль).
        findAllEmployeesWithMoreOrEqualSalaryInCents(employees, salaryToFind);
    }

    static void printAll(Employee[] employees) {
        System.out.println("\nВсе сотрудники организации:\nID Фамилия Имя Отчество Отдел Зарплата");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
    static void printAll(Employee[] employees,String division) {
        System.out.printf("%nВсе сотрудники отдела %s:%n    ID Фамилия Имя Отчество Зарплата",division);
        for (Employee employee : employees) {
            if (employee.getDivision().equals(division)){
            System.out.printf("%n    %d %s %s %s %.2f",
                    employee.getId(),
                    employee.getSurname(),
                    employee.getName(),
                    employee.getPatronymic(),
                    employee.getSalaryInCents()/100f);
        }
        }
    }
    static void printFIO(Employee[] employees) {
        System.out.printf("%nФИО всех сотрудников:%n    Фамилия Имя Отчество");
        for (Employee employee : employees) {
            System.out.printf("%s %s %s",
                    employee.getSurname(),
                    employee.getName(),
                    employee.getPatronymic());
        }
    }
    static int salariesSumInCents(Employee[] employees) {
        int sum=0;
        for (Employee employee : employees) {
            sum += employee.getSalaryInCents();
        }
        return sum;
    }
    static void salariesIndexing(Employee[] employees, float salaryIndex) {
        for (Employee employee : employees) {
            employee.setSalaryInCents((int) (employee.getSalaryInCents()*(salaryIndex+1f)));
        }
    }
    static void salariesIndexingInDivision(Employee[] employees, String division, float salaryIndex) {
        for (Employee employee : employees) {
            if (employee.getDivision().equals(division)) {
                employee.setSalaryInCents((int) (employee.getSalaryInCents() * (salaryIndex + 1f)));
            }
        }
    }

    static Employee employeeWithMinSalary(Employee[] employees) {
        int employeeIndex=0;
        int minSalaryInCents=employees[0].getSalaryInCents();
        for (int i = 0; i < employees.length; i++) {
            if (minSalaryInCents > employees[i].getSalaryInCents()){
                minSalaryInCents=employees[i].getSalaryInCents();
                employeeIndex=i;
            }
        }
        return employees[employeeIndex];
    }
    static Employee employeeWithMaxSalary(Employee[] employees) {
        int employeeIndex=0;
        int maxSalaryInCents=employees[0].getSalaryInCents();
        for (int i = 0; i < employees.length; i++) {
            if (maxSalaryInCents < employees[i].getSalaryInCents()){
                maxSalaryInCents = employees[i].getSalaryInCents();
                employeeIndex=i;
            }
        }
        return employees[employeeIndex];
    }
    private static int averageSalaryInCents(Employee[] employees) {
        return salariesSumInCents(employees)/employees.length;
    }
    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
    static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    static Employee[] employeesFromDivision(Employee[] employees,String division){
        int resultLength=0;
        int[] resultIndexes = new int[employees.length];
        for (int i = 0; i < employees.length; i++) {
            if (employees[i].getDivision().equals(division)){
                resultIndexes[resultLength++]=i;
            }
        }
        Employee[] resultArray = new Employee[resultLength];
        for (int i=0; i <resultLength; i++) {
            resultArray[i] = employees[resultIndexes[i]];
        }
        return resultArray;
    }
    static void findAllEmployeesWithLessSalaryInCents(Employee[] employees, int salaryInCents) {
        System.out.printf("%nВсе сотрудники с зарплатой меньше %.2f: ",salaryInCents/100f);
        for (Employee employee : employees) {
            if (employee.getSalaryInCents()<salaryInCents) {
                System.out.printf("%n    %d %s %s %s %.2f",
                        employee.getId(),
                        employee.getSurname(),
                        employee.getName(),
                        employee.getPatronymic(),
                        employee.getSalaryInCents()/100f);
            }
        }
    }
    static void findAllEmployeesWithMoreOrEqualSalaryInCents(Employee[] employees, int salaryInCents) {
        System.out.printf("%nВсе сотрудники с зарплатой не меньше, чем %.2f: ",salaryInCents/100f);
        for (Employee employee : employees) {
            if (employee.getSalaryInCents()>=salaryInCents) {
                System.out.printf("%n    %d %s %s %s %.2f",
                        employee.getId(),
                        employee.getSurname(),
                        employee.getName(),
                        employee.getPatronymic(),
                        employee.getSalaryInCents()/100f);
            }
        }
    }
}