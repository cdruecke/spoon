package pl.surecase.eu;

import com.sun.org.apache.xpath.internal.compiler.Keywords;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {

        Schema schema = new Schema(1, "greendao");

        Entity eFood_grp = schema.addEntity("Food_group");
        Property FoodGroup = eFood_grp.addLongProperty("Fdgrp_no").primaryKey().getProperty();
        eFood_grp.addStringProperty("name");

        Entity eFood_Desc = schema.addEntity("Food_desc");
        Property NDBNo = eFood_Desc.addLongProperty("NDB_no").primaryKey().getProperty();
        eFood_Desc.addStringProperty("name");
        Property FDFG = eFood_Desc.addLongProperty("Fdgrp_no").getProperty();
        eFood_Desc.addToOne(eFood_grp, FDFG);

        Entity eKeywords = schema.addEntity("Keywords");
        Property Lang_no = eKeywords.addLongProperty("Lang_no").primaryKey().getProperty();
        eKeywords.addStringProperty("name");

        Entity eFood_Keywords = schema.addEntity("Food_keywords");
        Property FKeywordsNDB = eFood_Keywords.addLongProperty("NDB_no").getProperty();
        Property FKeywordsLang = eFood_Keywords.addLongProperty("Lang_no").getProperty();
        eFood_Keywords.addToOne(eFood_Desc, FKeywordsNDB);
        eFood_Keywords.addToOne(eKeywords, FKeywordsLang);

        Entity eNutr_Def = schema.addEntity("Nutr_def");
        Property Nutr_no = eNutr_Def.addLongProperty("Nutr_no").primaryKey().getProperty();
        eNutr_Def.addStringProperty("units");
        eNutr_Def.addStringProperty("name");

        Entity eNut_Data = schema.addEntity("Nutr_data");
        eNut_Data.addFloatProperty("nutrient_amt");
        Property NutDataNDB = eNut_Data.addLongProperty("NDB_no").getProperty();
        eNut_Data.addToOne(eFood_Desc, NutDataNDB);
        Property NutDataNutr = eNut_Data.addLongProperty("Nutr_no").getProperty();
        eNut_Data.addToOne(eNutr_Def, NutDataNutr);

        Entity eWeight = schema.addEntity("Weight");
        eWeight.addLongProperty("w_id").primaryKeyAsc().autoincrement().getProperty();
        eWeight.addFloatProperty("amount");
        eWeight.addStringProperty("name");
        eWeight.addFloatProperty("gram_weight");
        Property WeightNDB = eWeight.addLongProperty("NDB_no").getProperty();
        eWeight.addToOne(eFood_Desc, WeightNDB);

        Entity eUserEntry = schema.addEntity("User_entry");
        eUserEntry.addLongProperty("ue_id").primaryKeyAsc().autoincrement();
        eUserEntry.addDateProperty("date");
        eUserEntry.addFloatProperty("serv_amt");
        Property eu1 = eUserEntry.addLongProperty("w_id").getProperty();
        eUserEntry.addToOne(eWeight, eu1);
        Property eu2 = eUserEntry.addLongProperty("NDB_no").getProperty();
        eUserEntry.addToOne(eFood_Desc, eu2);

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
