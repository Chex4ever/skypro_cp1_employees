import java.util.Arrays;
enum FieldConvention {
	ID("ID", FieldFormat.INT), SURNAME("Фамилия", FieldFormat.STRING), NAME("Имя", FieldFormat.STRING),
	PATRONYMIC("Отчество", FieldFormat.STRING), DIVISION("Отделение", FieldFormat.STRING),
	SALARY("Зарплата", FieldFormat.MONEY);

	public String title;
	public FieldFormat format;

	private FieldConvention(String title, FieldFormat contentType) {
		this.title = title;
		this.format = contentType;
	}
}
enum FieldFormat {
	STRING, INT, MONEY
}
public class PrintableFields {
	private FieldConvention[] fields;

	public PrintableFields(FieldConvention[] fields) {
		this.fields = fields;
	}

	public FieldConvention[] getAllFields() {
		return fields;
	}

	public FieldConvention[] getFieldsByTitles(FieldConvention[] fields) {
		FieldConvention[] resultArray = new FieldConvention[this.fields.length];
		int resultArrayIndex = 0;
		for (int iThis = 0; iThis < this.fields.length; iThis++) {
			for (int iThat = 0; iThat < fields.length; iThat++) {
				if (this.fields[iThis].title.equals(fields[iThat].title)) {
					resultArray[resultArrayIndex++] = this.fields[iThis];
				}
			}
		}
		return Arrays.copyOf(resultArray, resultArrayIndex);
	}

	public String[] getTitles() {
		String[] result = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			result[i] = fields[i].title;
		}
		return result;
	}

	public FieldFormat[] getFieldFormats() {
		FieldFormat[] result = new FieldFormat[fields.length];
		for (int i = 0; i < fields.length; i++) {
			result[i] = fields[i].format;
		}
		return result;
	}
}
