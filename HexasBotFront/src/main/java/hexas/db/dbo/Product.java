package hexas.db.dbo;

import java.util.Date;

import lombok.Data;

@Data
public class Product {

	private int id;
	private String marque;
	private String metaModel;
	private String model;
	private String url;
	private String lastPrice;
	private Date lastPriceDate;
	private String vendor;

}
