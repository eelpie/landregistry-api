package uk.co.eelpieconsulting.landregistry.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.parsing.PricePaidImportService;

@Controller
public class ImportController {
	
	private final PricePaidImportService pricePaidImportService;
	private final ViewFactory viewFactory;
	
	@Autowired
	public ImportController(PricePaidImportService pricePaidImportService, ViewFactory viewFactory) {
		this.pricePaidImportService = pricePaidImportService;
		this.viewFactory = viewFactory;
	}
	
	@RequestMapping("/import")
	public ModelAndView importPricePaidFiles() throws IOException, ParseException  {
		final ModelAndView mv = new ModelAndView(viewFactory.getJsonView());		
		pricePaidImportService.importPricePaidFiles();
		mv.addObject("data", "ok");
		return mv;
	}
	
}
