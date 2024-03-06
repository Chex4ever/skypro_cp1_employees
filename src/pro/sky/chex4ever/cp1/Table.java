package pro.sky.chex4ever.cp1;

public class Table {
	private String title;
	private Cell[] headers;
	private int columns, rows, width, indent;
	private Cell[][] table;
	private int defaultColumnWidth = 15;

	public enum Alignment {
		LEFT, CENTER, RIGHT
	}

	public Table(String title, EmployeeBook.EmployeeField[] employeeFields, Employee[] employees) {
		this.title = title;
		this.rows = employees.length;
		this.columns = employeeFields.length;
		this.table = new Cell[rows][columns];
		this.headers = new Cell[columns];
		this.indent = 1;
		for (int i = 0; i < employeeFields.length; i++) {
			headers[i] = switch (employeeFields[i]) {
			case ID -> new Cell("ID", Alignment.CENTER);
			case SURNAME -> new Cell("Фамилия", Alignment.CENTER);
			case NAME -> new Cell("Имя", Alignment.CENTER);
			case PATRONYMIC -> new Cell("Отчество", Alignment.CENTER);
			case DIVISION -> new Cell("Отдел", Alignment.CENTER);
			case SALARY -> new Cell("Зарплата", Alignment.CENTER);
			};
		}
		int row = 0;
		for (Employee employee : employees) {
			for (int i = 0; i < employeeFields.length; i++) {
				table[row][i] = switch (employeeFields[i]) {
				case ID -> new Cell(Integer.toString(employee.getId()), Alignment.RIGHT);
				case SURNAME -> new Cell(employee.getSurname(), Alignment.LEFT);
				case NAME -> new Cell(employee.getName(), Alignment.LEFT);
				case PATRONYMIC -> new Cell(employee.getPatronymic(), Alignment.LEFT);
				case DIVISION -> new Cell(employee.getDivision(), Alignment.CENTER);
				case SALARY -> new Cell(String.format("%.2f", employee.getSalaryInCents() / 100f), Alignment.RIGHT);
				};
			}
			row++;
		}
		setColumnsWidthByContent();
		calculateTableWidth();
	}

	public Table(String title, int[] columnWidths, String[] records) {
		this.title = title;
		columns = columnWidths.length;
		rows = (records.length / columns) - 1;
		table = new Cell[rows][columns];
		headers = new Cell[columns];
		indent = 1;
		for (int column = 0; column < columns; column++) {
			headers[column] = new Cell(records[column], Alignment.CENTER, columnWidths[column]);
		}
		int record = columns;
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				table[row][column] = new Cell(records[record], Alignment.LEFT, headers[column].width);
				record++;
			}
		}
		setColumnsWidthByContent();
		calculateTableWidth();
	}

	public void print() {
		System.out.print("\n"+" ".repeat((width - title.length()) / 2) + title.toUpperCase());
		printSeparator(1);
		System.out.printf("║");
		for (int column = 0; column < headers.length; column++) {
			printAlignmentString(headers[column]);
			System.out.printf((column != headers.length - 1) ? "│" : "║");
		}
		printSeparator(2);
		for (int row = 0; row < rows; row++) {
			System.out.print("║");
			for (int column = 0; column < columns; column++) {
				printAlignmentString(table[row][column]);
				System.out.printf((column != columns - 1) ? "│" : "");
			}
			System.out.print("║");
			if (row != rows - 1)
				printSeparator(2);
			else
				printSeparator(3);
		}
	}

	public class Cell {
		String content;
		Alignment alignment;
		int width;

		public Cell(String content, Table.Alignment alignment, int width) {
			super();
			this.content = content;
			this.alignment = alignment;
			this.width = width;
		}

		public Cell(String content, Table.Alignment alignment) {
			super();
			this.content = content;
			this.alignment = alignment;
			this.width = defaultColumnWidth;
		}

	}

	private void printAlignmentString(String string, int width, Alignment alignment) {
		switch (alignment) {
		case LEFT:
			System.out.printf(" ".repeat(indent) + "%-" + (width - indent) + "s", string);
			break;
		case CENTER:
			int pad = ((width - string.length()) / 2);
			System.out.printf(" ".repeat(pad) + "%-" + (width - pad) + "s", string);
			break;
		case RIGHT:
			System.out.printf("%" + (width - indent) + "s" + " ".repeat(indent), string);
			break;
		}
	}

	private void printAlignmentString(Cell cell) {
		printAlignmentString(cell.content, cell.width, cell.alignment);
	}

	private void printSeparator(int type) { // 1-top, 2-middle, 3-last
		String left = "|", middle = "|", right = "|";
		if (type == 1) { // ╔╤╗
			left = "╔";
			middle = "╤";
			right = "╗";
		}
		if (type == 2) { // ╠╪╣
			left = "╠";
			middle = "╪";
			right = "╣";
		}
		if (type == 3) { // ╚╧╝
			left = "╚";
			middle = "╧";
			right = "╝";
		}
		System.out.printf("%n" + left);
		for (int column = 0; column < headers.length; column++) {
			for (int i = 0; i < headers[column].width; i++) {
				System.out.print("═");
			}
			System.out.printf(column != headers.length - 1 ? middle : right + "%n");
		}
	}

	private int columnContentMaxWidth(int column) {
		int result = 0;
		for (int row = 0; row < rows; row++) {
			if (table[row][column].content.length() > result) {
				result = table[row][column].content.length();
			}
		}
		if (headers[column].content.length()>result) {
			result=headers[column].content.length();
		}
		return result;
	}

	private void setColumnsWidthByContent() {
		for (int column = 0; column < columns; column++) {
			int maxWidth=columnContentMaxWidth(column)+indent*2;
			headers[column].width = maxWidth;
			for (int row=0;row<rows;row++) {
				table[row][column].width =maxWidth;
			}
		}
	}
	private void calculateTableWidth() {
		width=0;
		for (Cell header:headers) {
			width += header.width + 2;
		}
		width = width - 4;
	}
}
