package shionn.hexas.bot.handlers.adventure;

import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.action.NextLvl;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;
import shionn.hexas.mongo.mo.adventure.UseMo;

/**
 * Traite les commande d'utilisation d'item
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class ItemUseHandle {
	@Inject
	private NextLvl nextLvl;
	@Inject
	private JacksonDBCollection<PlayerMo, String> players;

	public void run(Player player, AdventureMo adventure, MessageEvent<HexasBot> event) {
		String item = event.getMessage().replace(adventure.getCommands().getItemUse(), "").trim();
		if (player.item(item) > 0) {
			UseMo use = findUse(adventure, item);
			if (use == null) {
				new Message(adventure).noUse().item(item).send(event);
			} else {
				use(player, use, adventure).send(event);
			}
			players.save(player.updateLastItemUse().mo());
		} else if (item.length() == 0) {
			new Message(adventure).helpUse().send(event);
		} else {
			new Message(adventure).noItem().item(item).send(event);
			players.save(player.updateLastItemUse().mo());
		}
	}

	public Message use(Player player, UseMo use, AdventureMo adventure) {
		player.item(use.getItem(), -1);
		switch (use.getUsage()) {
		case pvGain:
			return pvGain(player, use, adventure);
		case xpGain:
			return xpGain(player, use, adventure);
		default:
			throw new IllegalStateException("Shionn, n'as pas encore fait : " + use.getUsage());
		}

	}

	private Message pvGain(Player player, UseMo use, AdventureMo adventure) {
		player.pv(Integer.parseInt(use.getVar()));
		return new Message(adventure).use(use);
	}

	public Message xpGain(Player player, UseMo use, AdventureMo adventure) {
		player.xp(Integer.parseInt(use.getVar()));
		Message message = new Message(adventure).use(use);
		if (nextLvl.lvlUp(adventure, player.mo())) {
			message.lvlUp().player(player.mo()).gamer();
		}
		return message;
	}

	private UseMo findUse(AdventureMo adventure, String item) {
		Iterator<UseMo> uses = adventure.getUses().iterator();
		UseMo use = null;
		while (use == null && uses.hasNext()) {
			UseMo current = uses.next();
			if (current.getItem().equalsIgnoreCase(item)) {
				use = current;
			}
		}
		return use;
	}

}
