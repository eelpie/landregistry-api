package uk.co.eelpieconsulting.landregistry.controllers;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", groupSnapshotsByListingId(snapshots));
	}
	
	@RequestMapping("/zoopla/addresses")
	public ModelAndView addresses() {
		return new ModelAndView(viewFactory.getJsonView()).addObject("data", zooplaDAO.addresses());
	}
	
	private Map<String, List<Listing>> groupSnapshotsByListingId(List<Listing> snapshots) {
		Map<String, Map<String, Listing>> grouped = Maps.newHashMap();
		for (Listing listing : snapshots) {
			final String listingId = listing.getId().split("-")[0];
			
			Map<String, Listing> listingSnapshots = grouped.get(listingId);
			if (listingSnapshots == null) {
				listingSnapshots = Maps.newHashMap();
			}
			
			final String checksum = DigestUtils.md5Hex(listing.getDescription() + listing.getListing_status() + listing.getPrice());			
			listingSnapshots.put(checksum, listing);
			
			grouped.put(listingId, listingSnapshots);
		}
		
		final Map<String, List<Listing>> deduplicated = Maps.newHashMap();
		for (String listingId : grouped.keySet()) {
			deduplicated.put(listingId, Lists.newArrayList(grouped.get(listingId).values()));
		}
				
		return deduplicated;
	}
	
}