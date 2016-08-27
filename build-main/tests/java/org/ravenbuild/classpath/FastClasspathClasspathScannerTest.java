package org.ravenbuild.classpath;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class FastClasspathClasspathScannerTest {
	@Test
	public void findsClassesImplementingAnInterface() {
		FastClasspathClasspathScanner scanner = new FastClasspathClasspathScanner();
		
		final List<Class<? extends TestInterface>> classes = scanner.findAllClassesImplementing(TestInterface.class);
		
		assertThat(classes, containsInAnyOrder(TestClass1.class, TestClass2.class));
	}
}