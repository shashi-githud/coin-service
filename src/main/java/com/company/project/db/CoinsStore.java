package com.company.project.db;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class CoinsStore {
  public Map<Double, Integer> coinsStoreMap;
  public List<Integer> validInputBills = Arrays.asList(new Integer[]{1,2,5,10,20,50,100}); ;

  
  @PostConstruct
  public void intializeCoins() {
	  coinsStoreMap = new HashMap<Double, Integer>();
	  coinsStoreMap.put(.01d, 100);
	  coinsStoreMap.put(.05d, 100);
	  coinsStoreMap.put(.10d, 100);
	  coinsStoreMap.put(.25d, 100);
  }
}
