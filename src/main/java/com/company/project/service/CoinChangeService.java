package com.company.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CoinChangeService {
	

	public  Map<Double, Long> getLeastAmountOfCoins( Map<Double, Integer> coinsCount ,Integer amount){
		List<Double> combination = new ArrayList<Double>();
			Set<Double> coinsDenomination =  coinsCount.keySet();
			List<Double> coinsDenominationList = new ArrayList<Double>(coinsDenomination);
			
	        Collections.sort(coinsDenominationList,Collections.reverseOrder());
			return computeCoinCombination(coinsDenominationList, coinsCount ,amount.doubleValue(), combination);

	}
	private  Map<Double, Long> computeCoinCombination(List<Double> coinsDenomination, Map<Double, Integer> coinsStoreMap ,Double amount, List<Double> combination)
    {
		try {
			int coinIndex = 0; 
	        while (amount > 0) {
	
				if(coinIndex >= coinsDenomination.size()) {
					throw new RuntimeException("insufficient coins to have change");
				}
				Double curentCoin =coinsDenomination.get(coinIndex);
				Integer currentCoinCount =  coinsStoreMap.get(curentCoin);
				if(currentCoinCount <= 0) {
					++ coinIndex;
					continue;
					
			}
				amount =Math.round((amount - curentCoin) * 100.0) / 100.0 ;
				if(amount < 0 ) {
					amount = amount + curentCoin;
					++ coinIndex;
					continue;
				}
				combination.add(curentCoin);
				coinsStoreMap.put(curentCoin, --currentCoinCount);
	        }
				
		} catch (Exception e) {
			  combination.parallelStream().forEach( coin -> {coinsStoreMap.put(coin,  coinsStoreMap.get(coin)+1);});
			 throw e;           
		}

		 Map<Double, Long> counted = combination.stream()
		            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	
        return counted;
    }
    
    

}
