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
  
  @Test
  public void successRegTest(){
    Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpecOK200());
    Integer id = 4;
    String token = "QpwL5tke4Pnpja7X4";
    Register user = new Register("eve.holt@reqres.in","pistol");
    SuccessReg successReg = given()
      .body(user)
      .when()
      .post("api/register")
      .then().log().all()
      .extract().as(SuccessReg.class);
    Assert.assertNotNull(successReg.getId());
    Assert.assertNotNull(successReg.getToken());
    
    Assert.assertEquals("Check id",id, successReg.getId());
    Assert.assertEquals("Check token", token,successReg.getToken());
  }
  
  @Test
  public void unSuccessRegTest(){
    Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpecError400());
    Register user = new Register("sydney@fife","");
    UnSuccessReg unSuccessReg = given()
      .body(user)
      .when()
      .post("api/register")
      .then().log().all()
      .extract().as(UnSuccessReg.class);
    Assert.assertEquals("Missing password", unSuccessReg.getError());
  
  }
}
