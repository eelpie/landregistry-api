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
import uk.co.eelpieconsulting.common.geo.model.LatLong;
import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.daos.PricePaidDAO;
import uk.co.eelpieconsulting.landregistry.parsing.PostcodeService;
import uk.co.eelpieconsulting.landregistry.parsing.PricePaidFileFinder;

import com.google.common.base.Strings;

@Controller
public class SearchController {

	private static final double NEAR_RADIUS = 0.005;
	
	private final PricePaidDAO pricePaidDAO;
	private final PricePaidFileFinder pricePaidFileFinder;
	private final FileInformationService fileInformationService;
	private final PostcodeService postcodeService;
	private final ViewFactory viewFactory;
	
	@Autowired
	public SearchController(PricePaidDAO pricePaidDAO, PricePaidFileFinder pricePaidFileFinder, PostcodeService postcodeService, ViewFactory viewFactory) {
		this.pricePaidDAO = pricePaidDAO;
		this.pricePaidFileFinder = pricePaidFileFinder;
		this.postcodeService = postcodeService;
		this.viewFactory = viewFactory;
		fileInformationService = new FileInformationService();
	}
	
	@RequestMapping("/near")
	public ModelAndView near(@RequestParam(value = "latitude", required = false) Double latitude,
			@RequestParam(value = "longitude", required = false) Double longitude,
			@RequestParam(value = "postcode", required = false) String postcode,
			@RequestParam(value = "format", required = false) String format) {
		
		LatLong latLong = null;
		if (latitude != null && longitude != null) {
			latLong = new LatLong(latitude, longitude);
		} else {
			if (!Strings.isNullOrEmpty(postcode)) {
				latLong = postcodeService.getLatLongFor(postcode);
				if (latLong == null) {
					throw new RuntimeException("Unknown postcode");
				}
			}
		}
		if (latLong == null) {
			throw new RuntimeException("No location given");
		}
		
		final View view = format != null && format.equals("rss") ? getRssViewFor(latLong) : viewFactory.getJsonView();
		final ModelAndView mv = new ModelAndView(view);
		mv.addObject("data", pricePaidDAO.near(latLong, NEAR_RADIUS));
		return mv;
	}
	
	@RequestMapping("/sources")
	public ModelAndView sources() throws IOException, ParseException  {
		final ModelAndView mv = new ModelAndView(viewFactory.getJsonView());		
		mv.addObject("data", fileInformationService.makeFileInformationForFiles(pricePaidFileFinder.getFilesInAscendingOrder()));
		return mv;
	}
		
	private View getRssViewFor(LatLong latLong) {
		final String title = "Prices paid near " + latLong.getLatitude() + ", " + latLong.getLongitude();
		return viewFactory.getRssView(title, "http://www.landregistry.gov.uk/public/information/public-data/price-paid-data", title);
	}
	
}
