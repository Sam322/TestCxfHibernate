package ts.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.RegionDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.UserInfoDao;
import ts.daoImpl.UsersPackageDao;
import ts.model.CodeNamePair;
import ts.model.CustomerInfo;
import ts.model.ListTransPackge;
import ts.model.Region;
import ts.model.TransNode;
import ts.model.TransPackage;
import ts.model.UserInfo;
import ts.model.UsersPackage;
import ts.serviceInterface.IMiscService;

public class MiscService implements IMiscService {
	// TransNodeCatalog nodes; //自己做的缓存和重定向先不要了,用Hibernate缓存对付一下，以后加上去
	// RegionCatalog regions;
	private TransNodeDao transNodeDao;
	private RegionDao regionDao;
	private CustomerInfoDao customerInfoDao;
	private UserInfoDao userInfoDao;
	private UsersPackageDao usersPackageDao;

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao dao) {
		this.transNodeDao = dao;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}

	public CustomerInfoDao getCustomerInfoDao() {
		return customerInfoDao;
	}

	public void setCustomerInfoDao(CustomerInfoDao dao) {
		this.customerInfoDao = dao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao dao) {
		this.userInfoDao = dao;
	}

	public void setUsersPackageDao(UsersPackageDao usersPackageDao) {
		this.usersPackageDao = usersPackageDao;
	}
	public UsersPackageDao getUsersPackageDao() {
		return usersPackageDao;
	}
	public MiscService() {
//		nodes = new TransNodeCatalog();
//		nodes.Load();
//		regions = new RegionCatalog();
//		regions.Load();
	}

	/*
	 * @Override public TransNode getNode(String code) { // TODO Auto-generated
	 * method stub return null; }
	 */
	/**
	 * ldq 通过id查询服务网点
	 */
	/*@Override
	public Response getNode(String code) {
		TransNode tn = transNodeDao.get(code);
		return Response.ok(tn).header("EntityClass", "TransNode").build();
	}*/

	/**
	 * ldq 获取所有服务网点
	 */
	@Override
	public List<TransNode> getAllNodesList() {
		return transNodeDao.getAll();
	}

	/**
	 * ldq 通过地区代码获取服务网点
	 */
	@Override
	public List<TransNode> getNodesListByRegionCode(String regionCode) {
		// TODO Auto-generated method stub
		return transNodeDao.findByRegionCode(regionCode);
	}

	/**
	 * ldq 新建服务网点
	 */
	@Override
	public Response saveTransNode(TransNode obj) {
		// TODO Auto-generated method stub
		System.out.println("调用了saveTransNode方法");
		try {
			transNodeDao.save(obj);
			return Response.ok("Saved").header("EntityClass", "R_TransNode").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * ldq 删除服务网点
	 */
	@Override
	public Response deleteTransNode(String id) {
		transNodeDao.removeById(id);
		return Response.ok("Deleted").header("EntityClass", "D_TransNode").build();
	}

	/*@Override
	public List<TransNode> getNodesList(String regionCode, int type) {
		// TODO Auto-generated method stub
		return null;
	}*/
	// ===============================================================================================

	/**
	 * ldq 获取所有顾客信息
	 */
	@Override
	public List<CustomerInfo> getAllCustomer() {
		return customerInfoDao.getAll();
	}

	@Override
	public List<CustomerInfo> getCustomerListByName(String name) {
//		List<CustomerInfo> listci = customerInfoDao.findByName(name);
//		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
//		for(CustomerInfo ci : listci){
//			CodeNamePair cn = new CodeNamePair(String.valueOf(ci.getID()),ci.getName());
//			listCN.add(cn);
//		}
//		return listCN;
		return customerInfoDao.findByName(name);
	}

	@Override
	public List<CustomerInfo> getCustomerListByTelCode(String TelCode) {
//		List<CustomerInfo> listci = customerInfoDao.findByTelCode(TelCode);
//		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
//		for(CustomerInfo ci : listci){
//			CodeNamePair cn = new CodeNamePair(String.valueOf(ci.getID()),ci.getName());
//			listCN.add(cn);
//		}
//		return listCN;
		return customerInfoDao.findByTelCode(TelCode);
	}

	@Override
	public Response getCustomerInfo(String id) {
		CustomerInfo cstm = customerInfoDao.get(Integer.parseInt(id));
//		try{
//			cstm.setRegionString(regionDao.getRegionNameByID(cstm.getRegionCode()));	//这部分功能放到DAO里去了
//		}catch(Exception e){}
		return Response.ok(cstm).header("EntityClass", "CustomerInfo").build();
	}

	@Override
	public Response deleteCustomerInfo(int id) {
		customerInfoDao.removeById(id);
		return Response.ok("Deleted").header("EntityClass", "D_CustomerInfo").build();
	}

	@Override
	public Response saveCustomerInfo(CustomerInfo obj) {
		System.out.println("运行了saveCustomerInfo方法");
		try {
			customerInfoDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_CustomerInfo").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@Override
	public List<CodeNamePair> getProvinceList() {
		List<Region> listrg = regionDao.getProvinceList();
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for (Region rg : listrg) {
			CodeNamePair cn = new CodeNamePair(rg.getORMID(), rg.getPrv());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public List<CodeNamePair> getCityList(String prv) {
		List<Region> listrg = regionDao.getCityList(prv);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for (Region rg : listrg) {
			CodeNamePair cn = new CodeNamePair(rg.getORMID(), rg.getCty());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public List<CodeNamePair> getTownList(String city) {
		List<Region> listrg = regionDao.getTownList(city);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for (Region rg : listrg) {
			CodeNamePair cn = new CodeNamePair(rg.getORMID(), rg.getTwn());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public String getRegionString(String code) {
		return regionDao.getRegionNameByID(code);
	}

	@Override
	public Region getRegion(String code) {
		return regionDao.getFullNameRegionByID(code);
	}

	@Override
	public void CreateWorkSession(int uid) {
		// TODO Auto-generated method stub

	}

	//whb
	@Override
	public Response doLogin(String telcode, String pwd) {
		List<UserInfo> listui = userInfoDao.findByTelCode(telcode);
		if(listui.size()==0)
			return Response.ok("该用户不存在").header("EntityClass", "N_UserInfo").build();
		if (listui.get(0).getPWD().equals(MD5.MD5Encode(pwd)))
			return Response.ok(listui.get(0)).header("EntityClass", "UserInfo").build();
		else
			return Response.ok("该用户不存在").header("EntityClass", "N_UserInfo").build();
	}


	@Override
	public void doLogOut(int uid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void RefreshSessionList() {
		// TODO Auto-generated method stub

	}

	// tzx
	@Override
	public Response saveUserInfo(UserInfo obj) {
		System.out.println("调用了saveUserInfo方法");

		try {
			userInfoDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_UserInfo").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}

	}

	// tzx
	@Override
	public List<UserInfo> getAllUserInfo() {
		return userInfoDao.getAll();
	}

	// ldq
	@Override
	public UserInfo getUserInfo(int uid) {
		return userInfoDao.get(uid);
	}

	//whb
	@Override
	public Response doRegister(String name,String telCode, String pwd, String dptid,Integer urull) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(name);
		userInfo.setTelCode(telCode);
		userInfo.setPWD(MD5.MD5Encode(pwd));
		userInfo.setDptID(dptid);
		userInfo.setURull(urull);
		userInfo.setStatus(0);
		List<UserInfo> userList = userInfoDao.findByTelCode(telCode);
		if (userList.size() != 0) {
			return Response.ok("该用户已注册").header("EntityClass", "NR_UserInfo").build();
		}
		try {
			userInfoDao.save(userInfo);
			return Response.ok(userInfo).header("EntityClass", "R_UserInfo").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	//whb
			@Override
			public Response resetPWD(String telcode,String newpwd) {
				UserInfo userInfo = new UserInfo();
				List<UserInfo> listui = userInfoDao.findByTelCode(telcode);
				if(listui.size()==0)
					return Response.ok("该用户不存在").header("EntityClass", "NP_UserInfo").build();
				userInfo = listui.get(0);
				userInfo.setPWD(MD5.MD5Encode(newpwd));
				try {
					userInfoDao.save(userInfo);
					return Response.ok(userInfo).header("EntityClass", "P_UserInfo").build();
				} catch (Exception e) {
					e.printStackTrace();
					return Response.serverError().entity(e.getMessage()).build();
				}
			}

	
	//lyy 修改
		@Override
		public TransNode getNode(String code) {
			TransNode ts = null;
			try {
				ts = transNodeDao.get(code);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			return ts;
		}
		

		//lyy 修改 
		//根据所在位置和类型搜索网点信息
		@Override
		public List<TransNode> getNodesList(String regionCode, int type) {
			
			List<TransNode> listTS = transNodeDao.findByRegionCode(regionCode);  //根据regionCode得到附近网点的列表
			for(TransNode transNode: listTS) {
				if(transNode.getNodeType() != type) {
					listTS.remove(transNode);
				}
			}
			return listTS;
		}
		
		//lyy 新增 获取网点信息
		@Override
		public List<TransNode> getTransNodeByNodeName(String nodeName) {
			// TODO Auto-generated method stub
			
			return transNodeDao.findByNodeName(nodeName);
		}

		//lyy 新增 获取网点信息
		@Override
		public List<TransNode> getTransNodeById(String id) {
			// TODO Auto-generated method stub
			return transNodeDao.findById(id);
		}

		//lyy 新增 获取网点信息
		@Override
		public List<TransNode> getTransNodeByRegion(String region) {
			// TODO Auto-generated method stub
			return transNodeDao.findByRegionCode(region);
		}
		
		//lyy 新增userInfo接口
		
		public Response getUserInfoById(Integer uid) {
			UserInfo userInfo = null;
			try {
				 userInfo = userInfoDao.get(uid);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return Response.ok(userInfo).header("EntityClass", "UserInfo").build(); 
		}
		
		//lyy 新增getManagerByNodeID接口
		public Response getManagerByNodeID(String nodeID) {
			UserInfo userInfo = null;
			List<UserInfo> list = null;
			try {
				 list=userInfoDao.getUserInfoByNodeID(nodeID);
				 for(UserInfo userInfo2:list) {
					 if(userInfo2.getURull() == 3) { //如果是网点的负责人
						 return Response.ok(userInfo2).header("EntityClass", "UserInfo").build();
					 }
				 }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//没有找到负责人
			return Response.ok(userInfo).header("EntityClass", "N_UserInfo").build(); 
		}
		
		//lyy 新增 
		public Response saveUsersPackage(UsersPackage usersPackage) {
			System.out.println("执行了这个方法！saveUsersPackage"+usersPackage.toString());
			try {
				usersPackageDao.save(usersPackage);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return Response.serverError().entity(e.getMessage()).build(); 
			}
			return Response.ok("添加成功！").header("EntityClass", "UsersPackage").build();
		}
		
		//lyy 新增 
		@Override
		public Response getOneTransNodeById(String id) {
			TransNode transNode = null;
			try {
				transNode = transNodeDao.get(id);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return Response.serverError().entity(e.getMessage()).build(); 
			}
			if(transNode != null) return Response.ok(transNode).header("EntityClass", "E_TransNode").build();
			else {
				return Response.ok(transNode).header("EntityClass", "N_TransNode").build();
			}
		}
		
		//lyy 新增
		public Response saveUsersPackageList(ListTransPackge listTransPackge,int uid) {
			List<TransPackage> lTransPackages = listTransPackge.getTransPackageList();
			try {
				UserInfo userInfo = userInfoDao.get(uid);
				if(lTransPackages.size() != 0) {
					for(TransPackage transPackage : lTransPackages) {
						UsersPackage usersPackage = new UsersPackage();
						usersPackage.setPkg(transPackage);
						usersPackage.setUserU(userInfo);
						usersPackageDao.save(usersPackage);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				return Response.serverError().entity(e.getMessage()).build(); 
			}
			return Response.ok("修改成功").header("EntityClass", "ListUsersPackage").build();
		}

}
