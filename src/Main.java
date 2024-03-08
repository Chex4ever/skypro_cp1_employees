public class Main {
	public static void main(String[] args) {
		/*
		int employeesTestGenerateCount = 10;
		EmployeeBook kadry = new EmployeeBook(employeesTestGenerateCount);
		kadry.print("Все сотрудники (все данные)", EmployeeBook.allFields);
		Table keyValueTable = new Table("Выполненные задания", new int[] { 50, 50 },
				new String[] { "Показатель", "Значение", "Сумма зарплат",
						String.format("%.2f", kadry.salariesSum() / 100f), "Сотрудник с минимальной зарплатой",
						kadry.getEmployeeWithMinSalary().toString(), "Сотрудник с максимальной зарплатой",
						kadry.getEmployeeWithMaxSalary().toString(), "Средняя зарплата",
						String.format("%.2f", kadry.averageSalary() / 100f) });
		keyValueTable.print();

		kadry.print("Все сотрудники (только ФИО)", EmployeeBook.fio);
		float salaryIndex = 0.05f;
		System.out.printf("%nИндексирую зарплату всех сотрудников на %.2f%%", salaryIndex * 100);
		kadry.salariesIndexing(salaryIndex);

		String selectedDivision = kadry.getEmployee(0).getDivision();
		System.out.printf("%n%nСотрудник с минимальной зарплатой из отдела %s: %s", selectedDivision,
				kadry.getEmployeeWithMinSalary(kadry.getEmployeesFromDivision(selectedDivision)));
		System.out.printf("%n%nСотрудник с максимальной зарплатой из отдела %s: %s", selectedDivision,
				kadry.getEmployeeWithMaxSalary(kadry.getEmployeesFromDivision(selectedDivision)));
		System.out.printf("%n%nСумма зарплат по отделу %s: %.2f", selectedDivision,
				kadry.salariesSum(kadry.getEmployeesFromDivision(selectedDivision)) / 100f);
		System.out.printf("%n%nСредняя зарплата по отделу %s: %.2f", selectedDivision,
				kadry.averageSalary(kadry.getEmployeesFromDivision(selectedDivision)) / 100f);
		// salaryIndex=0.05f;
		kadry.salariesIndexingInDivision(selectedDivision, salaryIndex);
		System.out.printf("%n%nПроиндесировал зарплату в отделе %s на %.2f", selectedDivision, salaryIndex);
		kadry.print("Все сотрудники из отдела " + selectedDivision, EmployeeBook.allFieldsWithoutDivision,
				kadry.getEmployeesFromDivision(selectedDivision));
		int salaryToFind = 100000_00;
		kadry.print("Все сотрудники с зарплатой меньше " + salaryToFind / 100f, EmployeeBook.allFieldsWithoutDivision,
				kadry.getEmployeesWithSalaryInRange(0, salaryToFind));

		kadry.print("Все сотрудники с зарплатой больше " + salaryToFind / 100f, EmployeeBook.allFieldsWithoutDivision,
				kadry.getEmployeesWithSalaryInRange(salaryToFind, -1));

		*/
		TestConsole console = new TestConsole();
		console.start();
	}
}