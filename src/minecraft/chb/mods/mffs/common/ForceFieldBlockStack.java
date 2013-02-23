/*     */ package chb.mods.mffs.common;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Queue;
/*     */ 
/*     */ public class ForceFieldBlockStack
/*     */ {
/*     */   private PointXYZ png;
/*     */   private boolean sync;
/*  32 */   public Queue blocks = new LinkedList();
/*     */ 
/*     */   public ForceFieldBlockStack(PointXYZ png)
/*     */   {
/*  36 */     this.png = png;
/*  37 */     this.sync = false;
/*     */   }
/*     */ 
/*     */   public int getsize()
/*     */   {
/*  42 */     return this.blocks.size();
/*     */   }
/*     */ 
/*     */   public void removeBlock()
/*     */   {
/*  47 */     this.blocks.poll();
/*     */   }
/*     */ 
/*     */   public synchronized void removebyProjector(int projectorid)
/*     */   {
/*  52 */     ArrayList tempblock = new ArrayList();
/*     */ 
/*  54 */     for (ForceFieldBlock ffblock : this.blocks)
/*     */     {
/*  56 */       if (ffblock.Projektor_ID == projectorid) {
/*  57 */         tempblock.add(ffblock);
/*     */       }
/*     */     }
/*  60 */     if (!tempblock.isEmpty())
/*  61 */       this.blocks.removeAll(tempblock);
/*     */   }
/*     */ 
/*     */   public int getGenratorID() {
/*  65 */     ForceFieldBlock ffblock = (ForceFieldBlock)this.blocks.peek();
/*  66 */     if (ffblock != null) {
/*  67 */       return ffblock.Generator_Id;
/*     */     }
/*  69 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getProjectorID() {
/*  73 */     ForceFieldBlock ffblock = (ForceFieldBlock)this.blocks.peek();
/*  74 */     if (ffblock != null) {
/*  75 */       return ffblock.Projektor_ID;
/*     */     }
/*  77 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getTyp() {
/*  81 */     ForceFieldBlock ffblock = (ForceFieldBlock)this.blocks.peek();
/*  82 */     if (ffblock != null) {
/*  83 */       return ffblock.typ;
/*     */     }
/*  85 */     return -1;
/*     */   }
/*     */ 
/*     */   public void setSync(boolean sync) {
/*  89 */     this.sync = sync;
/*     */   }
/*     */ 
/*     */   public boolean isSync() {
/*  93 */     return this.sync;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  97 */     return this.blocks.isEmpty();
/*     */   }
/*     */ 
/*     */   public ForceFieldBlock get()
/*     */   {
/* 102 */     return (ForceFieldBlock)this.blocks.peek();
/*     */   }
/*     */ 
/*     */   public void add(int Generator_Id, int Projektor_ID, int typ)
/*     */   {
/* 107 */     this.blocks.offer(new ForceFieldBlock(Generator_Id, Projektor_ID, typ));
/*     */   }
/*     */ 
/*     */   public PointXYZ getPoint() {
/* 111 */     return this.png;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.ForceFieldBlockStack
 * JD-Core Version:    0.6.2
 */