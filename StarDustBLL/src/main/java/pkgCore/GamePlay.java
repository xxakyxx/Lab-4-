package pkgCore;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pkgEnum.eCardDestination;
import pkgEnum.eDrawCount;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;

public class GamePlay { 

	private Rule rle;
	private ArrayList<Player> GamePlayers = new ArrayList<Player>();
	private HashMap<UUID, HandPoker> GameHand = new HashMap<UUID, HandPoker>();
	private ArrayList<Card> CommonCards = new ArrayList<Card>();
	private HashMap<UUID, HandPoker> BestMadeHand = new HashMap<UUID, HandPoker>();
	private HashMap<UUID, ArrayList<HandPoker>> BestPossibleHands = new HashMap<UUID, ArrayList<HandPoker>>();
	private Player PlayerButton;
	private LinkedList PlayerBetPosition;
	private Deck GameDeck;

	
	private void PutGameHand(UUID PlayerID, HandPoker hp)
	{
		GameHand.put(PlayerID,  hp);
	}
	
	
	/**
	 * GamePlay - Create an instance of GamePlay. For every player in the table, add
	 * them to the game Set the GameDeck.
	 * 
	 * @param t
	 * @param rle
	 */
	public GamePlay(Table t, Rule rle) {
		GamePlayers.addAll(t.getTablePlayers());
		GameDeck = new Deck();
	}

	
	public void Draw(Player p, CardDraw CD) throws DeckException, HandException {

		for (int crdCnt = 0; crdCnt < CD.getCardCount().getCardCount(); crdCnt++) {
			if (CD.getCardDestination() == eCardDestination.COMMON) {
				CommonCards.add(GameDeck.Draw());
			} else {
				GameHand.get(p.getPlayerID()).Draw(GameDeck);
			}
		}
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * EvaluateGameHands - Find every hand in the GameHand map and 
	 * evaluate it.  
	 * 
	 * @throws HandException
	 */
	public void EvaluateGameHands() throws HandException
	{
		Iterator<Map.Entry<UUID, HandPoker>> itr = GameHand.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<UUID, HandPoker> entry = itr.next();
			
			HandPoker hp = entry.getValue();
			hp = hp.EvaluateHand(hp);		
		}
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * getBestMadeHand - Return the best made hand for the player
	 * @param player
	 * @return
	 * @throws HandException 
	 */
	public HandPoker getBestMadeHand(Player player) throws HandException {
		//FIXME: this should not return null, it should return back the best made hand in the hashmap
//		this.EvaluateGameHands();
//		Iterator<Map.Entry<UUID, HandPoker>> iter = GameHand.entrySet().iterator();
//		HandPoker max = iter.next().getValue();
//		while(iter.hasNext()) {
//			Map.Entry<UUID, HandPoker> entry = iter.next();
//			max = entry.getValue().compareTo(max) == 1 ? entry.getValue() : max;
//		}
		return this.BestMadeHand.get(player.getPlayerID());
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * getBestPossibleHands - return a list of best possible hands for a player
	 * Could be more than one (example, same straight, but different suits)
	 * @param player
	 * @return
	 * @throws HandException 
	 */
	public ArrayList<HandPoker> getBestPossibleHands(Player player) throws HandException {
		//FIXME: this should not return null, it should return an array of the best possible hands
//		ArrayList<HandPoker> res = new ArrayList<>();
//		HandPoker max = this.getBestMadeHand(player);
//		for(HandPoker h: this.GameHand.values()) {
//			if(h.compareTo(max) == 0) {
//				res.add(h);
//			}
//		}
		return this.BestPossibleHands.get(player.getPlayerID());
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * getCommonCards - returns the common cards for the game.
	 * There's a bit of a cheat- return 'jokers' for cards that are 
	 * not yet dealt.  If there are supposed to be 5 community cards,
	 * and in the current state of the game there are 3, return the
	 * three + two jokers. 
	 * 
	 * @return - list of community cards.
	 */
	public ArrayList<Card> getCommonCards() {
		int iSize = CommonCards.size();
		ArrayList<Card> commonCards = (ArrayList<Card>) CommonCards.clone();
		for (int i = iSize; i < this.getRle().getCommunityCardsMax() ; i++) {
			commonCards.add(new Card(eSuit.JOKER, eRank.JOKER));
		}
		return commonCards;
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * GetGamePlayer - return the Player object for a given PlayerID
	 * @param PlayerID - ID for the Player
	 * @return - Player object
	 */
	private Player GetGamePlayer(UUID PlayerID)
	{
		for (Player p: GamePlayers)
		{
			if (p.getPlayerID() == PlayerID)
				return p;
		}
		return null;
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * GetPlayersHand - return the Hand in the GameHand hashmap for a given player
	 * @param player 
	 * @return
	 */
	public HandPoker GetPlayersHand(Player player) {
		//FIXME: this should return back the Player's hand from the hashmap
		return this.GameHand.get(player.getPlayerID());
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * getRle - Get the rule for the game.  It's set in the constructor
	 * @return
	 */
	public Rule getRle() {
		return rle;
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * isMadeHandBestPossibleHand - return 'true' if the BestMadeHand is
	 * one of the BestPossibleHands
	 * @param player
	 * @return
	 * @throws HandException 
	 */
	public boolean isMadeHandBestPossibleHand(Player player) throws HandException {
		//FIXME: If the BestMadeHand is in the BestPossibleHands, return true.  The player has the NUTS!
		HandPoker h = this.getBestMadeHand(player);
		if(this.BestPossibleHands.get(player.getPlayerID()).contains(h))
			return true;
		
		return false;
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * SetBestMadeHand - set the BestMadeHand for a given player
	 * @param PlayerID
	 * @param HandPoker
	 */
	protected void SetBestMadeHand(UUID PlayerID, HandPoker HandPoker) {
		//FIXME: Put the best made hand for a plyer in the map.
		this.BestMadeHand.put(PlayerID, HandPoker);
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * SetBestPossibleHands - set the BestPossibleHands for a given player
	 * @param PlayerID
	 * @param BestHands
	 */
	protected void SetBestPossibleHands(UUID PlayerID, ArrayList<HandPoker> BestHands) {
		//FIXME: Set the best possible hands in the map
		this.BestPossibleHands.put(PlayerID, BestHands);
	}
	
	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * StartGame - Create a new HandPoker for each player, put it in the 
	 * GameHand map, execute the first Draw
	 * 
	 * @throws DeckException
	 * @throws HandException
	 */
	public void StartGame() throws DeckException, HandException {
		for (Player p : GamePlayers) {
			HandPoker hp = new HandPoker(p, this);
			GameHand.put(p.getPlayerID(), hp);
			Draw(p, this.rle.getCardDraw(eDrawCount.FIRST));
		}
	}
	
	
	
	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #4
	 * 
	 * GetGameWinners - Return an ArrayList of players with the winning
	 * hand.  Could be a tie...
	 * @return
	 * @throws HandException 
	 */
	public ArrayList<Player> GetGameWinners() throws HandException {
		
		//FIXME: Find all the bestmadehand from each of the players, 
		//return an ArrayList of Players that have the best hand. 
		//Two players could tie (so two entries in the ArrayList		
		ArrayList<Player> WinningPlayers = new ArrayList<Player>();
		ArrayList<HandPoker> GameHands = new ArrayList<HandPoker>();
		ArrayList<Player> GamePlayers = this.GamePlayers;
		for(Player p : GamePlayers) {
			GameHands.addAll(this.getBestPossibleHands(p));
		}
		Collections.sort(GameHands);
		for(Player p : GamePlayers)
			if(this.getBestMadeHand(p).compareTo(GameHands.get(GameHands.size() - 1)) == 0)
				WinningPlayers.add(p);
		//FIXME: finish the implmentation
		return WinningPlayers;
		// hahahahhah
	}

}
