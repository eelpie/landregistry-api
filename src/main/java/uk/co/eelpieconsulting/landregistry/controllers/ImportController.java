package uk.co.eelpieconsulting.landregistry.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.eelpieconsulting.common.http.HttpBadRequestException;
import uk.co.eelpieconsulting.common.http.HttpFetchException;
import uk.co.eelpieconsulting.common.http.HttpForbiddenException;
import uk.co.eelpieconsulting.common.http.HttpNotFoundException;
import uk.co.eelpieconsulting.common.views.EtagGenerator;
import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.parsing.PricePaidImportService;
import uk.co.eelpieconsulting.landregistry.zoopla.ZooplaService;

@Controller
public class ImportController {
	
	private final PricePaidImportService pricePaidImportService;
	private final ViewFactory viewFactory;
	private final ZooplaService zooplaService;
	
	@Autowired
	public ImportController(PricePaidImportService pricePaidImportService, ZooplaService zooplaService) {
		this.pricePaidImportService = pricePaidImportService;
		this.zooplaService = zooplaService;
		this.viewFactory = new ViewFactory(new EtagGenerator());
	}
	
	@RequestMapping("/import/landregistry")
	public ModelAndView importPricePaidFiles() throws IOException, ParseException  {
		pricePaidImportService.importPricePaidFiles();
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", "ok");
	}
	
	@RequestMapping("/import/zoopla")
	public ModelAndView zoopla() throws IOException, ParseException, HttpNotFoundException, HttpBadRequestException, HttpForbiddenException, HttpFetchException  {
		zooplaService.query();
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", "ok");
	}
	
}
