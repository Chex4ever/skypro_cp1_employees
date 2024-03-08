import java.util.Arrays;
import java.util.Random;

public class EmployeeBook {
	public enum EmployeeField {
		ID, SURNAME, NAME, PATRONYMIC, DIVISION, SALARY
	}

	public final static EmployeeField[] allFields = EmployeeField.class.getEnumConstants();
	public final static EmployeeField[] allFieldsWithoutDivision = new EmployeeField[] { EmployeeField.ID,
			EmployeeField.SURNAME, EmployeeField.NAME, EmployeeField.PATRONYMIC, EmployeeField.SALARY };
	public final static EmployeeField[] fio = new EmployeeField[] { EmployeeField.SURNAME, EmployeeField.NAME,
			EmployeeField.PATRONYMIC };
	private Employee[] employees;
	private Employee[] selectedEmployees;
	private String organizationTitle = "ООО \"Рога и копыта\"";

	public EmployeeBook(int generateTestEmployeeCount) {
		if (generateTestEmployeeCount < 0) {
			generateTestEmployeeCount = -generateTestEmployeeCount;
		}
		employees = new Employee[generateTestEmployeeCount];
		for (int i = 0; i < employees.length; i++) {
			employees[i] = generateTestEmployee();
		}
	}

	public EmployeeBook() {
		employees = new Employee[0];
		selectedEmployees = new Employee[0];
	}

	public Employee getEmployee(int index) {
		return employees.length != 0 ? this.employees[index] : Employee.NULL_EMPLOYEE;
	}

	public int size() {
		return employees.length;
	}

	public void print(String title, EmployeeField[] employeeFields) {
		if (selectedEmployees.length == 0) {
			System.out.println("Сначала выберите сотрудников");
			return;
		}
		Table table = new Table(title, employeeFields, selectedEmployees);
		table.print();
	}

	public long salariesSum() {
		if (selectedEmployees.length == 0) {
			System.out.println("Сначала выберите сотрудников");
			return 0;
		}
		long sum = 0;
		for (Employee employee : selectedEmployees) {
			sum += employee.getSalaryInCents();
		}
		return sum;
	}

	public void salaryIndexing(float salaryIndex) {
		for (Employee employee : selectedEmployees) {
			employee.setSalaryInCents((int) (employee.getSalaryInCents() * (salaryIndex)));
		}
	}

	public void applyChanges() {
		int oldElementIndex;
		for (int newElementIndex = 0; newElementIndex < selectedEmployees.length; newElementIndex++) {
			oldElementIndex = getEmployeeIndexByID(selectedEmployees[newElementIndex].getId());
			employees[oldElementIndex] = selectedEmployees[newElementIndex];
		}
	}

	private int getEmployeeIndexByID(int id) {
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].getId() == id) {
				return i;
			}
		}
		return -1;
	}
	private Employee getEmployeeByID(int id) {
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].getId() == id) {
				return employees[i];
			}
		}
		return Employee.NULL_EMPLOYEE;
	}
	
	public Employee getEmployeeWithMinSalary() {
		if (selectedEmployees.length == 0) {
			System.out.println("Сначала выберите сотрудников");
			return Employee.NULL_EMPLOYEE;
		}
		int employeeIndex = 0;
		int minSalaryInCents = selectedEmployees[0].getSalaryInCents();
		for (int i = 0; i < selectedEmployees.length; i++) {
			if (minSalaryInCents > selectedEmployees[i].getSalaryInCents()) {
				minSalaryInCents = selectedEmployees[i].getSalaryInCents();
				employeeIndex = i;
			}
		}
		return employees[employeeIndex];
	}

	public Employee getEmployeeWithMaxSalary() {
		if (selectedEmployees.length == 0) {
			System.out.println("Сначала выберите сотрудников");
			return Employee.NULL_EMPLOYEE;
		}
		int employeeIndex = 0;
		int maxSalaryInCents = selectedEmployees[0].getSalaryInCents();
		for (int i = 0; i < selectedEmployees.length; i++) {
			if (maxSalaryInCents < selectedEmployees[i].getSalaryInCents()) {
				maxSalaryInCents = selectedEmployees[i].getSalaryInCents();
				employeeIndex = i;
			}
		}
		return employees[employeeIndex];
	}

	public int averageSalary() {
		return (int)(salariesSum() / selectedEmployees.length);
	}

	public void selectEmployeesFromDivision(String division) {
		Employee[] tempArray = new Employee[employees.length];
		int resultIndex = 0;
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].getDivision().equals(division)) {
				tempArray[resultIndex++] = employees[i];
			}
		}
		selectedEmployees = new Employee[resultIndex];
		System.arraycopy(tempArray, 0, selectedEmployees, 0, resultIndex);
	}

	public void selectEmployeesWithSalaryInRange(int minSalaryInCents, int maxSalaryInCents) {
		if (selectedEmployees.length == 0) {
			System.out.println("Сначала выберите сотрудников");
		}
		if (maxSalaryInCents == 0) {
			maxSalaryInCents = minSalaryInCents + 1;
		} else if (maxSalaryInCents ==1) {
			maxSalaryInCents = Integer.MAX_VALUE;
			System.out.println("Верхняя граница отключена");
		}
		if (maxSalaryInCents<minSalaryInCents) {
			int minSalaryInCentstemp=minSalaryInCents;
			minSalaryInCents=maxSalaryInCents;
			maxSalaryInCents=minSalaryInCentstemp;
		}
		int returnArrayIndex = 0;
		Employee[] tempArray = new Employee[selectedEmployees.length];
		for (int i = 0; i < selectedEmployees.length; i++) {
			if (selectedEmployees[i].getSalaryInCents() >= minSalaryInCents
					&& selectedEmployees[i].getSalaryInCents() < maxSalaryInCents) {
				tempArray[returnArrayIndex] = selectedEmployees[i];
				returnArrayIndex++;
			}
		}
		selectedEmployees = new Employee[returnArrayIndex];
		System.arraycopy(tempArray, 0, selectedEmployees, 0, returnArrayIndex);
	}

	public void removeSelectedEmployees() {
		if (selectedEmployees.length == 0) {
			System.out.println("Сначала выберите сотрудников");
			return;
		}
		if (selectedEmployees.length == employees.length) {
			employees = selectedEmployees = new Employee[0];
			return;
		}

		Employee[] tempArray = new Employee[employees.length - selectedEmployees.length];
		int tempArrayIndex = 0;

		for (int indexEmployees = 0; indexEmployees < employees.length; indexEmployees++) {
			boolean notToRemove = true;
			for (int indexSelected = 0; indexSelected < selectedEmployees.length; indexSelected++) {
				if (employees[indexEmployees].getId() == selectedEmployees[indexSelected].getId()) {
					notToRemove = false;
				}
			}
			if (notToRemove) {
				tempArray[tempArrayIndex++] = employees[indexEmployees];
			}
		}
		employees = tempArray;
		deselect();
	}

	public void addEmployee(Employee employee) {
		Employee[] tempArr = Arrays.copyOf(employees, employees.length + 1);
		tempArr[tempArr.length - 1] = employee;
		employees = tempArr;
	}

	public Employee generateTestEmployee() {
		String[] names = { "Святослав", "Тихомир", "Ратибор", "Ярополк", "Всеволод", "Добрыня", "Богдан", "Казимир",
				"Благослав", "Всеволод", "Красимир", "Златояр", "Ладислав", "Ратмир", "Пересвет", "Добролюб", "Изяслав",
				"Лучезар", "Ярополк", "Родогор" };
		String[] surnames = { "Святославов", "Тихомиров", "Ратиборов", "Ярополков", "Всеволодов", "Добрынин",
				"Богданов", "Казимиров", "Благославов", "Всеволодов", "Красимиров", "Златояров", "Ладиславов",
				"Ратмиров", "Пересветов", "Добролюбов", "Изяславов", "Лучезаров", "Ярополков", "Родогоров" };
		String[] patronymics = { "Святославович", "Тихомирович", "Ратиборович", "Ярополкович", "Всеволодович",
				"Добрынич", "Богданович", "Казимирович", "Благославович", "Всеволодович", "Красимирович", "Златоярович",
				"Ладиславович", "Ратмирович", "Пересветович", "Добролюбович", "Изяславович", "Лучезарович",
				"Ярополкович", "Родогорович" };
		String[] divisions = { "1", "2", "3", "4", "5" };
		int salaryInCentsMin = 45000_00;
		int salaryInCentsMax = 145000_00;
		return new Employee(getRandom(surnames), getRandom(names), getRandom(patronymics), getRandom(divisions),
				generateRandomIntInRange(salaryInCentsMin, salaryInCentsMax));
	}

	public Employee getEmployeeByIndex(int index) {
		if (index < 0 && index > employees.length || employees.length == 0) {
			System.out.println("trying to get employee with incorrect index or Book is empty");
			return null;
		}
		return employees[index];
	}

	public String getOrganizationTitle() {
		return organizationTitle;
	}

	public void setOrganizationTitle(String organizationTitle) {
		this.organizationTitle = organizationTitle;
	}

	public void selectAllEmployees() {
		selectedEmployees = employees;
	}

	public Employee[] selectedEmployees() {
		return selectedEmployees;
	}

	private String getRandom(String[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	private int generateRandomIntInRange(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public void deselect() {
		selectedEmployees=new Employee[0];
	}

	public void selectEmployeeById(int id) {
		selectedEmployees=new Employee[] {getEmployeeByID(id)};
	}
}

//    2. Удалить сотрудника (находим сотрудника по Ф. И. О. и/или id, обнуляем его ячейку в массиве). 
//5. Изменить сотрудника (получить сотрудника по Ф. И. О., модернизировать его запись): 
//    1. Изменить зарплату. 
//    2. Изменить отдел. Придумать архитектуру. Сделать или два метода, или один, но продумать его. 
//6. Получить Ф. И. О. всех сотрудников по отделам (напечатать список отделов и их сотрудников).
