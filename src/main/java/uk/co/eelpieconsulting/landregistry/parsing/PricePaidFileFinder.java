package uk.co.eelpieconsulting.landregistry.parsing;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PricePaidFileFinder {

	@Value("#{landRegistry['importFolder']}")
	private String importFolder;
	
	public PricePaidFileFinder() {
	}
	
	public List<File> getFilesInAscendingOrder() {
		Collection<File> listFiles = FileUtils.listFiles(new File(importFolder), new String[] {"csv"}, false);
		return new ArrayList<File>(listFiles);	
	}
	
}
