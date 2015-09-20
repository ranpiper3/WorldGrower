/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.goal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.ItemGenerator;

public class UTestBuySellUtils {

	@Test
	public void testGetPrice() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		performer.getProperty(Constants.INVENTORY).add(ItemGenerator.getIronCuirass(0f));
		performer.setProperty(Constants.PROFIT_PERCENTAGE, 100);
		
		assertEquals(600, BuySellUtils.getPrice(performer, 0));
	}
}