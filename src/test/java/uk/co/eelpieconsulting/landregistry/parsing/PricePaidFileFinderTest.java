package uk.co.eelpieconsulting.landregistry.parsing;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class PricePaidFileFinderTest {

	@Test
	public void filesArePresentedInCorrectOrder() {
		final File inputFileFromTestFolder = new File(this.getClass().getClassLoader().getResource("ppms-201206-with-columns.csv").getFile());
		
		PricePaidFileFinder finder = new PricePaidFileFinder();
		finder.setImportFolder(inputFileFromTestFolder.getParent());

		final List<File> files = finder.getFilesInAscendingOrder();

		assertEquals(6, files.size());
		assertEquals("ppms-201202-with-columns.csv", files.get(0).getName());
		assertEquals("ppms-201203-with-columns.csv", files.get(1).getName());
		assertEquals("ppms-201207-with-columns.csv", files.get(5).getName());
	}

}
