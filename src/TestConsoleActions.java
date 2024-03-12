enum ActionTargetType {
	ONE_EMPLOYEE, SOME_EMPLOYEE, NO_SELECTION, START;
}

public class TestConsoleActions {
	enum ActionEnum implements Action {
		ADD_EMPLOYEE("Добавить сотрудника",
				new ActionTargetType[] { ActionTargetType.NO_SELECTION, ActionTargetType.START }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				addEmployee(testConsole, employeeBook);
			}
		},
		EDIT_EMPLOYEE("Редактировать сотрудника", new ActionTargetType[] { ActionTargetType.ONE_EMPLOYEE }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				editEmployee(testConsole, employeeBook);
			}
		},
		GENERATE_TEST_DATA("Сгенерировать тестовых сотрудников",
				new ActionTargetType[] { ActionTargetType.START, ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.generateTestEmployees(
						testConsole.forceInput("Введите количество добавляемых сотрудников", 10));
			}
		},
		PRINT_SELECTION("Показать выборку", new ActionTargetType[] { ActionTargetType.SOME_EMPLOYEE }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				printTable("Текущая выборка", Employee.allFields.getAllFields(), employeeBook.selectedEmployees());
			}
		},
		PRINT_ALL_EMPLOYEES("Показать всех сотрудников организации",
				new ActionTargetType[] { ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.selectAllEmployees();
				printTable("Все сотрудники организации " + employeeBook.getOrganizationTitle(),
						Employee.allFields.getAllFields(), employeeBook.selectedEmployees());
				employeeBook.deselect();
			}
		},
		PRINT_SELECTION_BY_DIVISION("Показать всех сотрудников по отделениям",
				new ActionTargetType[] { ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				printByDivisions(testConsole, employeeBook);
			}
		},
		REMOVE_EMPLOYEE("Удалить выбранных сотрудников",
				new ActionTargetType[] { ActionTargetType.ONE_EMPLOYEE, ActionTargetType.SOME_EMPLOYEE }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.removeSelectedEmployees();
				System.out.println("Выбранные сотрудники удалены.");
			}
		},
		SALARY_INDEXING("Индексировать зарплату выбранных сотрудников", new ActionTargetType[] {
				ActionTargetType.ONE_EMPLOYEE, ActionTargetType.SOME_EMPLOYEE, ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				salaryIndexing(testConsole, employeeBook);
			}
		},
		SELECT_ALL_EMPLOYEES("Выбрать всех сотрудников", new ActionTargetType[] { ActionTargetType.ONE_EMPLOYEE,
				ActionTargetType.SOME_EMPLOYEE, ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.selectAllEmployees();
			}
		},
		SELECT_DIVISION("Выбрать сотрудников из отдела",
				new ActionTargetType[] { ActionTargetType.SOME_EMPLOYEE, ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.selectEmployeesByDivision(
						testConsole.forceInput("Введите отдел", employeeBook.getRandomEmployee().getDivision()));
			}
		},
		SELECT_EMPLOYEE_BY_ID("Выбрать сотрудника по ID", new ActionTargetType[] { ActionTargetType.ONE_EMPLOYEE,
				ActionTargetType.SOME_EMPLOYEE, ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.selectEmployeeById(
						testConsole.forceInput("Введите ID сотрудника", employeeBook.getRandomEmployee().getId()));
			}
		},
		SELECT_EMPLOYEE_WITH_MAX_SALARY("Выбрать сотрудника с наибольшей зарплатой",
				new ActionTargetType[] { ActionTargetType.SOME_EMPLOYEE, ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.selectEmployeeById(employeeBook.getEmployeeWithMaxSalary().getId());
			}
		},
		SELECT_EMPLOYEE_WITH_MIN_SALARY("Выбрать сотрудника с наименьшей зарплатой",
				new ActionTargetType[] { ActionTargetType.SOME_EMPLOYEE, ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.selectEmployeeById(employeeBook.getEmployeeWithMinSalary().getId());
			}
		},
		SELECT_EMPLOYEES_WITH_SALARY_IN_RANGE("Выбрать сотрудника с указанной зарплатой в диапазоне",
				new ActionTargetType[] { ActionTargetType.SOME_EMPLOYEE, ActionTargetType.NO_SELECTION }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook
						.selectEmployeesWithSalaryInRange(
								testConsole.forceInput("Введите нижнию границу зарплаты", 60_000) * 100,
								testConsole.forceInput(
										"Введите верхнюю границу зарплаты. \"1\" чтобы убрать верхнюю границу", 80_000)
										* 100);
				printTable("Сотрудники с зарплатой в указанном диапозоне", Employee.allFields.getAllFields(),
						employeeBook.selectedEmployees());
			}
		},
		SELECT_ORGANISATION("Снять выборку",
				new ActionTargetType[] { ActionTargetType.ONE_EMPLOYEE, ActionTargetType.SOME_EMPLOYEE }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				employeeBook.deselect();
			}
		},
		EXIT("Выход", new ActionTargetType[] { ActionTargetType.ONE_EMPLOYEE, ActionTargetType.SOME_EMPLOYEE,
				ActionTargetType.NO_SELECTION, ActionTargetType.START }) {
			public void action(TestConsole testConsole, EmployeeBook employeeBook) {
				System.out.println("Farewell...");
				System.exit(0);
			}
		};

		public final String title;
		public final ActionTargetType[] targets;

		private ActionEnum(String title2, ActionTargetType[] targets) {
			this.title = title2;
			this.targets = targets;
		}
	}

	private static void addEmployee(TestConsole testConsole, EmployeeBook employeeBook) {
		Employee newEmployee = new Employee(testConsole.forceInput("Введите фамилию", Randomizer.surname()),
				testConsole.forceInput("Введите имя", Randomizer.name()),
				testConsole.forceInput("Введите отчество", Randomizer.patronymic()),
				testConsole.forceInput("Введите отдел", Randomizer.division()),
				testConsole.forceInput("Введите зарплату", Randomizer.salaryInCents() / 100) * 100);
		employeeBook.addEmployee(newEmployee);
		System.out.println("Добавлен сотрудник: " + newEmployee);
	}

	private static void editEmployee(TestConsole testConsole, EmployeeBook employeeBook) {
		Employee editedEmployee = employeeBook.getEmployeeById(employeeBook.selectedEmployees()[0].getId());
		editedEmployee.setSurname(testConsole.forceInput("Введите фамилию", editedEmployee.getSurname()));
		editedEmployee.setName(testConsole.forceInput("Введите имя", editedEmployee.getName()));
		editedEmployee.setPatronymic(testConsole.forceInput("Введите отчество", editedEmployee.getPatronymic()));
		editedEmployee.setDivision(testConsole.forceInput("Введите отдел", editedEmployee.getDivision()));
		editedEmployee.setSalaryInCents(
				testConsole.forceInput("Введите зарплату", editedEmployee.getSalaryInCents() / 100) * 100);
	}

	private static void printTable(String title, FieldConvention[] columns, Employee[] employees) {
		String[] content = new String[columns.length * employees.length];
		int contentIndex = 0;
		for (int index = 0; index < employees.length; index++)
			for (String string : employees[index].toFields(columns)) {
				content[contentIndex++] = string;
			}
		Table table = new Table(title, columns, content);
		table.print();
	}

	private static void salaryIndexing(TestConsole testConsole, EmployeeBook employeeBook) {
		employeeBook.salaryIndexing(testConsole.forceInput("Введите коэффициент", 1.05f));
	}

	private static void printByDivisions(TestConsole testConsole, EmployeeBook employeeBook) {
		String[] allDivisions = employeeBook.getAllDivisions();
		for (String string : allDivisions) {
			employeeBook.deselect();
			employeeBook.selectEmployeesByDivision(string);
			printTable("Сотрудники отделения " + string, Employee.fio.getAllFields(), employeeBook.selectedEmployees());
			System.out.println("Cотрудников в отделении "+string+": " + employeeBook.selectedEmployees().length
					+ "\nСумма зарплат: " + String.format("%,.2f", employeeBook.salariesSum() / 100f)
					+ ", средняя зарплата: " + String.format("%,.2f", employeeBook.averageSalary() / 100f));
		}
		employeeBook.deselect();
	}
}
