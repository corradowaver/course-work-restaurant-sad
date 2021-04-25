package model;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
  private long id;
  private Date date;
  private boolean isProcessed;
  private long clientId;
  private long staffId;
  private Map<Integer, Integer> positionIdNumberMap;

  public Order(Date date, boolean isProcessed, long clientId, long staffId,
               Map<Integer, Integer> positionIdNumberMap) {
    this.date = date;
    this.isProcessed = isProcessed;
    this.clientId = clientId;
    this.staffId = staffId;
    this.positionIdNumberMap = positionIdNumberMap;
  }

  public Order(long id, Date date, boolean isProcessed, long clientId, long staffId) {
    this.id = id;
    this.date = date;
    this.isProcessed = isProcessed;
    this.clientId = clientId;
    this.staffId = staffId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public boolean isProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean processed) {
    isProcessed = processed;
  }

  public long getClientId() {
    return clientId;
  }

  public void setClientId(long clientId) {
    this.clientId = clientId;
  }

  public long getStaffId() {
    return staffId;
  }

  public void setStaffId(long staffId) {
    this.staffId = staffId;
  }

  public Map<Integer, Integer> getPositionIdNumberMap() {
    return positionIdNumberMap;
  }

  public void setPositionIdNumberMap(Map<Integer, Integer> positionIdNumberMap) {
    this.positionIdNumberMap = positionIdNumberMap;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", date=" + date +
        ", isProcessed=" + isProcessed +
        ", clientId=" + clientId +
        ", staffId=" + staffId +
        '}';
  }
}
