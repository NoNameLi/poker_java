import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


class CardConst{
	
	final static String[]  cardValues = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	final static String[] cardTypes={"黑桃","红桃","梅花","方片"};
}

/***
 * 卡牌类 
 * 有 花色 数值 属性
 * @author Peng
 *
 */
class Card{
	
	private String value;
	private String type;
	
	public Card(String value,String type){
		this.value = value;
		this.type = type;
	}
	
	public String getValue(){
		return value;
	}
	
	public String getType(){
		return type;
	}
	
}


/**
 * 扑克牌类 表示一副扑克 52张卡牌
 *
 * @author Peng
 *
 */
class Poker{
	
	public static final int CARDSNUM=13;
	public static final int MAX_NUM=52;
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	
	/**
	 *初始化  一副扑克  52
	 * 一共52张牌 
	 * 第m张牌
	 * m/13	 表示 花色
	 * m%13	表示 数字
	 * 
	 **/
	public void init(){
		
		for(int i = 0; i < MAX_NUM; i++){
			int valueIndex = i % CARDSNUM;
			int typeIndex = i / CARDSNUM;
			cards.add(new Card(CardConst.cardValues[valueIndex],CardConst.cardTypes[typeIndex]));
		}
	}
	
	//洗牌
	public void shuffle(){
		Collections.shuffle(cards);
	}
	/**
	 * 发牌 
	 * 向某一玩家发牌
	 * 随机选出13张牌 添加到玩家的手牌列表中
	 */
	public void dealToOne(Player player){
		
		Random random = new Random();
		for(int i= 0; i < CARDSNUM; i++){
			int cardcount = cards.size();
			int index = random.nextInt(cardcount);
			player.getCardList().add(cards.remove(index));
		}
	}
	
	/**
	 * 发牌 
	 * 向某多个玩家发牌
	 *
	 * 最后一个玩家 直接将剩余的卡牌给玩家
	 */
	public void dealToMany(ArrayList<Player> players){
		for(int i = 0; i < players.size() - 1; i++){
			dealToOne(players.get(i));
		}
		
		for(int i = 0; i < cards.size();i++){
			players.get(players.size()-1).getCardList().add(cards.get(i));
		}
		cards.clear();
	}
}

class PlayerCardComparator implements Comparator<Card>{
	public int compare(Card s1, Card s2){
		
		if(getCardLevel(s1) > getCardLevel(s2))
			return 1;
		else 
			return -1;
	}
	
	private float getCardLevel(Card card){
		float level = 0;
		String value = card.getValue();
		String type = card.getType();
		switch(value){
		case "A":
			level = 1;
			break;
		case "J":
			level = 11;
			break;
		case "Q":
			level = 12;
			break;
		case "K":
			level = 13;
			break;
		default:
			level = Integer.parseInt(value);
			break;	
		}
		for(int i = 0; i < CardConst.cardTypes.length; i++){
			if(CardConst.cardTypes[i].equals(type)){
				level += i * 0.1f;
			}
		}
		
		return level;
	}
	
}

/**
 * 玩家类 
 * 
 * 有 手牌属性 用于保存随机选出牌
 * 
 * 
 * @author Peng
 *
 */

class Player{
	private ArrayList<Card> handCard = new ArrayList<Card>();
	
	public ArrayList<Card> getCardList(){
		return handCard;
	}
	
	
	public void showCard(){
		
		Collections.sort(handCard, new PlayerCardComparator() );
		for(Card card : handCard){
			System.out.println(card.getType()+ card.getValue());
		}
			
	}
	public int getCardCount(){
		return handCard.size();
	}
}


public class PokerGame {
	public static void main(String[] args){

		Poker p = new Poker();

		ArrayList<Player> players = new ArrayList<Player>();

		for(int i = 0; i < 4; i++){
			players.add(new Player());
		}
		
		p.init();
		p.shuffle();
		p.dealToMany(players);
		
		for(int i = 0; i < 4; i++){
			
			System.out.println(1 + i +"玩家");
			System.out.println("count:" + players.get(i).getCardCount());
			players.get(i).showCard();
			System.out.println("***********************");
		}
	
	}
	
	
}
