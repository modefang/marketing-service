package rabbit.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRabbitPojo implements Serializable {

    private String id;

    private String name;

}
