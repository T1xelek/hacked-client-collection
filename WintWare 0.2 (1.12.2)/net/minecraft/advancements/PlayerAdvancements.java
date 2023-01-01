package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerAdvancements {
   private static final Logger field_192753_a = LogManager.getLogger();
   private static final Gson field_192754_b = (new GsonBuilder()).registerTypeAdapter(AdvancementProgress.class, new AdvancementProgress.Serializer()).registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).setPrettyPrinting().create();
   private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> field_192755_c = new TypeToken<Map<ResourceLocation, AdvancementProgress>>() {
   };
   private final MinecraftServer field_192756_d;
   private final File field_192757_e;
   private final Map<Advancement, AdvancementProgress> field_192758_f = Maps.newLinkedHashMap();
   private final Set<Advancement> field_192759_g = Sets.newLinkedHashSet();
   private final Set<Advancement> field_192760_h = Sets.newLinkedHashSet();
   private final Set<Advancement> field_192761_i = Sets.newLinkedHashSet();
   private EntityPlayerMP field_192762_j;
   @Nullable
   private Advancement field_194221_k;
   private boolean field_192763_k = true;

   public PlayerAdvancements(MinecraftServer p_i47422_1_, File p_i47422_2_, EntityPlayerMP p_i47422_3_) {
      this.field_192756_d = p_i47422_1_;
      this.field_192757_e = p_i47422_2_;
      this.field_192762_j = p_i47422_3_;
      this.func_192740_f();
   }

   public void func_192739_a(EntityPlayerMP p_192739_1_) {
      this.field_192762_j = p_192739_1_;
   }

   public void func_192745_a() {
      Iterator var1 = CriteriaTriggers.func_192120_a().iterator();

      while(var1.hasNext()) {
         ICriterionTrigger<?> icriteriontrigger = (ICriterionTrigger)var1.next();
         icriteriontrigger.func_192167_a(this);
      }

   }

   public void func_193766_b() {
      this.func_192745_a();
      this.field_192758_f.clear();
      this.field_192759_g.clear();
      this.field_192760_h.clear();
      this.field_192761_i.clear();
      this.field_192763_k = true;
      this.field_194221_k = null;
      this.func_192740_f();
   }

   private void func_192751_c() {
      Iterator var1 = this.field_192756_d.func_191949_aK().func_192780_b().iterator();

      while(var1.hasNext()) {
         Advancement advancement = (Advancement)var1.next();
         this.func_193764_b(advancement);
      }

   }

   private void func_192752_d() {
      List<Advancement> list = Lists.newArrayList();
      Iterator var2 = this.field_192758_f.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<Advancement, AdvancementProgress> entry = (Entry)var2.next();
         if (((AdvancementProgress)entry.getValue()).func_192105_a()) {
            list.add(entry.getKey());
            this.field_192761_i.add(entry.getKey());
         }
      }

      var2 = list.iterator();

      while(var2.hasNext()) {
         Advancement advancement = (Advancement)var2.next();
         this.func_192742_b(advancement);
      }

   }

   private void func_192748_e() {
      Iterator var1 = this.field_192756_d.func_191949_aK().func_192780_b().iterator();

      while(var1.hasNext()) {
         Advancement advancement = (Advancement)var1.next();
         if (advancement.func_192073_f().isEmpty()) {
            this.func_192750_a(advancement, "");
            advancement.func_192072_d().func_192113_a(this.field_192762_j);
         }
      }

   }

   private void func_192740_f() {
      if (this.field_192757_e.isFile()) {
         try {
            String s = Files.toString(this.field_192757_e, StandardCharsets.UTF_8);
            Map<ResourceLocation, AdvancementProgress> map = (Map)JsonUtils.func_193840_a(field_192754_b, s, field_192755_c.getType());
            if (map == null) {
               throw new JsonParseException("Found null for advancements");
            }

            Stream<Entry<ResourceLocation, AdvancementProgress>> stream = map.entrySet().stream().sorted(Comparator.comparing(Entry::getValue));
            Iterator var4 = ((List)stream.collect(Collectors.toList())).iterator();

            while(var4.hasNext()) {
               Entry<ResourceLocation, AdvancementProgress> entry = (Entry)var4.next();
               Advancement advancement = this.field_192756_d.func_191949_aK().func_192778_a((ResourceLocation)entry.getKey());
               if (advancement == null) {
                  field_192753_a.warn("Ignored advancement '" + entry.getKey() + "' in progress file " + this.field_192757_e + " - it doesn't exist anymore?");
               } else {
                  this.func_192743_a(advancement, (AdvancementProgress)entry.getValue());
               }
            }
         } catch (JsonParseException var7) {
            field_192753_a.error("Couldn't parse player advancements in " + this.field_192757_e, var7);
         } catch (IOException var8) {
            field_192753_a.error("Couldn't access player advancements in " + this.field_192757_e, var8);
         }
      }

      this.func_192748_e();
      this.func_192752_d();
      this.func_192751_c();
   }

   public void func_192749_b() {
      Map<ResourceLocation, AdvancementProgress> map = Maps.newHashMap();
      Iterator var2 = this.field_192758_f.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<Advancement, AdvancementProgress> entry = (Entry)var2.next();
         AdvancementProgress advancementprogress = (AdvancementProgress)entry.getValue();
         if (advancementprogress.func_192108_b()) {
            map.put(((Advancement)entry.getKey()).func_192067_g(), advancementprogress);
         }
      }

      if (this.field_192757_e.getParentFile() != null) {
         this.field_192757_e.getParentFile().mkdirs();
      }

      try {
         Files.write(field_192754_b.toJson(map), this.field_192757_e, StandardCharsets.UTF_8);
      } catch (IOException var5) {
         field_192753_a.error("Couldn't save player advancements to " + this.field_192757_e, var5);
      }

   }

   public boolean func_192750_a(Advancement p_192750_1_, String p_192750_2_) {
      boolean flag = false;
      AdvancementProgress advancementprogress = this.func_192747_a(p_192750_1_);
      boolean flag1 = advancementprogress.func_192105_a();
      if (advancementprogress.func_192109_a(p_192750_2_)) {
         this.func_193765_c(p_192750_1_);
         this.field_192761_i.add(p_192750_1_);
         flag = true;
         if (!flag1 && advancementprogress.func_192105_a()) {
            p_192750_1_.func_192072_d().func_192113_a(this.field_192762_j);
            if (p_192750_1_.func_192068_c() != null && p_192750_1_.func_192068_c().func_193220_i() && this.field_192762_j.world.getGameRules().getBoolean("announceAdvancements")) {
               this.field_192756_d.getPlayerList().sendChatMsg(new TextComponentTranslation("chat.type.advancement." + p_192750_1_.func_192068_c().func_192291_d().func_192307_a(), new Object[]{this.field_192762_j.getDisplayName(), p_192750_1_.func_193123_j()}));
            }
         }
      }

      if (advancementprogress.func_192105_a()) {
         this.func_192742_b(p_192750_1_);
      }

      return flag;
   }

   public boolean func_192744_b(Advancement p_192744_1_, String p_192744_2_) {
      boolean flag = false;
      AdvancementProgress advancementprogress = this.func_192747_a(p_192744_1_);
      if (advancementprogress.func_192101_b(p_192744_2_)) {
         this.func_193764_b(p_192744_1_);
         this.field_192761_i.add(p_192744_1_);
         flag = true;
      }

      if (!advancementprogress.func_192108_b()) {
         this.func_192742_b(p_192744_1_);
      }

      return flag;
   }

   private void func_193764_b(Advancement p_193764_1_) {
      AdvancementProgress advancementprogress = this.func_192747_a(p_193764_1_);
      if (!advancementprogress.func_192105_a()) {
         Iterator var3 = p_193764_1_.func_192073_f().entrySet().iterator();

         while(var3.hasNext()) {
            Entry<String, Criterion> entry = (Entry)var3.next();
            CriterionProgress criterionprogress = advancementprogress.func_192106_c((String)entry.getKey());
            if (criterionprogress != null && !criterionprogress.func_192151_a()) {
               ICriterionInstance icriterioninstance = ((Criterion)entry.getValue()).func_192143_a();
               if (icriterioninstance != null) {
                  ICriterionTrigger<ICriterionInstance> icriteriontrigger = CriteriaTriggers.func_192119_a(icriterioninstance.func_192244_a());
                  if (icriteriontrigger != null) {
                     icriteriontrigger.func_192165_a(this, new ICriterionTrigger.Listener(icriterioninstance, p_193764_1_, (String)entry.getKey()));
                  }
               }
            }
         }
      }

   }

   private void func_193765_c(Advancement p_193765_1_) {
      AdvancementProgress advancementprogress = this.func_192747_a(p_193765_1_);
      Iterator var3 = p_193765_1_.func_192073_f().entrySet().iterator();

      while(true) {
         Entry entry;
         CriterionProgress criterionprogress;
         do {
            do {
               if (!var3.hasNext()) {
                  return;
               }

               entry = (Entry)var3.next();
               criterionprogress = advancementprogress.func_192106_c((String)entry.getKey());
            } while(criterionprogress == null);
         } while(!criterionprogress.func_192151_a() && !advancementprogress.func_192105_a());

         ICriterionInstance icriterioninstance = ((Criterion)entry.getValue()).func_192143_a();
         if (icriterioninstance != null) {
            ICriterionTrigger<ICriterionInstance> icriteriontrigger = CriteriaTriggers.func_192119_a(icriterioninstance.func_192244_a());
            if (icriteriontrigger != null) {
               icriteriontrigger.func_192164_b(this, new ICriterionTrigger.Listener(icriterioninstance, p_193765_1_, (String)entry.getKey()));
            }
         }
      }
   }

   public void func_192741_b(EntityPlayerMP p_192741_1_) {
      if (!this.field_192760_h.isEmpty() || !this.field_192761_i.isEmpty()) {
         Map<ResourceLocation, AdvancementProgress> map = Maps.newHashMap();
         Set<Advancement> set = Sets.newLinkedHashSet();
         Set<ResourceLocation> set1 = Sets.newLinkedHashSet();
         Iterator var5 = this.field_192761_i.iterator();

         Advancement advancement1;
         while(var5.hasNext()) {
            advancement1 = (Advancement)var5.next();
            if (this.field_192759_g.contains(advancement1)) {
               map.put(advancement1.func_192067_g(), this.field_192758_f.get(advancement1));
            }
         }

         var5 = this.field_192760_h.iterator();

         while(var5.hasNext()) {
            advancement1 = (Advancement)var5.next();
            if (this.field_192759_g.contains(advancement1)) {
               set.add(advancement1);
            } else {
               set1.add(advancement1.func_192067_g());
            }
         }

         if (!map.isEmpty() || !set.isEmpty() || !set1.isEmpty()) {
            p_192741_1_.connection.sendPacket(new SPacketAdvancementInfo(this.field_192763_k, set, set1, map));
            this.field_192760_h.clear();
            this.field_192761_i.clear();
         }
      }

      this.field_192763_k = false;
   }

   public void func_194220_a(@Nullable Advancement p_194220_1_) {
      Advancement advancement = this.field_194221_k;
      if (p_194220_1_ != null && p_194220_1_.func_192070_b() == null && p_194220_1_.func_192068_c() != null) {
         this.field_194221_k = p_194220_1_;
      } else {
         this.field_194221_k = null;
      }

      if (advancement != this.field_194221_k) {
         this.field_192762_j.connection.sendPacket(new SPacketSelectAdvancementsTab(this.field_194221_k == null ? null : this.field_194221_k.func_192067_g()));
      }

   }

   public AdvancementProgress func_192747_a(Advancement p_192747_1_) {
      AdvancementProgress advancementprogress = (AdvancementProgress)this.field_192758_f.get(p_192747_1_);
      if (advancementprogress == null) {
         advancementprogress = new AdvancementProgress();
         this.func_192743_a(p_192747_1_, advancementprogress);
      }

      return advancementprogress;
   }

   private void func_192743_a(Advancement p_192743_1_, AdvancementProgress p_192743_2_) {
      p_192743_2_.func_192099_a(p_192743_1_.func_192073_f(), p_192743_1_.func_192074_h());
      this.field_192758_f.put(p_192743_1_, p_192743_2_);
   }

   private void func_192742_b(Advancement p_192742_1_) {
      boolean flag = this.func_192738_c(p_192742_1_);
      boolean flag1 = this.field_192759_g.contains(p_192742_1_);
      if (flag && !flag1) {
         this.field_192759_g.add(p_192742_1_);
         this.field_192760_h.add(p_192742_1_);
         if (this.field_192758_f.containsKey(p_192742_1_)) {
            this.field_192761_i.add(p_192742_1_);
         }
      } else if (!flag && flag1) {
         this.field_192759_g.remove(p_192742_1_);
         this.field_192760_h.add(p_192742_1_);
      }

      if (flag != flag1 && p_192742_1_.func_192070_b() != null) {
         this.func_192742_b(p_192742_1_.func_192070_b());
      }

      Iterator var4 = p_192742_1_.func_192069_e().iterator();

      while(var4.hasNext()) {
         Advancement advancement = (Advancement)var4.next();
         this.func_192742_b(advancement);
      }

   }

   private boolean func_192738_c(Advancement p_192738_1_) {
      for(int i = 0; p_192738_1_ != null && i <= 2; ++i) {
         if (i == 0 && this.func_192746_d(p_192738_1_)) {
            return true;
         }

         if (p_192738_1_.func_192068_c() == null) {
            return false;
         }

         AdvancementProgress advancementprogress = this.func_192747_a(p_192738_1_);
         if (advancementprogress.func_192105_a()) {
            return true;
         }

         if (p_192738_1_.func_192068_c().func_193224_j()) {
            return false;
         }

         p_192738_1_ = p_192738_1_.func_192070_b();
      }

      return false;
   }

   private boolean func_192746_d(Advancement p_192746_1_) {
      AdvancementProgress advancementprogress = this.func_192747_a(p_192746_1_);
      if (advancementprogress.func_192105_a()) {
         return true;
      } else {
         Iterator var3 = p_192746_1_.func_192069_e().iterator();

         Advancement advancement;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            advancement = (Advancement)var3.next();
         } while(!this.func_192746_d(advancement));

         return true;
      }
   }
}