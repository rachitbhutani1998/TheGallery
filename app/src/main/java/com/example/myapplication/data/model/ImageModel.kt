package com.example.myapplication.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ImageModel() : Parcelable {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("secret")
    var secret: String = ""

    @SerializedName("server")
    var server: String = ""

    @SerializedName("farm")
    var farm: Int = -1

    @SerializedName("title")
    var title: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString() ?: ""
        secret = parcel.readString() ?: ""
        server = parcel.readString() ?: ""
        farm = parcel.readInt()
        title = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(secret)
        parcel.writeString(server)
        parcel.writeInt(farm)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageModel> {
        override fun createFromParcel(parcel: Parcel): ImageModel {
            return ImageModel(parcel)
        }

        override fun newArray(size: Int): Array<ImageModel?> {
            return arrayOfNulls(size)
        }
    }

}