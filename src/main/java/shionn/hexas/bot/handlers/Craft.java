package shionn.hexas.bot.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.messages.MessageBuilder;
import shionn.hexas.mongo.mo.adventure.Adventure;
import shionn.hexas.mongo.mo.adventure.Player;
import shionn.hexas.mongo.mo.adventure.Schema;

/**
 * handler des la commande de craft
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Craft {

	private static final Pattern SPLIT = Pattern.compile("\\&");

	@Inject
	private JacksonDBCollection<Player, String> players;

	public void run(Player player, Adventure adventure, MessageEvent<HexasBot> event) {
		String item = event.getMessage().replace(adventure.getCommands().getCraft(), "").trim();
		Schema schema = getSchema(adventure, item);
		if (item.length() == 0) {
			new MessageBuilder(adventure.getMessages().getHelpCraft()).crafts(
					findSchemaNames(adventure).toString()).send(event);
		} else if (schema == null) {
			new MessageBuilder(adventure.getMessages().getNoSchema()).item(item).send(event);
		} else {
			List<String> requireds = findRequiereds(schema);
			if (haveAllItem(player, requireds, schema)) {
				craft(player, schema, requireds, adventure, event);
			} else {
				new MessageBuilder(adventure.getMessages().getNeedItem()).schema(schema)
						.items(requireds.toString()).send(event);
			}
		}
	}

	public void craft(Player player, Schema schema, List<String> requireds, Adventure adventure,
			MessageEvent<HexasBot> event) {
		for (String required : requireds) {
			player.item(required, -1);
		}
		player.po(-schema.getPo());
		player.item(schema.getItem(), 1);
		new MessageBuilder(adventure.getMessages().getCraft()).schema(schema)
				.items(requireds.toString()).send(event);
		players.save(player);
	}

	private boolean haveAllItem(Player player, List<String> requireds, Schema schema) {
		boolean haveAll = player.getPo() >= schema.getPo();
		Iterator<String> ite = requireds.iterator();
		while (haveAll && ite.hasNext()) {
			haveAll = haveItem(player, ite.next());
		}
		return haveAll;
	}

	private boolean haveItem(Player player, String item) {
		Integer qty = player.getItems().get(item);
		return qty != null && qty > 0;
	}

	private List<String> findRequiereds(Schema schema) {
		List<String> items = new ArrayList<String>();
		for (String item : SPLIT.split(schema.getRequiereds())) {
			items.add(item.trim());
		}
		return items;
	}

	private Schema getSchema(Adventure adventure, String item) {
		Schema schema = null;
		Iterator<Schema> schemes = adventure.getSchemes().iterator();
		while (schema == null && schemes.hasNext()) {
			Schema current = schemes.next();
			if (item.equals(current.getItem())) {
				schema = current;
			}
		}
		return schema;
	}

	private List<String> findSchemaNames(Adventure adventure) {
		List<String> names = new ArrayList<String>();
		for (Schema schema : adventure.getSchemes()) {
			names.add(schema.getItem());
		}
		return names;
	}

}
