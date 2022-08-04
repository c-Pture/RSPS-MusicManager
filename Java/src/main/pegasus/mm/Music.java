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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class Music {
	
	//<regionid, musicid>
	private static HashMap<Integer, Integer> music_ = new HashMap<Integer, Integer>();
	
	private static HashMap<String, Integer> music_list = new HashMap<String, Integer>();
	
	public Music() {
		loadMusicList();
		//TODO: load this from file maybe
		music_.put(12843, 383); //city of the dead
		music_.put(12844, 383); //city of the dead
		music_.put(12845, 352); //scarab
		music_.put(12846, 267); //sunburn
		music_.put(12847, 79); //The Desert
		music_.put(12848, 124); //Arabian 3
		music_.put(12849, 145); //Yesteryear, behind lumby graveyard
		music_.put(12850, 76); //Harmony in Lumbridge
		music_.put(12851, 2); //Autumn Voyage
		music_.put(12852, 106); //Expanse
		music_.put(12853, 56); //Garden
		music_.put(12854, 177); //Adventure
		music_.put(12855, 169); //Crystal Sword
		
		music_.put(12587, 352); //Scarab
		music_.put(12588, 352); //Scarab
		music_.put(12589, 352); //Scarab
		music_.put(12591, 79); //The Desert
		music_.put(12593, 64); //Book of spells
		music_.put(12594, 327); //Dream
		music_.put(12595, 163); //Flute salad
		music_.put(12596, 116); //Greatness
		music_.put(12597, 175); //Spirit
		music_.put(12598, 496); //Trade Parade
		music_.put(12599, 113); //Lightness
	}
	
	public int getMusicIdForRegion(int region) {
		try {
			return (int) music_.get(region);
		} catch(Exception e) {
			return 1;
		}
	}
	
	private static FileInputStream fstream;
	
	private boolean loadMusicList() {
		try {
			fstream = new FileInputStream("./data/musicdata.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				  String[] elements = strLine.split(":");
					  //0 = ID
					  //1 = Actual ID + Name
					  //2 = Actual Song name
			    String songID_Str = elements[1].replace(" ", "").replace("Name", "");
			    String songName = elements[2].replaceFirst(" ", "");
				int songID = Integer.parseInt(songID_Str);
				music_list.put(songName, songID);
			}
			System.out.println ("Total of " + music_list.size() + " music data has been loaded.");
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int getMusicIdForName(String name) {
		int id = 76;
		try {
			id = music_list.get(name);
		} catch(Exception e) {
			
		}
		return id;
	}
	
	public String getMusicNameForId(int id) {
		String songName;
		songName = getKeysByValue(music_list, id).toString().replace("[", "").replace("]", "");
		return songName;
	}
	
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
	    Set<T> keys = new HashSet<T>();
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            keys.add(entry.getKey());
	        }
	    }
	    return keys;
	}
	
}
