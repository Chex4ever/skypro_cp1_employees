import java.util.Arrays;

public class EmployeeBook {

	private Employee[] employees;
	private String organizationTitle = "ООО \"Рога и копыта\"";
	private Employee[] selectedEmployees;

	public EmployeeBook() {
		employees = new Employee[0];
		selectedEmployees = new Employee[0];
	}

	public EmployeeBook(int generateTestEmployeeCount) {
		this();
		generateTestEmployees(generateTestEmployeeCount);
	}

	public void generateTestEmployees(int newEmployeesCount) {
		if (newEmployeesCount < 0) {
			newEmployeesCount = -newEmployeesCount;
		}
		Employee[] newEmployeeArray = new Employee[newEmployeesCount];
		for (int i = 0; i < newEmployeesCount; i++) {
			newEmployeeArray[i] = new Employee(Randomizer.surname(), Randomizer.name(), Randomizer.patronymic(),
					Randomizer.division(), Randomizer.salaryInCents());
		}
		addEmployee(newEmployeeArray);
	}

	public void addEmployee(Employee employee) {
		Employee[] tempArr = Arrays.copyOf(employees, employees.length + 1);
		tempArr[tempArr.length - 1] = employee;
		employees = tempArr;
	}

	public void addEmployee(Employee[] newEmployees) {
		int newEmployeesLength = newEmployees.length;
		int employeesLength = employees.length;
		Employee[] tempArr = Arrays.copyOf(employees, employeesLength + newEmployeesLength);
		System.arraycopy(newEmployees, 0, tempArr, employeesLength, newEmployeesLength);
		employees = tempArr;
	}

	public int averageSalary() {
		return (int) (salariesSum() / selectedEmployees.length);
	}

	public void deselect() {
		selectedEmployees = new Employee[0];
	}

	public Employee getRandomEmployee() {
		return employees[Randomizer.intInRange(0, employees.length - 1)];
	}

	public Employee getEmployeeById(int id) {
		for (int i = 0; i < employees.length; i++)
			if (employees[i].getId() == id) {
				return employees[i];
			}
		System.out.println("Сотрудник с ID " + id + " не найден");
		return null;

	}

	public Employee getEmployeeWithMaxSalary() {
		if (selectedEmployees.length == 0) {
			selectedEmployees = employees;
		}
		int employeeIndex = 0;
		int maxSalaryInCents = selectedEmployees[0].getSalaryInCents();
		for (int i = 0; i < selectedEmployees.length; i++) {
			if (maxSalaryInCents < selectedEmployees[i].getSalaryInCents()) {
				maxSalaryInCents = selectedEmployees[i].getSalaryInCents();
				employeeIndex = i;
			}
		}
		return selectedEmployees[employeeIndex];
	}

	public Employee getEmployeeWithMinSalary() {
		if (selectedEmployees.length == 0) {
			selectedEmployees = employees;
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

	public String getOrganizationTitle() {
		return organizationTitle;
	}

	public void removeSelectedEmployees() {
		if (selectedEmployees.length == 0) {
			System.out.println("Сначала выберите сотрудников");
			return;
		}
		if (selectedEmployees.length == employees.length) {
			employees = selectedEmployees = new Employee[0];
			System.out.println("Удалены все сотрудники");
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

	public long salariesSum() {
		if (selectedEmployees.length == 0) {
			selectedEmployees = employees;
		}
		long sum = 0;
		for (Employee employee : selectedEmployees) {
			sum += employee.getSalaryInCents();
		}
		return sum;
	}

	public void salaryIndexing(float salaryIndex) {
		if (selectedEmployees.length == 0) {
			selectedEmployees = employees;
		}
		for (Employee employee : selectedEmployees) {
			employee.setSalaryInCents((int) (employee.getSalaryInCents() * (salaryIndex)));
		}
	}

	public void selectAllEmployees() {
		selectedEmployees = employees;
	}

	public Employee[] selectedEmployees() {
		return selectedEmployees;
	}

	public void selectEmployeeById(int id) {
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].getId() == id) {
				selectedEmployees = new Employee[] { employees[i] };
				return;
			}
		}
		System.out.println("Сотрудник с ID " + id + " не найден");
	}

	public void selectEmployeesByDivision(String division) {
		if (selectedEmployees.length == 0) {
			selectedEmployees = employees;
		}
		Employee[] tempArray = new Employee[selectedEmployees.length];
		int resultIndex = 0;
		for (int i = 0; i < selectedEmployees.length; i++) {
			if (selectedEmployees[i].getDivision().equals(division)) {
				tempArray[resultIndex++] = selectedEmployees[i];
			}
		}
		selectedEmployees = new Employee[resultIndex];
		System.arraycopy(tempArray, 0, selectedEmployees, 0, resultIndex);
	}

	public void selectEmployeesWithSalaryInRange(int minSalaryInCents, int maxSalaryInCents) {
		if (selectedEmployees.length == 0) {
			selectedEmployees = employees;
		}
		if (maxSalaryInCents == 0) {
			maxSalaryInCents = minSalaryInCents + 1;
		} else if (maxSalaryInCents == 1) {
			maxSalaryInCents = Integer.MAX_VALUE;
			System.out.println("Верхняя граница отключена");
		}
		if (maxSalaryInCents < minSalaryInCents) {
			int minSalaryInCentstemp = minSalaryInCents;
			minSalaryInCents = maxSalaryInCents;
			maxSalaryInCents = minSalaryInCentstemp;
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

	public void setOrganizationTitle(String organizationTitle) {
		this.organizationTitle = organizationTitle;
	}

	public int size() {
		return employees.length;
	}

	public String[] getAllDivisions() {
		String tempString = "";
		for (int i = 1; i < employees.length; i++) {
			String division = employees[i].getDivision();
			tempString += tempString.contains(division) ? "" : (division + "!@#");
		}
		return tempString.split("!@#");
	}
}