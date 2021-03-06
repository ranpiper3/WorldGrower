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
package org.worldgrower.profession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.Goal;
import org.worldgrower.goal.Goals;

public class UTestArenaFighterProfession {

	ArenaFighterProfession profession;
	List<Profession> professions;
	
	@Before
	public void setUp() {
		professions = new ArrayList<Profession>();
		profession = new ArenaFighterProfession(professions);
	}
	
	@Test
	public void testGetDescription() {
		assertEquals(profession.getDescription(), "arena fighter");
	}

	@Test
	public void testGetProfessionGoals() {
		List<Goal> goals = profession.getProfessionGoals();
		assertEquals(goals.get(0), Goals.ARENA_FIGHT_GOAL);
		assertEquals(goals.get(1), Goals.COLLECT_ARENA_REWARD_GOAL);
		assertEquals(goals.get(2), Goals.EQUIPMENT_GOAL);
	}

	@Test
	public void testGetSkillProperty() {
		assertEquals(profession.getSkillProperty(), Constants.DIPLOMACY_SKILL);
	}

	//@Test
	//public void testReadResolve() throws ObjectStreamException {
	//	ArenaFighterProfession profession2 = new ArenaFighterProfession(professions);
	//	assertEquals(profession.readResolveImpl(), profession2);
	//}

	@Test
	public void testIsPaidByVillagerLeader() {
		assertFalse(profession.isPaidByVillagerLeader());
	}

	@Test
	public void testAvoidEnemies() {
		assertFalse(profession.avoidEnemies());
	}
	
	@Test
	public void testGetSellItems() {
		List<Item> item = new ArrayList<Item>();
		assertEquals(profession.getSellItems(), item);
	}
}
