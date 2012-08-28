package uk.co.eelpieconsulting.landregistry.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
}
