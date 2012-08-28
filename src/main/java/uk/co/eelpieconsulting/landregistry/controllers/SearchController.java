package uk.co.eelpieconsulting.landregistry.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.daos.PricePaidDAO;

@Controller
public class SearchController {
	
	private final PricePaidDAO pricePaidDAO;
	private final ViewFactory viewFactory;
	
	@Autowired
	public SearchController(PricePaidDAO pricePaidDAO, ViewFactory viewFactory) {
		this.pricePaidDAO = pricePaidDAO;
		this.viewFactory = viewFactory;
	}
	
	@RequestMapping("/search")
	public ModelAndView search() throws IOException, ParseException  {
		final ModelAndView mv = new ModelAndView(viewFactory.getJsonView());		
		mv.addObject("data", pricePaidDAO.getAll());
		return mv;
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
	
	private View getRssViewFor(double latitude, double longitude) {
		final String title = "Prices paid near " + latitude + ", " + longitude;
		return viewFactory.getRssView(title, "http://www.landregistry.gov.uk/public/information/public-data/price-paid-data", title);
	}
	
}
