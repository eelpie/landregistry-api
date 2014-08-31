package uk.co.eelpieconsulting.landregistry.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import uk.co.eelpieconsulting.common.views.EtagGenerator;
import uk.co.eelpieconsulting.common.views.ViewFactory;
import uk.co.eelpieconsulting.landregistry.daos.ZooplaDAO;
import uk.co.eelpieconsulting.landregistry.zoopla.Listing;

@Controller
public class ZooplaController {

	private final ViewFactory viewFactory;

	private ZooplaDAO zooplaDAO;
	
	@Autowired
	public ZooplaController(ZooplaDAO zooplaDAO) {
		this.zooplaDAO = zooplaDAO;
		this.viewFactory = new ViewFactory(new EtagGenerator());
	}
	
	@RequestMapping("/zoopla/find")
	public ModelAndView find(@RequestParam String q) {
		final List<Listing> snapshots = zooplaDAO.find(q);
		
		final ModelAndView mv = new ModelAndView(viewFactory.getJsonView());
		mv.addObject("data", groupSnapshotsByListingId(snapshots));
		return mv;
	}
	
	@RequestMapping("/zoopla/addresses")
	public ModelAndView addresses() {
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", zooplaDAO.addresses());
	}
	
	private Map<String, List<Listing>> groupSnapshotsByListingId(List<Listing> snapshots) {
		Map<String, List<Listing>> grouped = Maps.newHashMap();
		for (Listing listing : snapshots) {
			final String listingId = listing.getId().split("-")[0];
			
			List<Listing> listingSnapshots = grouped.get(listingId);
			if (listingSnapshots == null) {
				listingSnapshots = Lists.newArrayList();
			}
			listingSnapshots.add(listing);
			grouped.put(listingId, listingSnapshots);
		}
		return grouped;
	}
	
}
