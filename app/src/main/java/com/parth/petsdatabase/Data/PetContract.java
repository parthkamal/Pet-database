package com.parth.petsdatabase.Data;


import android.provider.BaseColumns;

//final keyword is used to prevent the class from being extended
public final class PetContract {
    //to prevent the class from accidental instantiation we declare an empty constructor
    private PetContract(){;}


    //inner class for declaring the database constants and a final class to prevent it from extending
    public static final class PetEntry implements BaseColumns{
        public final static String TABLE_NAME = "pets";

        public final static String _ID = BaseColumns._ID;// we have used the parent variable from base column class

        public final static String COLUMN_PET_NAME ="name";

        public final static String COLUMN_PET_BREED = "breed";

        public final static String COLUMN_PET_GENDER = "gender";

        public final static String COLUMN_PET_WEIGHT = "weight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;


    }
}
