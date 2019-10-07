package pkgGame;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import pkgCore.Card;
import pkgCore.GamePlay;
import pkgCore.HandPoker;
import pkgCore.Player;
import pkgCore.Rule;
import pkgCore.Table;
import pkgEnum.eGame;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;
import pkgHelper.GamePlayHelper;
import pkgHelper.HandPokerHelper;

public class GamePlayTest {

	@Test
	public void GamePlay_Test1() {
		Table t = new Table("Table 1");
		t.AddPlayerToTable(new Player("Bert"));
		t.AddPlayerToTable(new Player("Joe"));
		t.AddPlayerToTable(new Player("Jim"));
		t.AddPlayerToTable(new Player("Jane"));

		Rule rle = new Rule(eGame.TexasHoldEm);
		GamePlay gp = new GamePlay(t, rle);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(eSuit.HEARTS, eRank.TWO));
		cards.add(new Card(eSuit.HEARTS, eRank.THREE));
		
		Player p1 = new Player("Bert");
		Player p2 = new Player("Joe");
		
		ArrayList<Card> p2Cards = new ArrayList<Card>();
		cards.add(new Card(eSuit.HEARTS, eRank.ACE));
		cards.add(new Card(eSuit.DIAMONDS, eRank.ACE));
		
		HandPoker hp1 = HandPokerHelper.SetHand(cards, new HandPoker());
		HandPoker hp2 = HandPokerHelper.SetHand(p2Cards, new HandPoker());
		
		gp = GamePlayHelper.PutGamePlay(gp, p1.getPlayerID(), hp1);		
		gp = GamePlayHelper.PutGamePlay(gp, p2.getPlayerID(), hp2);
		
		try {
			gp.EvaluateGameHands();
		} catch (HandException e) {
			fail("Evaluate hands failed");
		}

	}

}
