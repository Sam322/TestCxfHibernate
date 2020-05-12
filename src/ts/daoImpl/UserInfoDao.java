package ts.daoImpl;

import java.util.List;

import org.springframework.util.Assert;

import ts.daoBase.BaseDao;
import ts.model.UserInfo;


public class UserInfoDao extends BaseDao<UserInfo, Integer> {
	public UserInfoDao(){
		super(UserInfo.class);
	}
	public List<UserInfo> findByTelCode(String telCode) {
		return findBy("telCode", telCode, "telCode", true);
	}
	
		//lyy ÐÂÔö
	public List<UserInfo> getUserInfoByNodeID(String nodeID){
		Assert.hasText(nodeID);
		return findBy("dptID", nodeID,"URull", false);
		
	}
	//tzx
	public List<UserInfo> findByName(String name) {
		return findLike("name", name+"%", "telCode", true);
	}
			
	//tzx
	public List<UserInfo> findByDptID(String DptID) {
		return findLike("dptID", DptID, "telCode", true);
	}

}
