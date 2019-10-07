package pkgHelper;

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import pkgCore.GamePlay;
import pkgCore.HandPoker;

public class GamePlayHelper {

	public static GamePlay PutGamePlay(GamePlay gp, UUID PlayerID, HandPoker hp) {

		try {

			//	Find a method with arguments UUID, HandPoker
			Method mPutGameHand = gp.getClass().getDeclaredMethod("PutGameHand",
					new Class[] { UUID.class, HandPoker.class });
			//	Set up the arguments to pass a method
			Object[] ArgsPutHand = new Object[] { PlayerID, hp };
			// Change the visibility of 'setCardsInHand' to true *Good Grief!*
			mPutGameHand.setAccessible(true);

			//	Invoke the method for a given instance of a class, set arguments
			mPutGameHand.invoke(gp, ArgsPutHand);

		} catch (SecurityException e) {
			fail("Security Exception Thrown");
		} catch (IllegalArgumentException e) {
			fail("Illegal Arugment Exception Thrown");
		} catch (IllegalAccessException e) {
			fail("Illegal Access Exception Thrown");
		} catch (NoSuchMethodException e) {
			fail("No Such Method Exception Thrown");
		} catch (InvocationTargetException e) {
			fail("Invocation Target Exception Thrown");
		}
		return (GamePlay) gp;

	}
}
