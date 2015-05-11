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
import shionn.hexas.bot.messages.MessageBuilder;
import shionn.hexas.mongo.mo.adventure.AdventureMo;
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


	public void run(PlayerMo player, AdventureMo adventure, MessageEvent<HexasBot> event) {
		String item = event.getMessage().replace(adventure.getCommands().getShop(), "").trim();
		if (item.length() == 0) {
			help(adventure, event);
		} else {
			ItemShopMo itemShop = findItemShop(adventure, item);
			if (itemShop == null) {
				new MessageBuilder(adventure.getMessages().getShopNoItem()).item(item).send(event);
			} else if (haveEnouth(itemShop, player)) {
				player.po(-itemShop.getSellPrice());
				player.item(itemShop.getItem(), 1);
				new MessageBuilder(adventure.getMessages().getShopBuy()).item(itemShop).send(event);
			} else {
				new MessageBuilder(adventure.getMessages().getShopNotEnouthMoney()).item(item)
						.send(event);
			}
			player.setLastShop(System.currentTimeMillis());
			players.save(player);
		}
	}


	private boolean haveEnouth(ItemShopMo itemShop, PlayerMo player) {
		return player.getPo() >= itemShop.getSellPrice();
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
		return itemShop ;
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
		new MessageBuilder(adventure.getMessages().getHelpShop()).items(items)
				.send(event);
	}

}
