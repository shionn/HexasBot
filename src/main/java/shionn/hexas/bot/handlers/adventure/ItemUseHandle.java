package shionn.hexas.bot.handlers.adventure;

import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.action.NextLvl;
import shionn.hexas.bot.messages.MessageBuilder;
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

	public void run(PlayerMo player, AdventureMo adventure, MessageEvent<HexasBot> event) {
		String item = event.getMessage().replace(adventure.getCommands().getItemUse(), "").trim();
		if (player.haveItem(item)) {
			UseMo use = findUse(adventure, item);
			if (use == null) {
				new MessageBuilder(adventure.getMessages().getNoUse()).item(item).send(event);
			} else {
				use(player, use, adventure, event);
			}
			player.setLastItemUse(System.currentTimeMillis());
			players.save(player);
		} else if (item.length() == 0) {
			new MessageBuilder(adventure.getMessages().getHelpUse()).send(event);
		} else {
			new MessageBuilder(adventure.getMessages().getNoItem()).item(item).send(event);
			player.setLastItemUse(System.currentTimeMillis());
			players.save(player);
		}
	}

	public void use(PlayerMo player, UseMo use, AdventureMo adventure, 
			MessageEvent<HexasBot> event) {
		player.item(use.getItem(), -1);
		switch (use.getUsage()) {
		case pvGain:
			pvGain(player, use, event);
			break;
		case xpGain:
			xpGain(player, use, adventure, event);
			break;
		default:
			new MessageBuilder("Shionn, n'as pas encore fait : " + use.getUsage()).send(event);
			break;
		}
	}

	public void pvGain(PlayerMo player, UseMo use, MessageEvent<HexasBot> event) {
		player.setPv(Math.min(player.getPv() + Integer.parseInt(use.getVar()), player.getMaxPv()));
		new MessageBuilder(use.getMessage()).var(use.getVar()).send(event);
	}

	public void xpGain(PlayerMo player, UseMo use, AdventureMo adventure, MessageEvent<HexasBot> event) {
		player.setXp(player.getXp() + Integer.parseInt(use.getVar()));
		MessageBuilder message = new MessageBuilder(use.getMessage()).var(use.getVar());
		if (nextLvl.lvlUp(adventure, player)) {
			message.append(adventure.getMessages().getLvlUp()).player(player)
					.gamer(adventure.getGamer());
		}
		message.send(event);
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
