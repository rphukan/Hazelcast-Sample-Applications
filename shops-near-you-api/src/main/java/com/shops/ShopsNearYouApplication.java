package com.shops;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.google.maps.model.LatLng;
import com.shops.caching.CachingKeyGenerator;
import com.shops.data.HazelcastStore;
import com.shops.data.Store;
import com.shops.geocode.GeocodeService;
import com.shops.model.Shop;

@SpringBootApplication
public class ShopsNearYouApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopsNearYouApplication.class, args);
	}

	@Bean(name = "geocodeService")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@ConfigurationProperties(prefix = "config")
	public GeocodeService getGeocodeService() {
		GeocodeService service = new GeocodeService();
		return service;
	}

	@Bean(name = "hazelcastStore")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public Store<Shop, LatLng> getHazelcastStore() {
		return new HazelcastStore();
	}

	@Bean(name = "cachingKeyGenerator")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CachingKeyGenerator getKeyGenerator() {
		return new CachingKeyGenerator();
	}
}
