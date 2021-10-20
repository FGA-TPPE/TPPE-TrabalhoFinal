package services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;

import exceptions.ArquivoNaoEncontradoException;
import exceptions.EscritaNaoPermitidaException;

public class FileService {
	
	public static String ParseStreamToString(FileInputStream fis, String encoding) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding))) {
			StringBuilder sb = new StringBuilder();
			String line;
			if((line = br.readLine()) != null) {
				sb.append(line);
			}
			while ((line = br.readLine()) != null) {
				sb.append('\n');
				sb.append(line);
			}
			return sb.toString();
		}
	}

	public static String ReadFileAsString(String path) throws IOException, ArquivoNaoEncontradoException {
		try {
			FileInputStream fis = new FileInputStream(path);
			String data = FileService.ParseStreamToString(fis, "UTF-8");
			return data;
		}
		catch(FileNotFoundException e) {
			throw new ArquivoNaoEncontradoException(path);
		}
	}
	
	public static void WriteStringInFile(String path, String content) throws IOException, EscritaNaoPermitidaException {
		try {
			Path fileName = Path.of(path);
			Files.writeString(fileName, content);
		}
		catch(AccessDeniedException e) {
			throw new EscritaNaoPermitidaException(path);
		}
	}
}
