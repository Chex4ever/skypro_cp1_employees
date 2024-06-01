import java.util.Objects;

public class Employee {
	public final static PrintableFields allFields = new PrintableFields(
			new FieldConvention[] { FieldConvention.ID, FieldConvention.SURNAME, FieldConvention.NAME,
					FieldConvention.PATRONYMIC, FieldConvention.DIVISION, FieldConvention.SALARY });
	public final static PrintableFields allFieldsWithoutDivison = new PrintableFields(
			new FieldConvention[] { FieldConvention.ID, FieldConvention.SURNAME, FieldConvention.NAME,
					FieldConvention.PATRONYMIC, FieldConvention.SALARY });
	public final static PrintableFields fio = new PrintableFields(
			new FieldConvention[] { FieldConvention.SURNAME, FieldConvention.NAME, FieldConvention.PATRONYMIC });
	private static int nextId = 1;
	private final int id;
	private String surname;
	private String name;
	private String patronymic;
	private String division;
	private int salaryInCents;

	public Employee(String surname, String name, String patronymic, String division, int salaryInCents) {
		this.id = nextId++;
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
		this.division = division;
		this.salaryInCents = salaryInCents;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Employee other = (Employee) obj;
		return Objects.equals(division, other.division) && Objects.equals(name, other.name)
				&& Objects.equals(patronymic, other.patronymic) && salaryInCents == other.salaryInCents
				&& Objects.equals(surname, other.surname);
	}

	public String getDivision() {
		return this.division;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPatronymic() {
		return this.patronymic;
	}

	public int getSalaryInCents() {
		return this.salaryInCents;
	}

	public String getSurname() {
		return this.surname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(surname, name, patronymic, division, salaryInCents);
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public void setSalaryInCents(int salaryInCents) {
		this.salaryInCents = salaryInCents;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String toString() {
		return "id " + this.id + ", ФИО: " + this.surname + " " + this.name + " " + this.patronymic + ", отдел "
				+ this.division + ", оклад " + this.salaryInCents / 100f;
	}

	public String[] toFields(FieldConvention[] fieldsToExtract) {
		String[] result = new String[fieldsToExtract.length];
		for (int i = 0; i < fieldsToExtract.length; i++) {
			result[i] = switch (fieldsToExtract[i]) {
			case ID -> Integer.toString(this.id);
			case SURNAME -> this.surname;
			case NAME -> this.name;
			case PATRONYMIC -> this.patronymic;
			case DIVISION -> this.division;
			case SALARY -> String.format("%,.2f", this.salaryInCents / 100f);
			};
		}
		return result;
	}
}
