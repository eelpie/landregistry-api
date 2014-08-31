package uk.co.eelpieconsulting.landregistry.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import uk.co.eelpieconsulting.common.files.FileInformationService;
import uk.co.eelpieconsulting.common.geo.model.LatLong;
import uk.co.eelpieconsulting.common.views.EtagGenerator;
import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.daos.PricePaidDAO;
import uk.co.eelpieconsulting.landregistry.parsing.PostcodeService;
import uk.co.eelpieconsulting.landregistry.parsing.PricePaidFileFinder;

import com.google.common.base.Strings;

@Controller
public class PricePaidController {

	private static final double NEAR_RADIUS = 0.005;
	
	private final PricePaidDAO pricePaidDAO;
	private final PricePaidFileFinder pricePaidFileFinder;
	private final FileInformationService fileInformationService;
	private final PostcodeService postcodeService;
	private final ViewFactory viewFactory;
	
	@Autowired
	public PricePaidController(PricePaidDAO pricePaidDAO, PricePaidFileFinder pricePaidFileFinder, PostcodeService postcodeService) {
		this.pricePaidDAO = pricePaidDAO;
		this.pricePaidFileFinder = pricePaidFileFinder;
		this.postcodeService = postcodeService;
		this.viewFactory = new ViewFactory(new EtagGenerator());
		fileInformationService = new FileInformationService();
	}
	
	@RequestMapping("/pricespaid/{id}")
	public ModelAndView pricePaid(@PathVariable String id) {
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", pricePaidDAO.getById(id));
	}
	
	@RequestMapping("/pricespaid/property/{property}")
	public ModelAndView property(@PathVariable String property) {
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", pricePaidDAO.getForProperty(property));
	}
	
	@RequestMapping("/pricespaid/counties")
	public ModelAndView counties() {
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", pricePaidDAO.getCounties());
	}
	
	@RequestMapping("/pricespaid/boroughs")
	public ModelAndView boroughs(@RequestParam String county) {
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", pricePaidDAO.getBoroughs(county));
	}
	
	@RequestMapping("/pricespaid/streets")
	public ModelAndView streets(@RequestParam String borough) {
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", pricePaidDAO.getStreets(borough));
	}
	
	@RequestMapping("/pricespaid/find")
	public ModelAndView find(@RequestParam String borough, 
			@RequestParam String street) {		
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", pricePaidDAO.find(borough, street));		
	}
	
	@RequestMapping("/pricespaid/near")
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
		return new ModelAndView(view).addObject("data", pricePaidDAO.near(latLong, NEAR_RADIUS));
	}
	
	@RequestMapping("/sources")
	public ModelAndView sources() throws IOException, ParseException  {
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", fileInformationService.makeFileInformationForFiles(pricePaidFileFinder.getFilesInAscendingOrder()));
	}
	
	private View getRssViewFor(LatLong latLong) {
		final String title = "Prices paid near " + latLong.getLatitude() + ", " + latLong.getLongitude();
		return viewFactory.getRssView(title, "http://www.landregistry.gov.uk/public/information/public-data/price-paid-data", title);
	}
	
}
