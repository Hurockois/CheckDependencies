import java.io.IOException;
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

		try (Stream<String> stream = Files.lines(Paths.get(this.path))) {

	           includes = stream
	                    .filter(line -> line.startsWith("#include"))
	                    .map(str -> str.replaceAll("\"", ""))
	                    .collect(Collectors.toList());
	           
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		for(int i=0;i<includes.size();i++) {
			String s = includes.get(i).split(" ")[1];
			s.replaceAll("\"","" );
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