package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.UserInfo;


public class UserInfoDao extends BaseDao<UserInfo, Integer> {
	public UserInfoDao(){
		super(UserInfo.class);
	}
	public List<UserInfo> findByTelCode(String telCode) {
		return findBy("telCode", telCode, "telCode", true);
	}
}
