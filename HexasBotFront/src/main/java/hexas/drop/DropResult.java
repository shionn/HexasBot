package hexas.drop;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class DropResult {
	String metalModel;
	String marque;
	String model;
	String price;
	String vendor;
	String url;

}
