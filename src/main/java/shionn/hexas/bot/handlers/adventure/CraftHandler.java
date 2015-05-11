package shionn.hexas.bot.handlers.adventure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
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
@Named
public class CraftHandler {

	private static final Pattern SPLIT = Pattern.compile("\\&");

	@Inject
	private JacksonDBCollection<PlayerMo, String> players;

	public void run(Player player, AdventureMo adventure, MessageEvent<HexasBot> event) {
		String item = event.getMessage().replace(adventure.getCommands().getCraft(), "").trim();
		SchemaMo schema = getSchema(adventure, item);
		if (item.length() == 0) {
			new Message(adventure).helpCraft().crafts(schemesNames(adventure).toString())
					.send(event);
		} else if (schema == null) {
			player.updateLastCraft();
			new Message(adventure).noSchema().item(item).send(event);
		} else {
			List<String> requireds = findRequiereds(schema);
			if (haveAllItem(player, requireds, schema)) {
				craft(player, schema, requireds, adventure).send(event);
			} else {
				new Message(adventure).needItem().schema(schema).items(requireds.toString())
						.send(event);
			}
			player.updateLastCraft();
		}
		players.save(player.mo());
	}

	public Message craft(Player player, SchemaMo schema, List<String> requireds,
			AdventureMo adventure) {
		for (String required : requireds) {
			player.item(required, -1);
		}
		player.po(-schema.getPo());
		player.item(schema.getItem(), 1);
		return new Message(adventure).craft().schema(schema).items(requireds.toString());
	}

	private boolean haveAllItem(Player player, List<String> requireds, SchemaMo schema) {
		boolean haveAll = player.po() >= schema.getPo();
		Iterator<String> ite = requireds.iterator();
		while (haveAll && ite.hasNext()) {
			haveAll = player.item(ite.next()) > 0;
		}
		return haveAll;
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

	private List<String> schemesNames(AdventureMo adventure) {
		List<String> names = new ArrayList<String>();
		for (SchemaMo schema : adventure.getSchemes()) {
			names.add(schema.getItem());
		}
		return names;
	}

}
