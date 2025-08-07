package hexas.db.dbo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bundle {
	private int id;
	private String url;
	private String name;
	private Date endDate;
	private List<BundleChoice> choices;
}
