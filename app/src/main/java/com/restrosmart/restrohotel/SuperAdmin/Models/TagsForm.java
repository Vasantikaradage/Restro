package com.restrosmart.restrohotel.SuperAdmin.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class TagsForm  implements Parcelable {
    private  int tagId;
    private  String tagName;
    public boolean selected = false;

    public TagsForm() {
    }

    protected TagsForm(Parcel in) {
        tagId = in.readInt();
        tagName = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<TagsForm> CREATOR = new Creator<TagsForm>() {
        @Override
        public TagsForm createFromParcel(Parcel in) {
            return new TagsForm(in);
        }

        @Override
        public TagsForm[] newArray(int size) {
            return new TagsForm[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tagId);
        dest.writeString(tagName);
        dest.writeByte((byte) (selected ? 1 : 0));
    }
}
