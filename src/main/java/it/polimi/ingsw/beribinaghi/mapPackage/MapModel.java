package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.gameNames.SectorName;

/**
 *	is the representation of a map, through a matrix of sector names
 */
public class MapModel {
	private static SectorName B = SectorName.BLANK;
	private static SectorName AB = SectorName.ALIENBASE;
	private static SectorName HB = SectorName.HUMANBASE;
	private static SectorName D = SectorName.DANGEROUS;
	private static SectorName S = SectorName.SAFE;
	private static SectorName SH = SectorName.SHALLOP;
	public static SectorName GALILEI[][] =    {{B ,D ,S ,B ,B ,S ,D ,S ,S ,S ,D ,D ,D ,D ,B ,S ,S ,S ,B ,B ,S ,S ,B},
											   {D ,SH,D ,D ,S ,D ,D ,S ,D ,D ,S ,S ,S ,D ,D ,D ,D ,D ,D ,D ,D ,SH,D},
											   {D ,D ,D ,D ,D ,D ,D ,S ,D ,D ,D ,D ,D ,S ,B ,S ,D ,D ,B ,B ,D ,D ,S},
											   {S ,D ,D ,B ,D ,D ,D ,D ,D ,D ,D ,S ,D ,D ,B ,S ,S ,S ,D ,B ,D ,D ,S},
											   {S ,S ,D ,D ,D ,D ,D ,B ,D ,D ,S ,D ,S ,D ,S ,D ,D ,D ,D ,D ,S ,D ,S},
											   {S ,D ,D ,B ,D ,D ,D ,D ,B ,D ,D ,AB ,D ,D ,D ,D ,S ,S ,D ,D ,D ,D ,S},
											   {B ,B ,D ,B ,S ,D ,S ,S ,D ,B ,B ,B ,B ,B ,D ,D ,D ,S ,D ,S ,D ,B ,B},
											   {B ,D ,D ,D ,D ,D ,D ,D ,D ,D ,D ,HB, D ,D ,D ,D ,D ,S ,D ,S ,D ,S ,B},
											   {S ,D ,D ,D ,D ,D ,D ,D ,S ,D ,S ,S ,S ,D ,S ,D ,D ,D ,D ,B ,D ,D ,D},
											   {S ,S ,D ,S ,D ,S ,D ,B ,D ,D ,D ,D ,D ,D ,D ,D ,D ,B ,B ,B ,D ,D ,S},
											   {S ,D ,D ,D ,D ,D ,D ,B ,D ,D ,S ,S ,S ,D ,D ,D ,S ,B ,B ,D ,D ,D ,S},
											   {S ,D ,D ,D ,S ,B ,S ,D ,B ,B ,D ,D ,D ,D ,D ,S ,D ,S ,D ,D ,S ,D ,S},
											   {S ,SH,D ,D ,D ,B ,D ,D ,D ,D ,B ,D ,D ,D ,D ,D ,D ,D ,D ,D ,D ,SH,D},
											   {D ,D ,S ,S ,B ,B ,S ,S ,S ,S ,D ,S ,D ,S ,S ,D ,S ,B ,B ,S ,D ,D ,D}};
											   
}