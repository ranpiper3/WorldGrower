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
import org.worldgrower.MockCommonerGenerator;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.actions.VotingPropertyUtils;
import org.worldgrower.generator.CommonerGenerator;
import org.worldgrower.profession.Professions;

public class UTestOrganizationVoteGoal {

	private OrganizationVoteGoal goal = Goals.ORGANIZATION_VOTE_GOAL;
	private final CommonerGenerator commonerGenerator = new MockCommonerGenerator();
	
	@Test
	public void testCalculateGoalCandidateNull() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject organization = GroupPropertyUtils.createProfessionOrganization(null, "TestOrg", Professions.FARMER_PROFESSION, world);
		WorldObject performer = createCommoner(world, organization);
		performer.setProperty(Constants.PROFESSION, Professions.FARMER_PROFESSION);
		VotingPropertyUtils.createVotingBox(performer, organization, world);
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testCalculateGoalVoteForSelf() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject organization = GroupPropertyUtils.createProfessionOrganization(null, "TestOrg", Professions.FARMER_PROFESSION, world);
		WorldObject performer = createCommoner(world, organization);
		performer.setProperty(Constants.PROFESSION, Professions.FARMER_PROFESSION);
		
		int votingBoxId = VotingPropertyUtils.createVotingBox(performer, organization, world);
		WorldObject votingBox = world.findWorldObjectById(votingBoxId);
		votingBox.getProperty(Constants.CANDIDATES).add(performer);
		
		assertEquals(Actions.VOTE_FOR_LEADER_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testIsGoalMetNoVotingBox() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject organization = GroupPropertyUtils.create(1, "TestOrg", world);
		WorldObject performer = createCommoner(world, organization);
		
		assertEquals(true, goal.isGoalMet(performer, world));
	}

	@Test
	public void testIsGoalMetVotingBox() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject organization = GroupPropertyUtils.createProfessionOrganization(null, "TestOrg", Professions.FARMER_PROFESSION, world);
		WorldObject performer = createCommoner(world, organization);
		performer.setProperty(Constants.PROFESSION, Professions.FARMER_PROFESSION);
		
		int votingBoxId = VotingPropertyUtils.createVotingBox(performer, organization, world);
		WorldObject votingBox = world.findWorldObjectById(votingBoxId);
		votingBox.setProperty(Constants.TURN_COUNTER, 400);
		
		assertEquals(false, goal.isGoalMet(performer, world));
	}
	
	private WorldObject createCommoner(World world, WorldObject organization) {
		int commonerId = commonerGenerator.generateCommoner(0, 0, world, organization, CommonerGenerator.NO_PARENT);
		WorldObject commoner = world.findWorldObjectById(commonerId);
		return commoner;
	}
}