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
import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;
import shionn.hexas.mongo.mo.adventure.SchemaMo;

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
	private JacksonDBCollection<PlayerMo, String> players;

	public void run(PlayerMo player, AdventureMo adventure, MessageEvent<HexasBot> event) {
		String item = event.getMessage().replace(adventure.getCommands().getCraft(), "").trim();
		SchemaMo schema = getSchema(adventure, item);
		if (item.length() == 0) {
			new MessageBuilder(adventure.getMessages().getHelpCraft()).crafts(
					findSchemaNames(adventure).toString()).send(event);
		} else if (schema == null) {
			player.setLastCraft(System.currentTimeMillis());
			new MessageBuilder(adventure.getMessages().getNoSchema()).item(item).send(event);
		} else {
			List<String> requireds = findRequiereds(schema);
			if (haveAllItem(player, requireds, schema)) {
				craft(player, schema, requireds, adventure, event);
			} else {
				new MessageBuilder(adventure.getMessages().getNeedItem()).schema(schema)
						.items(requireds.toString()).send(event);
			}
			player.setLastCraft(System.currentTimeMillis());
		}
		players.save(player);
	}

	public void craft(PlayerMo player, SchemaMo schema, List<String> requireds, AdventureMo adventure,
			MessageEvent<HexasBot> event) {
		for (String required : requireds) {
			player.item(required, -1);
		}
		player.po(-schema.getPo());
		player.item(schema.getItem(), 1);
		new MessageBuilder(adventure.getMessages().getCraft()).schema(schema)
				.items(requireds.toString()).send(event);
	}

	private boolean haveAllItem(PlayerMo player, List<String> requireds, SchemaMo schema) {
		boolean haveAll = player.getPo() >= schema.getPo();
		Iterator<String> ite = requireds.iterator();
		while (haveAll && ite.hasNext()) {
			haveAll = haveItem(player, ite.next());
		}
		return haveAll;
	}

	private boolean haveItem(PlayerMo player, String item) {
		Integer qty = player.getItems().get(item);
		return qty != null && qty > 0;
	}

	private List<String> findRequiereds(SchemaMo schema) {
		List<String> items = new ArrayList<String>();
		for (String item : SPLIT.split(schema.getRequiereds())) {
			items.add(item.trim());
		}
		return items;
	}

	private SchemaMo getSchema(AdventureMo adventure, String item) {
		SchemaMo schema = null;
		Iterator<SchemaMo> schemes = adventure.getSchemes().iterator();
		while (schema == null && schemes.hasNext()) {
			SchemaMo current = schemes.next();
			if (item.equalsIgnoreCase(current.getItem())) {
				schema = current;
			}
		}
		return schema;
	}

	private List<String> findSchemaNames(AdventureMo adventure) {
		List<String> names = new ArrayList<String>();
		for (SchemaMo schema : adventure.getSchemes()) {
			names.add(schema.getItem());
		}
		return names;
	}

}
