package ru.praktikum.scooter.api.order.list;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

public class OrderListResponse {
    private List<Orders> orders;
    private PageInfo pageInfo;
    private List<AvailableStations> availableStations;

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AvailableStations> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<AvailableStations> availableStations) {
        this.availableStations = availableStations;
    }

    public String getOrderListResponseToJson(Object orderListResponse){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(orderListResponse);
    }
}