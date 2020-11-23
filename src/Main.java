import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {

		if (args.length == 2) {
			String path1 = args[0];
			String path2 = args[1];
			String name1 = new File(path1).getName();
			String name2 = new File(path2).getName();

			String outputFileName = "c:\\temp\\dpdencies-" + name1 + "To" + name2 + ".txt";

			PrintWriter filePrintWriter = null;

			try {
				filePrintWriter = new PrintWriter(outputFileName);
				filePrintWriter.println("DEPENDENCY | PATH IN " + name1 +" | PATH IN " + name2);
				
				List<String> listOfFilesContainingPotentialIncludes = WalkPath(path1, null);
				List<String> listOfFilesPotentiallyIncluded = WalkPath(path2, "h");

				for (String f : listOfFilesContainingPotentialIncludes) {
					FileToInspect file = new FileToInspect(f);
					if (file.extractIncludes().size() != 0) {
						List<String> includes = file.extractIncludes();
						// includes.forEach(System.out::println);
						for (String i : listOfFilesPotentiallyIncluded) {
							String nameInclude = new File(i).getName();
							if (includes.contains(nameInclude)) {
								filePrintWriter.println(nameInclude + " | " + file.getPath() + " | " + i );
							}
						}
					}
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} finally {
				// always close the output stream
				if (filePrintWriter != null) {
					filePrintWriter.close();
				}
			}

		} else {
			System.out.println("Please give two arguments as input");
		}
	}

	public static List<String> WalkPath(String path, String extension) {
		try (Stream<Path> walk = Files.walk(Paths.get(path)).filter(Files::isRegularFile)) {
			if (extension != null) {
				List<String> result = walk.map(x -> x.toString()).filter(f -> f.endsWith("." + extension))
						.collect(Collectors.toList());
				return result;
			} else {
				List<String> result = walk.map(x -> x.toString()).collect(Collectors.toList());
				return result;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
