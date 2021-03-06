package shionn.hexas.bot.handlers.adventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.manipulator.Event;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.EventMo;
import shionn.hexas.mongo.mo.adventure.ItemShopMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

/**
 * Gere le magasin dans l'aventure
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class ShopHandler {

	@Inject
	private JacksonDBCollection<PlayerMo, String> players;
	@Inject
	private JacksonDBCollection<EventMo, String> events;

	public void run(Player player, MessageEvent<HexasBot> event) {
		String item = event.getMessage().replace(player.adventure().getCommands().getShop(), "")
				.trim();
		if (item.length() == 0) {
			help(player.adventure(), event);
		} else {
			ItemShopMo itemShop = findItemShop(player.adventure(), item);
			if (itemShop == null) {
				new Message(player).shopNoItem().item(item).send(event);
			} else if (haveEnouth(itemShop, player)) {
				player.po(-itemShop.getSellPrice());
				player.item(itemShop.getItem(), 1);
				Message message = new Message(player).shopBuy().item(itemShop);
				message.send(event);
				events.insert(new Event().shop().item(itemShop).message(message).event(event).mo());
			} else {
				new Message(player).shopNotEnouthMoney().item(item).send(event);
			}
			players.save(player.updateLastShop().mo());
		}
	}

	private boolean haveEnouth(ItemShopMo itemShop, Player player) {
		return player.po() >= itemShop.getSellPrice();
	}

	private ItemShopMo findItemShop(AdventureMo adventure, String item) {
		ItemShopMo itemShop = null;
		Iterator<ItemShopMo> shops = adventure.getShops().iterator();
		while (itemShop == null && shops.hasNext()) {
			ItemShopMo current = shops.next();
			if (current.getItem().equalsIgnoreCase(item)) {
				itemShop = current;
			}
		}
		return itemShop;
	}

	public void help(AdventureMo adventure, MessageEvent<HexasBot> event) {
		List<String> items = new ArrayList<>();
		for (ItemShopMo itemShop : adventure.getShops()) {
			Map<String, Object> subtitution = new HashMap<String, Object>();
			subtitution.put("item", itemShop.getItem());
			subtitution.put("sellPrice", itemShop.getSellPrice());
			items.add(new StrSubstitutor(subtitution).replace(adventure.getMessages()
					.getItemShopDesc()));
		}
		new Message(adventure).shopHelp().items(items).send(event);
	}

}
