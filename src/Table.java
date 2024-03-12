public class Table {
	private String title;
	private Cell[] headers;
	private int columns, rows, width, indent;
	private Cell[][] table;
	private int defaultColumnWidth = 15;

	public enum Alignment {
		LEFT, CENTER, RIGHT
	}

	public Table(String title, FieldConvention[] fields, String[] contents) {
		this.title = title;
		columns = fields.length;
		rows = contents.length/columns;
		table = new Cell[rows][columns];
		headers = new Cell[columns];
		indent = 1;
		for (int i = 0; i < fields.length; i++) {
			headers[i] = new Cell(fields[i].title, Alignment.CENTER);
		}
		int row = 0;
		int column = 0;
		for (String content : contents) {
			if (column >= fields.length) {
				column = 0;
				row++;
			}
			table[row][column] = switch (fields[column].format) {
			case INT -> new Cell(content, Alignment.RIGHT);
			case STRING -> new Cell(content, Alignment.LEFT);
			case MONEY -> new Cell(content, Alignment.RIGHT);
			};
			column++;
		}

		calculateColumnsWidthByContent();
		calculateTableWidth();
	}

	public void print() {
		print(100500);//builder get error if print more rows than this
	}
	public void print(int maxRows) {
		printTable(maxRows>rows?rows:maxRows);
	}
	private void printTable(int rows) {
		StringBuilder builder = new StringBuilder();
		builder.append("\n" + " ".repeat((width - title.length()) / 2) + title.toUpperCase());
		builder.append(printSeparator(1));
		builder.append("║");
		for (int column = 0; column < headers.length; column++) {
			builder.append(printAlignmentString(headers[column]));
			builder.append((column != headers.length - 1) ? "│" : "║");
		}
		builder.append(printSeparator(2));
		for (int row = 0; row < rows; row++) {
			builder.append("║");
			for (int column = 0; column < columns; column++) {
				builder.append(printAlignmentString(table[row][column]));
				builder.append((column != columns - 1) ? "│" : "");
			}
			builder.append("║"+((row != rows - 1)?"\n":""));
		}
		builder.append(printSeparator(3));
		System.out.print(builder);
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

	private String printAlignmentString(String string, int width, Alignment alignment) {
		StringBuilder builder = new StringBuilder();

		switch (alignment) {
		case LEFT:
			builder.append(" ".repeat(indent) + String.format("%-" + (width - indent) + "s", string));
			break;
		case CENTER:
			int pad = ((width - string.length()) / 2);
			builder.append(" ".repeat(pad) + String.format("%-" + (width - pad) + "s", string));
			break;
		case RIGHT:
			builder.append(String.format("%" + (width - indent) + "s", string) + " ".repeat(indent));
			break;
		}
		return builder.toString();
	}

	private String printAlignmentString(Cell cell) {
		return printAlignmentString(cell.content, cell.width, cell.alignment);
	}

	private String printSeparator(int type) { // 1-top, 2-middle, 3-last
		StringBuilder builder = new StringBuilder();
		String left = "|", middle = "|", right = "|", horizont = "─";
		if (type == 1) { // ╔═╤═╗
			left = "╔";
			middle = "╤";
			right = "╗";
			horizont = "═";
		}
		if (type == 2) { // ─ ╠╪╣
			left = "╠";
			middle = "┼";
			right = "╣";
			horizont = "─";
		}
		if (type == 3) { // ╚╧╝
			left = "╚";
			middle = "╧";
			right = "╝";
			horizont = "═";
		}
		builder.append("\n"+left);
		for (int column = 0; column < headers.length; column++) {
			for (int i = 0; i < headers[column].width; i++) {
				builder.append(horizont);
			}
			builder.append(column != headers.length - 1 ? middle : right + "\n");
		}
		return builder.toString();
	}

	private int columnContentMaxWidth(int column) {
		int result = 0;
		for (int row = 0; row < rows; row++) {
			if (table[row][column].content.length() > result) {
				result = table[row][column].content.length();
			}
		}
		if (headers[column].content.length() > result) {
			result = headers[column].content.length();
		}
		return result;
	}

	private void calculateColumnsWidthByContent() {
		for (int column = 0; column < columns; column++) {
			int maxWidth = columnContentMaxWidth(column) + indent * 2;
			headers[column].width = maxWidth;
			for (int row = 0; row < rows; row++) {
				table[row][column].width = maxWidth;
			}
		}
	}

	private void calculateTableWidth() {
		width = 0;
		for (Cell header : headers) {
			width += header.width + 2;
		}
		width = width - 4;
	}
}
