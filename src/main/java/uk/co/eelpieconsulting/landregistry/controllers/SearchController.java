package uk.co.eelpieconsulting.landregistry.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import uk.co.eelpieconsulting.common.files.FileInformationService;
import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.daos.PricePaidDAO;
import uk.co.eelpieconsulting.landregistry.parsing.PricePaidFileFinder;

@Controller
public class SearchController {
	
	private final PricePaidDAO pricePaidDAO;
	private PricePaidFileFinder pricePaidFileFinder;
	private final ViewFactory viewFactory;
	private FileInformationService fileInformationService;
	
	@Autowired
	public SearchController(PricePaidDAO pricePaidDAO, PricePaidFileFinder pricePaidFileFinder, ViewFactory viewFactory) {
		this.pricePaidDAO = pricePaidDAO;
		this.pricePaidFileFinder = pricePaidFileFinder;
		this.viewFactory = viewFactory;
		fileInformationService = new FileInformationService();
	}
	
	@RequestMapping("/near")
	public ModelAndView near(@RequestParam(value = "latitude", required = true) double latitude,
			@RequestParam(value = "longitude", required = true) double longitude,
			@RequestParam(value = "format", required = false) String format) {
		
		final View view = format != null && format.equals("rss") ? getRssViewFor(latitude, longitude) : viewFactory.getJsonView();
		final ModelAndView mv = new ModelAndView(view);
		
		mv.addObject("data", pricePaidDAO.near(latitude, longitude));
		return mv;
	}
	
	@RequestMapping("/sources")
	public ModelAndView sources() throws IOException, ParseException  {
		final ModelAndView mv = new ModelAndView(viewFactory.getJsonView());		
		mv.addObject("data", fileInformationService.makeFileInformationForFiles(pricePaidFileFinder.getFilesInAscendingOrder()));
		return mv;
	}
		
	private View getRssViewFor(double latitude, double longitude) {
		final String title = "Prices paid near " + latitude + ", " + longitude;
		return viewFactory.getRssView(title, "http://www.landregistry.gov.uk/public/information/public-data/price-paid-data", title);
	}
	
}
