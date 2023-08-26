/**
	c-pture/Pegasus (Manu on Rune-Server)
	Copyright (c) 2022 - Pegasus/C-Pture Team
	Written by Manuel K. - Germany
	Version: 1.0.0
	
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.pegasus.mm;

import java.util.ArrayList;

import cache.test.loaders.ClientScriptMap;

public class MusicUpdateManager {

	private transient int currentRegion;
	private transient int lastRegion = -1;
	
	private transient int curMusicID = -1;
	private transient int lastMusicID = -1;
	
	private transient long playingMusicDelay;
	
	private Player player;
	
	//private HashMap<Integer, String> unlocked = new HashMap<Integer, String>();
	
	private ArrayList<Integer> unlocked = new ArrayList<Integer>();
	
	public MusicUpdateManager(Player player) {
		this.player = player;
	}
	
	public void update() {
			currentRegion = player.getLocation().getRegionId();
			if(currentRegion != lastRegion) {
				lastRegion = currentRegion;
				int musicID = // !-- IMPORTANT --!
				// Add your way to get the music id it should play, for example random number or by region!
				
				if(curMusicID != -1) {
					lastMusicID = curMusicID;
				}
				curMusicID = musicID;
				
				if(curMusicID == lastMusicID) {
					return;
				}
				//checkUnlocked();
				refreshMusicListConfig();
				playMusic(musicID);
			}
			if(musicEnded()) {
				replayMusic();
			}
	}
	
	public boolean musicEnded() {
		return curMusicID != -2 && playingMusicDelay + (180000) < Misc.currentTimeMillis();//Misc.currentTimeMillis is just current time in ms + correction
	}
	
	public void playMusic(int musicId) {
		playingMusicDelay = Misc.currentTimeMillis();//Misc.currentTimeMillis is just current time in ms + correction
		if (musicId == -2) {
			curMusicID = musicId;
			// !-- IMPORTANT --!
			// Add your packet to play music right here!
			// And update the title/text in the music player according to the current music title playing
			return;
		}
		// !-- IMPORTANT --!
		// Add your packet to play music right here!
		
		// !-- WARNING -- !
		//The operations below require advanced prep such as cache reading!
		curMusicID = musicId;
		int musicIndex = (int) ClientScriptMap.getMap(1351).getKeyForValue(musicId);
		if (musicIndex != -1) {
			String musicName = ClientScriptMap.getMap(1345).getStringValue(musicIndex);
					// !-- IMPORTANT --!
					// Send your music title to the music player here
			if (!unlocked.contains(musicId)) {
				unlocked.add(musicId);
				if (musicName != null)
					// !-- IMPORTANT --!
					// Add your unlock message here! -> You have unlocked a new music track: " + musicName + "."
				refreshMusicListConfig();
			}
		}
	}
	
	public void replayMusic() {
	   if(curMusicID != -1) {
		   lastMusicID = curMusicID;
	   }
       if (unlocked.size() > 0) // random music
			curMusicID = unlocked.get(Misc.getRandom(unlocked.size() - 1));
		
		/*if(curMusicID == lastMusicID) {
			replayMusic();//TODO: Stack Overflow at low unlock number
			return;
		}*/
		playMusic(curMusicID);
	}
	
	// !-- WARNING --!
	// This sends the configs to the music list, making them turn green (Unlocked)
	// Requires cache reading, edit and use this if you need it, else disable this and remove the reference refreshMusicListConfig()
	public void refreshMusicListConfig() {
		int[] musicConfigs = {20, 21, 22, 23, 24, 25, 298, 311, 346, 414, 464, 598, 662, 721, 906, 1009, 1104, 1136, 1180, 1202};
		int[] configValues = new int[musicConfigs.length];
		for (int musicId : unlocked) {
			int musicIndex = (int) ClientScriptMap.getMap(1351).getKeyForValue(musicId);
			if (musicIndex == -1)
				continue;
			int index = getConfigIndex(musicIndex);
			if (index >= musicConfigs.length)
				continue;
			configValues[index] |= 1 << (musicIndex - (index * 32));
		}
		for (int i = 0; i < musicConfigs.length; i++) {
			if (musicConfigs[i] != -1 && configValues[i] != 0)
				player.getActionSender().sendConfig(musicConfigs[i], configValues[i]);
		}
		
	}
	
	public int getConfigIndex(int musicId) {
		return (musicId + 1) / 32;
	}
	
}
