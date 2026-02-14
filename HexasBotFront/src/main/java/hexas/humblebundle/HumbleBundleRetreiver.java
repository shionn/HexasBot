package hexas.humblebundle;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hexas.db.SpringSessionFactory;
import hexas.db.dao.HumbleBundleDao;
import hexas.db.dbo.Bundle;
import hexas.db.dbo.BundleChoice;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HumbleBundleRetreiver {

	private static String URL = "https://raw.githubusercontent.com/shionn/HumbleBundleGamePack/refs/heads/master/data/game-bundles.json";

	private final SpringSessionFactory factory;

	@Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
	public void retreive() throws IOException, InterruptedException {
		Iterator<JsonNode> jsonGames = readGames();
		try (SqlSession session = factory.open()) {
			HumbleBundleDao dao = session.getMapper(HumbleBundleDao.class);
			while (jsonGames.hasNext()) {
				JsonNode jsonGame = jsonGames.next();
				Bundle bundle = toBundle(jsonGame);
				if (!dao.exists(bundle)) {
					System.out.println("nouveau bundle : " + bundle.getName() + " " + bundle.getUrl());
					dao.create(bundle);
					bundle.getChoices().forEach(choice -> {
						dao.createChoice(bundle, choice);
						choice.getGames().forEach(game -> {
							dao.createChoiceGame(choice, game);
						});
					});
					session.commit();
				}
			}
		}
	}

	private Bundle toBundle(JsonNode jsonGame) {
		List<BundleChoice> choices = new ArrayList<>();
		jsonGame.get("choices").elements().forEachRemaining(choice->{
			List<String> games = new ArrayList<>();
			choice.get("games").elements().forEachRemaining(game -> games.add(game.asText()));
			choices
					.add(BundleChoice
							.builder()
							.price(BigDecimal.valueOf(choice.get("price").asDouble()))
							.games(games)
							.build());
		});
		return Bundle
				.builder()
				.url(jsonGame.get("url").asText())
				.name(jsonGame.get("name").asText())
				.endDate(new Date(jsonGame.get("endDate").asLong()))
				.choices(choices)
				.build();
	}

	private Iterator<JsonNode> readGames()
			throws IOException, InterruptedException, JsonProcessingException, JsonMappingException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		ObjectMapper om = new ObjectMapper();
		Iterator<JsonNode> jsonGames = om.readTree(response.body()).elements();
		return jsonGames;
	}

}
