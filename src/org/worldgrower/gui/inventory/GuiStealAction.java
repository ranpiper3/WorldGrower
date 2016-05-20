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
package org.worldgrower.gui.inventory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import org.worldgrower.Args;
import org.worldgrower.Constants;
import org.worldgrower.DungeonMaster;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.gui.ImageInfoReader;
import org.worldgrower.gui.WorldPanel;
import org.worldgrower.gui.start.Game;
import org.worldgrower.gui.util.TextInputDialog;
import org.worldgrower.util.NumberUtils;

public class GuiStealAction extends AbstractAction {

	private WorldObject playerCharacter;
	private World world;
	private DungeonMaster dungeonMaster;
	private WorldPanel container;
	private WorldObject target;
	private InventoryDialog dialog;
	private ImageInfoReader imageInfoReader;
	
	public GuiStealAction(WorldObject playerCharacter, World world, DungeonMaster dungeonMaster, WorldPanel container, WorldObject target, ImageInfoReader imageInfoReader) {
		super();
		this.playerCharacter = playerCharacter;
		this.world = world;
		this.dungeonMaster = dungeonMaster;
		this.container = container;
		this.target = target;
		this.imageInfoReader = imageInfoReader;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		InventoryActionFactory inventoryActionFactory = new InventoryActionFactory(playerCharacter, imageInfoReader, world, dungeonMaster, container);
		dialog = new InventoryDialog(new InventoryDialogModel(playerCharacter, target), new InventoryDialogStealAction(), imageInfoReader, inventoryActionFactory);
		inventoryActionFactory.setDialog(dialog);
		dialog.showMe();
	}

	private class InventoryDialogStealAction implements InventoryDialogAction {

		@Override
		public String getDescription() {
			return "Steal selected item";
		}
		
		@Override
		public String getDescription2() {
			return "Steal money";
		}

		@Override
		public ActionListener getGuiAction() {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					InventoryItem inventoryItem = getSelectedItem(dialog);
					int[] args = new int[] { inventoryItem.getId() };
					steal(args);
					
					dialog.refresh(new InventoryDialogModel(playerCharacter, target));
				}
			};
		}
		
		@Override
		public ActionListener getGuiAction2() {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					int targetGold = target.getProperty(Constants.GOLD).intValue();
					TextInputDialog textInputDialog = new TextInputDialog("Steal how much money (1-" + targetGold + ")?", true);
					String input = textInputDialog.showMe();
					if (input != null && input.length() > 0 && NumberUtils.isNumeric(input)) {
						int amount = Integer.parseInt(input);
						if (amount > 0 && amount <= targetGold) {
							int[] args = new int[] { amount };
							stealMoney(args);
							
							dialog.refresh(new InventoryDialogModel(playerCharacter, target));
						}
					}
				}
			};
		}

		@Override
		public boolean isPossible(InventoryItem inventoryItem) {
			return Game.canActionExecute(playerCharacter, playerCharacter.getOperation(Actions.STEAL_ACTION), Args.EMPTY, world, target);
		}

		@Override
		public InventoryItem getSelectedItem(InventoryDialog dialog) {
			return dialog.getTargetSelectedValue();
		}
		
		@Override
		public boolean allowCancel() {
			return true;
		}
	}
	
	public void steal(int[] args) {
		Game.executeActionAndMoveIntelligentWorldObjects(playerCharacter, playerCharacter.getOperation(Actions.STEAL_ACTION), args, world, dungeonMaster, target, container);
	}
	
	public void stealMoney(int[] args) {
		Game.executeActionAndMoveIntelligentWorldObjects(playerCharacter, playerCharacter.getOperation(Actions.STEAL_GOLD_ACTION), args, world, dungeonMaster, target, container);
	}
}