package chb.mods.mffs.common;

import java.util.HashMap;
import java.util.Map;

public class SecurityRight
{
	public static Map<String, SecurityRight> rights = new HashMap();
	public final String rightKey;
	public final String name;
	public final String description;
	public String texture = "/chb/mods/mffs/sprites/AdvSecStationButtons.png";
	public final int texIndex;
	public static SecurityRight FFB = new SecurityRight("FFB", "ForceField Bypass", "", 0);
	public static SecurityRight EB = new SecurityRight("EB", "Edit MFFS Block", "", 1);
	public static SecurityRight CSR = new SecurityRight("CSR", "Config Security Rights", "", 2);
	public static SecurityRight SR = new SecurityRight("SR", "Stay in Area", "", 3);
	public static SecurityRight OSS = new SecurityRight("OSS", "Open Secure Storage", "", 4);
	public static SecurityRight RPB = new SecurityRight("RPB", "Change Protected Block", "", 5);
	public static SecurityRight AAI = new SecurityRight("AAI", "Allow have all Items", "", 6);
	public static SecurityRight UCS = new SecurityRight("UCS", "Use Control System", "", 7);

	public SecurityRight(String ID, String name, String description, int txIndex)
	{
		this.rightKey = ID;
		this.name = name;
		this.description = description;
		this.texIndex = txIndex;
		rights.put(ID, this);
	}
}