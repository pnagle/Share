package com.dao;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class SchemaGenerator {

    public static void main(String[] args) throws IOException, Exception {
        Schema schema = new Schema(1, "com.sharesmile.share");
        addWorkoutData(schema);
        addUserData(schema);
        addCauseData(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java-gen");
    }

    private static void addWorkoutData(Schema schema) {
        Entity workout = schema.addEntity("Workout");
        workout.addIdProperty();
        workout.addFloatProperty("distance").notNull();
        workout.addStringProperty("elapsedTime").notNull();
        workout.addIntProperty("steps");
        workout.addFloatProperty("recordedTime").notNull();
        workout.addFloatProperty("avgSpeed").notNull();
        workout.addStringProperty("causeBrief");
        workout.addDateProperty("date");
        workout.addFloatProperty("runAmount");
        workout.addBooleanProperty("is_sync");
    }

    private static void addUserData(Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("name");
        user.addStringProperty("emailId").notNull();
        user.addStringProperty("birthday");
        user.addStringProperty("mobileNO");
        user.addStringProperty("gender");
        user.addStringProperty("profileImageUrl");
    }

    private static void addCauseData(Schema schema) {
        Entity cause = schema.addEntity("Cause");

       /* pk: 2,
                cause_title: "Run a mile. Feed a child. (Dummy)",
                cause_description: "Shwas works with the extreme poor section of society and aims to provide them with food which is necessary for them to carry their day. More than 80 million kids are hungry in India and Shwas is working tirelessly to reduce this number everyday.",
                conversion_rate: "0.02",
                min_distance: 100,
                cause_category: "Hunger",*/

        cause.addIdProperty();
        cause.addStringProperty("causeTitle");
        cause.addStringProperty("causeDescription");
        cause.addFloatProperty("conversionRate");
        cause.addIntProperty("minDistance");
        cause.addStringProperty("causeCategory");

        /*

        cause_brief: "For every 1.6 kilometers that you run, a hungry child is fed a healthy meal.",
                cause_image: "http://139.59.243.247/media/photos/2/09-food-security-IndiaInk-superJumbo.jpg",
                cause_thank_you_image: "http://139.59.243.247/media/photos/2/Cancer_HomePage_.jpg",
                cause_share_message_template: "Great Work",
                is_active: false*/

        cause.addStringProperty("causeBrief");
        cause.addStringProperty("causeImage");
        cause.addStringProperty("causeThankyouImage");
        cause.addStringProperty("share_template");
        cause.addBooleanProperty("isActive");

        //sponsors

        /*sponsor_id: 2,
                sponsor_type: "Company",
                sponsor_company: "DBS Bank",
                sponsor_ngo: null,
                sponsor_logo: "http://139.59.243.247/media/photos/2/DBS_Logo.png"*/

        cause.addIntProperty("sponsorId");
        cause.addStringProperty("sponsorCompany");
        cause.addStringProperty("sponsorNgo");
        cause.addStringProperty("sponsorLogo");

        //Partners

      /*  partner_id: 2,
                partner_type: "NGO",
                partner_company: null,
                partner_ngo: "Avanti Fellows",
                partnered_on: "2016-05-25T12:00:00Z"*/

        cause.addIntProperty("partnerId");
        cause.addStringProperty("partnerCompany");
        cause.addStringProperty("partnerNgo");
        cause.addStringProperty("partnerType");

    }

}
