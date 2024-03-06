package pro.sky.chex4ever.cp1;

import java.util.Arrays;
import java.util.Random;

public class EmployeeBook {
	public enum EmployeeField {
		ID, SURNAME, NAME, PATRONYMIC, DIVISION, SALARY
	}
	public final static EmployeeField[] allFields = EmployeeField.class.getEnumConstants();
	public final static EmployeeField[] allFieldsWithoutDivision = new EmployeeField[] { EmployeeField.ID, EmployeeField.SURNAME,
			EmployeeField.NAME, EmployeeField.PATRONYMIC, EmployeeField.SALARY };
	public final static EmployeeField[] fio = new EmployeeField[] { EmployeeField.SURNAME, EmployeeField.NAME,
			EmployeeField.PATRONYMIC };
	private Employee[] employees;
	private Employee[] selectedEmployees;
	private final static Employee[] NULL_EMPLOYEE_ARRAY = new Employee[0];
	private String organizationTitle = "ООО \"Рога и копыта\"";
	public EmployeeBook(int generateTestEmployeeCount) {
		if (generateTestEmployeeCount<0) {
			generateTestEmployeeCount=-generateTestEmployeeCount;
		}
		employees = new Employee[generateTestEmployeeCount];
		for (int i = 0; i < employees.length; i++) {
			employees[i] = generateTestEmployee();
		}
	}

	public EmployeeBook() {
		employees = new Employee[0];
	}

	public Employee getEmployee(int id) {
		return employees.length != 0 ? this.employees[id] : Employee.NULL_EMPLOYEE;
	}

	public int size() {
		return employees.length;
	}

	private String getRandom(String[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	private int generateRandomIntInRange(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public void print(String title, EmployeeField[] employeeFields, Employee[]... customEmployees) {
		Employee[] array = NULL_EMPLOYEE_ARRAY;
		if (customEmployees.length == 0) {
			array = employees;
		} else {
			array = customEmployees[0];
		}
		if (array.length == 0) {
			System.out.println("\nСписок сотрудников пуст");
			return;
		}
		Table table = new Table(title, employeeFields, array);
		table.print();
	}

	public int salariesSum(Employee[]... customEmployees) {
		Employee[] array = NULL_EMPLOYEE_ARRAY;
		if (customEmployees.length == 0) {
			array = employees;
		} else {
			array = customEmployees[0];
		}
		int sum = 0;
		for (Employee employee : array) {
			sum += employee.getSalaryInCents();
		}
		return sum;
	}

	public void salaryIndexing(float salaryIndex) {
		for (Employee employee : selectedEmployees) {
			employee.setSalaryInCents((int) (employee.getSalaryInCents() * (salaryIndex)));
		}
	}

	public void salariesIndexingInDivision(String division, float salaryIndex) {
		for (Employee employee : employees) {
			if (employee.getDivision().equals(division)) {
				employee.setSalaryInCents((int) (employee.getSalaryInCents() * (salaryIndex + 1f)));
			}
		}
	}
	public void applyChanges() {
		int oldElementIndex;
		for (int newElementIndex=0; newElementIndex<selectedEmployees.length;newElementIndex++) {
			oldElementIndex=getEmployeeIndexByID(selectedEmployees[newElementIndex].getId());
			employees[oldElementIndex]=selectedEmployees[newElementIndex];
		}
	}
	
	private int getEmployeeIndexByID(int id) {
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].getId()==id){
				return i;
			}
		}
		return -1;
	}

	public Employee getEmployeeWithMinSalary(Employee[]... customEmployees) {
		Employee[] array = NULL_EMPLOYEE_ARRAY;
		if (customEmployees.length == 0) {
			array = employees;
		} else {
			array = customEmployees[0];
		}
		if (array.length == 0) {
			System.out.println("\nTrying to find employee with min salary in empty employees array");
			return null;
		}
		int employeeIndex = 0;
		int minSalaryInCents = array[0].getSalaryInCents();
		for (int i = 0; i < array.length; i++) {
			if (minSalaryInCents > array[i].getSalaryInCents()) {
				minSalaryInCents = array[i].getSalaryInCents();
				employeeIndex = i;
			}
		}
		return employees[employeeIndex];
	}

	public Employee getEmployeeWithMaxSalary(Employee[]... customEmployees) {
		Employee[] array = NULL_EMPLOYEE_ARRAY;
		if (customEmployees.length == 0) {
			array = employees;
		} else {
			array = customEmployees[0];
		}
		if (array.length == 0) {
			System.out.println("\nTrying to find employee with max salary in empty employees array");
			return null;
		}
		int employeeIndex = 0;
		int maxSalaryInCents = array[0].getSalaryInCents();
		for (int i = 0; i < array.length; i++) {
			if (maxSalaryInCents < array[i].getSalaryInCents()) {
				maxSalaryInCents = array[i].getSalaryInCents();
				employeeIndex = i;
			}
		}
		return employees[employeeIndex];
	}

	public int averageSalary(Employee[]... customEmployees) {
		Employee[] array = NULL_EMPLOYEE_ARRAY;
		if (customEmployees.length == 0) {
			array = employees;
		} else {
			array = customEmployees[0];
		}
		assert array.length > 0 : "trying to calculate average salary in empty employees array";
		return employees.length != 0 ? salariesSum() / employees.length : 0;
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

	/**
	 * @param minSalaryInCents more or equal
	 * @param maxSalaryInCents send 0 if max==min. Negative for MAX_VALUE
	 * @param customEmployees  optional custom array of employees
	 */
	public Employee[] getEmployeesWithSalaryInRange(int minSalaryInCents, int maxSalaryInCents,
			Employee[]... customEmployees) {
		Employee[] searchArray;
		if (customEmployees.length == 0) {
			searchArray = employees;
		} else {
			searchArray = customEmployees[0];
		}
		if (searchArray.length == 0) {
			System.out.println("Trying to find employee with salary range in empty employees array");
			return null;
		}
		if (maxSalaryInCents == 0) {
			maxSalaryInCents = minSalaryInCents + 1;
		} else if (maxSalaryInCents < minSalaryInCents) {
			maxSalaryInCents = Integer.MAX_VALUE;
		}
		int returnArrayLength = 0;
		Employee[] tempArray = new Employee[searchArray.length];
		for (int i = 0; i < searchArray.length; i++) {
			if (searchArray[i].getSalaryInCents() >= minSalaryInCents
					&& searchArray[i].getSalaryInCents() < maxSalaryInCents) {
				tempArray[returnArrayLength] = searchArray[i];
				returnArrayLength++;
			}
		}
		Employee[] returnArray = new Employee[returnArrayLength];
		System.arraycopy(tempArray, 0, returnArray, 0, returnArrayLength);
		return returnArray;
	}

	public void removeEmployee(Employee employee) {
		if (employees.length == 0) {
			System.out.println("Trying to remove employee from empty list");
			return;
		}
		Employee[] tempArr = new Employee[employees.length - 1];
		int tempI = 0;
		for (int i = 0; i < employees.length; i++) {
			if (!employee.equals(employees[i])) {
				tempArr[tempI++] = employees[i];
			}
		}
		employees = tempArr;
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
		selectedEmployees=employees;
	}
	public Employee[] selectedEmployees() {
		return selectedEmployees;
	}
}

// Привести структуру проекта к ООП.
//
//+1. Создать класс EmployeeBook.
//+2. Перенести хранилище сотрудников в него(массив), закрыть к нему доступ извне (сделать приватным).
//+3. Все статические методы по работе с массивом перенести в этот класс и сделать нестатическими.
// 4. Добавить несколько новых методов: 
//    1. Добавить нового сотрудника (создаем объект, заполняем поля, кладем в массив). Нужно найти
//свободную ячейку в массиве и добавить нового сотрудника в нее. Искать нужно
//всегда с начала, так как возможно добавление в ячейку удаленных ранее сотрудников.
//    2. Удалить сотрудника (находим сотрудника по Ф. И. О. и/или id, обнуляем его ячейку в массиве). 
//5. Изменить сотрудника (получить сотрудника по Ф. И. О., модернизировать его запись): 
//    1. Изменить зарплату. 
//    2. Изменить отдел. Придумать архитектуру. Сделать или два метода, или один, но продумать его. 
//6. Получить Ф. И. О. всех сотрудников по отделам (напечатать список отделов и их сотрудников).
