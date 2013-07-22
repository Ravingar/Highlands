package highlands.worldgen.layer;

import highlands.HighlandsMain;
import highlands.api.HighlandsBiomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeHL extends GenLayer
{
	private WorldType wt;
	
    /** this sets all the biomes that are allowed to appear in the overworld */
    private BiomeGenBase[] allowedBiomes;

    public GenLayerBiomeHL(long par1, GenLayer par3GenLayer, WorldType par4WorldType)
    {
        super(par1);
        this.allowedBiomes = par4WorldType.getBiomesForWorldType();
        this.parent = par3GenLayer;
        wt = par4WorldType;
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        int[] var6 = IntCache.getIntCache(par3 * par4);

        for (int var7 = 0; var7 < par4; ++var7)
        {
            for (int var8 = 0; var8 < par3; ++var8)
            {
                this.initChunkSeed((long)(var8 + par1), (long)(var7 + par2));
                int var9 = var5[var8 + var7 * par3];

                
                //System.out.println("  Biome Gen Layer Uses: " + var8 + var7 * par3);
                
                if (var9 == 0)
                {
                	if(HighlandsMain.improvedOceans)
                		var6[var8 + var7 * par3] = HighlandsBiomes.ocean2.biomeID;
                	else var6[var8 + var7 * par3] = BiomeGenBase.ocean.biomeID;
                }
                else if (var9 == BiomeGenBase.mushroomIsland.biomeID)
                {
                    var6[var8 + var7 * par3] = var9;
                }
                else if (var9 == 1)
                {
                    var6[var8 + var7 * par3] = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;
                }
                else
                {
                    int var10 = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;

                    if (var10 == BiomeGenBase.taiga.biomeID)
                    {
                        var6[var8 + var7 * par3] = var10;
                    }
                    else
                    {
                    	if(wt == WorldType.DEFAULT || wt == WorldType.LARGE_BIOMES)
                    		var6[var8 + var7 * par3] = BiomeGenBase.icePlains.biomeID;
                    	else{
                    		if(HighlandsMain.improvedOceans)
                        		var6[var8 + var7 * par3] = HighlandsBiomes.ocean2.biomeID;
                        	else var6[var8 + var7 * par3] = BiomeGenBase.ocean.biomeID;
                    	}
                    }
                }
            }
        }

        return var6;
    }
}
