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
package org.worldgrower.generator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;

public class UTestCreatureGenerator {

	private final CreatureGenerator creatureGenerator = new CreatureGenerator(TestUtils.createWorldObject(7, "TestOrg"));
	
	@Test
	public void testGenerateRat() {
		World world = new WorldImpl(10, 10, null, null);
		
		int id = creatureGenerator.generateRat(2, 2, world);
		assertEquals(0, id);
		
		WorldObject worldObject = world.findWorldObjectById(id);
		assertEquals(2, worldObject.getProperty(Constants.X).intValue());
		assertEquals(2, worldObject.getProperty(Constants.Y).intValue());
	}
	
}