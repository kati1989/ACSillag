import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Megoldo {

	static int total = 0;
	static int[][] kezdetiAllapot = { { 1, 2, 3 }, { 4, 0, 5 }, { 6, 7, 8 } };

	static int[][] celAllapot = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
	static int checkHeuristic = 1; // Rossz Helyen
	static String inputFile = "";
	static boolean pcost=false;
	static boolean nvisited=false;
	static boolean solseq=false;

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		ArrayList<int[][]> array = new ArrayList<>();

		if (Arrays.stream(args).filter(item -> item.startsWith("-input")).findAny().isPresent()) {
			int n = 0;
			for (int i = 0; i < args.length; i++)
				if ("-input".equals(args[i])) {
					n = i + 1;
					break;
				}

			inputFile = args[n];
		}
		
		if (Arrays.stream(args).filter(item -> item.startsWith("-pcost")).findAny().isPresent()) {
			pcost=true;
		}
		if (Arrays.stream(args).filter(item -> item.startsWith("-nvisited")).findAny().isPresent()) {
			nvisited=true;
		}
		if (Arrays.stream(args).filter(item -> item.startsWith("-solseq")).findAny().isPresent()) {
			solseq=true;
		}	

		Scanner sc = new Scanner(System.in);

		if (inputFile.equals("")) {
			System.out.println("Kerem a bemeneti kezdeti allapotot:");
		} else {
			try {
				sc = new Scanner(new FileInputStream(new File(inputFile)));
			} catch (FileNotFoundException e) {
				System.out.println("File nem talalhato.");
				System.out.println("Kerem a bemeneti kezdeti allapotot:");
				e.printStackTrace();
			}
		}

		String numbers = sc.nextLine();
		String[] numberTokens = numbers.split(" ");
		int k = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				kezdetiAllapot[i][j] = Integer.valueOf(numberTokens[k]);
				k++;
			}
		}

		array.add(kezdetiAllapot);

		if (Arrays.stream(args).filter(item -> item.startsWith("-h")).findAny().isPresent()) {
			int n = 0;
			for (int i = 0; i < args.length; i++)
				if ("-h".equals(args[i])) {
					n = i + 1;
					break;
				}

			checkHeuristic = Integer.valueOf(args[n]);
		}

		if (Arrays.stream(args).filter(item -> item.startsWith("-h")).findAny().isPresent()) {
			String heuristic = Arrays.stream(args).filter(item -> item.startsWith("-h")).findAny().get();
			String[] tags = heuristic.split(" ");

			if (tags.length > 1)
				checkHeuristic = Integer.valueOf(tags[1]);
		}

		if (checkHeuristic == 1)
			System.out.println("Rossz Helyen Heuristika");
		else
			System.out.println("Manhattan Heurisztika");

		for (int i = 0; i < array.size(); i++) {
			System.out.println("**********************************");

			System.out.println("A* Algoritmus ");
			aCsillag(array.get(i), celAllapot);

			if (pcost)
			System.out.println("koltseg: " + total);

			total = 0;

			System.out.println("****************************************");
		}

	}

	public static void printTable(int[][] a) {

		for (int i = 0; i < 3; i++) {
			System.out.println("");

			for (int j = 0; j < 3; j++) {
				System.out.print(" " + a[i][j] + "");
			}
		}

		System.out.println(" ");
	}

	public static int compare(int[][] a, int[][] b) {

		int rosszHelyen = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (a[i][j] == b[i][j]) {
					rosszHelyen = rosszHelyen + 0;
				} else {
					rosszHelyen++;
				}
			}
		}
		return rosszHelyen;
	}

	public static ArrayList<Tabla> kibontCsomopont(Tabla n) {
		n.uresKocka(n.getAllapot());
		int x = n.getX();
		int y = n.getY();
		ArrayList<Tabla> gyerekek = new ArrayList<Tabla>();

		if (x == 0 && y == 0) {
			Tabla gyerek = new Tabla(n, mozgatJobbra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Jobb");
			gyerek.setRosszHelyen();
			Tabla gyerek1 = new Tabla(n, mozgatLe(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Le");
			gyerek1.setRosszHelyen();

			gyerekek.add(gyerek);
			gyerekek.add(gyerek1);
			return gyerekek;

		}
		if (x == 1 && y == 0) {

			Tabla gyerek1 = new Tabla(n, mozgatJobbra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Jobb");
			gyerek1.setRosszHelyen();
			Tabla gyerek3 = new Tabla(n, mozgatLe(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Le");
			gyerek3.setRosszHelyen();
			Tabla gyerek = new Tabla(n, mozgatFel(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Fel");
			gyerek.setRosszHelyen();

			gyerekek.add(gyerek1);
			gyerekek.add(gyerek3);
			gyerekek.add(gyerek);
			return gyerekek;
		}
		if (x == 2 && y == 0) {

			Tabla gyerek3 = new Tabla(n, mozgatJobbra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Jobb");
			gyerek3.setRosszHelyen();
			Tabla gyerek = new Tabla(n, mozgatFel(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Fel");
			gyerek.setRosszHelyen();
			gyerekek.add(gyerek3);
			gyerekek.add(gyerek);

			return gyerekek;
		}
		if (x == 0 && y == 1) {
			Tabla gyerek = new Tabla(n, mozgatJobbra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Jobb");
			gyerek.setRosszHelyen();
			Tabla gyerek1 = new Tabla(n, mozgatLe(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Le");
			gyerek1.setRosszHelyen();
			Tabla gyerek3 = new Tabla(n, mozgatBalra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Bal");
			gyerek3.setRosszHelyen();

			gyerekek.add(gyerek);
			gyerekek.add(gyerek1);
			gyerekek.add(gyerek3);
			return gyerekek;

		}
		if (x == 1 && y == 1) {
			Tabla gyerek = new Tabla(n, mozgatJobbra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Jobb");
			gyerek.setRosszHelyen();
			Tabla gyerek1 = new Tabla(n, mozgatLe(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Le");
			gyerek1.setRosszHelyen();
			Tabla gyerek2 = new Tabla(n, mozgatBalra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Bal");
			gyerek2.setRosszHelyen();
			Tabla gyerek3 = new Tabla(n, mozgatFel(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Fel");
			gyerek3.setRosszHelyen();

			gyerekek.add(gyerek);
			gyerekek.add(gyerek1);
			gyerekek.add(gyerek2);
			gyerekek.add(gyerek3);
			return gyerekek;

		}
		if (x == 2 && y == 1) {
			Tabla gyerek = new Tabla(n, mozgatJobbra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Jobb");
			gyerek.setRosszHelyen();
			Tabla gyerek1 = new Tabla(n, mozgatBalra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Bal");
			gyerek1.setRosszHelyen();
			Tabla gyerek3 = new Tabla(n, mozgatFel(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Fel");
			gyerek3.setRosszHelyen();
			gyerekek.add(gyerek);
			gyerekek.add(gyerek1);
			gyerekek.add(gyerek3);
			return gyerekek;

		}

		if (x == 0 && y == 2) {
			Tabla childs = new Tabla(n, mozgatLe(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Le");
			childs.setRosszHelyen();
			Tabla childs1 = new Tabla(n, mozgatBalra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Bal");
			childs1.setRosszHelyen();

			gyerekek.add(childs);
			gyerekek.add(childs1);

			return gyerekek;
		}
		if (x == 1 && y == 2) {
			Tabla childs = new Tabla(n, mozgatLe(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Le");
			childs.setRosszHelyen();
			Tabla childs1 = new Tabla(n, mozgatBalra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Bal");
			childs1.setRosszHelyen();
			Tabla childs3 = new Tabla(n, mozgatFel(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Fel");
			childs3.setRosszHelyen();

			gyerekek.add(childs);
			gyerekek.add(childs1);
			gyerekek.add(childs3);
			return gyerekek;

		}

		if (x == 2 && y == 2) {
			Tabla childs = new Tabla(n, mozgatBalra(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Bal");
			childs.setRosszHelyen();
			Tabla childs1 = new Tabla(n, mozgatFel(x, y, masolTomb(n.getAllapot())), n.getszint() + 1, "Fel");
			childs1.setRosszHelyen();

			gyerekek.add(childs);
			gyerekek.add(childs1);

			return gyerekek;

		}

		return gyerekek;

	}

	public static int[][] mozgatJobbra(int sor, int oszlop, int[][] state) {
		int[][] a = state;
		int kocka = a[sor][oszlop];
		a[sor][oszlop] = a[sor][oszlop + 1];
		a[sor][oszlop + 1] = kocka;
		total++;
		return a;
	}

	public static int[][] mozgatBalra(int sor, int oszlop, int[][] state) {
		int[][] a = state;
		int kocka = a[sor][oszlop];
		a[sor][oszlop] = a[sor][oszlop - 1];
		a[sor][oszlop - 1] = kocka;
		total++;
		return a;
	}

	public static int[][] mozgatFel(int sor, int oszlop, int[][] state) {
		int[][] a = state;
		int kocka = a[sor][oszlop];
		a[sor][oszlop] = a[sor - 1][oszlop];
		a[sor - 1][oszlop] = kocka;
		total++;
		return a;
	}

	public static int[][] mozgatLe(int sor, int oszlop, int[][] state) {
		int[][] a = state;
		int kocka = a[sor][oszlop];
		a[sor][oszlop] = a[sor + 1][oszlop];
		a[sor + 1][oszlop] = kocka;
		total++;
		return a;
	}

	public static int[][] masolTomb(int[][] b) {
		int[][] a = new int[b.length][b.length];

		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				a[i][j] = b[i][j];

			}
		}

		return a;
	}

	public static int optimalisUt(Tabla n) {
		int i = 0;
		while (n.getSzulo() != null) {
			printTable(n.getAllapot());
			System.out.println(n);
			n = n.getSzulo();
			i++;
		}
		return i;
	}

	public static void aCsillag(int[][] inicial, int[][] cel) {

		Tabla root = new Tabla(null, inicial, 0);
		root.setAllapot(inicial);
		PriorityQueue<Tabla> pQueueA = new PriorityQueue<Tabla>();
		pQueueA.add(root);
		int visited = 0;
		HashMap<String, Tabla> close = new HashMap<String, Tabla>();

		while (!pQueueA.isEmpty()) {

			Tabla aktualis = pQueueA.remove();
			aktualis.setRosszHelyen();
			visited++;

			if (Arrays.deepEquals(masolTomb(aktualis.getAllapot()), cel)) {

				if (solseq){
					printTable(aktualis.getAllapot());
					System.out.println("Cel megtalalva!  ");
					System.out.println("optimalisUt " + optimalisUt(aktualis));
				}
				if (nvisited)
					System.out.println("latogatott csomopontok:  " + visited);
				break;

			} else {

				if (!close.containsKey(Arrays.deepToString(aktualis.getAllapot()))) {
					close.put(Arrays.deepToString(aktualis.getAllapot()), aktualis);

					ArrayList<Tabla> gyerekek = kibontCsomopont(aktualis);
					for (int i = 0; i < gyerekek.size(); i++) {
						pQueueA.add(gyerekek.get(i));
					}

				}

			}

		}

	}

}