package shionn.hexas.bot.messages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.mongo.mo.adventure.DropMo;
import shionn.hexas.mongo.mo.adventure.GamerMo;
import shionn.hexas.mongo.mo.adventure.ItemShopMo;
import shionn.hexas.mongo.mo.adventure.MonsterMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;
import shionn.hexas.mongo.mo.adventure.SchemaMo;

public class MessageBuilder {

	private String message;

	private Map<String, String> substitution = new HashMap<String, String>();

	public MessageBuilder(String message) {
		this.message = message;
	}

	public String message() {
		StrSubstitutor substitutor = new StrSubstitutor(substitution);
		return substitutor.replace(message);
	}

	public MessageBuilder append(String message) {
		if (!message.isEmpty()) {
			this.message = this.message.concat(" ");
		}
		this.message = this.message.concat(message);
		return this;
	}

	public MessageBuilder event(MessageEvent<HexasBot> event) {
		substitution.put("user", event.getUser().getNick());
		substitution.put("channel", event.getChannel().getName());
		return this;
	}

	public MessageBuilder player(PlayerMo player) {
		return pv(player.getPv()).maxPv(player.getMaxPv()).lvl(player.getLvl()).xp(player.getXp())
				.po(player.getPo()).mp(player.getMp()).maxMp(player.getMaxMp());
	}

	public MessageBuilder maxMp(int maxMp) {
		return substitution("maxMp", maxMp);
	}

	public MessageBuilder mp(int mp) {
		return substitution("mp", mp);
	}

	public MessageBuilder xp(int xp) {
		return substitution("xp", xp);
	}

	public MessageBuilder lvl(int lvl) {
		return substitution("lvl", lvl);
	}

	public MessageBuilder maxPv(int maxPv) {
		return substitution("maxPv", maxPv);
	}

	public MessageBuilder monster(MonsterMo monster) {
		return monster(monster.getName()).xp(monster.getXp());
	}

	public MessageBuilder monster(String monster) {
		return substitution("monster", monster);
	}

	public void send(MessageEvent<HexasBot> event) {
		event.getChannel().send().message(event(event).message());
	}

	public MessageBuilder drop(DropMo drop) {
		substitution.put("item", drop.getItem());
		return this;
	}

	public MessageBuilder po(int po) {
		return substitution("po", po);
	}

	public MessageBuilder coldDown(int coldDown) {
		substitution.put("coldDown", Integer.toString(coldDown));
		return this;
	}

	public MessageBuilder pv(int damage) {
		substitution.put("pv", Integer.toString(damage));
		return this;
	}

	public MessageBuilder nextXp(int xp) {
		substitution.put("nextXp", Integer.toString(xp));
		return this;
	}

	public MessageBuilder bag(String bag) {
		substitution.put("bag", bag);
		return this;
	}

	public MessageBuilder item(String item) {
		substitution.put("item", item);
		return this;
	}

	public MessageBuilder var(String var) {
		substitution.put("var", var);
		return this;
	}

	public MessageBuilder schema(SchemaMo schema) {
		return item(schema.getItem()).po(schema.getPo()).items(schema.getRequiereds());
	}

	public MessageBuilder items(String items) {
		substitution.put("items", items);
		return this;
	}

	public MessageBuilder crafts(String crafts) {
		substitution.put("crafts", crafts);
		return this;
	}

	public MessageBuilder command(String cmd) {
		return substitution("cmd", cmd);
	}

	public MessageBuilder items(List<String> items) {
		return items(StringUtils.join(items, ", "));
	}

	public MessageBuilder item(ItemShopMo itemShop) {
		return item(itemShop.getItem()).po(itemShop.getSellPrice());
	}

	public MessageBuilder gamer(GamerMo gamer) {
		return pvFactor(gamer.getPvFactor());
	}

	public MessageBuilder pvFactor(int pvFactor) {
		return substitution("pvFactor",pvFactor);
	}

	private MessageBuilder substitution(String key, int value) {
		return substitution(key, Integer.toString(value));
	}

	private MessageBuilder substitution(String key, String value) {
		substitution.put(key, value);
		return this;
	}

}
