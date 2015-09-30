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
package org.worldgrower.gui.start;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.worldgrower.Main;
import org.worldgrower.Version;
import org.worldgrower.World;
import org.worldgrower.gui.AbstractDialog;
import org.worldgrower.gui.ExceptionHandler;

public class StartScreen {

	private StartScreenDialog frame;

	private JButton btnSaveGame;

	private World world;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ExceptionHandler.registerExceptionHandler();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen window = new StartScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					ExceptionHandler.handle(e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartScreen() {
		initialize();
	}
	
	public StartScreen(World world) {
		this();
		this.world = world;
	}
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new StartScreenDialog();
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setToolTipText("Starts a new game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					CharacterCustomizationScreen characterCustomizationScreen = new CharacterCustomizationScreen();
					characterCustomizationScreen.setVisible(true);
				} catch (Exception e1) {
					ExceptionHandler.handle(e1);
				}
			}
		});
		btnNewGame.setBounds(78, 81, 157, 44);
		frame.getRootPane().setDefaultButton(btnNewGame);
		frame.addComponent(btnNewGame);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.setToolTipText("Loads a game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    loadGame(selectedFile);
				}
			}
		});
		btnLoadGame.setBounds(78, 138, 157, 44);
		frame.addComponent(btnLoadGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setToolTipText("Exits program");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(78, 266, 157, 44);
		frame.addComponent(btnExit);
		
		JLabel lblVersion = new JLabel("Version " + Version.getVersion());
		lblVersion.setToolTipText("Current version");
		lblVersion.setBounds(83, 342, 168, 21);
		frame.addComponent(lblVersion);
		
		btnSaveGame = new JButton("Save Game");
		btnSaveGame.setToolTipText("Saves current game");
		btnSaveGame.setEnabled(false);
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame parentFrame = new JFrame();

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");    

				int userSelection = fileChooser.showSaveDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    saveGame(fileToSave);
				}
			}
		});
		btnSaveGame.setBounds(78, 195, 157, 44);
		frame.addComponent(btnSaveGame);
	}
	
	private void loadGame(File selectedFile) {
		Main.load(selectedFile);
		setVisible(false);
	}
	

	private void saveGame(File fileToSave) {
		if (world == null) {
			throw new IllegalStateException("world is null");
		}
		world.save(fileToSave);
	}

	public void enableSaveButton(boolean enabled) {
		btnSaveGame.setEnabled(enabled);
	}
	
	private static class StartScreenDialog extends AbstractDialog {

		public StartScreenDialog() {
			super(337, 438);
		}
	}
}
