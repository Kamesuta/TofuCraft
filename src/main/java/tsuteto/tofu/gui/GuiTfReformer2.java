package tsuteto.tofu.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import tsuteto.tofu.gui.guiparts.GuiPart;
import tsuteto.tofu.gui.guiparts.GuiPartGaugeRevH;
import tsuteto.tofu.gui.guiparts.GuiPartTfCharge;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;
import tsuteto.tofu.tileentity.ContainerTfReformer2;
import tsuteto.tofu.tileentity.TileEntityTfReformer;

@SideOnly(Side.CLIENT)
public class GuiTfReformer2 extends GuiTfReformerBase
{
    public GuiTfReformer2(InventoryPlayer par1InventoryPlayer, TileEntityTfReformer machine)
    {
        super(new ContainerTfReformer2(par1InventoryPlayer, machine), machine);
        progress = new GuiPartGaugeRevH(62, 36, TfMachineGuiParts.progressArrowRevBg, TfMachineGuiParts.progressArrowRev);
        smallArrow = new GuiPart(45, 43, TfMachineGuiParts.smallArrowDown);
        tfCharged = new GuiPartTfCharge(112, 52, TileEntityTfReformer.COST_TF_PER_TICK / TileEntityTfReformer.TF_CAPACITY);
    }
}
