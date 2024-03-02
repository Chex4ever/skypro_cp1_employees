import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        EmployeeBook kadry = new EmployeeBook();
        //1. Получить список всех сотрудников со всеми имеющимися по ним данными
        kadry.printAll();
        //2. Посчитать сумму затрат на зарплаты в месяц.
        System.out.printf("%nСумма зарплат: %.2f",kadry.salariesSumInCents()/100f);
        //3. Найти сотрудника с минимальной зарплатой.
        System.out.printf("%nСотрудник с минимальной зарплатой: %s",kadry.employeeWithMinSalary());
        //4. Найти сотрудника с максимальной зарплатой.
        System.out.printf("%nСотрудник с максимальной зарплатой: %s",kadry.employeeWithMaxSalary());
        //5. Подсчитать среднее значение зарплат
        System.out.printf("%nСредняя зарплата: %.2f",kadry.averageSalaryInCents()/100f);
        //6. Получить Ф. И. О. всех сотрудников (вывести в консоль)
        kadry.printFIO();
        //Part 2
        //1. Проиндексировать зарплату
        float salaryIndex=0.05f;
        System.out.printf("Индексирую зарплату всех сотрудников на %.2f%%",
                salaryIndex*100);
        kadry.salariesIndexing(salaryIndex);
        //2. Функции для отделов:
        String selectedDivision=kadry.getEmployee(0).getDivision();
        //    2.1. Сотрудника с минимальной зарплатой.
        System.out.printf("%n%nСотрудник с минимальной зарплатой из отдела %s: %n    %s",
                selectedDivision,
                kadry.employeeWithMinSalaryFromDivision(selectedDivision));
        //    2.2. Сотрудника с максимальной зарплатой.
        System.out.printf("%n%nСотрудник с максимальной зарплатой из отдела %s: %n    %s",
                selectedDivision,
                kadry.employeeWithMaxSalaryFromDivision(selectedDivision));
        //    2.3. Сумму затрат на зарплату по отделу.
        System.out.printf("%n%nСумма зарплат по отделу %s: %n    %s",
                selectedDivision,
                kadry.salariesSumInCentsFromDivision(selectedDivision)/100f);
        //    2.4. Среднюю зарплату по отделу (учесть, что количество людей в отделе отличается от employees.length).
        System.out.printf("%n%nСредняя зарплата по отделу %s:%n    %s",
                selectedDivision,
                kadry.averageSalaryInCentsFromDivision(selectedDivision)/100f);
        //    2.5. Проиндексировать зарплату всех сотрудников отдела на процент, который приходит в качестве параметра.
        //salaryIndex=0.05f;
        kadry.salariesIndexingInDivision(selectedDivision,salaryIndex);
        //    2.6. Напечатать всех сотрудников отдела (все данные, кроме отдела).
        kadry.printAllFromDivision(selectedDivision);
        //    3.1. Всех сотрудников с зарплатой меньше числа (вывести id, Ф. И. О. и зарплатой в консоль).
        int salaryToFind=100000_00;
        kadry.findAllEmployeesWithLessSalaryInCents(salaryToFind);
        //    3.2. Всех сотрудников с зарплатой больше (или равно) числа (вывести id, Ф. И. О. и зарплатой в консоль).
        kadry.findAllEmployeesWithMoreOrEqualSalaryInCents(salaryToFind);
    }
}