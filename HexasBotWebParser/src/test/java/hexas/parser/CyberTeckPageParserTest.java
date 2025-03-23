package hexas.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CyberTeckPageParserTest {

	@Test
	public void testParsePice() {
		assertThat(new CyberTeckPageParser().parsePrice("779€99")).isEqualByComparingTo(BigDecimal.valueOf(779.99));
	}
}
