import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashSet;
import java.util.Set;

public class Test {

	public static ArrayList<Integer> row_count = new ArrayList<Integer>();
	public static ArrayList<Integer> column_count = new ArrayList<Integer>();
	public static Set setA = new HashSet();
	public static HashMap<String, Integer> count_value = new HashMap<>();
	public static int count = 0;

	public static void main(String args[]) throws IOException {
		File currentDir = new File("/root/data");
		displayDirectoryContents(currentDir);
		System.out.println("Total number of rows for all csv files : " + calculatesum());
		System.out.println("Average number of fields across all csv: " + calculateAverage());

		for (Object s : setA) {
			check(s.toString(), currentDir);

		}
	}

	
	public static void check(String element, File dir) throws IOException {

		File[] files = dir.listFiles();
		for (File file : files) {

			if (file.isDirectory()) {

				check(element, file);
			}

			else {

				String fileName = file.getName();

				if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("csv")) {

					try (Stream<String> lines = Files.lines(Paths.get(file.getCanonicalPath()),
							StandardCharsets.ISO_8859_1)) {
						List<List<String>> values = lines.map(line -> Arrays.asList(line.split(",")))
								.collect(Collectors.toList());
						column_count.add(values.get(0).size());

						for (List l : values) {

							for (Object o : l) {

								if (element.equals(o.toString())) {

									count = count + 1;

								}

							}

						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				count_value.put(element, count);
			}
		}
		count = 0;

	}

	// Function to iterate over data folder to access the data and check if the file
	// is CSV or not
	public static void displayDirectoryContents(File dir) throws IOException {
		File[] files = dir.listFiles();

		for (File file : files) {

			if (file.isDirectory()) {
				displayDirectoryContents(file);
			} else {
				String fileName = file.getName();

				if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("csv")) {
					readCsv(file.getCanonicalPath());
					giveCount(file.getCanonicalPath());
				}
			}
		}
	}

	// Function to calculate total number of rows for all csv files
	private static int calculatesum() {
		int sum = 0;
		for (Integer x : row_count) {
			sum += x;
		}
		return sum;
	}

	// Function to calculate average number of fields across all csv files
	private static int calculateAverage() {
		int sum = 0, avg = 0;
		for (Integer x : column_count) {
			sum += x;
		}
		avg = sum / column_count.size();
		return avg;
	}

	// function to read all the csv files one by one and add column count of each
	// csv file in a array.
	public static void readCsv(String filename) {
		String fileName = filename;
		try (Stream<String> lines = Files.lines(Paths.get(fileName), StandardCharsets.ISO_8859_1)) {
			List<List<String>> values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
			column_count.add(values.get(0).size());
			for (List l : values) {
				for (Object o : l) {
					// System.out.println(o+"dgsg");
					setA.add(o);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// function to count value of all rows
	public static void giveCount(String filename) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
		String input;
		// System.out.println(bufferedReader.readLine()+"data");
		int count = 0;
		while ((input = bufferedReader.readLine()) != null) {
			count++;
		}
		row_count.add(count);
	}
}
