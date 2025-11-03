import java.util.Arrays;
import java.util.Scanner;

public class BaseEx6Operation {

	private static final Scanner sc = new Scanner(System.in);

	//////// CLI COLORS ///////////////////////////

	private static final String RESET = "\u001B[0m";
	private static final String YELLOW = "\u001B[33m";
	private static final String RED = "\u001B[31m";
	private static final String CYAN = "\u001B[36m";

	public static void main(String[] args) {
		runMenu();
		sc.close();
	}

	////////////////////////// MENU /////////////////////////

	private static void runMenu() {
		boolean keepRunning = true;
		while (keepRunning) {
			System.out.println("\n=== Ex 6 : Opérations ===");
			System.out.println("1) [6.1] Operation: add/sub/mul/div");
			System.out.println("2) [6.2] Dessiner un triangle");
			System.out.println("3) [6.3] Somme des 2 plus grands d'un tableau");
			System.out.println("4) [6.4] Addition de matrices");
			System.out.println("5) [6.5] Soustraction de matrices");
			System.out.println("6) [6.6] Multiplication par un scalaire");
			System.out.println("0) Quitter");
			System.out.print("Choix : ");

			int choice = readIntInRange(0, 6);

			switch (choice) {
				case 1 -> demo61Operations();
				case 2 -> demo62Triangles();
				case 3 -> demo63SumTwoLargest();
				case 4 -> demo64MatrixAdd();
				case 5 -> demo65MatrixSub();
				case 6 -> demo66MatrixScalar();
				case 0 -> {
					System.out.println("Bye...!!!");
					keepRunning = false;
				}
				default -> System.out.println("Choix invalide.");
			}
		}
	}

	////////////// [6.1] ////////////////////

	private static void demo61Operations() {
		System.out.println("\n[6.1] Operation (add/sub/mul/div)");
		System.out.print("a = ");
		double a = readDouble();
		System.out.print("b = ");
		double b = readDouble();

		boolean bEqualsToZero = false;
		if (b == 0.0) {
			System.out.println(RED + "Division par zéro impossible." + RESET);
			System.out.print(
					"Continuer avec '0' [o] -  ressaisir 'b' [r] ? ");
			while (true) {
				String choice = sc.nextLine().trim().toLowerCase();
				if (choice.equals("o")) { // b = 0
					bEqualsToZero = true;
					break;
				} else if (choice.equals("r")) { // re-enter b
					System.out.print("Nouvelle valeur pour b = ");
					b = readDouble();
					if (b == 0.0) {
						System.out
								.print("Division par zéro TOUJOURS impossible : Continuer [o] - ressaisir 'b' [r] ? ");
						continue;
					}
					break;
				} else {
					System.out.print("'o' pour continuer - 'r' pour ressaisir) : ");
				}
			}
		}

		Visual.showOpStep("add", a, "+", b, Operation.add(a, b));
		Visual.showOpStep("sub", a, "-", b, Operation.sub(a, b));
		Visual.showOpStep("mul", a, "×", b, Operation.mul(a, b));

		if (bEqualsToZero) {
			Visual.showOpError("div", a, "÷", b, "Division par zéro impossible. ON VOUS AVAIT PREVENU...");
		} else {
			Visual.showOpStep("div", a, "÷", b, Operation.div(a, b));
		}
	}

	/////////////////// [6.2] /////////////////////////////

	private static void demo62Triangles() {
		System.out.println("\n[6.2] Triangles");
		System.out.print("Hauteur du triangle : ");
		int h = readIntInRange(0, 100);

		System.out.println(CYAN + "→ Rectangle" + RESET);
		Visual.animateRightTriangle(h);

		System.out.println("-----------------------------");

		System.out.println(CYAN + "→ Isoscèle" + RESET);
		Visual.animateIsoTriangle(h);
	}

	//////////////////// [6.3] ///////////////////////////

	private static void demo63SumTwoLargest() {
		System.out.println("\n[6.3] Somme des 2 plus grands");
		System.out.print("Taille du tableau : ");
		int n = readIntInRange(2, Integer.MAX_VALUE);

		double[] arr = new double[n];
		for (int i = 0; i < n; i++) {
			System.out.print("arr[" + i + "] = ");
			arr[i] = readDouble();
		}

		double sum = Visual.sumTwoLargestPassesWithSteps(arr);
		System.out.println("Table = " + Arrays.toString(arr));
		System.out.println(YELLOW + "Somme des 2 plus grands = " + sum + RESET);
	}

	//////////////// [6.4] //////////////////

	private static void demo64MatrixAdd() {
		System.out.println("\n[6.4] Addition de matrices A + B (même taille)");
		Matrix A = readMatrixFromUser("A"); // user choice
		Matrix B = readMatrixSameShape("B", A); // same size
		Matrix C = Visual.addWithSteps(A, B);
		System.out.println("A + B = \n" + C);
	}

	///////////////// [6.5] ////////////////

	private static void demo65MatrixSub() {
		System.out.println("\n[6.5] Soustraction de matrices A - B");
		Matrix A = readMatrixFromUser("A"); // user choise
		Matrix B = readMatrixSameShape("B", A); // same size
		Matrix C = Visual.subWithSteps(A, B);
		System.out.println("A - B = \n" + C);
	}

	///////////////// [6.6] ////////////////

	private static void demo66MatrixScalar() {
		System.out.println("\n[6.6] Multiplication par un scalaire k * A");
		Matrix A = readMatrixFromUser("A");
		System.out.print("k = ");
		double k = readDouble();

		Matrix C = Visual.mulScalarWithSteps(A, k);
		System.out.println(k + " * A = \n" + C);
	}

	//////////////// HELPERS ///////////////

	private static int readInt() {
		while (true) {
			String s = sc.nextLine().trim();
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				System.out.print("Nombre entier attendu : ");
			}
		}
	}

	private static int readIntInRange(int min, int max) {
		while (true) {
			int v = readInt();
			if (v < min || v > max) {
				System.out.print("Entre " + min + " et " + max + " : ");
				continue;
			}
			return v;
		}
	}

	private static double readDouble() {
		while (true) {
			String s = sc.nextLine().trim();
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
				System.out.print("Nombre (réel) attendu : ");
			}
		}
	}

	private static Matrix readMatrixFromUser(String name) {
		System.out.print("Nombre de lignes pour " + name + " : ");
		int rows = readIntInRange(0, 50);
		System.out.print("Nombre de colonnes pour " + name + " : ");
		int cols = readIntInRange(0, 50);

		double[][] data = new double[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				System.out.print(name + "[" + r + "][" + c + "] = ");
				data[r][c] = readDouble();
			}
		}
		return new Matrix(data);
	}

	private static Matrix readMatrixSameShape(String name, Matrix shapeFrom) {
		int rows = shapeFrom.getRows();
		int cols = shapeFrom.getCols();
		double[][] data = new double[rows][cols];

		System.out.println("Saisie de la matrice " + name + " (" + rows + "x" + cols + ") :");
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				System.out.print(name + "[" + r + "][" + c + "] = ");
				data[r][c] = readDouble();
			}
		}
		return new Matrix(data);
	}
}
