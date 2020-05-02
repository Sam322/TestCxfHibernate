package ts.serviceInterface;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ts.model.CodeNamePair;
import ts.model.CustomerInfo;
import ts.model.Region;
import ts.model.TransNode;
import ts.model.UserInfo;

@Path("/Misc")
public interface IMiscService {
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getNode/{NodeCode}") 
	public Response getNode(@PathParam("NodeCode")String code);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getNodesList/{RegionCode}/{Type}") 
	public List<TransNode> getNodesList(@PathParam("RegionCode")String regionCode, @PathParam("Type")int type);
    
    // ldq @return获取所有服务站点
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getAllNodesList") 
	public List<TransNode> getAllNodesList();
    
    // ldq@return通过地区代码获取服务站点
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getNodesListByRegionCode/{RegionCode}") 
	public List<TransNode> getNodesListByRegionCode(@PathParam("RegionCode")String regionCode);
    
    //** ldq @return通过id删除服务站点
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/deleteTransNode/{id}") 
	public Response deleteTransNode(@PathParam("id")String id);
    
    // ldq @return新建服务站点
    @POST
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/saveTransNode")
	public Response saveTransNode(TransNode obj);
    


    //===============================================================================================
    
    //ldq获取所有顾客信息
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getAllCustomerInfo")
    public List<CustomerInfo> getAllCustomer();
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerListByName/{name}") 
	public List<CustomerInfo> getCustomerListByName(@PathParam("name")String name);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerListByTelCode/{TelCode}") 
	public List<CustomerInfo> getCustomerListByTelCode(@PathParam("TelCode")String TelCode);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerInfo/{id}") 
	public Response getCustomerInfo(@PathParam("id")String id);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deleteCustomerInfo/{id}") 
	public Response deleteCustomerInfo(@PathParam("id")int id);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveCustomerInfo") 
	public Response saveCustomerInfo(CustomerInfo obj);
    
    //==============================================================================================
    
    //tzx
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveUserInfo")
    public Response saveUserInfo(UserInfo obj);
    //tzx
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getAllUserInfo")
    public List<UserInfo> getAllUserInfo();
    
    //ldq 用uid查询用户
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUserInfo/{uid}")
    public UserInfo getUserInfo(@PathParam("uid")int uid);
    
    //===============================================================================================
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getProvinceList") 
	public List<CodeNamePair> getProvinceList();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getCityList/{prv}") 
	public List<CodeNamePair> getCityList(@PathParam("prv")String prv);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getTownList/{city}") 
	public List<CodeNamePair> getTownList(@PathParam("city")String city);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getRegionString/{id}") 
	public String getRegionString(@PathParam("id")String id);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getRegion/{id}") 
	public Region getRegion(@PathParam("id")String id);
    
    //===============================================================================================
	public void CreateWorkSession(int uid);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/doLogin/{telcode}/{pwd}") 
	public UserInfo doLogin(@PathParam("telcode") String telcode, @PathParam("pwd") String pwd);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/doLogOut/{uid}") 
	public void doLogOut(@PathParam("uid") int uid);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/doRegister/{telcode}/{pwd}/{dptid}")
    public boolean doRegister(@PathParam("telcode") String telcode,@PathParam("pwd") String pwd,@PathParam("dptid") String dptid);
	public void RefreshSessionList();

	
}
