package excel.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainClass {
	public static List<String> LINKS = Arrays.asList(
			"https://claim-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://notification-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://product-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://securitymanagement-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://audit-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://authentication-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://benefit-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://common-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://computation-chain-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://customer-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://file-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://insurer-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://planindexer-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://proposal-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://productengineratingcalc-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://risk-details-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://search-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://tariff-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://tenant-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://user-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://parametric-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://contract-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://data-exchange-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://submission-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://partner-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://scan-service-dev.discovermarket.com/v2/api-docs?group=discovermarket",
    		"https://payment-service-dev.discovermarket.com/v2/api-docs?group=discovermarket");

	public static String LINK_NAME = "";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		List<String> files = new ArrayList<>();

		for (String link : LINKS) {
			LINK_NAME = link;
			JsonToExcel.exportSwaggerUrlToExcel(link);
			files.add(LINK_NAME.split("/")[2] + ".xlsx");
		}
	}

}
