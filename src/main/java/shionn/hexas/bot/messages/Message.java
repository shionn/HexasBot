package shionn.hexas.bot.messages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.DropMo;
import shionn.hexas.mongo.mo.adventure.GamerMo;
import shionn.hexas.mongo.mo.adventure.ItemShopMo;
import shionn.hexas.mongo.mo.adventure.MonsterMo;
import shionn.hexas.mongo.mo.adventure.SchemaMo;
import shionn.hexas.mongo.mo.adventure.UseMo;

public class Message {

	private String message;

	private Map<String, String> substitution = new HashMap<String, String>();

	private AdventureMo adventure;

	public Message(String message) {
		this.message = message;
	}

	public Message(AdventureMo adventure) {
		this("");
		this.adventure = adventure;
	}

	public Message(Player player) {
		this(player.adventure());
		player(player);
	}

	public String message() {
		StrSubstitutor substitutor = new StrSubstitutor(substitution);
		return substitutor.replace(message);
	}

	public Message coldDown() {
		return append(adventure.getMessages().getColdDown());
	}

	/*
	 * combat
	 */
	public Message battleWin() {
		return append(adventure.getMessages().getBattleWin());
	}

	public Message battleLoose() {
		return append(adventure.getMessages().getBattleLoose());
	}

	public Message pvGain() {
		return append(adventure.getMessages().getPvGain());
	}

	public Message pvLoose() {
		return append(adventure.getMessages().getPvLoose());
	}

	public Message xpGain() {
		return append(adventure.getMessages().getXpGain());
	}

	public Message xpLoose() {
		return append(adventure.getMessages().getXpLoose());
	}

	public Message poGain() {
		return append(adventure.getMessages().getPoGain());
	}

	public Message poLoose() {
		return append(adventure.getMessages().getPoLoose());
	}

	public Message lvlUp() {
		return append(adventure.getMessages().getLvlUp());
	}

	public Message itemGain() {
		return append(adventure.getMessages().getItemGain());
	}

	/*
	 * Sac
	 */
	public Message bagEmpty() {
		return append(adventure.getMessages().getBagEmpty());
	}

	public Message bag() {
		return append(adventure.getMessages().getBag());
	}

	/*
	 * Craft
	 */
	public Message craft() {
		return append(adventure.getMessages().getCraft());
	}

	public Message helpCraft() {
		return append(adventure.getMessages().getHelpCraft());
	}

	public Message needItem() {
		return append(adventure.getMessages().getNeedItem());
	}

	public Message noSchema() {
		return append(adventure.getMessages().getNoSchema());
	}

	/*
	 * Utilisation d'objet
	 */
	public Message noUse() {
		return append(adventure.getMessages().getNoUse());
	}

	public Message helpUse() {
		return append(adventure.getMessages().getHelpUse());
	}

	public Message noItem() {
		return append(adventure.getMessages().getNoItem());
	}

	public Message use(UseMo use) {
		return append(use.getMessage()).var(use.getVar());
	}

	/*
	 * Shop
	 */
	public Message shopNoItem() {
		return append(adventure.getMessages().getShopNoItem());
	}

	public Message shopBuy() {
		return append(adventure.getMessages().getShopBuy());
	}

	public Message shopNotEnouthMoney() {
		return append(adventure.getMessages().getShopNotEnouthMoney());
	}

	public Message shopHelp() {
		return append(adventure.getMessages().getHelpShop());
	}

	/*
	 * Stat du joueur
	 */
	public Message stat() {
		return append(adventure.getMessages().getStat());
	}

	private Message append(String message) {
		if (!message.isEmpty()) {
			this.message = this.message.concat(" ");
		}
		this.message = this.message.concat(message);
		return this;
	}

	public Message event(MessageEvent<HexasBot> event) {
		substitution.put("user", event.getUser().getNick());
		substitution.put("channel", event.getChannel().getName());
		return this;
	}

	public Message player(Player player) {
		return pv(player.pv()).maxPv(player.maxPv()).lvl(player.lvl()).xp(player.xp())
				.po(player.po()).mp(player.mp()).maxMp(player.maxMp()).def(player.defPc());
	}

	public Message maxMp(int maxMp) {
		return substitution("maxMp", maxMp);
	}

	public Message mp(int mp) {
		return substitution("mp", mp);
	}

	public Message xp(int xp) {
		return substitution("xp", xp);
	}

	public Message lvl(int lvl) {
		return substitution("lvl", lvl);
	}

	public Message maxPv(int maxPv) {
		return substitution("maxPv", maxPv);
	}

	private Message def(int def) {
		return substitution("def", def);
	}

	public Message monster(MonsterMo monster) {
		return monster(monster.getName()).xp(monster.getXp());
	}

	public Message monster(String monster) {
		return substitution("monster", monster);
	}

	public void send(MessageEvent<HexasBot> event) {
		event.getChannel().send().message(event(event).message());
	}

	public Message drop(DropMo drop) {
		substitution.put("item", drop.getItem());
		return this;
	}

	public Message po(int po) {
		return substitution("po", po);
	}

	public Message coldDown(float coldDown) {
		substitution.put("coldDown", Float.toString(coldDown));
		return this;
	}

	public Message pv(int damage) {
		substitution.put("pv", Integer.toString(damage));
		return this;
	}

	public Message nextXp(int xp) {
		substitution.put("nextXp", Integer.toString(xp));
		return this;
	}

	public Message bag(Map<String, Integer> bag) {
		return substitution("bag", bag.toString());
	}

	public Message item(String item) {
		substitution.put("item", item);
		return this;
	}

	public Message var(String var) {
		substitution.put("var", var);
		return this;
	}

	public Message schema(SchemaMo schema) {
		return item(schema.getItem()).po(schema.getPo()).items(schema.getRequiereds());
	}

	public Message items(String items) {
		substitution.put("items", items);
		return this;
	}

	public Message crafts(String crafts) {
		substitution.put("crafts", crafts);
		return this;
	}

	public Message command(String cmd) {
		return substitution("cmd", cmd);
	}

	public Message items(List<String> items) {
		return items(StringUtils.join(items, ", "));
	}

	public Message item(ItemShopMo itemShop) {
		return item(itemShop.getItem()).po(itemShop.getSellPrice());
	}

	@Deprecated
	public Message gamer(GamerMo gamer) {
		return pvFactor(gamer.getPvFactor());
	}

	public Message gamer() {
		return pvFactor(adventure.getGamer().getPvFactor());
	}

	public Message pvFactor(int pvFactor) {
		return substitution("pvFactor", pvFactor);
	}

	private Message substitution(String key, int value) {
		return substitution(key, Integer.toString(value));
	}

	private Message substitution(String key, String value) {
		substitution.put(key, value);
		return this;
	}

}
