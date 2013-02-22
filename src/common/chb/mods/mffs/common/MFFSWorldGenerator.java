/*  
    Copyright (C) 2012 Thunderdark

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
    
    Contributors:
    Thunderdark - initial implementation
*/

package chb.mods.mffs.common;

import java.util.Random;

import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class MFFSWorldGenerator implements IWorldGenerator{
		@Override
		public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
		{
			chunkX = chunkX << 4;
			chunkZ = chunkZ << 4;

	                	WorldGenMinable worldGenMinable = new WorldGenMinable(ModularForceFieldSystem.MFFSMonazitOre.blockID, 0, 8);

	                    for (int i = 0; i < 8; i++)
	                    {
	                        int x = chunkX + rand.nextInt(16);
	                        int y = rand.nextInt(80) + 0;
	                        int z = chunkZ + rand.nextInt(16);

	            			int randAmount = rand.nextInt(8);

	                    	worldGenMinable.generate(world, rand, x, y, z);
	                    }
		}
	}
