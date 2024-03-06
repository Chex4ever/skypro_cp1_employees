package pro.sky.chex4ever.cp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestConsole {
	private enum Option {
		SELECT_SCOPE, SELECT_ALL_EMPLOYEES, SELECT_DIVISION, SELECT_EMPLOYEE, RENAME_ORGANISATION, PRINT_SELECTION,
		GENERATE_TEST_DATA, SALARY_INDEXING, SELECT_EMPLOYEE_WITH_MIN_SALARY,
		SELECT_EMPLOYEE_WITH_MAX_SALARY, SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE, NOOP, EXIT
	}

	private enum Status {
		START, SELECT_SCOPE, SCOPE_ALL, SCOPE_DIVISION, SCOPE_EMPLOYEE, SCOPE_SALARY_RANGE
	}

	BufferedReader input;
	EmployeeBook employeeBook;
	
	Status status;
	Option option;
	String selectedDivision;

	public TestConsole() {
		input = new BufferedReader(new InputStreamReader(System.in));
	}

	public void start() {
		welcomeSequence();
		while (option != Option.EXIT) {
//			System.out.println("Текущий статус: " + status);
			takeAction(option);
			option = askUser(optionsAvailable());
		}
		exitSequence();
	}

	private void takeAction(Option option) {
		switch (option) {
		case SELECT_EMPLOYEE_WITH_MAX_SALARY -> {
		}
		case SELECT_EMPLOYEE_WITH_MIN_SALARY -> {
		}
		case EXIT -> {
		}
		case GENERATE_TEST_DATA -> {
			employeeBook = new EmployeeBook(
					forceInputInt("Введите количество работников для генерации тестового массива данных"));
			employeeBook.selectAllEmployees();
			employeeBook.print("Список сотрудников " + employeeBook.getOrganizationTitle(), EmployeeBook.allFields,
					employeeBook.selectedEmployees());
			status = Status.SELECT_SCOPE;
		}
		case SALARY_INDEXING-> {
			float salaryIndex=forceInputFloat("Введите коэффициент");
			employeeBook.salaryIndexing(salaryIndex);
			checkAndApply();
		}
		case SELECT_ALL_EMPLOYEES -> {
			status = Status.SCOPE_ALL;
		}
		case SELECT_DIVISION -> {
			status = Status.SCOPE_DIVISION;
			employeeBook.selectEmployeesFromDivision(forceInputString("Введите наименование отдела"));
		}
		case SELECT_EMPLOYEE -> {
			status = Status.SCOPE_EMPLOYEE;
		}
		case SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE -> {
		}
		case SELECT_SCOPE -> status = Status.SELECT_SCOPE;
		case RENAME_ORGANISATION ->
			employeeBook.setOrganizationTitle(forceInputString("Введите новое наименование организации"));

		case PRINT_SELECTION -> employeeBook.print("Выбранные сотрудники", EmployeeBook.allFields, employeeBook.selectedEmployees());
		case NOOP -> {}
		
		};

	}

	private void checkAndApply() {
		employeeBook.print("Выборка с изменениями", EmployeeBook.allFields, employeeBook.selectedEmployees());
		String userInput=forceInputString("Введите \"д\" или \"y\", чтобы применить изменения");
		userInput=userInput.toLowerCase();
		System.out.println(userInput);
		if (userInput.equals("y")||userInput.equals("д")||userInput.equals("yes")||userInput.equals("да")) {
			employeeBook.applyChanges();
			System.out.println("Изменения применены");
			return;
		} else {
			System.out.println("Изменения не применены");
		}
		
	}

	private void exitSequence() {
		System.out.println("Faerwell, dear %username%!");
	}

	private Option askUser(Option[] options) {
		for (int option = 0; option < options.length; option++) {
			System.out.println((option + 1) + ". " + switch (options[option]) {
			case SELECT_EMPLOYEE_WITH_MAX_SALARY -> "Выбрать сотрудника с максимальной заралатой";
			case SELECT_EMPLOYEE_WITH_MIN_SALARY -> "Выбрать сотрудника с минимальной заралатой";
			case EXIT -> "Выход";
			case GENERATE_TEST_DATA -> "Сгенерировать тестовые данные";
			case SALARY_INDEXING -> "Индексировать зарплату выбранных сотрудников";
			case SELECT_DIVISION -> "Выбрать отдел";
			case SELECT_EMPLOYEE -> "Выбрать сотрудника";
			case SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE -> "Выбрать сотрудников с указанным диапозоном зарплат";
			case SELECT_SCOPE -> "Изменить выборку";
			case SELECT_ALL_EMPLOYEES -> "Выбрать всех сотрудников";
			case RENAME_ORGANISATION -> "Ввести новое наименование организации";
			case PRINT_SELECTION -> "Показать выборку";
			default -> options[option];
			});
		}
		int inputInt = forceInputInt("") - 1;
		if (inputInt >= 0 && inputInt < options.length) {
			return options[inputInt];
		}
		System.out.println("Неверный ввод \"" + ++inputInt + "\"");
		return this.option;
	}

	private Option[] optionsAvailable() {
		return switch (status) {
		case SELECT_SCOPE -> new Option[] { Option.SELECT_ALL_EMPLOYEES, Option.SELECT_DIVISION, Option.SELECT_EMPLOYEE,
				Option.SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE, Option.RENAME_ORGANISATION, Option.EXIT };
		case SCOPE_ALL ->
			new Option[] { Option.SALARY_INDEXING, Option.PRINT_SELECTION, Option.SELECT_SCOPE, Option.EXIT };
		case SCOPE_DIVISION -> new Option[] { Option.SALARY_INDEXING, Option.SELECT_DIVISION,
				Option.PRINT_SELECTION, Option.SELECT_SCOPE, Option.EXIT };
		case SCOPE_EMPLOYEE ->
			new Option[] { Option.SALARY_INDEXING, Option.PRINT_SELECTION, Option.SELECT_SCOPE, Option.EXIT };
		case SCOPE_SALARY_RANGE ->
			new Option[] { Option.SALARY_INDEXING, Option.PRINT_SELECTION, Option.SELECT_SCOPE, Option.EXIT };
		case START -> new Option[] { Option.GENERATE_TEST_DATA, Option.EXIT };
		default -> throw new IllegalArgumentException("Unexpected value: " + status);
		};
	}

	private void welcomeSequence() {
		System.out.println(
				"Добро пожаловать в тестовую консоль курсового проекта Skypro \"Журнал учёта сотрудников организации\".");
		System.out.println("Выберите действие:");
		status = Status.START;
		option = Option.NOOP;

	}

	private String forceInputString(String question) {
		if (question != "") {
			System.out.println(question);
		}
		String userInput = "";
		try {
			userInput = input.readLine().trim();
		} catch (IOException e) {
			System.out.println("Ошибка ввода");
		}
		return userInput;
	}

	private int forceInputInt(String question) {
		int userInput = 0;
		try {
			userInput = Integer.parseInt(forceInputString(question));
		} catch (NumberFormatException e) {
			System.out.println("Невозможно преобразовать ввод в число");
		}
		return userInput;
	}
	private float forceInputFloat(String question) {
		float userInput = 0;
		try {
			userInput = Float.parseFloat(forceInputString(question));
		} catch (NumberFormatException e) {
			System.out.println("Невозможно преобразовать ввод в число");
		}
		return userInput;
	}
}
