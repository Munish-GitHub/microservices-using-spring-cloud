package com.munish.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.munish.microservices.currencyconversionservice.bean.CurrencyConversionBean;

@RestController
public class CurrencyConverterController {
	
	@Autowired private CurrencyConversionProxy proxy;
	
	private static Logger logger = LoggerFactory.getLogger(CurrencyConverterController.class);
	

	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
		
		Map<String, String> uriMap = new HashMap<>();
		uriMap.put("from", from);
		uriMap.put("to", to);
		
		ResponseEntity<CurrencyConversionBean> currencyConversion =
				new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class, uriMap);
		CurrencyConversionBean ccBean = currencyConversion.getBody();
		
		return new CurrencyConversionBean(ccBean.getId(),ccBean.getFrom(),ccBean.getTo(),ccBean.getConversionMultiple(),quantity,quantity.multiply(ccBean.getConversionMultiple()),ccBean.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyUsingFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
		
		//Feign client getting used as proxy to remove the complicated code of RestTemplate
		CurrencyConversionBean ccBean = proxy.retrieveExchangeValue(from, to);
		logger.info("response "+ccBean);
//		ResponseEntity<CurrencyConversionBean> currencyConversion =
//				new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
//				CurrencyConversionBean.class, uriMap);
//		CurrencyConversionBean ccBean = currencyConversion.getBody();
		return new CurrencyConversionBean(ccBean.getId(),ccBean.getFrom(),ccBean.getTo(),ccBean.getConversionMultiple(),quantity,quantity.multiply(ccBean.getConversionMultiple()),ccBean.getPort());
	}

}
