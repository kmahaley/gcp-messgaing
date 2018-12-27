package edu.gcp.messaging.data;

import lombok.Data;

@Data
public class PrivateMessage {

    private String id;
    private String name;
    private int value;

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
