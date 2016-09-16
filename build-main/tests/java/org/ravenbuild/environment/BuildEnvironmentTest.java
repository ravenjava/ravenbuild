package org.ravenbuild.environment;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BuildEnvironmentTest {
	@Test
	public void readsFileFromBaseDirectoryIfPathStartsWithSlash() throws IOException {
		File baseDir = new File("build-main/resources/test/buildenv");
		File currentDir = new File(baseDir, "subdir");
		BuildEnvironment buildEnvironment = new BuildEnvironment(RunMode.ALLOW_MODIFICATIONS, baseDir, currentDir);
		
		BufferedReader reader = new BufferedReader(buildEnvironment.readFile("/testfile"));
		
		String line = reader.readLine();
		assertThat(line, is("Base Directory"));
	}

	@Test
	public void readsFileFromBaseDirectoryIfPathDoesntStartWithSlash() throws IOException {
		File baseDir = new File("build-main/resources/test/buildenv");
		File currentDir = new File(baseDir, "subdir");
		BuildEnvironment buildEnvironment = new BuildEnvironment(RunMode.ALLOW_MODIFICATIONS, baseDir, currentDir);
		
		BufferedReader reader = new BufferedReader(buildEnvironment.readFile("testfile"));
		
		String line = reader.readLine();
		assertThat(line, is("Subdirectory"));
	}
}