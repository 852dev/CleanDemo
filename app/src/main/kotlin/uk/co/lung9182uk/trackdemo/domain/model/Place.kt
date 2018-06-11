package uk.co.lung9182uk.trackdemo.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

data class Place(
        var name: String,
        var location: LatLng,
        val formattedAddress: String,
        var types: List<String>,
        var icon: String) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Place> = object : Parcelable.Creator<Place> {
            override fun createFromParcel(source: Parcel): Place = Place(source)
            override fun newArray(size: Int): Array<Place?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readParcelable(LatLng::class.java.classLoader),
            source.readString(),
            source.createStringArrayList(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeParcelable(location, flags);
        writeString(formattedAddress)
        writeStringList(types)
        writeString(icon)
    }
}
