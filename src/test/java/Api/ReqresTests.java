package Api;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTests {
  
  private final static String URL = "https://reqres.in/";
  
  @Test
  public void checkAvatarAndIdTest(){
    Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpecOK200());
    List<UserData> users = given()
      .when()
      .get("api/users?page=2")
      .then().log().all()
      .extract().body().jsonPath().getList("data", UserData.class);
    users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
    
    Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
    
    List<String> avatars = users.stream().map(UserData::getAvatar).toList();
  
    List<String> ids = users.stream().map(x->x.getId().toString()).toList();
    
    for(int i = 0; i<avatars.size();i++){
      Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
    }
  }
}
