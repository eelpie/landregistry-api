package uk.co.eelpieconsulting.landregistry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.eelpieconsulting.common.views.EtagGenerator;
import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.daos.ZooplaDAO;

@Controller
public class ZooplaController {

	private final ViewFactory viewFactory;

	private ZooplaDAO zooplaDAO;
	
	@Autowired
	public ZooplaController(ZooplaDAO zooplaDAO) {
		this.zooplaDAO = zooplaDAO;
		this.viewFactory = new ViewFactory(new EtagGenerator());
	}
	
	@RequestMapping("/zoopla")
	public ModelAndView pricePaid() {
		final ModelAndView mv = new ModelAndView(viewFactory.getJsonView());
		mv.addObject("data",  zooplaDAO.getAll());
		return mv;
	}
	
}
