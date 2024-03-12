import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TestConsole {
	EmployeeBook employeeBook = new EmployeeBook();
	String selectedDivision;
	BufferedReader input;
	TestConsoleActions.ActionEnum action;
	ActionTargetType scope;

	public TestConsole() {
		input = new BufferedReader(new InputStreamReader(System.in));
	}

	public void start() {
		welcomeSequence();
		while (action != TestConsoleActions.ActionEnum.EXIT) {
			System.out.println("\n---------------------------------------------------");
			printScope(scope);
			TestConsoleActions.ActionEnum[] actions = selectAvailableActions(scope);
			printActions(actions);
			int input = forceInput("Выберите действие:", (int)1) - 1;
			if (input < 0 || input >= actions.length) {
				System.out.println("Неверный ввод \"" + ++input + "\"");
				input = 1;
			}
			actions[input].action(this, employeeBook);
			selectScope(employeeBook.selectedEmployees().length);
		}
		exitSequence();
	}

	private TestConsoleActions.ActionEnum[] selectAvailableActions(ActionTargetType scope) {
		TestConsoleActions.ActionEnum[] allActions = TestConsoleActions.ActionEnum.class.getEnumConstants();
		int overallActionsCount = allActions.length;
		TestConsoleActions.ActionEnum[] tempActions = new TestConsoleActions.ActionEnum[overallActionsCount];
		int tempActionsIndex = 0;
		for (int i = 0; i < overallActionsCount; i++) {
			for (ActionTargetType target : allActions[i].targets) {

				if (target.equals(scope)) {
					tempActions[tempActionsIndex++] = allActions[i];
					break;
				}
			}
		}
		return Arrays.copyOf(tempActions, tempActionsIndex);
	}
	public void printActions(TestConsoleActions.ActionEnum[] actions) {
		for (int option = 0; option < actions.length; option++) {
			System.out.println((option + 1) + ". " + actions[option].title);
		}
	}
	private void exitSequence() {
		System.out.println("Faerwell, dear %username%!");
		System.exit(0);
	}

	public String forceInput(String question, String def) {
		if (question != "" || def != "") {
			System.out.println(question + " [" + def + "]");
		}
		String userInput = def;
		try {
			userInput = input.readLine().trim().replaceAll("\\r|\\n", "");
		} catch (Exception e) {
			System.out.println("Ошибка ввода");
		}
		return userInput.equals("") ? def : userInput;
	}
	public float forceInput(String question, float def) {
		float userInput = def;
		try {
			userInput = Float.parseFloat(forceInput(question, Float.toString(def)));
		} catch (NumberFormatException e) {
			System.out.println("Невозможно преобразовать ввод в число");
		}
		return userInput;
	}

	public int forceInput(String question, int def) {
		int userInput = def;
		try {
			userInput = Integer.parseInt(forceInput(question, Integer.toString(def)));
		} catch (NumberFormatException e) {
			System.out.println("Невозможно преобразовать ввод в число");
		}
		return userInput;
	}


	private void welcomeSequence() {
		System.out.println("Starting welcome sequence...");
		System.out.println("Тестовая консоль курсового проекта Skypro \"Журнал учёта сотрудников организации\".");
		scope = ActionTargetType.START;
	}

	private void selectScope(int selectionCount) {
		if (selectionCount == 0) {
			scope = ActionTargetType.NO_SELECTION;
		} else if (selectionCount == 1) {
			scope = ActionTargetType.ONE_EMPLOYEE;
		} else if (selectionCount > 1) {
			scope = ActionTargetType.SOME_EMPLOYEE;
		} else {
			scope = ActionTargetType.NO_SELECTION;
			System.out.println("Ошибка программы. Обратитесь к разработчику :). selectionCount=" + selectionCount);
		}
	}

	public void printScope(ActionTargetType scope) {
		System.out.println(switch (scope) {
		case ONE_EMPLOYEE -> "Выбран сотрудник: " + employeeBook.selectedEmployees()[0].toString();
		case NO_SELECTION ->
			"Меню организации " + employeeBook.getOrganizationTitle() + "\nВсего сотрудников: " + employeeBook.size();
		case SOME_EMPLOYEE -> "Выбрано сотрудников: " + employeeBook.selectedEmployees().length + "\nСумма зарплат: "
				+ String.format("%,.2f",employeeBook.salariesSum() / 100f) + ", средняя зарплата: " + String.format("%,.2f",employeeBook.averageSalary() / 100f);
		case START -> "Для начала работы сгенерируйте тестовых сотрудников, или начните заводить первого сотрудника";
		default -> "Unexpected value: " + scope;
		});
	}
}
