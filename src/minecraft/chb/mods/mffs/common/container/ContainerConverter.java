/*     */ package chb.mods.mffs.common.container;
/*     */ 
/*     */ import chb.mods.mffs.common.SlotHelper;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityConverter;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerConverter extends Container
/*     */ {
/*     */   private int linkPower;
/*     */   private int capacity;
/*     */   private EntityPlayer player;
/*     */   private TileEntityConverter Convertor;
/*     */   private int IC_Outputpacketsize;
/*     */   private int IC_Outputpacketamount;
/*     */   private int IC_Output;
/*     */   private int UE_Outputvoltage;
/*     */   private int UE_Outputamp;
/*     */   private int UE_Output;
/*     */ 
/*     */   public ContainerConverter(EntityPlayer player, TileEntityConverter tileentity)
/*     */   {
/*  47 */     this.Convertor = tileentity;
/*  48 */     this.player = player;
/*  49 */     this.linkPower = -1;
/*  50 */     this.capacity = -1;
/*  51 */     this.IC_Outputpacketamount = -1;
/*  52 */     this.IC_Outputpacketsize = -1;
/*  53 */     this.IC_Output = -1;
/*  54 */     this.UE_Outputvoltage = -1;
/*  55 */     this.UE_Outputamp = -1;
/*  56 */     this.UE_Output = -1;
/*     */ 
/*  58 */     addSlotToContainer(new SlotHelper(this.Convertor, 0, 44, 28));
/*     */ 
/*  62 */     for (int var3 = 0; var3 < 3; var3++) {
/*  63 */       for (int var4 = 0; var4 < 9; var4++) {
/*  64 */         addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 50 + var4 * 18, 133 + var3 * 18));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  69 */     for (var3 = 0; var3 < 9; var3++)
/*  70 */       addSlotToContainer(new Slot(player.inventory, var3, 50 + var3 * 18, 191));
/*     */   }
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/*  76 */     super.detectAndSendChanges();
/*     */ 
/*  78 */     for (int i = 0; i < this.crafters.size(); i++) {
/*  79 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */ 
/*  81 */       if (this.linkPower != this.Convertor.getLinkPower()) {
/*  82 */         icrafting.sendProgressBarUpdate(this, 0, this.Convertor.getLinkPower() & 0xFFFF);
/*  83 */         icrafting.sendProgressBarUpdate(this, 1, this.Convertor.getLinkPower() >>> 16);
/*     */       }
/*     */ 
/*  86 */       if (this.capacity != this.Convertor.getPercentageCapacity()) {
/*  87 */         icrafting.sendProgressBarUpdate(this, 3, this.Convertor.getPercentageCapacity());
/*     */       }
/*  89 */       if (this.IC_Outputpacketamount != this.Convertor.getIC_Outputpacketamount()) {
/*  90 */         icrafting.sendProgressBarUpdate(this, 5, this.Convertor.getIC_Outputpacketamount());
/*     */       }
/*  92 */       if (this.IC_Outputpacketsize != this.Convertor.getIC_Outputpacketsize()) {
/*  93 */         icrafting.sendProgressBarUpdate(this, 4, this.Convertor.getIC_Outputpacketsize());
/*     */       }
/*  95 */       if (this.IC_Output != this.Convertor.getIC_Output()) {
/*  96 */         icrafting.sendProgressBarUpdate(this, 6, this.Convertor.getIC_Output());
/*     */       }
/*  98 */       if (this.UE_Output != this.Convertor.getUE_Output()) {
/*  99 */         icrafting.sendProgressBarUpdate(this, 7, this.Convertor.getUE_Output());
/*     */       }
/* 101 */       if (this.UE_Outputvoltage != this.Convertor.getUE_Outputvoltage()) {
/* 102 */         icrafting.sendProgressBarUpdate(this, 8, this.Convertor.getUE_Outputvoltage());
/*     */       }
/* 104 */       if (this.UE_Outputamp != this.Convertor.getUE_Outputamp()) {
/* 105 */         icrafting.sendProgressBarUpdate(this, 9, this.Convertor.getUE_Outputamp());
/*     */       }
/*     */     }
/*     */ 
/* 109 */     this.linkPower = this.Convertor.getLinkPower();
/* 110 */     this.capacity = this.Convertor.getPercentageCapacity();
/* 111 */     this.IC_Outputpacketamount = this.Convertor.getIC_Outputpacketamount();
/* 112 */     this.IC_Outputpacketsize = this.Convertor.getIC_Outputpacketsize();
/* 113 */     this.IC_Output = this.Convertor.getIC_Output();
/* 114 */     this.UE_Outputvoltage = this.Convertor.getUE_Outputvoltage();
/* 115 */     this.UE_Outputamp = this.Convertor.getUE_Outputamp();
/* 116 */     this.UE_Output = this.Convertor.getUE_Output();
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int i, int j) {
/* 120 */     switch (i) {
/*     */     case 0:
/* 122 */       this.Convertor.setLinkPower(this.Convertor.getLinkPower() & 0xFFFF0000 | j);
/* 123 */       break;
/*     */     case 1:
/* 125 */       this.Convertor.setLinkPower(this.Convertor.getLinkPower() & 0xFFFF | j << 16);
/* 126 */       break;
/*     */     case 3:
/* 128 */       this.Convertor.setCapacity(j);
/* 129 */       break;
/*     */     case 4:
/* 132 */       this.Convertor.setIC_Outputpacketsize(j);
/* 133 */       break;
/*     */     case 5:
/* 136 */       this.Convertor.setIC_Outputpacketamount(j);
/* 137 */       break;
/*     */     case 6:
/* 140 */       this.Convertor.setIC_Output(j);
/* 141 */       break;
/*     */     case 7:
/* 144 */       this.Convertor.setUE_Output(j);
/* 145 */       break;
/*     */     case 8:
/* 148 */       this.Convertor.setUE_Outputvoltage(j);
/* 149 */       break;
/*     */     case 9:
/* 152 */       this.Convertor.setUE_Outputamp(j);
/*     */     case 2:
/*     */     }
/*     */   }
/*     */ 
/*     */   public EntityPlayer getPlayer()
/*     */   {
/* 159 */     return this.player;
/*     */   }
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer entityplayer) {
/* 163 */     return this.Convertor.isUseableByPlayer(entityplayer);
/*     */   }
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer p, int i)
/*     */   {
/* 168 */     ItemStack itemstack = null;
/* 169 */     Slot slot = (Slot)this.inventorySlots.get(i);
/* 170 */     if ((slot != null) && (slot.getHasStack())) {
/* 171 */       ItemStack itemstack1 = slot.getStack();
/* 172 */       itemstack = itemstack1.copy();
/* 173 */       if (itemstack1.stackSize == 0)
/* 174 */         slot.putStack(null);
/*     */       else {
/* 176 */         slot.onSlotChanged();
/*     */       }
/* 178 */       if (itemstack1.stackSize != itemstack.stackSize)
/* 179 */         slot.onSlotChanged();
/*     */       else {
/* 181 */         return null;
/*     */       }
/*     */     }
/* 184 */     return itemstack;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.container.ContainerConverter
 * JD-Core Version:    0.6.2
 */