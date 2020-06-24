package com.example.projectmantap11.models;

import java.util.List;

class Sort {
    public boolean sorted;
    public boolean unsorted;
    public boolean empty;
}

class Pageable {


    public Sort sort;
    public int page_number;
    public int page_size;
    public int offset;
    public boolean unpaged;
    public boolean paged;
}

public class ReimbursementServerResponse {


    public List<Reimbursement> content;
    public Pageable pageable;

    public boolean last;
    public int total_pages;
    public int total_elements;

    public Sort sort;
    public int number_of_elements;
    public boolean first;
    public int size;
    public int number;
    public boolean empty;
}
