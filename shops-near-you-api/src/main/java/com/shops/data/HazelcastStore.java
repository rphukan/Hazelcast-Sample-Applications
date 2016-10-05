package com.shops.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.maps.model.LatLng;
import com.hazelcast.core.HazelcastInstance;
import com.shops.model.Shop;

/**
 * An in memory store which stores all the data in an array list. Provide a
 * different implementation if you want data persistence.
 * 
 * @author ranjan
 *
 */
public class HazelcastStore implements Store<Shop, LatLng> {

	/**
	 * logger
	 */
	private static final Logger LOG = Logger.getLogger(HazelcastStore.class.getName());

	@Autowired
	private HazelcastInstance hazelInstance;

	/**
	 * get the shop nearest to a geocode
	 */
	@Override
	public Shop get(LatLng geocode) {
		Shop nearestShop = findNearest(geocode);
		return nearestShop;
	}

	/**
	 * get all the registered shops
	 */
	@Override
	public List<Shop> getAll() {
		List<Shop> shops = new ArrayList<Shop>(this.hazelInstance.getList("shopsList"));
		return shops;
	}

	/**
	 * register a shop
	 */
	@Override
	public Shop add(Shop item) {
		this.hazelInstance.getList("shopsList").add(item);
		return item;
	}

	/**
	 * Find the shop nearest to this geocode
	 * 
	 * @param geocode
	 * @return
	 */
	public Shop findNearest(LatLng geocode) {
		List<Shop> shops = this.hazelInstance.getList("shopsList");
		// customer latitude and longitude
		double lat1 = geocode.lat;
		double lon1 = geocode.lng;
		// hold the nearest distance found till now
		double nearestDist = -1;
		// hold the reference to the nearest shop found till now
		Shop nearestShop = null;
		for (Shop shop : shops) {
			// latitude and longitude of the shop to compare
			double lat2 = shop.getLatitude();
			double lon2 = shop.getLongitude();
			// distance to the shop in comparison
			double dist = Util.haversine(lat1, lon1, lat2, lon2);
			// if the shop in comparison is nearer than the previous shop or if
			// it is the first shop
			if (dist < nearestDist || nearestDist == -1) {
				nearestShop = shop;
				nearestDist = dist;
				LOG.log(Level.INFO, " Shop " + nearestShop.getName() + " found at " + nearestDist + " KM");
			}
		}
		return nearestShop;
	}

}
