package shionn.hexas.bot.handlers;

import java.util.HashMap;
import java.util.Map;

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
import shionn.hexas.bot.handlers.adventure.TopHandler;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
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

	private static final int CHAT_SIZE_FACTOR = 10;
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
	@Inject
	private TopHandler top;

	private Map<String, Long> globalColdDown = new HashMap<>();

	public void handle(AdventureMo adventure, MessageEvent<HexasBot> event) {
		PlayerMo player = getPlayer(adventure, event);
		if (event.getMessage().equals(adventure.getCommands().getBattle())
				&& battleNotTooEarly(adventure, player, event)) {
			battle.run(new Player(player, adventure), event);
		} else if (event.getMessage().equals(adventure.getCommands().getStat())
				&& statNotTooEarly(adventure, player, event)) {
			player.setLastStat(System.currentTimeMillis());
			stat.run(new Player(player, adventure), event);
		} else if (event.getMessage().equals(adventure.getCommands().getBag())
				&& bagNotTooEarly(adventure, player, event)) {
			bag.run(new Player(player, adventure), event);
		} else if (event.getMessage().equals(adventure.getCommands().getTopLvl())
				&& topLvlNotTooEarly(adventure, player, event)) {
			top.runTopLvl(new Player(player, adventure), event);
			updateGlobalTooEarly(adventure.getCommands().getTopLvl(), event);
		} else if (event.getMessage().startsWith(adventure.getCommands().getItemUse())
				&& itemUseNotTooEarly(adventure, player, event)) {
			use.run(new Player(player, adventure), event);
		} else if (event.getMessage().startsWith(adventure.getCommands().getCraft())
				&& craftNotTooEarly(adventure, player, event)) {
			craft.run(new Player(player, adventure), event);
		} else if (event.getMessage().startsWith(adventure.getCommands().getShop())
				&& shopNotTooEarly(adventure, player, event)) {
			shop.run(new Player(player, adventure), event);
		}
	}

	private boolean battleNotTooEarly(AdventureMo adventure, PlayerMo player,
			MessageEvent<HexasBot> event) {
		return isNotTooEarly(adventure.getCommands().getBattle(), battleColdDown(adventure, event),
				player.getLastBattle(), adventure, event);
	}

	private float battleColdDown(AdventureMo adventure, MessageEvent<HexasBot> event) {
		float value = adventure.getCommands().getBattleColdDown();
		if (adventure.getCommands().isAutoBattleColdDown()) {
			value = Math.max(event.getChannel().getUsers().size() / CHAT_SIZE_FACTOR, 1) * value;
		}
		return value;
	}

	private boolean statNotTooEarly(AdventureMo adventure, PlayerMo player,
			MessageEvent<HexasBot> event) {
		return isNotTooEarly(adventure.getCommands().getStat(), adventure.getCommands()
				.getStatColdDown(), player.getLastStat(), adventure, event);
	}

	private boolean bagNotTooEarly(AdventureMo adventure, PlayerMo player,
			MessageEvent<HexasBot> event) {
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

	private boolean shopNotTooEarly(AdventureMo adventure, PlayerMo player,
			MessageEvent<HexasBot> event) {
		String cmd = adventure.getCommands().getShop();
		float coldDown = adventure.getCommands().getShopColdDown();
		return isNotTooEarly(cmd, coldDown, player.getLastShop(), adventure, event);
	}

	private boolean isNotTooEarly(String cmd, float coldDown, long last, AdventureMo adventure,
			MessageEvent<HexasBot> event) {
		boolean tooEarly = last + coldDown * MILLIS_IN_MIN > System.currentTimeMillis();
		if (tooEarly) {
			new Message(adventure).coldDown().coldDown(coldDown).command(cmd).send(event);
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

	private boolean topLvlNotTooEarly(AdventureMo adventure, PlayerMo player,
			MessageEvent<HexasBot> event) {
		String cmd = adventure.getCommands().getTopLvl();
		float coldDown = adventure.getCommands().getTopLvlColdDown();
		return isNotGlobalTooEarly(adventure, event, cmd, coldDown);
	}

	private boolean isNotGlobalTooEarly(AdventureMo adventure, MessageEvent<HexasBot> event,
			String cmd, float coldDown) {
		Long last = globalColdDown.get(cmd + event.getChannel().getName());
		boolean tooEarly = last != null
				&& last + coldDown * MILLIS_IN_MIN > System.currentTimeMillis();
		if (tooEarly) {
			new Message(adventure).coldDown().coldDown(coldDown).command(cmd).send(event);
		}
		return !tooEarly;
	}

	private void updateGlobalTooEarly(String cmd, MessageEvent<HexasBot> event) {
		globalColdDown.put(cmd + event.getChannel().getName(), System.currentTimeMillis());
	}

}
