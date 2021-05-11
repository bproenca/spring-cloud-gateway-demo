package com.scg.service;

import java.time.LocalTime;

public class DTO {
    
    private Integer port;
    private String hostName;
    private String hostAddress;
    private String message; 
    private LocalTime time;
    
    public DTO() {
        time = LocalTime.now();
    }

    public DTO(Integer port, String hostName, String hostAddress, String message) {
        this.port = port;
        this.hostName = hostName;
        this.hostAddress = hostAddress;
        this.message = message;
        this.time = LocalTime.now();
    }

    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public String getHostName() {
        return hostName;
    }
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public String getHostAddress() {
        return hostAddress;
    }
    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DTO [hostAddress=" + hostAddress + ", hostName=" + hostName + ", message=" + message + ", port=" + port
                + ", time=" + time + "]";
    }
    

    
}
