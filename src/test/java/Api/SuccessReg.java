package Api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessReg {
  private Integer id;
  private String token;
  
  @JsonCreator
  public SuccessReg(@JsonProperty("id") int id,
                    @JsonProperty("token")String token) {
    this.id = id;
    this.token = token;
  }
  
  public Integer getId() {
    return id;
  }
  
  public String getToken() {
    return token;
  }
}
