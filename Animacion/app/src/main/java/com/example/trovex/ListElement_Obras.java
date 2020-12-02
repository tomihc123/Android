package com.example.trovex;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ListElement_Obras implements Parcelable {

    public String calle;
    public String cliente;
    public String status;

    public ListElement_Obras(String calle, String cliente, String status) {
        this.calle = calle;
        this.cliente = cliente;
        this.status = status;
    }

    public ListElement_Obras(Parcel in) {
        calle = in.readString();
        cliente = in.readString();
        status = in.readString();
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ListElement_Obras{" +
                "calle='" + calle + '\'' +
                ", cliente='" + cliente + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(calle);
        out.writeString(cliente);
        out.writeString(status);
    }

    public static final Parcelable.Creator<ListElement_Obras> CREATOR = new Parcelable.Creator<ListElement_Obras>() {
        public ListElement_Obras createFromParcel(Parcel in) {
            return new ListElement_Obras(in);
        }

        public ListElement_Obras[] newArray(int size) {
            return new ListElement_Obras[size];
        }
    };


}
