package com.dart.wgw.bean

import android.os.Parcel
import android.os.Parcelable

class NoTargetItem() :Parcelable{
    var name:String?="wgw"
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoTargetItem> {
        override fun createFromParcel(parcel: Parcel): NoTargetItem {
            return NoTargetItem(parcel)
        }

        override fun newArray(size: Int): Array<NoTargetItem?> {
            return arrayOfNulls(size)
        }
    }

}