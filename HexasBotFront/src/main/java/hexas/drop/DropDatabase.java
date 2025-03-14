package hexas.drop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.Data;

@Component
@ApplicationScope
@Data
public class DropDatabase {

	private List<DropResult> drops = new ArrayList<>();
}
