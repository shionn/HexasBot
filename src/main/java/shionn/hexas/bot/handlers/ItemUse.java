package shionn.hexas.bot.handlers;

import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Named;

import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.NextLvl;
import shionn.hexas.bot.messages.MessageBuilder;
import shionn.hexas.mongo.mo.adventure.Adventure;
import shionn.hexas.mongo.mo.adventure.Player;
import shionn.hexas.mongo.mo.adventure.Use;

/**
 * Traite les commande d'utilisation d'item
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class ItemUse {
	@Inject
	private NextLvl nextLvl;

	public void run(Player player, Adventure adventure, MessageEvent<HexasBot> event) {
		String item = event.getMessage().replace(adventure.getCommands().getItemUse(), "").trim();
		if (haveItem(player, item)) {
			Use use = findUse(adventure, item);
			if (use == null) {
				new MessageBuilder(adventure.getMessages().getNoUse()).item(item).send(event);
			} else {
				use(player, use, adventure, item, event);
			}
		} else {
			new MessageBuilder(adventure.getMessages().getNoItem()).item(item).send(event);
		}
	}

	public void use(Player player, Use use, Adventure adventure, String item,
			MessageEvent<HexasBot> event) {
		player.getItems().put(item, player.getItems().get(item) - 1);
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

	public void pvGain(Player player, Use use, MessageEvent<HexasBot> event) {
		player.setPv(Math.min(player.getPv() + Integer.parseInt(use.getVar()), player.getMaxPv()));
		new MessageBuilder(use.getMessage()).var(use.getVar()).send(event);
	}

	public void xpGain(Player player, Use use, Adventure adventure, MessageEvent<HexasBot> event) {
		player.setXp(player.getXp() + Integer.parseInt(use.getVar()));
		MessageBuilder message = new MessageBuilder(use.getMessage()).var(use.getVar());
		if (nextLvl.lvlUp(adventure, player)) {
			message.append(adventure.getMessages().getLvlUp()).player(player);
		}
		message.send(event);
	}

	private Use findUse(Adventure adventure, String item) {
		Iterator<Use> uses = adventure.getUses().iterator();
		Use use = null;
		while (use == null && uses.hasNext()) {
			Use current = uses.next();
			if (current.getItem().equals(item)) {
				use = current;
			}
		}
		return use;
	}

	private boolean haveItem(Player player, String item) {
		Integer qty = player.getItems().get(item);
		return qty != null && qty > 0;
	}

}
