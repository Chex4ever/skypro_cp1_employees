
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestConsole {
	private enum Option {
		SELECT_SCOPE, SELECT_ALL_EMPLOYEES, SELECT_DIVISION, SELECT_EMPLOYEE, RENAME_ORGANISATION, PRINT_SELECTION,
		GENERATE_TEST_DATA, SALARY_INDEXING, SELECT_EMPLOYEE_WITH_MIN_SALARY, ADD_EMPLOYEE,
		SELECT_EMPLOYEE_WITH_MAX_SALARY, SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE, NOOP, EXIT, REMOVE_EMPLOYEE
	}

	private enum Status {
		START, SCOPE_ALL_EMPLOYEES, SCOPE_DIVISION, SCOPE_ONE_EMPLOYEE, SCOPE_SOME_EMPLOYEE, SCOPE_ORGANISATION
	}

	BufferedReader input;
	EmployeeBook employeeBook = new EmployeeBook();

	Status status;
	Option option;
	String selectedDivision;

	public TestConsole() {
		input = new BufferedReader(new InputStreamReader(System.in));
	}

	public void start() {
		welcomeSequence();
		while (option != Option.EXIT) {
			printStatus();
			takeAction(askUser(optionsAvailable()));
			selectStatus(employeeBook.selectedEmployees().length);
		}
		exitSequence();
	}

	private void printStatus() {
		System.out.println(switch (status) {
		case SCOPE_ALL_EMPLOYEES -> "Выбраны все сотрудники организации. (" + employeeBook.selectedEmployees().length +")\nСумма зарплат: "
				+ employeeBook.salariesSum() / 100 + ", средняя зарплата: " + employeeBook.averageSalary() / 100;
		case SCOPE_DIVISION ->
			"Выбрано отделение: " + employeeBook.selectedEmployees()[0].getDivision() + "\nСумма зарплат: "
					+ employeeBook.salariesSum() / 100 + ", средняя зарплата: " + employeeBook.averageSalary() / 100;
		case SCOPE_ONE_EMPLOYEE -> "Выбран сотрудник: " + employeeBook.selectedEmployees()[0].toString();
		case SCOPE_ORGANISATION -> "Меню организации";
		case SCOPE_SOME_EMPLOYEE ->
			"Выбрано сотрудников: " + employeeBook.selectedEmployees().length + "\nСумма зарплат: "
					+ employeeBook.salariesSum() / 100 + ", средняя зарплата: " + employeeBook.averageSalary() / 100;
		case START -> "Для начала работы сгенерируйте тестовых сотрудников, или начните заводить первого сотрудника";
		});
	}

	private void selectStatus(int scope) {
		if (scope == 0) {
			status = Status.SCOPE_ORGANISATION;
		} else if (scope == employeeBook.size()) {
			status = Status.SCOPE_ALL_EMPLOYEES;
		} else if (scope == 1) {
			status = Status.SCOPE_ONE_EMPLOYEE;
		} else if (scope > 1 && scope < employeeBook.size()) {
			status = Status.SCOPE_SOME_EMPLOYEE;
		} else {
			status = Status.SCOPE_ORGANISATION;
			System.out.println("Ошибка программы. Обратитесь к разработчику :). scope=" + scope);
		}

	}

	private void takeAction(Option option) {
		switch (option) {
		case SELECT_EMPLOYEE_WITH_MAX_SALARY -> selectEmployeeWithMaxSalary();
		case SELECT_EMPLOYEE_WITH_MIN_SALARY -> selectEmployeeWithMinSalary();
		case EXIT -> exitSequence();
		case GENERATE_TEST_DATA -> generateTestData();
		case SALARY_INDEXING -> salaryIndexing();
		case SELECT_SCOPE -> employeeBook.deselect();
		case SELECT_ALL_EMPLOYEES -> employeeBook.selectAllEmployees();
		case SELECT_DIVISION -> employeeBook.selectEmployeesFromDivision(
				forceInputString("Введите наименование отдела", employeeBook.getEmployee(0).getDivision()));
		case SELECT_EMPLOYEE -> {
		}
		case SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE -> selectEmployeesWithSalaryInRange();
		case RENAME_ORGANISATION ->
			employeeBook.setOrganizationTitle(forceInputString("Введите новое наименование организации", "Скайпро"));

		case PRINT_SELECTION -> employeeBook.print("Выбранные сотрудники", EmployeeBook.allFields);
		case NOOP -> {
		}
		case ADD_EMPLOYEE -> addEmployee();
		case REMOVE_EMPLOYEE -> removeEmployee();
		default -> System.out.println("Непонятная хпция: " + option);
		}
		;

	}

	private void selectEmployeeWithMaxSalary() {
		employeeBook.selectEmployeeById(employeeBook.getEmployeeWithMaxSalary().getId());
	}

	private void selectEmployeeWithMinSalary() {
		employeeBook.selectEmployeeById(employeeBook.getEmployeeWithMinSalary().getId());
	}

	private void generateTestData() {
		int newEmployeesCount = forceInputInt("Введите количество работников для генерации тестового массива данных",
				10);
		for (int i = 0; i < newEmployeesCount; i++) {
			employeeBook.addEmployee(employeeBook.generateTestEmployee());
		}
		employeeBook.selectAllEmployees();
		employeeBook.print("Список сотрудников " + employeeBook.getOrganizationTitle(), EmployeeBook.allFields);
	}

	private void salaryIndexing() {
		float salaryIndex = forceInputFloat("Введите коэффициент", 1.05f);
		employeeBook.salaryIndexing(salaryIndex);
		checkAndApply();
	}

	private void selectEmployeesWithSalaryInRange() {
		employeeBook.selectEmployeesWithSalaryInRange(forceInputInt("Введите нижнию границу зарплаты", 60_000) * 100,
				forceInputInt("Введите верхнюю границу зарплаты. \"1\" чтобы убрать верхнюю границу", 80_000) * 100);
		employeeBook.print("Сотрудники с зарплатой в указанном диапозоне", EmployeeBook.allFields);
	}

	private void addEmployee() {
		Employee exampleEmployee = employeeBook.generateTestEmployee();
		Employee newEmployee = new Employee(forceInputString("Введите фамилию", exampleEmployee.getSurname()),
				forceInputString("Введите имя", exampleEmployee.getName()),
				forceInputString("Введите отчество", exampleEmployee.getPatronymic()),
				forceInputString("Введите отдел", exampleEmployee.getDivision()),
				forceInputInt("Введите зарплату", exampleEmployee.getSalaryInCents() / 100) * 100);
		employeeBook.addEmployee(newEmployee);
	}

	private void removeEmployee() {
		employeeBook.removeSelectedEmployees();
		System.out.println("Выбранные сотрудники удалены.");
	}

	private void checkAndApply() {
		employeeBook.print("Выборка с изменениями", EmployeeBook.allFields);
		String userInput = forceInputString("Введите \"д\" или \"y\", чтобы применить изменения", "да");
		userInput = userInput.toLowerCase();
		System.out.println(userInput);
		if (userInput.equals("y") || userInput.equals("д") || userInput.equals("yes") || userInput.equals("да")) {
			employeeBook.applyChanges();
			System.out.println("Изменения применены");
			return;
		} else {
			System.out.println("Изменения не применены");
		}

	}

	private void exitSequence() {
		System.out.println("Faerwell, dear %username%!");
		System.exit(0);
	}

	private Option askUser(Option[] options) {
		for (int option = 0; option < options.length; option++) {
			System.out.println((option + 1) + ". " + switch (options[option]) {
			case SELECT_EMPLOYEE_WITH_MAX_SALARY -> "Выбрать сотрудника с максимальной заралатой";
			case SELECT_EMPLOYEE_WITH_MIN_SALARY -> "Выбрать сотрудника с минимальной заралатой";
			case EXIT -> "Выход";
			case ADD_EMPLOYEE -> "Добавить нового сотрудника";
			case GENERATE_TEST_DATA -> "Сгенерировать тестовые данные";
			case SALARY_INDEXING -> "Индексировать зарплату выбранных сотрудников";
			case SELECT_DIVISION -> "Выбрать отдел";
			case SELECT_EMPLOYEE -> "Выбрать сотрудника";
			case SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE -> "Выбрать сотрудников с указанным диапозоном зарплат";
			case SELECT_SCOPE -> "Сбросить выборку, меню организации";
			case SELECT_ALL_EMPLOYEES -> "Выбрать всех сотрудников";
			case RENAME_ORGANISATION -> "Ввести новое наименование организации";
			case PRINT_SELECTION -> "Показать выборку";
			case REMOVE_EMPLOYEE -> "Удалить выбранных сотрудников";
			default -> options[option];
			});
		}
		int inputInt = forceInputInt("Выберите действие:", 1) - 1;
		if (inputInt >= 0 && inputInt < options.length) {
			return options[inputInt];
		}
		System.out.println("Неверный ввод \"" + ++inputInt + "\"");
		return this.option;
	}

	private Option[] optionsAvailable() {
		return switch (status) {
		case SCOPE_ORGANISATION -> new Option[] { Option.SELECT_ALL_EMPLOYEES, Option.ADD_EMPLOYEE,
				Option.SELECT_DIVISION, Option.SELECT_EMPLOYEE,
				Option.GENERATE_TEST_DATA, Option.RENAME_ORGANISATION, Option.EXIT };
		case SCOPE_ALL_EMPLOYEES -> new Option[] { Option.SALARY_INDEXING, Option.PRINT_SELECTION, Option.SELECT_SCOPE,
				Option.SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE, Option.SELECT_EMPLOYEE_WITH_MAX_SALARY,
				Option.SELECT_EMPLOYEE_WITH_MIN_SALARY, Option.REMOVE_EMPLOYEE, Option.EXIT };
		case SCOPE_DIVISION ->
			new Option[] { Option.SALARY_INDEXING, Option.SELECT_DIVISION, Option.PRINT_SELECTION, Option.SELECT_SCOPE,
					Option.SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE, Option.SELECT_EMPLOYEE_WITH_MAX_SALARY,
					Option.SELECT_EMPLOYEE_WITH_MIN_SALARY, Option.REMOVE_EMPLOYEE, Option.EXIT };
		case SCOPE_ONE_EMPLOYEE -> new Option[] { Option.SALARY_INDEXING, Option.PRINT_SELECTION, Option.SELECT_SCOPE,
				Option.REMOVE_EMPLOYEE, Option.EXIT };
		case SCOPE_SOME_EMPLOYEE -> new Option[] { Option.SALARY_INDEXING, Option.PRINT_SELECTION, Option.SELECT_SCOPE,
				Option.SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE, Option.SELECT_EMPLOYEE_WITH_MAX_SALARY,
				Option.SELECT_EMPLOYEE_WITH_MIN_SALARY, Option.REMOVE_EMPLOYEE, Option.EXIT };
		case START -> new Option[] { Option.GENERATE_TEST_DATA, Option.ADD_EMPLOYEE, Option.EXIT };

		default -> throw new IllegalArgumentException("Unexpected value: " + status);
		};
	}

	private void welcomeSequence() {
		System.out.println(
				"Добро пожаловать в тестовую консоль курсового проекта Skypro \"Журнал учёта сотрудников организации\".");
//		System.out.println("");
		status = Status.START;
		option = Option.NOOP;

	}

	private String forceInputString(String question, String def) {
		if (question != "" || def != "") {
			System.out.println(question + " [" + def + "]");
		}
		String userInput = def;
		try {
			userInput = input.readLine().trim().replaceAll("\\r|\\n", "");
		} catch (IOException e) {
			System.out.println("Ошибка ввода");
		}
		return userInput.equals("") ? def : userInput;
	}

	private int forceInputInt(String question, int def) {
		int userInput = def;
		try {
			userInput = Integer.parseInt(forceInputString(question, Integer.toString(def)));
		} catch (NumberFormatException e) {
			System.out.println("Невозможно преобразовать ввод в число");
		}
		return userInput;
	}

	private float forceInputFloat(String question, float def) {
		float userInput = def;
		try {
			userInput = Float.parseFloat(forceInputString(question, Float.toString(def)));
		} catch (NumberFormatException e) {
			System.out.println("Невозможно преобразовать ввод в число");
		}
		return userInput;
	}
}
