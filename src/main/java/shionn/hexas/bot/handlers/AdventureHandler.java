package shionn.hexas.bot.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.BagHandler;
import shionn.hexas.bot.handlers.adventure.BattleHandler;
import shionn.hexas.bot.handlers.adventure.CraftHandler;
import shionn.hexas.bot.handlers.adventure.ItemUseHandle;
import shionn.hexas.bot.handlers.adventure.ShopHandler;
import shionn.hexas.bot.handlers.adventure.StatHandler;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.MessageBuilder;
import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

/**
 * Traite les commandes pour l'aventure
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class AdventureHandler {

	private static final int MILLIS_IN_MIN = 60 * 1000;

	@Inject
	private JacksonDBCollection<PlayerMo, String> players;
	@Inject
	private BattleHandler battle;
	@Inject
	private StatHandler stat;
	@Inject
	private BagHandler bag;
	@Inject
	private ItemUseHandle use;
	@Inject
	private CraftHandler craft;
	@Inject
	private ShopHandler shop;

	public void handle(AdventureMo adventure, MessageEvent<HexasBot> event) {
		PlayerMo player = getPlayer(adventure, event);
		if (event.getMessage().equals(adventure.getCommands().getBattle())
				&& battleNotTooEarly(adventure, player, event)) {
			battle.run(new Player(player), adventure, event);
		} else if (event.getMessage().equals(adventure.getCommands().getStat())
				&& statNotTooEarly(adventure, player, event)) {
			player.setLastStat(System.currentTimeMillis());
			stat.run(player, adventure, event);
		} else if (event.getMessage().equals(adventure.getCommands().getBag())
				&& bagNotTooEarly(adventure, player, event)) {
			bag.run(player, adventure, event);
		} else if (event.getMessage().startsWith(adventure.getCommands().getItemUse())
				&& itemUseNotTooEarly(adventure, player, event)) {
			use.run(player, adventure, event);
		} else if (event.getMessage().startsWith(adventure.getCommands().getCraft())
				&& craftNotTooEarly(adventure, player, event)) {
			craft.run(player, adventure, event);
		} else if (event.getMessage().startsWith(adventure.getCommands().getShop())
				&& shopNotTooEarly(adventure, player, event)) {
			shop.run(player, adventure, event);
		}
	}

	private boolean battleNotTooEarly(AdventureMo adventure, PlayerMo player,
			MessageEvent<HexasBot> event) {
		return isNotTooEarly(adventure.getCommands().getBattle(), adventure.getCommands()
				.getBattleColdDown(), player.getLastBattle(), adventure, event);
	}

	private boolean statNotTooEarly(AdventureMo adventure, PlayerMo player, MessageEvent<HexasBot> event) {
		return isNotTooEarly(adventure.getCommands().getStat(), adventure.getCommands()
				.getStatColdDown(), player.getLastStat(), adventure, event);
	}

	private boolean bagNotTooEarly(AdventureMo adventure, PlayerMo player, MessageEvent<HexasBot> event) {
		return isNotTooEarly(adventure.getCommands().getBag(), adventure.getCommands()
				.getBagColdDown(), player.getLastBag(), adventure, event);
	}

	private boolean itemUseNotTooEarly(AdventureMo adventure, PlayerMo player,
			MessageEvent<HexasBot> event) {
		return isNotTooEarly(adventure.getCommands().getItemUse(), adventure.getCommands()
				.getItemUseColdDown(), player.getLastItemUse(), adventure, event);
	}

	private boolean craftNotTooEarly(AdventureMo adventure, PlayerMo player,
			MessageEvent<HexasBot> event) {
		return isNotTooEarly(adventure.getCommands().getCraft(), adventure.getCommands()
				.getCraftColdDown(), player.getLastCraft(), adventure, event);
	}

	private boolean shopNotTooEarly(AdventureMo adventure, PlayerMo player, MessageEvent<HexasBot> event) {
		return isNotTooEarly(adventure.getCommands().getShop(), adventure.getCommands()
				.getShopColdDown(), player.getLastShop(), adventure, event);
	}

	private boolean isNotTooEarly(String cmd, int coldDown, long last, AdventureMo adventure,
			MessageEvent<HexasBot> event) {
		boolean tooEarly = last + coldDown * MILLIS_IN_MIN > System.currentTimeMillis();
		if (tooEarly) {
			new MessageBuilder(adventure.getMessages().getColdDown()).command(cmd)
					.coldDown(coldDown).send(event);
		}
		return !tooEarly;
	}

	private PlayerMo getPlayer(AdventureMo adventure, MessageEvent<HexasBot> event) {
		String key = event.getUser().getNick() + event.getChannel().getName();
		PlayerMo player = players.findOneById(key);
		if (player == null) {
			player = new PlayerMo(key);
			player.setPv(adventure.getGamer().getPvBase());
			player.setMaxPv(adventure.getGamer().getPvBase());
			player.setMp(adventure.getGamer().getMpBase());
			player.setMaxMp(adventure.getGamer().getMpBase());
			players.insert(player);
		}
		return player;
	}

}
