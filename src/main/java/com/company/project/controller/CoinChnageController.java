package com.company.project.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.db.CoinsStore;
import com.company.project.service.CoinChangeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(value = "Endpoint for Coin Service", tags = "CoinExchange API")
@ApiResponses({ @ApiResponse(code = 404, message = "Throws when source can not be found"),
		@ApiResponse(code = 409, message = "Throws when system detects illegal state") })

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coins")
public class CoinChnageController {

	private final CoinChangeService coinChangeService;
	private final CoinsStore coinsStore;

	@ApiOperation("get Coins with denomination")
	@GetMapping("/getCoins")
	public ResponseEntity<Map<Double, Long>> getLeastCoinsDenominations(@RequestParam Integer amount) throws Exception {
		if(this.coinsStore.validInputBills.contains(amount)) {
			return ResponseEntity.ok(coinChangeService.getLeastAmountOfCoins(this.coinsStore.coinsStoreMap, amount));
		} else {
			return new ResponseEntity<Map<Double, Long>>(HttpStatus.BAD_REQUEST);
		}
	}

	
	@ApiOperation("load coing")
	@PutMapping("/loadCoins/coin/{coinValue}/count/{coinCount}")
	public ResponseEntity<String> loadCoin(@ApiParam @PathVariable Double coinValue, @ApiParam @PathVariable Integer coinCount) throws Exception {
		Integer currentCoinCount = this.coinsStore.coinsStoreMap.get(coinValue);
		if(currentCoinCount != null) {
			this.coinsStore.coinsStoreMap.put(coinValue, currentCoinCount+coinCount);
			return new ResponseEntity<String>("coin loaded",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Invalid coin to laod",HttpStatus.BAD_REQUEST);
		
		}
	}

	
	@ApiOperation("get status of Coin store")
	@GetMapping("/getCoinsStore")
	public Map<Double, Integer> getCoinsStore() throws Exception {
		return this.coinsStore.coinsStoreMap;

	}

}
