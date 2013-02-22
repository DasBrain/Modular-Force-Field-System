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

public class ForceFieldBlock {
	public int typ;
	public int Projektor_ID;
	public int Generator_Id;

	public ForceFieldBlock(int Generator_Id, int Projektor_ID, int typ) {
		this.Projektor_ID = Projektor_ID;
		this.Generator_Id = Generator_Id;
		this.typ = typ;
	}

	@Override
	public String toString(){
		return Projektor_ID+"-"+Generator_Id+"-"+typ;
	}
}
