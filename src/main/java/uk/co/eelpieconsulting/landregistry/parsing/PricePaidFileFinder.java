package uk.co.eelpieconsulting.landregistry.parsing;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class PricePaidFileFinder {

	@Value("#{landRegistry['importFolder']}")
	private String importFolder;
	
	public PricePaidFileFinder() {
	}
	
	public void setImportFolder(String importFolder) {
		this.importFolder = importFolder;
	}

	public List<File> getFilesInAscendingOrder() {
		final List<File> fileList = Lists.newArrayList(FileUtils.listFiles(new File(importFolder), new String[] {"csv"}, false));
		Collections.sort(fileList);
		return fileList;	
	}
	
}
