/*     */ package chb.mods.mffs.common.container;
/*     */ 
/*     */ import chb.mods.mffs.common.SlotHelper;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerAreaDefenseStation extends Container
/*     */ {
/*     */   private TileEntityAreaDefenseStation defstation;
/*     */   private int capacity;
/*     */   private int SwitchTyp;
/*     */   private int contratyp;
/*     */   private int actionmode;
/*     */   private int scanmode;
/*     */   private EntityPlayer player;
/*     */ 
/*     */   public ContainerAreaDefenseStation(EntityPlayer player, TileEntityAreaDefenseStation tileentity)
/*     */   {
/*  43 */     this.capacity = -1;
/*  44 */     this.SwitchTyp = -1;
/*  45 */     this.contratyp = -1;
/*  46 */     this.actionmode = -1;
/*  47 */     this.scanmode = -1;
/*     */ 
/*  49 */     this.defstation = tileentity;
/*  50 */     this.player = player;
/*     */ 
/*  52 */     addSlotToContainer(new SlotHelper(this.defstation, 0, 13, 27));
/*  53 */     addSlotToContainer(new SlotHelper(this.defstation, 1, 97, 27));
/*     */ 
/*  55 */     addSlotToContainer(new SlotHelper(this.defstation, 2, 14, 51));
/*  56 */     addSlotToContainer(new SlotHelper(this.defstation, 3, 14, 88));
/*     */ 
/*  62 */     for (int var3 = 0; var3 < 2; var3++) {
/*  63 */       for (int var4 = 0; var4 < 4; var4++) {
/*  64 */         addSlotToContainer(new SlotHelper(this.defstation, var4 + var3 * 4 + 5, 176 + var4 * 18, 26 + var3 * 18));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  70 */     for (var3 = 0; var3 < 5; var3++) {
/*  71 */       for (int var4 = 0; var4 < 4; var4++) {
/*  72 */         addSlotToContainer(new SlotHelper(this.defstation, var4 + var3 * 4 + 15, 176 + var4 * 18, 98 + var3 * 18));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  79 */     for (var3 = 0; var3 < 3; var3++) {
/*  80 */       for (int var4 = 0; var4 < 9; var4++) {
/*  81 */         addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 134 + var3 * 18));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  86 */     for (var3 = 0; var3 < 9; var3++)
/*  87 */       addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 192));
/*     */   }
/*     */ 
/*     */   public EntityPlayer getPlayer()
/*     */   {
/*  92 */     return this.player;
/*     */   }
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/*  97 */     super.detectAndSendChanges();
/*     */ 
/*  99 */     for (int i = 0; i < this.crafters.size(); i++) {
/* 100 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */ 
/* 103 */       if (this.contratyp != this.defstation.getcontratyp()) {
/* 104 */         icrafting.sendProgressBarUpdate(this, 1, this.defstation.getcontratyp());
/*     */       }
/*     */ 
/* 107 */       if (this.actionmode != this.defstation.getActionmode()) {
/* 108 */         icrafting.sendProgressBarUpdate(this, 2, this.defstation.getActionmode());
/*     */       }
/*     */ 
/* 111 */       if (this.scanmode != this.defstation.getScanmode()) {
/* 112 */         icrafting.sendProgressBarUpdate(this, 3, this.defstation.getScanmode());
/*     */       }
/*     */ 
/* 116 */       if (this.capacity != this.defstation.getPercentageCapacity()) {
/* 117 */         icrafting.sendProgressBarUpdate(this, 4, this.defstation.getPercentageCapacity());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 122 */     this.scanmode = this.defstation.getScanmode();
/* 123 */     this.actionmode = this.defstation.getActionmode();
/* 124 */     this.contratyp = this.defstation.getcontratyp();
/* 125 */     this.capacity = this.defstation.getPercentageCapacity();
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int i, int j) {
/* 129 */     switch (i)
/*     */     {
/*     */     case 1:
/* 132 */       this.defstation.setcontratyp(j);
/* 133 */       break;
/*     */     case 2:
/* 135 */       this.defstation.setActionmode(j);
/* 136 */       break;
/*     */     case 3:
/* 138 */       this.defstation.setScanmode(j);
/* 139 */       break;
/*     */     case 4:
/* 141 */       this.defstation.setCapacity(j);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer entityplayer)
/*     */   {
/* 147 */     return this.defstation.isUseableByPlayer(entityplayer);
/*     */   }
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer p, int i)
/*     */   {
/* 152 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.container.ContainerAreaDefenseStation
 * JD-Core Version:    0.6.2
 */