import java.util.Random;

public class Randomizer {
	private static String[] names = new String[] { "Святослав", "Тихомир", "Ратибор", "Ярополк", "Всеволод", "Добрыня",
			"Богдан", "Казимир", "Благослав", "Всеволод", "Красимир", "Златояр", "Ладислав", "Ратмир", "Пересвет",
			"Добролюб", "Изяслав", "Лучезар", "Ярополк", "Родогор" };
	private static String[] surnames = new String[] { "Святославов", "Тихомиров", "Ратиборов", "Ярополков",
			"Всеволодов", "Добрынин", "Богданов", "Казимиров", "Благославов", "Всеволодов", "Красимиров", "Златояров",
			"Ладиславов", "Ратмиров", "Пересветов", "Добролюбов", "Изяславов", "Лучезаров", "Ярополков", "Родогоров" };
	private static String[] patronymics = new String[] { "Святославович", "Тихомирович", "Ратиборович", "Ярополкович",
			"Всеволодович", "Добрынич", "Богданович", "Казимирович", "Благославович", "Всеволодович", "Красимирович",
			"Златоярович", "Ладиславович", "Ратмирович", "Пересветович", "Добролюбович", "Изяславович", "Лучезарович",
			"Ярополкович", "Родогорович" };
	private static String[] divisions = new String[] { "1", "2", "3", "4", "5" };

	public static String division() {
		return getRandom(divisions);
	}

	public static String name() {
		return getRandom(names);
	}

	public static String patronymic() {
		return getRandom(patronymics);
	}

	public static int salaryInCents() {
		return intInRange(45000_00, 145000_00);
	}

	public static String surname() {
		return getRandom(surnames);
	}

	public static int intInRange(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	private static String getRandom(String[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
}
