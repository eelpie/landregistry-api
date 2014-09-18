package uk.co.eelpieconsulting.landregistry.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.co.eelpieconsulting.common.views.EtagGenerator;
import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.daos.AddressDAO;
import uk.co.eelpieconsulting.landregistry.model.Address;
import uk.co.eelpieconsulting.landregistry.parsing.PricePaidImportService;

@Controller
public class AddressController {
	
	private final static Logger log = Logger.getLogger(PricePaidImportService.class);

	private final ViewFactory viewFactory;

	private AddressDAO addressDAO;
	
	@Autowired
	public AddressController(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
		this.viewFactory = new ViewFactory(new EtagGenerator());
	}
	
	@RequestMapping(value="/addresses", method=RequestMethod.POST)
	public ModelAndView post(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		log.info("POST: " + json);

		try {
			final Address address = new ObjectMapper().readValue(json, Address.class);
			addressDAO.save(address);
			log.info("Saved address: " + address);
		} catch (Exception e) {
			log.error(e);
		}
		
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", "ok");
	}	
	
	@RequestMapping(value="/addresses", method=RequestMethod.OPTIONS)
	public ModelAndView options(HttpServletRequest request, HttpServletResponse response, @RequestBody String json) {		
		response.addHeader("Access-Control-Allow-Methods", "POST");		
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");	    
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", "ok");
	}
	
}