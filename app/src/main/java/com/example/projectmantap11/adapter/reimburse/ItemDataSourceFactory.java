package com.example.projectmantap11.adapter.reimburse;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.projectmantap11.models.Reimbursement;
import com.example.projectmantap11.models.ReimbursementServerResponse;

public class ItemDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Reimbursement>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        ItemDataSource itemDataSource = new ItemDataSource();

        Log.d("REIMBURSE", "TEST: ");

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //Log.d("itemdata", "create: " + itemLiveDataSource.getValue().toString());

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Reimbursement>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}