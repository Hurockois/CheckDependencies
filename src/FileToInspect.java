import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileToInspect {
	String path;

	public FileToInspect(String path) {
		super();
		this.path = path;

	}

	public List<String> extractIncludes() {

		List<String> includes = new ArrayList<>();
		System.out.println("Extracting data from " + this.getPath());
		try (Stream<String> stream = Files.lines(Paths.get(this.path), StandardCharsets.ISO_8859_1)) {

			includes = stream.filter(line -> line.startsWith("#include"))
					.map(str -> str.replaceAll("\"", ""))
					.collect(Collectors.toList());

		} catch (MalformedInputException e) {
			e.printStackTrace();
			try (Stream<String> stream = Files.lines(Paths.get(this.path), StandardCharsets.US_ASCII)) {

				includes = stream.filter(line -> line.startsWith("#include")).map(str -> str.replaceAll("\"", ""))
						.collect(Collectors.toList());
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < includes.size(); i++) {
			String s = includes.get(i).split(" ")[1];
			s.replaceAll("\"", "");
			includes.set(i, s);
		}

		return includes;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
