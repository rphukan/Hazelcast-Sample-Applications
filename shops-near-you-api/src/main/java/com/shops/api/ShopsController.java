package com.shops.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.LatLng;
import com.shops.data.Store;
import com.shops.geocode.GeocodeService;
import com.shops.model.Shop;

/**
 * the controller for all the REST endpoints
 * 
 * @author ranjan
 *
 */
@RestController
public class ShopsController {

	/**
	 * the google geocoding service
	 */
	@Autowired
	@Qualifier("geocodeService")
	private GeocodeService geocodeService;

	/**
	 * the data store
	 */
	@Autowired
	@Qualifier("hazelcastStore")
	private Store<Shop, LatLng> store;

	/**
	 * REST endpoint for adding a shop
	 * 
	 * @param shop
	 */
	@RequestMapping(path = "/shop", method = RequestMethod.POST)
	@CacheEvict(value="shopsCache", keyGenerator="cachingKeyGenerator")
	public void addShop(@RequestBody Shop shop) {
		LatLng geocode = geocodeService.getGeocode(shop);
		if (null != geocode) {
			shop.setLatitude(geocode.lat);
			shop.setLongitude(geocode.lng);
		}
		store.add(shop);
	}

	/**
	 * REST endpoint to find the nearest shop from a location marked with
	 * latitude and longitude
	 * 
	 * @param latitude,
	 *            the latitude of the customer
	 * @param longitude,
	 *            the longitude of the customer
	 * @return the nearest shop
	 */
	@RequestMapping(path = "/shop/{latitude}/{longitude}", method = RequestMethod.GET)
	public Shop getShop(@PathVariable double latitude, @PathVariable double longitude) {
		LatLng geocode = new LatLng(latitude, longitude);
		Shop shop = store.get(geocode);
		return shop;
	}

	/**
	 * REST endpoint for a default request without the customer latitude and
	 * longitude
	 * 
	 * @return returns all the registered shops
	 */
	@RequestMapping(path = "/shop", method = RequestMethod.GET)
	@Cacheable(value="shopsCache", keyGenerator="cachingKeyGenerator")
	public List<Shop> getShops() {
		return store.getAll();
	}

}
